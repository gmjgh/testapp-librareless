package com.paulost.testapplibrareless.data.base;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.net.ssl.HttpsURLConnection;

// I used this Future based approach because I don't like AsyncTasks.
public abstract class ImageRequest implements Callable<InputStream> {

    protected ResponseCallback<Bitmap> responseCallback;

    public ImageRequest(ResponseCallback<Bitmap> responseCallback) {
        this.responseCallback = responseCallback;
    }

    protected abstract String getUrlString();

    protected Future<InputStream> execute(ExecutorService executorService) {
        return executorService.submit(this);
    }

    public void loadData(ExecutorService executorService){
        try {
            InputStream data = execute(executorService).get();
            Log.d("Image loaded ", "success");
            Bitmap result = BitmapFactory.decodeStream(data);
            success(result);
        } catch (Exception e) {
            Log.d("Image load failed", e.getMessage());
            failure(e);
        }
    }

    protected void success(Bitmap data) {
        new Handler(Looper.getMainLooper()).post(() -> responseCallback.success(data));
    }

    protected void failure(Exception exception) {
        new Handler(Looper.getMainLooper()).post(() -> responseCallback.failure(exception));
    }

    @Override
    public InputStream call() throws Exception {
        return sendRequest();
    }

    public InputStream sendRequest() throws IOException {
        URL url = new URL(getUrlString());
        InputStream stream = null;
        HttpURLConnection connection = null;
        InputStream result = null;
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
                result = stream;
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;

    }
}
