package com.zhaoqy.self.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.widget.Toast;

import org.apache.http.util.EncodingUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.math.BigDecimal;

@SuppressWarnings("deprecation")
public class FileUtils {

    public static final String BASE_PATH = "/Self";
    public static final String PATH = "/Self/";

    public static void initFile() {
        File file = new File(Environment.getExternalStorageDirectory()
                + FileUtils.BASE_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }

        File file1 = new File(Environment.getExternalStorageDirectory()
                + FileUtils.PATH + "tempFile");
        if (!file1.exists()) {
            file1.mkdirs();
        }
    }

    public static String getFileStorePath() {
        return Environment.getExternalStorageDirectory() + PATH;
    }

    public static String getTmpCacheFilePath() {
        return Environment.getExternalStorageDirectory() + PATH + "tempFile";
    }

    /**
     * @param fileName : 本地文件名
     * @Title: getFromAssets
     * @Description: 读取本地文件信息(该方法可以换段落)
     * @return: String
     */
    public static String getFromAssets(Context context, String fileName) {
        String text = "";
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            int lenght = in.available(); // 获取文件的字节数
            byte[] buffer = new byte[lenght]; // 创建byte数组
            in.read(buffer); // 将文件中的数据读到byte数组中
            text = EncodingUtils.getString(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }


    /**
     * @param file
     * @Title: delete
     * @Description: 删除文件
     * @return: void
     */
    public static void delete(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }
            file.delete();
        }
    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri)
            return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * the traditional io way
     *
     * @param filename
     * @throws IOException
     */
    public static byte[] toByteArray(String filename) throws IOException {

        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }

    public static byte[] fileToByte(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                long fileSize = file.length();
                if (fileSize > 2147483647L) {
                    return null;
                }
                FileInputStream fi = new FileInputStream(file);
                byte[] buffer = new byte[(int) fileSize];
                int offset = 0;
                int numRead = 0;
                while ((offset < buffer.length) && (
                        (numRead = fi.read(buffer, offset, buffer.length -
                                offset)) >= 0)) {
                    offset += numRead;
                }

                if (offset != buffer.length) {
                    fi.close();
                    return null;
                }
                fi.close();
                return buffer;
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] fileToByte(String path, int byteCount) {
        try {
            File file = new File(path);
            if (file.exists()) {
                long fileSize = file.length();
                if (fileSize > 2147483647L) {
                    return null;
                }
                if (byteCount > fileSize) {
                    return null;
                }
                FileInputStream fi = new FileInputStream(file);
                byte[] buffer = new byte[byteCount];
                int offset = 0;
                int numRead = 0;
                while ((offset < buffer.length) && (
                        (numRead = fi.read(buffer, offset, buffer.length -
                                offset)) >= 0)) {
                    offset += numRead;
                }

                if (offset != buffer.length) {
                    fi.close();
                    return null;
                }
                fi.close();
                return buffer;
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] fileToByte(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 保存图片到本地
     * @param context
     * @param bitmap
     */

    public static Uri saveBitmapToFile(Context context, String id, Bitmap bitmap){
        String fileName = id + ".jpg";
        File fileDir = new File(getFileStorePath());
        if (!fileDir.exists()){
            fileDir.mkdir();
        }
        File imageFile = new File(fileDir,fileName);
        Uri uri = Uri.fromFile(imageFile);

        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            boolean isCompress = bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            if (isCompress) {
                Toast.makeText(context,"保存妹纸成功n(*≧▽≦*)n",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context,"保存妹纸失败ヽ(≧Д≦)ノ",Toast.LENGTH_SHORT).show();
            }
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context,"保存妹纸失败ヽ(≧Д≦)ノ",Toast.LENGTH_SHORT).show();
        }
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), imageFile.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,uri));
        return uri;
    }

    /**
     * 返回自定文件或文件夹的大小
     *
     * @param f
     * @return
     * @throws Exception
     */
    public static long getFileSizes(File f) throws Exception {// 取得文件大小
        long s = 0;
        if (f.exists()) {
            FileInputStream fis = new FileInputStream(f);
            s = fis.available();
            fis.close();
        } else {
            f.createNewFile();
            System.out.println("文件不存在");
        }
        return s;
    }

    // 递归
    public static long getFileSize(File f) throws Exception// 取得文件夹大小
    {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    public static String formetFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#0.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    public static long getlist(File f) {// 递归求取目录文件个数
        long size = 0;
        File flist[] = f.listFiles();
        size = flist.length;
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getlist(flist[i]);
                size--;
            }
        }
        return size;
    }

    public static String format(long length) {
        if (length < 1024) {
            return "小于1K";
        } else {
            BigDecimal b = new BigDecimal(length / (1024 * 1024.0));
            return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue() + "M";
        }
    }

    private static final String SD_PATH = Environment.getExternalStorageDirectory().getPath();
    public static final String NAME = "self/videorecord";

    public static String getAppPath() {
        StringBuilder sb = new StringBuilder();
        sb.append(SD_PATH);
        sb.append(File.separator);
        sb.append(NAME);
        sb.append(File.separator);
        return sb.toString();
    }

    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else {
                String[] filePaths = file.list();
                for (String path : filePaths) {
                    deleteFile(filePath + File.separator + path);
                }
                file.delete();
            }
        }
    }
}
