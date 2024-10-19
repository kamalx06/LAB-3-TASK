package com.example.lab_3_task;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView calculatorScreen;
    private String input = "";
    private String operator = "";
    private double firstValue = Double.NaN;
    private double secondValue;
    private boolean isNewOperator = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculatorScreen = findViewById(R.id.calculatorScreen);
        setButtonListeners();
    }

    private void setButtonListeners() {
        // Numbers
        findViewById(R.id.n0).setOnClickListener(this::onNumberClick);
        findViewById(R.id.n1).setOnClickListener(this::onNumberClick);
        findViewById(R.id.n2).setOnClickListener(this::onNumberClick);
        findViewById(R.id.n3).setOnClickListener(this::onNumberClick);
        findViewById(R.id.n4).setOnClickListener(this::onNumberClick);
        findViewById(R.id.n5).setOnClickListener(this::onNumberClick);
        findViewById(R.id.n6).setOnClickListener(this::onNumberClick);
        findViewById(R.id.n7).setOnClickListener(this::onNumberClick);
        findViewById(R.id.n8).setOnClickListener(this::onNumberClick);
        findViewById(R.id.n9).setOnClickListener(this::onNumberClick);

        findViewById(R.id.addition).setOnClickListener(this::onOperatorClick);
        findViewById(R.id.subtraction).setOnClickListener(this::onOperatorClick);
        findViewById(R.id.multiplication).setOnClickListener(this::onOperatorClick);
        findViewById(R.id.division).setOnClickListener(this::onOperatorClick);

        findViewById(R.id.squareroot).setOnClickListener(this::onSpecialClick);
        findViewById(R.id.signchange).setOnClickListener(this::onSpecialClick);
        findViewById(R.id.dot).setOnClickListener(this::onSpecialClick);
        findViewById(R.id.clear).setOnClickListener(v -> clearCalculator());
        findViewById(R.id.delete).setOnClickListener(v -> deleteLast());

        findViewById(R.id.equals).setOnClickListener(v -> onEqualsClick());
    }

    private void onNumberClick(View view) {
        Button button = (Button) view;
        if (isNewOperator) {
            input = "";
            isNewOperator = false;
        }
        input += button.getText().toString();
        calculatorScreen.setText(input);
    }

    private void onOperatorClick(View view) {
        Button button = (Button) view;
        if (!Double.isNaN(firstValue)) {
            secondValue = Double.parseDouble(input);
            firstValue = calculate(firstValue, secondValue, operator);
            calculatorScreen.setText(String.valueOf(firstValue));
        } else {
            firstValue = Double.parseDouble(input);
        }
        operator = button.getText().toString();
        isNewOperator = true;
    }

    private void onSpecialClick(View view) {
        Button button = (Button) view;
        String operation = button.getText().toString();

        switch (operation) {
            case "√":
                double result = Math.sqrt(Double.parseDouble(input));
                calculatorScreen.setText(String.valueOf(result));
                input = String.valueOf(result);
                break;

            case "+/-":
                double value = Double.parseDouble(input) * -1;
                calculatorScreen.setText(String.valueOf(value));
                input = String.valueOf(value);
                break;

            case ".":
                if (!input.contains(".")) {
                    input += ".";
                    calculatorScreen.setText(input);
                }
                break;
        }
    }

    private void onEqualsClick() {
        if (!operator.isEmpty()) {
            secondValue = Double.parseDouble(input);
            double result = calculate(firstValue, secondValue, operator);
            calculatorScreen.setText(String.valueOf(result));
            firstValue = result;
            operator = "";
            isNewOperator = true;
        }
    }

    private double calculate(double firstValue, double secondValue, String operator) {
        switch (operator) {
            case "+":
                return firstValue + secondValue;
            case "-":
                return firstValue - secondValue;
            case "*":
                return firstValue * secondValue;
            case "/":
                if (secondValue != 0) {
                    return firstValue / secondValue;
                } else {
                    calculatorScreen.setText("Error in operation");
                    return Double.NaN;
                }
        }
        return firstValue;
    }

    private void clearCalculator() {
        firstValue = Double.NaN;
        secondValue = Double.NaN;
        input = "";
        operator = "";
        calculatorScreen.setText("");
    }

    private void deleteLast() {
        if (!input.isEmpty()) {
            input = input.substring(0, input.length() - 1);
            calculatorScreen.setText(input);
        }
    }
}
