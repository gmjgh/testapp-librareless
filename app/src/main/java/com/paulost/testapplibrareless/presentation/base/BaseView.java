package com.paulost.testapplibrareless.presentation.base;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

public interface BaseView {

    @Nullable
    <V extends BaseViewModel> Class<V> provideViewModelClass();

    @LayoutRes
    int getLayoutRes();

    void navigateBack();

}
