package com.example.a2020_5_24_byxcx.View.Fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainFragmentAdapter extends FragmentPagerAdapter {

    private NewsFragment newsFragment;
    private VideoFragment videoFragment;
    private UserFragment userFragment;


    private int count;

    public MainFragmentAdapter(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
        newsFragment = new NewsFragment();
        videoFragment = new VideoFragment();
        userFragment = new UserFragment();
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return newsFragment;
        }
        if (i == 1) {
            return videoFragment;
        }
        if (i == 2) {
            return userFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return count;
    }
}
