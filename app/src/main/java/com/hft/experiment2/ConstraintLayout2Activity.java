package com.hft.experiment2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ConstraintLayout2Activity extends AppCompatActivity {

    private Switch oneWaySwitch;
    private Button travellerButton;
    private Button departButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.constraint_layout_2);

        initializeComponents();
        setupEventListeners();
    }

    private void initializeComponents() {
        oneWaySwitch = findViewById(R.id.switch_one_way);
        travellerButton = findViewById(R.id.bt_traveller);
        departButton = findViewById(R.id.bt_depart);

        // 设置其他功能按钮
        setupSpaceStationButton();
        setupFlightsButton();
        setupRoversButton();
        setupDCAButton();
        setupMarsButton();
    }

    private void setupEventListeners() {
        // 单程/往返开关
        if (oneWaySwitch != null) {
            oneWaySwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                String message = isChecked ? "单程旅行已选择" : "往返旅行已选择";
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            });
        }

        // 旅客信息按钮
        if (travellerButton != null) {
            travellerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTravellerInfo();
                }
            });
        }

        // 出发按钮
        if (departButton != null) {
            departButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startJourney();
                }
            });
        }
    }

    private void setupSpaceStationButton() {
        Button spaceStationButton = findViewById(R.id.bt_space_stations);
        if (spaceStationButton != null) {
            spaceStationButton.setOnClickListener(v ->
                    Toast.makeText(this, "查看空间站信息", Toast.LENGTH_SHORT).show());
        }
    }

    private void setupFlightsButton() {
        Button flightsButton = findViewById(R.id.bt_flights);
        if (flightsButton != null) {
            flightsButton.setOnClickListener(v ->
                    Toast.makeText(this, "查看航班信息", Toast.LENGTH_SHORT).show());
        }
    }

    private void setupRoversButton() {
        Button roversButton = findViewById(R.id.bt_rovers);
        if (roversButton != null) {
            roversButton.setOnClickListener(v ->
                    Toast.makeText(this, "查看漫游车信息", Toast.LENGTH_SHORT).show());
        }
    }

    private void setupDCAButton() {
        Button dcaButton = findViewById(R.id.bt_DCA);
        if (dcaButton != null) {
            dcaButton.setOnClickListener(v ->
                    Toast.makeText(this, "选择DCA作为出发地", Toast.LENGTH_SHORT).show());
        }
    }

    private void setupMarsButton() {
        Button marsButton = findViewById(R.id.bt_MARS);
        if (marsButton != null) {
            marsButton.setOnClickListener(v ->
                    Toast.makeText(this, "选择火星作为目的地", Toast.LENGTH_SHORT).show());
        }
    }

    private void showTravellerInfo() {
        // 这里可以跳转到旅客信息页面或显示对话框
        Toast.makeText(this, "编辑旅客信息", Toast.LENGTH_SHORT).show();
    }

    private void startJourney() {
        boolean isOneWay = oneWaySwitch != null && oneWaySwitch.isChecked();
        String journeyType = isOneWay ? "单程" : "往返";

        String message = "开始" + journeyType + "太空旅行！准备发射！";
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();


    }
}