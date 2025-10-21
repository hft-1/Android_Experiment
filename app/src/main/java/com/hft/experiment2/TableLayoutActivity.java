package com.hft.experiment2;

import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TableLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_layout);

        setupTableRowClicks();
    }

    private void setupTableRowClicks() {
        // 获取最外层LinearLayout，再获取其中的TableLayout
        View rootLayout = findViewById(R.id.table_layout);
        if (!(rootLayout instanceof android.widget.LinearLayout)) return;

        android.widget.LinearLayout linearLayout = (android.widget.LinearLayout) rootLayout;
        if (linearLayout.getChildCount() == 0) return;

        // 获取TableLayout
        View child = linearLayout.getChildAt(0);
        if (!(child instanceof TableLayout)) return;

        TableLayout tableLayout = (TableLayout) child;

        // 通过遍历TableLayout中的所有子视图动态绑定事件（跳过非TableRow）
        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            View rowView = tableLayout.getChildAt(i);

            if (rowView instanceof TableRow) {
                TableRow row = (TableRow) rowView;

                // 获取第一列文字（菜单名）
                String menuText = "";
                if (row.getChildCount() > 0 && row.getChildAt(0) instanceof TextView) {
                    menuText = ((TextView) row.getChildAt(0)).getText().toString();
                }

                final String finalMenuText = menuText;

                // 为整行设置点击监听
                row.setOnClickListener(v -> handleMenuClick(finalMenuText));

                // 为每个TextView也设置点击监听（方便独立点击）
                for (int j = 0; j < row.getChildCount(); j++) {
                    View cell = row.getChildAt(j);
                    if (cell instanceof TextView) {
                        cell.setOnClickListener(v -> handleMenuClick(finalMenuText));
                        cell.setClickable(true);
                        cell.setFocusable(true);
                    }
                }

                // 添加按下效果（点击反馈）
                row.setClickable(true);
                row.setFocusable(true);
                row.setBackgroundResource(android.R.drawable.list_selector_background);
            }
        }
    }

    private void handleMenuClick(String menuText) {
        switch (menuText) {
            case "Open...":
                openFile();
                break;
            case "Save...":
                saveFile();
                break;
            case "Save As...":
                saveAsFile();
                break;
            case "X Import...":
                importFile();
                break;
            case "X Export...":
                exportFile();
                break;
            case "Quit":
                quitApplication();
                break;
            default:
                showToast("点击了：" + menuText);
                break;
        }
    }

    private void openFile() {
        showToast("打开文件对话框");
    }

    private void saveFile() {
        showToast("保存当前文件");
    }

    private void saveAsFile() {
        showToast("文件另存为");
    }

    private void importFile() {
        showToast("导入文件");
    }

    private void exportFile() {
        showToast("导出文件");
    }

    private void quitApplication() {
        showToast("退出应用");
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
