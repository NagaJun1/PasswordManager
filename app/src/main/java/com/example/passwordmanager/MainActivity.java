package com.example.passwordmanager;

import android.content.Intent;
import android.os.Bundle;

import com.example.passwordmanager.constant.Constant;
import com.google.android.material.snackbar.Snackbar;

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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 画面を設定
        this.setContentView(R.layout.activity_main);

        // 「パスワード生成」ボタン押下処理を設定
        this.setAddPasswordClickEvent();

        // パスワードリスト押下時の処理を設定
        this.setPasswordList();
    }

    /**
     * パスワードリスト押下時の処理を設定
     */
    private void setPasswordList() {
        ListView listView = this.getPasswordList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String password = "...load password";

                MainActivity.this.showSubActivity(password);
            }
        });
    }

    private ListView getPasswordList() {
        ListAdapter listAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, this.getFilesDir().list());

        ListView listView = this.findViewById(R.id.password_list);
        listView.setAdapter(listAdapter);
        return listView;
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
                String password = createNewPassword();

                // パスワード表示画面に遷移
                MainActivity.this.showSubActivity(password);
            }
        });
    }

    /**
     * SecondFragment 画面を表示
     *
     * @param passwordText 画面に表示するパスワード
     */
    private void showSubActivity(String passwordText) {

        // パスワード表示画面へ遷移
        Intent intent = new Intent(this.getApplication(), SubActivity.class);

        // パスワードを SubActivity に引き渡す
        intent.putExtra(Constant.PASSWORD_TEXT, passwordText);

        this.startActivity(intent);
    }

    /**
     * パスワード生成
     * @return
     */
    private static String createNewPassword() {
        UUID uuid = UUID.randomUUID();
        String[] idList = uuid.toString().split("-");
        return idList[idList.length - 1];
    }
}