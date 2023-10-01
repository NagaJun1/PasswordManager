package com.example.passwordmanager;

import android.content.ClipData;
import android.content.ClipboardManager;

public class Util {
    public static void setClipboard(ClipboardManager manager, CharSequence label, CharSequence text) {
        ClipData clipData = ClipData.newPlainText(label, text);
        manager.setPrimaryClip(clipData);
    }
}
