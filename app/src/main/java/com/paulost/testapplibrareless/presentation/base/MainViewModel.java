package com.paulost.testapplibrareless.presentation.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;


public class MainViewModel extends AndroidViewModel {

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public ObservableField<Boolean> progress = new ObservableField<>(false);
}
