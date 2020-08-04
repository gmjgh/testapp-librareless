package com.paulost.testapplibrareless.presentation.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.paulost.testapplibrareless.BR;
import com.paulost.testapplibrareless.R;

public class BaseActivity extends FragmentActivity implements BaseView {

    private static final String MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT";

    protected ViewDataBinding binding;

    ViewModelProvider.Factory viewModelFactory;

    protected MainViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        setAndBindContentView(savedInstanceState);
    }

    @Nullable
    @Override
    public <V extends BaseViewModel> Class<V> provideViewModelClass() {
        return null;
    }

    public MainViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_base;
    }

    @Override
    public void navigateBack() {
        onBackPressed();
    }

    private void setAndBindContentView(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, getLayoutRes());
        if (provideViewModelClass() != null)
            binding.setVariable(BR.vm, viewModel);
        binding.executePendingBindings();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
        viewModel.progress = true;
    }

    @Override
    public void hideProgress() {
        viewModel.progress = false;
    }
}
