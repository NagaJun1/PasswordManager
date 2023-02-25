package com.example.passwordmanager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.passwordmanager.component.KeywordInputDialogBuilder;
import com.example.passwordmanager.constant.Constant;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.UUID;

public class SubActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
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

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("...SubActivity.onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("...SubActivity.onDestroy()");
    }

    /**
     * 画面上にパスワードを表示
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setPassword() {
        String password;

        // パスワードのキーワードを取得、画面上に設定
        TextView keywordText = this.findViewById(R.id.keyword_text);

        // 前画面で選択されたキーワードを取得
        String passwordKeyWord = this.getIntent().getStringExtra(Constant.PASSWORD_KEY_WORD);

        // パスワードに紐づくキーワードが存在しない場合、新規作成する
        if (Constant._String.EMPTY.equals(passwordKeyWord)) {
            // パスワード新規作成
            password = this.createNewPassword();

            // ダイアログを表示して、キーワードを入力値で取得
            this.setNewPasswordKeyword(keywordText, password);
        } else {
            // ローカルのパスワードをロード
            password = this.loadPassword(passwordKeyWord);

            // キーワードを画面に入力
            keywordText.setText(passwordKeyWord.replace(".txt", Constant._String.EMPTY));
        }

        // パスワードを画面中心に表示
        TextView textView = this.findViewById(R.id.password_text);
        textView.setText(password);
    }

    /**
     * ローカルのパスワードをロード
     *
     * @param passwordKeyWord パスワードに紐づくキーワードを取得
     * @return パスワード
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private String loadPassword(String passwordKeyWord) {
        // ファイルを新規作成
        File file = new File(this.getFilesDir(), passwordKeyWord);

        if (Files.exists(file.toPath())) {
            try (FileReader fileReader = new FileReader(file)) {
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String password = bufferedReader.readLine();
                bufferedReader.close();
                return password;
            } catch (Exception ex) {
                System.out.println(ex.toString());
                Toast.makeText(this, "パスワードのロードに失敗しました", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "パスワードを記録したファイルが存在しません", Toast.LENGTH_SHORT).show();
        }
        this.finish();
        return Constant._String.EMPTY;
    }

    /**
     * パスワードのキーワードを新規に設定するためのダイアログを表示
     * @param keywordText パスワードに紐づけるキーワードの入力項目
     * @param password パスワード
     */
    private void setNewPasswordKeyword(TextView keywordText, String password) {

        // パスワードのキーワード入力用のダイアログを表示
        KeywordInputDialogBuilder builder = new KeywordInputDialogBuilder(this);

        // 「OK」ボタン押下時の処理を実装
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // 入力値が空の場合はパスワード表示画面を閉じる
                if (TextUtils.isEmpty(builder.getEditText())) {
                    Toast.makeText(SubActivity.this, "空で設定はできません", Toast.LENGTH_SHORT).show();
                    SubActivity.this.finish();
                } else {
                    // 入力されたテキストで画面上のキーワードを設定
                    keywordText.setText(builder.getEditText());

                    // 生成したパスワードをローカルファイルに保存
                    SubActivity.this.createNewFile(builder.getEditText().toString(), password);
                }
            }
        });
        builder.create().show();
    }

    /**
     * パスワードを保存するローカルファイルを生成
     *
     * @param keyword  パスワードに紐づけるキーワード
     * @param password パスワード
     */
    private void createNewFile(String keyword, String password) {
        // ファイルを新規作成
        File file = new File(this.getFilesDir(), keyword + ".txt");

        try (FileWriter writer = new FileWriter(file, false)) {
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.append(password);
            bufferedWriter.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * パスワード生成
     * @return 新規に生成したパスワード
     */
    private String createNewPassword() {
        UUID uuid = UUID.randomUUID();
        String[] idList = uuid.toString().split("-");
        return idList[idList.length - 1];
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