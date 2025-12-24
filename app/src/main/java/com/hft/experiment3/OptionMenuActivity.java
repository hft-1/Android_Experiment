package com.hft.experiment3;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OptionMenuActivity extends AppCompatActivity {

    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_menu);

        tvContent = findViewById(R.id.tv_content);
        ImageView ivMenuIcon = findViewById(R.id.iv_menu_icon);

        ivMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }

    private void showPopupMenu(View view) {
        // 创建 PopupMenu 对象，绑定到点击的视图
        PopupMenu popupMenu = new PopupMenu(this, view);
        // 加载菜单资源
        popupMenu.getMenuInflater().inflate(R.menu.xml_menu, popupMenu.getMenu());
        
        // 设置菜单项点击监听器
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();
                
                if (itemId == R.id.font_10) {
                    tvContent.setTextSize(10);
                    return true;
                } else if (itemId == R.id.font_16) {
                    tvContent.setTextSize(16);
                    return true;
                } else if (itemId == R.id.font_20) {
                    tvContent.setTextSize(20);
                    return true;
                } else if (itemId == R.id.plain_item) {
                    Toast.makeText(OptionMenuActivity.this, "您点击了普通菜单项", Toast.LENGTH_SHORT).show();
                    return true;
                } else if (itemId == R.id.red_font) {
                    tvContent.setTextColor(Color.RED);
                    return true;
                } else if (itemId == R.id.black_font) {
                    tvContent.setTextColor(Color.BLACK);
                    return true;
                }
                
                return false;
            }
        });
        
        // 显示菜单
        popupMenu.show();
    }
}
