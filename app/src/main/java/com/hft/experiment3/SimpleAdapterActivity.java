package com.hft.experiment3;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleAdapterActivity extends AppCompatActivity {

    private ListView listView;
    // Lion, Tiger, Monkey, Dog, Cat, Elephant
    private String[] names = {"Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant"};
    private int[] images = {
            R.drawable.lion,
            R.drawable.tiger,
            R.drawable.monkey,
            R.drawable.dog,
            R.drawable.cat,
            R.drawable.elephant
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_adapter);

        // 检查通知权限 (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        listView = findViewById(R.id.listView);

        List<Map<String, Object>> listItems = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> listItem = new HashMap<>();
            listItem.put("name", names[i]);
            listItem.put("image", images[i]);
            listItems.add(listItem);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                this,
                listItems,
                R.layout.item_animal,
                new String[]{"name", "image"},
                new int[]{R.id.tv_name, R.id.iv_image}
        );

        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 3. 点击任一列表项，该列表项变成红色，2s后变回原来的样子
                // 先将当前View背景设置为红色
                view.setBackgroundColor(Color.RED);

                // 2秒后恢复背景
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        view.setBackgroundColor(Color.TRANSPARENT);
                    }
                }, 2000);

                // 使用自定义Toast显示选中的列表项信息
                Map<String, Object> item = (Map<String, Object>) parent.getItemAtPosition(position);
                String name = (String) item.get("name");
                int imageResId = (int) item.get("image"); // 获取图片资源ID
                showCustomToast(name);

                // 4. 发送通知
                sendNotification(name, imageResId, position);
            }
        });
    }

    private void showCustomToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_custom, null);

        ImageView image = layout.findViewById(R.id.toast_image);
        // 图标可以根据需要修改，这里使用ic_launcher
        image.setImageResource(R.mipmap.ic_launcher);
        TextView text = layout.findViewById(R.id.toast_text);
        text.setText(message);

        Toast toast = new Toast(getApplicationContext());
        // 设置位置，可以调整
        // toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0); 
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    private void sendNotification(String title, int imageResId, int notificationId) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String channelId = "animal_channel";

        // Android 8.0 及以上需要创建通知渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Animal Channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        // 创建点击通知后的跳转 Intent
        Intent intent = new Intent(this, SimpleAdapterActivity.class);
        // 设置 Flag 避免重复创建 Activity 实例
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 
                notificationId, 
                intent, 
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // 将资源图片转为 Bitmap 以用于 LargeIcon
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), imageResId);

        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.ic_launcher) // 小图标通常使用 App 图标（状态栏显示）
                .setLargeIcon(largeIcon) // 大图标显示动物图片（通知栏右侧或左侧大图）
                .setContentTitle(title) // 显示的Title为列表项内容
                .setContentText("You clicked on " + title) // 通知内容自拟
                .setAutoCancel(true)
                .setContentIntent(pendingIntent) // 设置点击跳转
                .build();

        // 使用 position 作为 notificationId，确保每条通知都有唯一 ID，不会相互覆盖
        notificationManager.notify(notificationId, notification);
    }
}
