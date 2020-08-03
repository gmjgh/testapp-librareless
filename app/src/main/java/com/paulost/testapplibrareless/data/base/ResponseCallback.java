package com.paulost.testapplibrareless.data.base;

public interface ResponseCallback<T> {
    void success(T data);

    void failure(Exception e);
}
