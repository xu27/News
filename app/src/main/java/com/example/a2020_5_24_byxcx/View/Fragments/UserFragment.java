package com.example.a2020_5_24_byxcx.View.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.a2020_5_24_byxcx.Base.AppMsgDialog;
import com.example.a2020_5_24_byxcx.Base.Header_imgDialog;
import com.example.a2020_5_24_byxcx.Modle.Dao.CacheDBUtils;
import com.example.a2020_5_24_byxcx.R;
import com.example.a2020_5_24_byxcx.View.CollectionActivity;
import com.example.xcxlibrary.BaseFragment;
import com.example.xcxlibrary.Util.AlbumUtil;
import com.example.xcxlibrary.Util.FileCacheUtils;
import com.example.xcxlibrary.Util.NetUtil;
import com.google.android.material.navigation.NavigationView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UserFragment extends Fragment {
    private static final String TAG = "UserFragment";

    private View view;
    private Unbinder mUnbinder = null;
    private static final int CHOSSE_PHOTO = 1;
    private static final int CAMERA_SHOOTING = 2;

    @BindView(R.id.user_header)
    protected NavigationView head;
    @BindView(R.id.user_menu)
    protected NavigationView menu;
    protected ImageView head_img;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_layout, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        init();
        setListener();
        return view;
    }

    private void init() {
        View headview = head.getHeaderView(0);
        head_img = headview.findViewById(R.id.user_header_img);
        try {
            String path = getActivity().getExternalFilesDir("xcx").getPath();
            String fileName = "user_head";
            File file = new File(path, fileName);
            if (file.exists()) {
                Bitmap bitmap = AlbumUtil.rotateBitmap(AlbumUtil.getBitmap(file),AlbumUtil.getRotateDegree(file.getPath()));
                Bitmap round = AlbumUtil.toRoundBitmap(bitmap);
                head_img.setImageBitmap(round);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setListener() {
        menu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.user_collection:
                        //收藏
                        startActivity(new Intent(UserFragment.this.getActivity(), CollectionActivity.class));
                        break;
                    case R.id.user_appmsg:
                        //应用信息
                        new AppMsgDialog(getActivity()).show();
                        break;
                    case R.id.clear_app:
                        //清除缓存
                        clearCache();
                        CacheDBUtils utils = new CacheDBUtils(UserFragment.this.getContext());
                        boolean b = utils.deleteAll();
                        Log.d(TAG, "onNavigationItemSelected: " + b);
                        break;
                }
                return true;
            }
        });
        head_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    private void openDialog() {
        final Header_imgDialog dialog = new Header_imgDialog(getContext());
        dialog.setListener(new Header_imgDialog.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.header_img_Dialog_check_byDCIM:
                        //相册
                        if (ContextCompat.checkSelfPermission(getContext(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        } else {
                            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                            intent.setType("image/*");
                            startActivityForResult(intent, CHOSSE_PHOTO);
                        }
                        dialog.dismiss();
                        break;
                    case R.id.header_img_Dialog_check_byCAMERA:
                        //相机
                        Uri uri = null;
                        String userhead_path = getActivity().getExternalFilesDir("xcx").getPath();
                        File file = new File(userhead_path, "user_head");
                        //指定保存路径
                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            uri = FileProvider.getUriForFile(getContext(), "com.example.a2020_5_24_byxcx.FileProvider", file);
                        } else {
                            uri = Uri.fromFile(file);
                        }
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, CAMERA_SHOOTING);
                        dialog.dismiss();
                        break;
                    case R.id.header_img_Dialog_check_byCANCEL:
                        //取消
                        dialog.dismiss();
                        break;
                }
            }
        });
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case CHOSSE_PHOTO:
                if (android.os.Build.VERSION.SDK_INT >= 29) {
                    Log.d(TAG, "onActivityResult: ");
                    String imgPath = AlbumUtil.handleImageOnKitKat(getContext(), data);
                    setHeadby29(data, imgPath);
                } else {
                    String imgPath = AlbumUtil.handleImageOnKitKat(getContext(), data);
                    setHead(imgPath);
                }
                break;
            case CAMERA_SHOOTING:
                /*Log.d(TAG, "onActivityResult: ");
                if(android.os.Build.VERSION.SDK_INT>=29){
                    Log.d(TAG, "onActivityResult: ");
                    setHeadby29(data);
                }else {
                    String imgPath = AlbumUtil.handleImageOnKitKat(getContext(), data);
                    setHead(imgPath);
                }*/
                String userhead_path = getActivity().getExternalFilesDir("xcx").getPath();
                File file = new File(userhead_path, "user_head");
                setHeadbyFile(file);
                break;
            default:
                break;
        }
    }

    private void clearCache() {
        try {
            String cacheSize = FileCacheUtils.getCacheSize(this.getActivity().getExternalCacheDir());
            FileCacheUtils.cleanInternalCache(this.getActivity());
            Toast.makeText(this.getActivity(), "本次清理" + cacheSize + "缓存", Toast.LENGTH_SHORT).show();
            Glide.get(this.getActivity()).clearDiskCache();
            Glide.get(this.getActivity()).clearMemory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    private void setHead(String imgPath) {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            if (imgPath != null) {
                Bitmap bitmap = AlbumUtil.rotateBitmap(BitmapFactory.decodeFile(imgPath)
                        , AlbumUtil.getRotateDegree(imgPath));
                Bitmap round = AlbumUtil.toRoundBitmap(bitmap);
                try {
                    String path = getActivity().getExternalFilesDir("xcx").getPath();
                    File file = new File(path, "user_head");
                    round.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                head_img.setImageBitmap(round);
            } else {
                Toast.makeText(getContext(), "failed to get image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setHeadby29(Intent data, String path) {
        Bitmap bitmap = AlbumUtil.rotateBitmap(AlbumUtil.getBitmap(data.getData(), getContext())
                , AlbumUtil.getRotateDegree(path));
        Bitmap round = AlbumUtil.toRoundBitmap(bitmap);
        bitmap.recycle();
        
        head_img.setImageBitmap(round);
        try {
            String userhead_path = getActivity().getExternalFilesDir("xcx").getPath();
            File file = new File(userhead_path, "user_head");
            round.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setHeadbyFile(File file) {
        Bitmap bitmap = AlbumUtil.rotateBitmap(AlbumUtil.getBitmap(file)
                , AlbumUtil.getRotateDegree(file.getPath()));
        Bitmap round = AlbumUtil.toRoundBitmap(bitmap);
        head_img.setImageBitmap(round);
        bitmap.recycle();
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
