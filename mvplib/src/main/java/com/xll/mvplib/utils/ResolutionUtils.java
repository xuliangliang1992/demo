package com.xll.mvplib.utils;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * 主要是用来控制输入的金额不能超过小数点后两位
 *
 * @author xll
 * @date 2018/1/1
 */
public class ResolutionUtils {
    private final static String rootPath = "E:\\layout\\values-{0}x{1}\\";

    private final static float dw = 720f;
    private final static float dh = 1280f;

    private final static String WTemplate = "<dimen name=\"x{0}\">{1}px</dimen>\n";
    private final static String HTemplate = "<dimen name=\"y{0}\">{1}px</dimen>\n";

    public static void main(String[] args) {
//        makeString(320, 480);
//        makeString(480, 782);
//        makeString(480, 800);
//        makeString(480, 854);
//        makeString(540, 888);
//        makeString(540, 960);
//        makeString(600, 1024);
//        makeString(640, 960);
//        makeString(640, 1136);
        makeString(1920, 1200);
//        makeString(720, 1184);
//        makeString(720, 1196);
//        makeString(720, 1200);
//        makeString(720, 1208);
//        makeString(720, 1280);
//        makeString(600, 1024);
//        makeString(768, 1024);
//        makeString(768, 1184);
//        makeString(768, 1196);
//        makeString(768, 1280);
//        makeString(800, 1232);
//        makeString(800, 1280);
//        makeString(900, 1392);
//        makeString(1080, 1776);
//        makeString(1080, 1794);
//        makeString(1080, 1812);
//        makeString(1080, 1824);
//        makeString(1080, 1800);
//        makeString(1080, 1836);
//        makeString(1080, 1920);
//        makeString(1152, 1920);
//        makeString(1200, 1830);
//        makeString(1200, 1920);
//        makeString(1440, 2392);
//        makeString(1440, 2560);
//        makeString(1536, 2048);
//        makeString(1536, 2560);
//        makeString(1600, 2560);
//        makeString(1600, 2438);
    }

    public static void makeString(int w, int h) {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb.append("<resources>");
        float cellw = w / dw;
        for (int i = 1; i < dw; i++) {
            sb.append(WTemplate.replace("{0}", i + "").replace("{1}",
                    change(cellw * i) + ""));
        }
        sb.append(WTemplate.replace("{0}", "720").replace("{1}", w + ""));
        sb.append("</resources>");

        StringBuffer sb2 = new StringBuffer();
        sb2.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb2.append("<resources>");
        float cellh = h / dh;
        for (int i = 1; i < dh; i++) {
            sb2.append(HTemplate.replace("{0}", i + "").replace("{1}",
                    change(cellh * i) + ""));
        }
        sb2.append(HTemplate.replace("{0}", "1280").replace("{1}", h + ""));
        sb2.append("</resources>");

        String path = rootPath.replace("{0}", h + "").replace("{1}", w + "");
        File rootFile = new File(path);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        File layxFile = new File(path + "lay_x.xml");
        File layyFile = new File(path + "lay_y.xml");
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(layxFile));
            pw.print(sb.toString());
            pw.close();
            pw = new PrintWriter(new FileOutputStream(layyFile));
            pw.print(sb2.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            Log.e(ResolutionUtils.class.getSimpleName(), e.getMessage());
        }

    }

    public static float change(float a) {
        int temp = (int) (a * 100);
        return temp / 100f;
    }
}
