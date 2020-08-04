package com.paulost.testapplibrareless;

import android.app.Application;
import android.content.Context;

import com.paulost.testapplibrareless.data.di.BaseModule;
import com.paulost.testapplibrareless.data.di.RepoBindModule;

public class App extends Application implements AppComponent {

    private static App instance;

    public static Context getContext() {
        return instance;
    }

    public static App get() {
        return (App) getContext();
    }

    private BaseModule baseModule;

    private RepoBindModule repoBindModule;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        baseModule = new BaseModule();
        repoBindModule = new RepoBindModule(baseModule);
    }

    @Override
    public BaseModule provideBaseModule() {
        return baseModule;
    }

    @Override
    public RepoBindModule provideRepoBindModule() {
        return repoBindModule;
    }
}
