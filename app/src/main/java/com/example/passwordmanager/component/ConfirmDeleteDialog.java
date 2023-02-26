package com.example.passwordmanager.component;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.passwordmanager.constant.Constant;

import java.io.File;
import java.nio.file.Files;

/**
 * 削除確認ダイアログ
 */
public class ConfirmDeleteDialog extends AlertDialog.Builder {
    /**
     *
     * @param activity AppCompatActivity
     * @param targetKeyword 削除対象のファイル名
     */
    public ConfirmDeleteDialog(@NonNull AppCompatActivity activity,String targetKeyword) {
        super(activity);
        this.setMessage("ファイルを削除してよろしいですか？");

        // 「OK」ボタンを生成
        this.setOkButtonClick(activity, targetKeyword);

        // 「キャンセル」ボタン生成
        this.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
    }

    /**
     * 「OK」ボタン押下で削除実行の処理を追加
     * @param activity 終了する AppCompatActivity
     * @param targetKeyword 削除対象
     */
    private void setOkButtonClick(AppCompatActivity activity,String targetKeyword) {
        this.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                File targetFile = new File(activity.getFilesDir(), targetKeyword + Constant._String.TXT_EXTENSION);

                Toast messageToast;
                if (Files.exists(targetFile.toPath())) {

                    // ファイルを削除
                    if (targetFile.delete()) {
                        messageToast = Toast.makeText(activity, "データを削除しました", Toast.LENGTH_SHORT);
                    } else {
                        messageToast = Toast.makeText(activity, "データ削除に失敗しました", Toast.LENGTH_SHORT);
                    }
                } else {
                    messageToast = Toast.makeText(activity, "削除対象のデータが存在しません", Toast.LENGTH_SHORT);
                }
                messageToast.show();
                activity.finish();
            }
        });
    }
}
