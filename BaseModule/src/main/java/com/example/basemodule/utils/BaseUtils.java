package com.example.basemodule.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * create by zy on 2019/9/25
 * </p>
 */
public class BaseUtils {

    private static Toast toast;

    public static void showShortToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
