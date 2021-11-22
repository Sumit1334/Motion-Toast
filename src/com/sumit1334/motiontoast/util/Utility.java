package com.sumit1334.motiontoast.util;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.sumit1334.motiontoast.MotionToast;

public class Utility {
    private final String TAG = "Motion Toast";
    private final Form form;
    private final MotionToast motionToast;

    public Utility(Form form, MotionToast motionToast) {
        this.form = form;
        this.motionToast = motionToast;
    }

    public int parseColor(String hex) {
        return Color.parseColor(hex);
    }

    public BitmapDrawable getIcon(String path) {
        try {
            return MediaUtil.getBitmapDrawable(this.form, path);
        } catch (Exception e) {
            Log.e(TAG, "getIcon: " + e.getMessage());
            motionToast.ErrorOccurred(e.getMessage());
            return null;
        }
    }
}
