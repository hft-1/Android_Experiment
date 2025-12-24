package com.hft.experiment3;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AlertDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);

        // 页面加载完成后显示对话框
        showLoginDialog();
    }

    private void showLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 获取布局加载器
        LayoutInflater inflater = getLayoutInflater();
        // 加载自定义布局
        View dialogView = inflater.inflate(R.layout.alert_dialog, null);
        // 设置布局到对话框
        builder.setView(dialogView);
        // 设置对话框不可取消，强制用户操作
        builder.setCancelable(false);

        // 创建对话框
        final AlertDialog dialog = builder.create();

        // 初始化控件
        EditText etUsername = dialogView.findViewById(R.id.username);
        EditText etPassword = dialogView.findViewById(R.id.password);
        Button btnLogin = dialogView.findViewById(R.id.btn_login);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);

        // 设置 Cancel 按钮点击事件
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });

        // 设置 Sign in 按钮点击事件
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // 如果用户输入空白字符或不输入也要进行相应的toast提示
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(AlertDialogActivity.this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 用户输入用户名 = hft ,密码 = 123456时才能成功登录，并给出toast提示
                if ("hft".equals(username) && "123456".equals(password)) {
                    Toast.makeText(AlertDialogActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    // 登录成功也可以选择关闭对话框
                    dialog.dismiss();
                    finish();
                } else {
                    Toast.makeText(AlertDialogActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 显示对话框
        dialog.show();
    }
}
