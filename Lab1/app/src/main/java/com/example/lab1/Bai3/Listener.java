package com.example.lab1.Bai3;

import android.graphics.Bitmap;

public interface Listener {
    void onImageLoaded(Bitmap bitmap);

    void onError();
}
