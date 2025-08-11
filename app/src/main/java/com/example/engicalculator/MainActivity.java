package com.example.engicalculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.mariuszgromada.math.mxparser.*;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView previousCalculation;
    private EditText display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        previousCalculation = findViewById(R.id.PreviousCalculationView);
        display = findViewById(R.id.DisplayEditText);
    }

    private void updateText(String strToAdd) {
        String oldstr = display.getText().toString();
        int cursorPos = display.getSelectionStart();
        String leftstr = oldstr.substring(0, cursorPos);
        String rightstr = oldstr.substring(cursorPos);

        display.setText(String.format("%s%s%s", leftstr, strToAdd, rightstr));
        display.setSelection(cursorPos + strToAdd.length());
        display.setShowSoftInputOnFocus(false);
    }

    public void zeroBTNPush(View view) {
        updateText((getResources().getString(R.string.zeroText)));
    }

    public void oneBTNPush(View view) {
        updateText((getResources().getString(R.string.oneText)));
    }

    public void twoBTNPush(View view) {
        updateText((getResources().getString(R.string.twoText)));
    }

    public void threeBTNPush(View view) {
        updateText((getResources().getString(R.string.threeText)));
    }

    public void fourBTNPush(View view) {
        updateText((getResources().getString(R.string.fourText)));
    }

    public void fiveBTNPush(View view) {
        updateText((getResources().getString(R.string.fiveText)));
    }

    public void sixBTNPush(View view) {
        updateText((getResources().getString(R.string.sixText)));
    }

    public void sevenBTNPush(View view) {
        updateText((getResources().getString(R.string.sevenText)));
    }

    public void eightBTNPush(View view) {
        updateText((getResources().getString(R.string.eightText)));
    }

    public void nineBTNPush(View view) {
        updateText((getResources().getString(R.string.nineText)));
    }

    public void decimalBTNPush(View view) {
        updateText((getResources().getString(R.string.decimalText)));
    }

    public void parOpenBTNPush(View view) {
        updateText((getResources().getString(R.string.parenthesesOpenText)));
    }

    public void parCloseBTNPush(View view) {
        updateText((getResources().getString(R.string.parenthesesCloseText)));
    }

    public void divideBTNPush(View view) {
        updateText((getResources().getString(R.string.divideText)));
    }

    public void addBTNPush(View view) {
        updateText((getResources().getString(R.string.addText)));
    }

    public void SubtractBTNPush(View view) {
        updateText((getResources().getString(R.string.subtractText)));
    }

    public void multiplyBTNPush(View view) {
        updateText((getResources().getString(R.string.multiplyText)));
    }

    public void clearBTNPush(View view) {
        display.setText("");
        previousCalculation.setText("");
    }

    public void backspaceBTNPush(View view) {
        int cursorPos = display.getSelectionStart();
        int textlen = display.getText().length();
        if (cursorPos != 0 && textlen != 0) {
            SpannableStringBuilder selection = (SpannableStringBuilder) display.getText();
            selection.replace(cursorPos - 1, cursorPos, "");
            display.setText(selection);
            display.setSelection((cursorPos - 1));

            if (display.getText().toString().isEmpty()) {
                previousCalculation.setText("");
            }
        }
    }

    @SuppressLint("SetTextI18n")
    public void equalBTNPush(View view) {
        String userExp = display.getText().toString();
        previousCalculation.setText(userExp);

        userExp = userExp.replaceAll("÷", "/");
        userExp = userExp.replaceAll("×", "*");
        userExp = userExp.replaceAll("\\+", "+");
        userExp = userExp.replaceAll("-", "-");

        int openCount = userExp.length() - userExp.replace("(", "").length();
        int closeCount = userExp.length() - userExp.replace(")", "").length();
        while (closeCount < openCount) {
            userExp += ")";
            closeCount++;
        }

        if (userExp.contains("()")) {
            display.setText("Error: empty ()");
            return;
        }

        Expression exp = new Expression(userExp);
        double result = exp.calculate();
        if (Double.isNaN(result)) {
            display.setText("Invalid input");
        } else {
            display.setText(String.valueOf(result));
        }
        display.setSelection(display.getText().length());
    }

    // Landscape screen buttons
    public void trigSinBTNPush(View view) {
        updateText("sin(");
    }

    public void trigCosBTNPush(View view) {
        updateText("cos(");
    }

    public void trigTanBTNPush(View view) {
        updateText("tan(");
    }

    public void trigArcSinBTNPush(View view) {
        updateText("arcsin(");
    }

    public void trigArcCosBTNPush(View view) {
        updateText("arccos(");
    }

    public void trigArcTanBTNPush(View view) {
        updateText("arctan(");
    }

    public void logBTNPush(View view) {
        updateText("log( , )");
    }

    public void naturalLogBTNPush(View view) {
        updateText("ln(");
    }

    public void squareRootBTNPush(View view) {
        updateText("sqrt(");
    }

    public void eBTNPush(View view) {
        updateText("exp(");
    }

    public void pieBTNPush(View view) {
        updateText("pi");
    }

    public void AbsBTNPush(View view) {
        updateText("abs(");
    }

    public void squareBTNPush(View view) {
        updateText("^(2)");
    }

    public void xPowerYBTNPush(View view) {
        updateText("^(");
    }

    // fraction to decimal
    @SuppressLint("SetTextI18n")
    public void fractionDecimalToggleBTNPush(View view) {
        String input = display.getText().toString().trim();

        if (input.contains("/")) {
            String[] parts = input.split("/");
            if (parts.length == 2) {
                try {
                    double numerator = Double.parseDouble(parts[0].trim());
                    double denominator = Double.parseDouble(parts[1].trim());
                    if (denominator != 0) {
                        double decimal = numerator / denominator;
                        display.setText(String.valueOf(decimal));
                    } else {
                        display.setText("Error: divide by zero");
                    }
                } catch (Exception e) {
                    display.setText("Invalid fraction");
                }
            } else {
                display.setText("Invalid fraction");
            }
        } else {
            try {
                double value = Double.parseDouble(input);
                String fraction = decimalToFraction(value);
                display.setText(fraction);
            } catch (Exception e) {
                display.setText("Invalid decimal");
            }
        }
    }

    private String decimalToFraction(double decimal) {
        double tolerance = 1.0E-6;
        double h1 = 1;
        double h2 = 0;
        double k1 = 0;
        double k2 = 1;
        double b = decimal;
        do {
            double a = Math.floor(b);
            double aux = h1;
            h1 = a * h1 + h2;
            h2 = aux;
            aux = k1;
            k1 = a * k1 + k2;
            k2 = aux;
            b = 1 / (b - a);
        } while (Math.abs(decimal - h1 / k1) > decimal * tolerance);

        return (int) h1 + "/" + (int) k1;
    }
}
