package com.example.passwordmanager.events;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.passwordmanager.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * ボタン押下で、パスワード情報のバックアップ処理（ローカルファイルをコピー）
 */
public class CreateBackupEvent implements View.OnClickListener {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        Toast toast;
        String text = "";

        try {
            // コピー元取得
            for (File file : view.getContext().getFilesDir().listFiles()) {
                try (FileReader reader = new FileReader(file)) {
                    BufferedReader bReader = new BufferedReader(reader);
                    text += file.getName() + " = " + bReader.readLine() + "\n";
                }
            }
            ClipboardManager clipboard = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            Util.setClipboard(clipboard, "backup", text);

            toast = Toast.makeText(view.getContext(), "全データをクリップボードに貼付けました", Toast.LENGTH_LONG);
        } catch (Exception e) {
            System.out.println(e.toString());
            toast = Toast.makeText(view.getContext(), "エラー：バックアップファイル生成", Toast.LENGTH_LONG);
        }
        toast.show();
    }
}