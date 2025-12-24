package com.hft.experiment3;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ActionModeActivity extends AppCompatActivity {

    private ListView listView;
    private List<String> dataList;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_mode);

        listView = findViewById(R.id.list_view);
        dataList = new ArrayList<>();
        dataList.add("One");
        dataList.add("Two");
        dataList.add("Three");
        dataList.add("Four");
        dataList.add("Five");

        adapter = new MyAdapter();
        listView.setAdapter(adapter);

        // 开启多选模式
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                // 更新标题，显示选中数量
                mode.setTitle(listView.getCheckedItemCount() + " selected");
                adapter.notifyDataSetChanged();
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // 加载菜单
                getMenuInflater().inflate(R.menu.context_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if (item.getItemId() == R.id.menu_delete) {
                    // 获取选中的项目并删除
                    long[] checkedItemIds = listView.getCheckedItemIds();
                    // 因为getCheckedItemIds返回的是id，而我们的id就是position（在getItemId中返回position）
                    // 为了避免删除时索引变化的问题，我们倒序删除或者收集要删除的对象
                    // 这里我们使用SparseBooleanArray来获取选中的位置
                    
                    // 获取选中的位置
                    android.util.SparseBooleanArray checkedItemPositions = listView.getCheckedItemPositions();
                    List<String> toDelete = new ArrayList<>();
                    for (int i = 0; i < checkedItemPositions.size(); i++) {
                        int position = checkedItemPositions.keyAt(i);
                        if (checkedItemPositions.valueAt(i)) {
                            toDelete.add(dataList.get(position));
                        }
                    }

                    for (String s : toDelete) {
                        dataList.remove(s);
                    }
                    
                    Toast.makeText(ActionModeActivity.this, "Deleted " + toDelete.size() + " items", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                    mode.finish(); // 关闭ActionMode
                    return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                adapter.notifyDataSetChanged();
            }
        });
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.simple_list_item, parent, false);
            }

            ImageView imageView = convertView.findViewById(R.id.item_image);
            TextView textView = convertView.findViewById(R.id.item_text);

            textView.setText(dataList.get(position));
            imageView.setImageResource(R.mipmap.ic_launcher); // 使用默认图标

            // 高亮选中项
            if (listView.isItemChecked(position)) {
                convertView.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
            } else {
                convertView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            }

            return convertView;
        }
    }
}
