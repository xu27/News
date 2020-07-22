package com.example.xcxlibrary.parser;

import java.io.IOException;

import okhttp3.Response;

public class TextPrarser extends BaseParser {
    @Override
    public String parse(Response response) throws IOException {

        if (response.isSuccessful()) {
            return response.body().string();
        }

        return null;
    }
}
