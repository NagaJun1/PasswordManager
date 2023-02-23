package com.example.passwordmanager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.passwordmanager.constant.Constant;
import com.google.android.material.snackbar.Snackbar;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // パスワード表示画面
        this.setContentView(R.layout.activity_sub);

        //　画面上にパスワードを表示
        this.setPassword();

        // 削除ボタン押下時のイベントを設定
        this.setDeleteButtonClickEvent();

        // 「コピー」ボタン押下時イベントを設定
        this.setCopyButtonClickEvent();
    }

    /**
     * 画面上にパスワードを表示
     */
    private void setPassword() {

        String password = this.getIntent().getStringExtra(Constant.PASSWORD_TEXT);

        TextView textView = this.findViewById(R.id.password_text);
        textView.setText(password);
    }

    /**
     * 「コピー」ボタン押下時イベントを設定
     */
    private void setCopyButtonClickEvent() {

        Button buttonCopy = this.findViewById(R.id.button_copy);

        buttonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context thisContext = view.getContext();

                // パスワードを取得
                TextView passwordText = SubActivity.this.findViewById(R.id.password_text);
                String password = passwordText.getText().toString();

                // クリップボードに貼り付け
                ClipData data = ClipData.newPlainText("password", password);
                ClipboardManager clipboard = (ClipboardManager) thisContext.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setPrimaryClip(data);

                Toast.makeText(thisContext, "パスワードをコピーしました", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 「削除」ボタン押下時イベントを設定
     */
    private void setDeleteButtonClickEvent() {
        Button buttonDelete = this.findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(), "削除処理未実装", Toast.LENGTH_SHORT).show();

                // 前画面に戻る
                SubActivity.this.finish();
            }
        });
    }
}