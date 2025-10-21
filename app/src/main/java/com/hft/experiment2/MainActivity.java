package com.hft.experiment2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setupVerticalButtonLayout();
    }

    private void setupVerticalButtonLayout() {
        // 获取主布局
        LinearLayout mainLayout = findViewById(R.id.main);

        // 设置线性布局为垂直方向
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setGravity(Gravity.CENTER);

        // 创建跳转到线性布局的按钮
        Button linearLayoutButton = createButton("线性布局", LinearLayoutActivity.class);

        // 创建跳转到表格布局的按钮
        Button tableLayoutButton = createButton("表格布局", TableLayoutActivity.class);

        // 创建跳转到约束布局1的按钮
        Button constraintLayout1Button = createButton("约束布局1", ConstraintLayout1Activity.class);

        // 创建跳转到约束布局2的按钮
        Button constraintLayout2Button = createButton("约束布局2", ConstraintLayout2Activity.class);

        // 设置按钮的布局参数（外边距）
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(50, 30, 50, 30);

        // 将按钮添加到布局中
        mainLayout.addView(linearLayoutButton, params);
        mainLayout.addView(tableLayoutButton, params);
        mainLayout.addView(constraintLayout1Button, params);
        mainLayout.addView(constraintLayout2Button, params);
    }

    private Button createButton(String text, final Class<?> targetActivity) {
        Button button = new Button(this);
        button.setText(text);
        button.setTextSize(18);
        button.setPadding(20, 20, 20, 20);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, targetActivity);
                startActivity(intent);
            }
        });
        return button;
    }
}