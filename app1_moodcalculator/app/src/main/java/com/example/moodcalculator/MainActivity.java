package com.example.moodcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView displayText;
    private StringBuilder currentInput = new StringBuilder();

    // Mood words mapped to calculator-style buttons
    private static final String[] MOOD_TOKENS = {
        "😊Happy", "😢Sad", "😡Angry", "😰Anxious",
        "😴Tired", "🤩Excited", "😌Calm", "😕Confused",
        "+", "-", "×", "÷",
        "AC", "⌫", "=", "💫"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayText = findViewById(R.id.tvDisplay);
        Button btnInfo = findViewById(R.id.btnInfo);

        btnInfo.setOnClickListener(v -> {
            Intent intent = new Intent(this, InfoActivity.class);
            startActivity(intent);
        });

        setupButtons();
    }

    private void setupButtons() {
        int[] buttonIds = {
            R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8,
            R.id.btn9, R.id.btn10, R.id.btn11, R.id.btn12,
            R.id.btn13, R.id.btn14, R.id.btn15, R.id.btn16
        };

        for (int i = 0; i < buttonIds.length; i++) {
            Button btn = findViewById(buttonIds[i]);
            if (btn != null) {
                final String token = MOOD_TOKENS[i];
                btn.setText(token);
                btn.setOnClickListener(v -> handleInput(token));
            }
        }
    }

    private void handleInput(String token) {
        switch (token) {
            case "AC":
                currentInput.setLength(0);
                displayText.setText("Your Mood...");
                break;
            case "⌫":
                if (currentInput.length() > 0) {
                    // Remove last token (find last space or start)
                    String s = currentInput.toString().trim();
                    int lastSpace = s.lastIndexOf(" ");
                    if (lastSpace >= 0) {
                        currentInput = new StringBuilder(s.substring(0, lastSpace));
                    } else {
                        currentInput.setLength(0);
                    }
                    displayText.setText(currentInput.length() > 0 ? currentInput.toString() : "Your Mood...");
                }
                break;
            case "=":
                computeMoodResult();
                break;
            case "💫":
                surpriseMood();
                break;
            default:
                if (currentInput.length() > 0) currentInput.append(" ");
                currentInput.append(token);
                displayText.setText(currentInput.toString());
                break;
        }
    }

    private void computeMoodResult() {
        String input = currentInput.toString();
        if (input.isEmpty()) {
            displayText.setText("Add some moods first!");
            return;
        }

        // Count positive vs negative mood tokens
        int positive = countOccurrences(input, "😊") + countOccurrences(input, "🤩") + countOccurrences(input, "😌");
        int negative = countOccurrences(input, "😢") + countOccurrences(input, "😡") + countOccurrences(input, "😰");
        int neutral = countOccurrences(input, "😴") + countOccurrences(input, "😕");

        // Check operators
        boolean hasPlus = input.contains("+");
        boolean hasMinus = input.contains("-");
        boolean hasMult = input.contains("×");

        String result;
        if (positive > negative && hasPlus) {
            result = "✨ Amplified Joy! You're radiating good vibes doubled!";
        } else if (negative > positive && hasMinus) {
            result = "🌱 Subtracting pain... You're healing. Keep going.";
        } else if (hasMult && positive > 0) {
            result = "🚀 Multiplied energy! Today you can conquer anything!";
        } else if (negative > positive) {
            result = "🌧️ Heavy heart detected. Be gentle with yourself today.";
        } else if (positive > negative) {
            result = "☀️ Your emotional balance is positive! Share that energy!";
        } else if (neutral > 0) {
            result = "🍃 Balanced neutrality — a quiet day of steady calm.";
        } else {
            result = "🌀 Complex emotional equation... Only you know the answer.";
        }

        currentInput.setLength(0);
        displayText.setText(result);
    }

    private void surpriseMood() {
        String[] surprises = {
            "🌈 Today holds unexpected colour!",
            "⚡ A spark of inspiration is coming...",
            "🦋 Transformation is in progress.",
            "🌊 Go with the flow today.",
            "🔮 Mystery energy surrounds you!"
        };
        int idx = (int)(Math.random() * surprises.length);
        currentInput.setLength(0);
        displayText.setText(surprises[idx]);
    }

    private int countOccurrences(String text, String sub) {
        int count = 0;
        int idx = 0;
        while ((idx = text.indexOf(sub, idx)) != -1) {
            count++;
            idx += sub.length();
        }
        return count;
    }
}
