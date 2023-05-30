package com.example.passwordmanager.component;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.passwordmanager.Util;

public class ActivityBase extends AppCompatActivity {

    /**
     * Toast.makeText().show()
     *
     * @param text 通知するテキスト
     */
    public void ToastShow(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * クリップボードテキストを貼り付け
     *
     * @param label ラベル
     * @param text  テキスト
     */
    protected void setClipboard(CharSequence label, CharSequence text) {
        ClipboardManager clipboard = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        Util.setClipboard(clipboard, label, text);
    }
}
