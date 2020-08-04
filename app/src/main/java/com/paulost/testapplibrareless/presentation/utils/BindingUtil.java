package com.paulost.testapplibrareless.presentation.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class BindingUtil {
    @BindingAdapter("bitmap")
    public static void setBitmap(ImageView view, Bitmap bitmap){
        view.setImageBitmap(bitmap);
    }
}
