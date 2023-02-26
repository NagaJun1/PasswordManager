package com.example.passwordmanager.component;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityBase extends AppCompatActivity {

    /**
     * Toast.makeText().show()
     * @param text 通知するテキスト
     */
    public void ToastShow(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * クリップボードテキストを貼り付け
     * @param label ラベル
     * @param text テキスト
     */
    public void setClipboard(CharSequence label, CharSequence text) {
        ClipData clipData = ClipData.newPlainText(label, text);
        ClipboardManager clipboard = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(clipData);
    }
}
