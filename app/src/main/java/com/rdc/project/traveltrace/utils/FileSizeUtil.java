package com.rdc.project.traveltrace.utils;

import android.text.format.Formatter;
import android.util.Log;

import com.rdc.project.traveltrace.app.App;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

public class FileSizeUtil {

    public static final int SIZE_TYPE_B = 1;//获取文件大小单位为B的double值
    public static final int SIZE_TYPE_KB = 2;//获取文件大小单位为KB的double值
    public static final int SIZE_TYPE_MB = 3;//获取文件大小单位为MB的double值
    public static final int SIZE_TYPE_GB = 4;//获取文件大小单位为GB的double值

    /**
     * 获取文件指定文件的指定单位的大小
     *
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    public static double getFileOrFilesSize(String filePath, int sizeType) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("获取文件大小", "获取失败!");
        }
        return FormatFileSize(blockSize, sizeType);

    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     *
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    public static String getAutoFileOrFilesSize(String filePath) {
        File file = new File(filePath);
        long blockSize = 0;
        try {
            if (file.isDirectory()) {
                blockSize = getFileSizes(file);
            } else {
                blockSize = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Formatter.formatFileSize(App.getAppContext(), blockSize);
    }

    public static String getAutoFileOrFilesSize(String... filePaths) {
        long totalSize = 0;
        for (String filePath : filePaths) {
            File file = new File(filePath);
            long blockSize = 0;
            try {
                if (file.isDirectory()) {
                    blockSize = getFileSizes(file);
                } else {
                    blockSize = getFileSize(file);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            totalSize = totalSize + blockSize;
        }
        return Formatter.formatFileSize(App.getAppContext(), totalSize);
    }

    /**
     * 获取指定文件大小
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
            fis.close();
        } else {
            boolean success = file.createNewFile();
            Log.e("获取文件大小", "文件不存在!" + success);
        }
        return size;
    }


    /**
     * 获取指定文件夹
     */
    private static long getFileSizes(File f) throws Exception {
        long size = 0;
        File first[] = f.listFiles();
        for (File aFirst : first) {
            if (aFirst.isDirectory()) {
                size = size + getFileSizes(aFirst);
            } else {
                size = size + getFileSize(aFirst);
            }
        }
        return size;
    }

    /**
     * 转换文件大小,指定转换的类型
     */
    private static double FormatFileSize(long fileS, int sizeType) {
        DecimalFormat df = new DecimalFormat("#.00");
        double fileSizeLong = 0;
        switch (sizeType) {
            case SIZE_TYPE_B:
                fileSizeLong = Double.valueOf(df.format((double) fileS));
                break;
            case SIZE_TYPE_KB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
                break;
            case SIZE_TYPE_MB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
                break;
            case SIZE_TYPE_GB:
                fileSizeLong = Double.valueOf(df.format((double) fileS / 1073741824));
                break;
            default:
                break;
        }
        return fileSizeLong;
    }
}
