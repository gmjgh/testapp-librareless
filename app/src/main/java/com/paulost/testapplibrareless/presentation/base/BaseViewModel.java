package com.paulost.testapplibrareless.presentation.base;

import androidx.lifecycle.AndroidViewModel;

import com.paulost.testapplibrareless.App;

public class BaseViewModel extends AndroidViewModel {
    public BaseViewModel() {
        super(App.get());
    }
}
