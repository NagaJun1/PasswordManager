package com.example.passwordmanager;

import android.content.Intent;
import android.os.Bundle;

import com.example.passwordmanager.component.ActivityBase;
import com.example.passwordmanager.constant.Constant;
import com.example.passwordmanager.events.CreateBackupEvent;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.UUID;

public class MainActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 画面を設定
        this.setContentView(R.layout.activity_main);

        // 「パスワード生成」ボタン押下処理を設定
        this.setAddPasswordClickEvent();

        // "button_create_back_up" のイベント追加（パスワードのバックアップファイル生成）
        this.setCreateBackupClickEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();

        System.out.println("...MainActivity.onStart()");

        // パスワードリスト押下時の処理を設定
        this.setPasswordList();
    }

    /**
     * パスワードリスト押下時の処理を設定
     */
    private void setPasswordList() {
        // ファイル名一覧を取得
        String[] fileList = this.getFilesDir().list();

        // password_list を取得、リスト内アイテムの登録
        ListView listView = this.getPasswordList(fileList);

        // リスト内アイテム押下時の処理
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 押下されたアイテムのインデックスからフィル名を取得
                String passwordKeyWord = fileList[i];

                // パスワード表示画面に遷移
                MainActivity.this.showSubActivity(passwordKeyWord);
            }
        });
    }

    /**
     * ListView の取得、リスト内アイテムの登録
     *
     * @param fileList パスワードを保存しているファイル名一覧
     * @return password_list
     */
    @NonNull
    private ListView getPasswordList(String[] fileList) {
        ListAdapter listAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, fileList);

        ListView listView = this.findViewById(R.id.password_list);
        listView.setAdapter(listAdapter);
        return listView;
    }

    /**
     * ボタン押下で、パスワード情報のバックアップ処理（ローカルファイルをコピー）
     */
    private void setCreateBackupClickEvent() {
        Button btn = this.findViewById(R.id.button_create_back_up);

        // パスワードファイルをコピー
        btn.setOnClickListener(new CreateBackupEvent());
    }

    /**
     * 「パスワード生成」ボタン押下処理を設定
     */
    private void setAddPasswordClickEvent() {
        Button button = this.findViewById(R.id.button_add);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // パスワード生成
                String passwordKeyWord = Constant._String.EMPTY;

                // パスワード表示画面に遷移
                MainActivity.this.showSubActivity(passwordKeyWord);
            }
        });
    }

    /**
     * SubActivity 画面を表示
     *
     * @param passwordKeyWord 画面に表示するパスワード
     */
    private void showSubActivity(String passwordKeyWord) {

        // パスワード表示画面へ遷移
        Intent intent = new Intent(this.getApplication(), SubActivity.class);

        // パスワードを SubActivity に引き渡す
        intent.putExtra(Constant.PASSWORD_KEY_WORD, passwordKeyWord);

        this.startActivity(intent);
    }
}