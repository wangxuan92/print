package io.kuban.print.util;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Created by wangxuan on 17/5/2.
 */

public class Utils {
    //防止控件被重复点击
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
    /**
     * @param type
     * @return 以MB为单位
     */
    public static String memorySize(String type) {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize;
        long totalBlocks;
        long availableBlocks;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
            totalBlocks = stat.getBlockCountLong();
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            blockSize = stat.getBlockSize();
            totalBlocks = stat.getBlockCount();
            availableBlocks = stat.getAvailableBlocks();
        }
        String totalText = "";
        if (type.equals("total")) {
            totalText = ((blockSize * totalBlocks) / (1024 * 1024)) + "";
        } else if (type.equals("available")) {
            totalText = ((blockSize * availableBlocks) / (1024 * 1024)) + "";
        }
        return totalText;
    }
}
