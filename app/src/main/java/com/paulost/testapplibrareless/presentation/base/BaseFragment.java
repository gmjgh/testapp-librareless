package com.paulost.testapplibrareless.presentation.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.paulost.testapplibrareless.BR;

public abstract class BaseFragment<B extends ViewDataBinding, V extends BaseViewModel> extends Fragment implements BaseView {
    protected Activity activityContext;
    protected B binding;

    protected V viewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (provideViewModelClass() != null)
            viewModel = new ViewModelProvider(this).get(provideViewModelClass());
        activityContext = (Activity) context;
        beforeViewInit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewInit(view, savedInstanceState);
        listenDataChanges();
    }

    protected void beforeViewInit() {

    }

    protected void onViewInit(View view, Bundle savedInstanceState) {

    }

    protected void listenDataChanges() {

    }

    @Override
    public int getLayoutRes() {
        return 0;
    }

    @Override
    public void navigateBack() {
        activityContext.onBackPressed();
    }

    @StyleRes
    @Nullable
    protected Integer customTheme() {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = inflater;
        if (customTheme() != null)
            layoutInflater = inflater.cloneInContext(new ContextThemeWrapper(activityContext, customTheme()));
        return setAndBindContentView(layoutInflater, container, savedInstanceState, getLayoutRes());
    }

    protected final View setAndBindContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState, @LayoutRes int layoutResID) {
        binding = DataBindingUtil.inflate(inflater, layoutResID, container, false);
        if (provideViewModelClass() != null)
            binding.setVariable(BR.vm, viewModel);
        binding.executePendingBindings();
        return binding.getRoot();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<V> provideViewModelClass() {
        return null;
    }

    @Override
    public void showError(String message) {
        if (getActivity() != null && getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).showError(message);
    }

    @Override
    public void showProgress() {
        if (getActivity() != null && getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).showProgress();
    }

    @Override
    public void hideProgress() {
        if (getActivity() != null && getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).hideProgress();
    }
}
