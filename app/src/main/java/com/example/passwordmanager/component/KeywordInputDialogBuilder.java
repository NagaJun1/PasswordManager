package com.example.passwordmanager.component;

import android.content.DialogInterface;
import android.text.Editable;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

/**
 * 新規パスワード生成時にキーワードを入力するためのダイアログ
 */
public class KeywordInputDialogBuilder extends AlertDialog.Builder {
    /**
     * ダイアログ内に表示する、キーワードの入力項目
     */
    private EditText ThisEditText;

    public KeywordInputDialogBuilder(@NonNull  AppCompatActivity activity) {
        super(activity);

        this.setMessage("パスワードに紐づけるキーワードを入力してください");

        // ダイアログ内に表示する、キーワードの入力項目
        this.ThisEditText = new AppCompatEditText(activity);
        this.setView(this.ThisEditText);

        this.setCancelEvent(activity);
    }

    /**
     * キーワード入力項目のテキストを取得
     * @return パスワードに紐づけるキーワード
     */
    public Editable getEditText() {
        return this.ThisEditText.getText();
    }

    /**
     * 「OK」ボタン押下以外でダイアログが閉じる場合、表示画面を閉じる
     * @param activity 終了する activity
     */
    private void setCancelEvent(AppCompatActivity activity) {
        this.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Toast.makeText(activity, "パスワードを保存するには、「OK」を押下してください", Toast.LENGTH_SHORT).show();
                activity.finish();
            }
        });
    }
}
