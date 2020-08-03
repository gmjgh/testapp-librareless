package com.paulost.testapplibrareless.data.base;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.net.ssl.HttpsURLConnection;

public abstract class HttpRequest<T> implements Callable<String> {

    protected ResponseCallback<T> responseCallback;

    public HttpRequest(ResponseCallback<T> responseCallback) {
        this.responseCallback = responseCallback;
    }

    protected abstract String getUrlString();

    protected Future<String> execute(ExecutorService executorService) {
        return executorService.submit(this);
    }

    public abstract void send(ExecutorService executorService);

    protected void success(T data) {
        new Handler(Looper.getMainLooper()).post(() -> responseCallback.success(data));
    }

    protected void failure(Exception exception) {
        new Handler(Looper.getMainLooper()).post(() -> responseCallback.failure(exception));
    }

    @Override
    public String call() throws Exception {
        return sendRequest();
    }

    public String sendRequest() {
        try {
            URL url = new URL(getUrlString());
            InputStream stream = null;
            HttpURLConnection connection = null;
            String result = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(3000);
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode != HttpsURLConnection.HTTP_OK) {
                    throw new IOException("HTTP error code: " + responseCode);
                }
                stream = connection.getInputStream();
                if (stream != null) {
                    // Converts Stream to String with max length of 500.
                    result = readStream(stream, 500);
                }
            } finally {
                // Close Stream and disconnect HTTPS connection.
                if (stream != null) {
                    stream.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        } catch (Exception e) {
            return null;
        }

    }

    public String readStream(InputStream stream, int maxReadSize)
            throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] rawBuffer = new char[maxReadSize];
        int readSize;
        StringBuffer buffer = new StringBuffer();
        while (((readSize = reader.read(rawBuffer)) != -1) && maxReadSize > 0) {
            if (readSize > maxReadSize) {
                readSize = maxReadSize;
            }
            buffer.append(rawBuffer, 0, readSize);
            maxReadSize -= readSize;
        }
        return buffer.toString();
    }

}
