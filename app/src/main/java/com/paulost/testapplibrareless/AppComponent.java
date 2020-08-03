package com.paulost.testapplibrareless;

import android.app.Application;

import com.paulost.testapplibrareless.data.di.BaseModule;
import com.paulost.testapplibrareless.data.di.RepoBindModule;

public interface AppComponent {

    BaseModule provideBaseModule();

    RepoBindModule provideRepoBindModule();

}
