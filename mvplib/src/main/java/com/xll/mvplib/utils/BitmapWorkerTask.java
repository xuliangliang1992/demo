package com.xll.mvplib.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * 异步加载图片
 *
 * @author xll
 * @date 2018/1/1
 */
public class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

    private WeakReference<ImageView> imageViewReference;
    public String filePath;
    private int type = 0;

    public BitmapWorkerTask(int type, ImageView imageView) {
        this.type = type;
        imageViewReference = new WeakReference<>(imageView);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        filePath = params[0];
        if (type == 0) {
            return BitmapUtil.decodeSampledBitmapFromFile(filePath, 140, 140);
        } else if (type == 1) {//图片预览
            return BitmapUtil.decodeSampledBitmapFromFile(filePath, 480, 400);
        }
        return BitmapUtil.decodeSampledBitmapFromFile(filePath, 140, 140);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (imageViewReference != null && bitmap != null) {
            final ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
