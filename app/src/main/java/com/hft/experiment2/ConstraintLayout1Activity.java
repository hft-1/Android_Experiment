package com.hft.experiment2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import java.text.DecimalFormat;

public class ConstraintLayout1Activity extends AppCompatActivity {

    private static final String TAG = "ConstraintLayout1Activity";

    private TextView valueDisplay;
    private String currentInput = "0";
    private double firstValue = 0;
    private String currentOperator = "";
    private boolean resetOnNextInput = false;

    private final DecimalFormat decimalFormat = new DecimalFormat("0.##########");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.constraint_layout_1);
            initializeCalculator();
        } catch (Exception e) {
            Log.e(TAG, "初始化失败: " + e.getMessage(), e);
            Toast.makeText(this, "界面初始化失败: " + e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void initializeCalculator() {
        Log.d(TAG, "开始初始化计算器");

        valueDisplay = findViewById(R.id.value);
        if (valueDisplay == null) {
            throw new RuntimeException("找不到value TextView");
        }

        initializeNumberButtons();
        initializeOperatorButtons();
        initializeFunctionButtons();

        updateDisplay();
        Log.d(TAG, "计算器初始化完成");
    }

    private void initializeNumberButtons() {
        setupNumberButton(R.id.bt0, "0");
        setupNumberButton(R.id.bt1, "1");
        setupNumberButton(R.id.bt2, "2");
        setupNumberButton(R.id.bt3, "3");
        setupNumberButton(R.id.bt4, "4");
        setupNumberButton(R.id.bt5, "5");
        setupNumberButton(R.id.bt6, "6");
        setupNumberButton(R.id.bt7, "7");
        setupNumberButton(R.id.bt8, "8");
        setupNumberButton(R.id.bt9, "9");
        setupDecimalButton();
    }

    private void initializeOperatorButtons() {
        setupOperatorButton(R.id.bt_plus, "+");
        setupOperatorButton(R.id.bt_subtract, "-");
        setupOperatorButton(R.id.bt_multiply, "×");
        setupOperatorButton(R.id.bt_divide, "÷");
    }

    private void initializeFunctionButtons() {
        setupEqualsButton();
    }

    private void setupNumberButton(int cardViewId, final String number) {
        setupButton(cardViewId, v -> appendNumber(number));
    }

    private void setupOperatorButton(int cardViewId, final String operator) {
        setupButton(cardViewId, v -> setOperator(operator));
    }

    private void setupDecimalButton() {
        setupButton(R.id.bt_point, v -> {
            if (resetOnNextInput) {
                currentInput = "0";
                resetOnNextInput = false;
            }
            if (!currentInput.contains(".")) {
                // 如果是刚开始输入，直接变为 "0."
                if (currentInput.equals("0") || currentInput.equals("Error")) {
                    currentInput = "0.";
                } else {
                    currentInput += ".";
                }
                updateDisplay();
            }
        });
    }

    private void setupEqualsButton() {
        setupButton(R.id.bt_equal, v -> calculateResult());
    }

    private void setupButton(int cardViewId, View.OnClickListener listener) {
        try {
            CardView cardView = findViewById(cardViewId);
            if (cardView != null && cardView.getChildCount() > 0) {
                View child = cardView.getChildAt(0);
                if (child instanceof Button) {
                    ((Button) child).setOnClickListener(listener);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "设置按钮失败: " + e.getMessage());
        }
    }

    private void appendNumber(String number) {
        if (currentInput.equals("Error")) {
            // 从错误状态恢复
            currentInput = "0";
        }
        if (resetOnNextInput) {
            currentInput = "0";
            resetOnNextInput = false;
        }
        // 防止多余前导0
        if (currentInput.equals("0") && !number.equals(".")) {
            currentInput = number;
        } else {
            currentInput += number;
        }
        updateDisplay();
    }

    private void setOperator(String operator) {
        // 如果当前已存在运算符并且不是刚刚输入的运算符，则先计算结果
        if (!currentOperator.isEmpty() && !resetOnNextInput) {
            calculateResult();
        }
        try {
            firstValue = Double.parseDouble(currentInput);
            currentOperator = operator;
            resetOnNextInput = true;
        } catch (NumberFormatException e) {
            Toast.makeText(this, "数字格式错误", Toast.LENGTH_SHORT).show();
            currentInput = "0";
            updateDisplay();
        }
    }

    private void calculateResult() {
        if (currentOperator.isEmpty()) return;

        try {
            double secondValue = Double.parseDouble(currentInput);
            double result = 0;

            switch (currentOperator) {
                case "+": result = firstValue + secondValue; break;
                case "-": result = firstValue - secondValue; break;
                case "×": result = firstValue * secondValue; break;
                case "÷":
                    if (secondValue == 0) {
                        currentInput = "Error";
                        updateDisplay();
                        currentOperator = "";
                        return;
                    }
                    result = firstValue / secondValue;
                    break;
            }

            currentInput = decimalFormat.format(result);
            firstValue = result; // 支持连续计算
            currentOperator = "";
            resetOnNextInput = true;
            updateDisplay();

        } catch (Exception e) {
            Log.e(TAG, "计算错误: " + e.getMessage());
            currentInput = "Error";
            updateDisplay();
        }
    }

    private void updateDisplay() {
        if (valueDisplay != null) {
            valueDisplay.setText(currentInput);
        }
    }
}
