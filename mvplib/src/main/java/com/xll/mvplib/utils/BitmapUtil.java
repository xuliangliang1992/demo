package com.xll.mvplib.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * bitmap 处理工具类
 *
 * @author xll
 * @date 2018/1/1
 */
public class BitmapUtil {
    /**
     * 将bytes转换成bitmap
     * @param bytes bytes
     * @return bitmap
     */
    public static Bitmap byte2Bitmap(byte[] bytes){
        Logger.t(BitmapUtil.class.getSimpleName()).i("bytes length = " + bytes.length);
        if (bytes.length != 0){
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return null;
    }

    /**
     * 将bytes转换成指定宽高的bitmap 一遍用于压缩图片
     * @param bytes bytes[]
     * @param width 指定款
     * @param height 指定高
     * @return bitmap
     */
    public static Bitmap bytes2Bitmap(byte[] bytes, int width, int height){
        if (bytes.length != 0){
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
            opts.inJustDecodeBounds = false;
            int scaleX = opts.outWidth / width;
            int scaleY = opts.outHeight / height;
            int scale = scaleX > scaleY ? scaleX : scaleY;
            opts.inSampleSize = scale;
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
        }
        return null;
    }

    public static Bitmap inputStream2Bitmap(InputStream inputStream){
        return BitmapFactory.decodeStream(inputStream);
    }

    public static InputStream Bitmap2InputStream(Bitmap bitmap, int quality){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    /**
     * 将View转成Bitmap
     * @param view view
     * @return btmap
     */
    public static Bitmap getBitmapFromView(View view){
        Bitmap bmp = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Drawable drawable = view.getBackground();
        if (drawable != null){
            drawable.draw(canvas);
        }else{
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return bmp;
    }

    /**
     * 将View转成Bitmap
     * @param view view
     * @return btmap
     */
    public static Bitmap getBitmapFromView(View view, int width, int height){
        if (width <=0 || height<=0 ){
            width = view.getWidth();
            height = view.getHeight();
        }
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Drawable drawable = view.getBackground();
        if (drawable != null){
            drawable.draw(canvas);
        }else{
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return bmp;
    }

    /**
     * view 转 bitmap
     * @param view view
     * @return bitmap
     */
    public static Bitmap viewToBitmap(View view){
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

    public static Bitmap loadBitmapFromView(View view){
        if (view == null){
            return null;
        }
        Bitmap screenShot;
        screenShot = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(screenShot);
        canvas.translate(-view.getScrollX(), -view.getScrollY());
        view.draw(canvas);
        return screenShot;
    }

    /**
     * 将bitmap 转为 文件
     * @param bitmap bitmap
     * @param fileName file
     * @return 是否成功
     */
    public static boolean saveBitmap2File(Bitmap bitmap, String fileName){
        File file = FileUtils.createFile(fileName, FileUtils.FILE_TYPE_IMAGE);
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream outputStream = null;
        try {
            if (file != null){
                outputStream = new FileOutputStream(file);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap.compress(format, quality, outputStream);
    }

    /**
     * 缩放bitmap
     * @param bitmap bitmap
     * @param width 目标宽
     * @return reBitmap
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, int width){
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        float temp = (float) h / (float) w;
        int height = (int)(width * temp);

        float scaleWidth = (float) width / w;
        float scaleHeight = (float)height / h;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap reBitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return reBitmap;
    }
    /**
     * 按大小缩放图片
     * @param srcPath 图片路径
     * @return
     */
    public static Bitmap getBitmapFromFile(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww)
        {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        }
        else if (w < h && h > hh)
        {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        newOpts.inJustDecodeBounds = false;
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    // 图片质量压缩
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100)
        { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public static byte[] bitmap2bytes(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 对data进行Base64编码,得到字符串
     *
     * @param data
     * @return string编码后
     */
    public static String GetImageStr(byte[] data) {
        if (data == null)
            return null;
        return Base64.encodeToString(data, Base64.DEFAULT);
//        return BASE64Encoder.encode(data);
    }

    /**
     * 读取imgFilePath路径下的文件，并对其进行Base64编码处理
     *
     * @param imgFilePath
     * @return string编码后
     */
    public static String getImageStrWithPath(String imgFilePath) {
        if (imgFilePath == null || ("").equals(imgFilePath)) {
            return null;
        }
        // 读取图片字节数组
        byte[] data = null;
        InputStream in = null;
        try {
            in = new FileInputStream(imgFilePath);
            int count = in.available();
            if (count <= 0) {// 没有可用字节
                return null;
            }
            data = new byte[count];
            in.read(data);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        // 对字节数组Base64编码
        return GetImageStr(data);
    }

    /**
     * 字符转成图片
     * @param photoData
     * @return
     */
    public static Bitmap BytesYoBitmap(String photoData){
        byte[] decodedString = Base64.decode(photoData, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight){
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize =1;

        if(height > reqHeight || width > reqWidth){
            final int halfHeight = height /2;
            final int halfWidth = width /2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while((halfHeight / inSampleSize)> reqHeight
                    &&(halfWidth / inSampleSize)> reqWidth){
                inSampleSize *=2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight){

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options =new BitmapFactory.Options();
        options.inJustDecodeBounds =true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds =false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static Bitmap decodeSampledBitmapFromFile(String filePath, int reqWidth, int reqHeight){

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options =new BitmapFactory.Options();
        options.inJustDecodeBounds =true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds =false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static boolean cancelPotentialWork(String filePath, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final String path = bitmapWorkerTask.filePath;
            // If bitmapData is not yet set or it differs from the new data
            if (TextUtils.isEmpty(path) || !path.equals(filePath)) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }

    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTaskWeakReference();
            }
        }
        return null;
    }

    /**
     * bitmap转为base64
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
