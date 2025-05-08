package com.example.flagquizgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int QUESTIONS_PER_GAME = 10;
    private static final int OPTIONS_PER_QUESTION = 5;

    private ImageView ivFlag;
    private TextView tvQuestionNumber;
    private TextView tvFeedback;
    private Button btnNext;
    private List<Button> optionButtons = new ArrayList<>();

    private List<Country> gameCountries; // 本轮游戏的所有国家
    private List<Country> currentOptions; // 当前问题的选项
    private Country currentCorrectCountry; // 当前问题的正确答案
    private int currentQuestionIndex = 0; // 当前问题索引
    private int correctAnswersCount = 0; // 正确答案计数
    private boolean hasAnswered = false; // 是否已回答当前问题

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化视图
        initViews();

        // 开始游戏
        startNewGame();
    }

    private void initViews() {
        ivFlag = findViewById(R.id.ivFlag);
        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        tvFeedback = findViewById(R.id.tvFeedback);
        btnNext = findViewById(R.id.btnNext);

        // 初始化选项按钮
        optionButtons.add(findViewById(R.id.btnOption1));
        optionButtons.add(findViewById(R.id.btnOption2));
        optionButtons.add(findViewById(R.id.btnOption3));
        optionButtons.add(findViewById(R.id.btnOption4));
        optionButtons.add(findViewById(R.id.btnOption5));

        // 设置下一题按钮点击事件
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNextQuestion();
            }
        });
    }

    private void startNewGame() {
        // 重置游戏状态
        currentQuestionIndex = 0;
        correctAnswersCount = 0;

        // 获取随机国家列表作为问题
        gameCountries = CountryData.getRandomCountries(QUESTIONS_PER_GAME);

        // 加载第一个问题
        loadQuestion(currentQuestionIndex);
    }

    private void loadQuestion(int questionIndex) {
        // 更新问题编号
        tvQuestionNumber.setText(getString(R.string.question_format, questionIndex + 1, QUESTIONS_PER_GAME));

        // 重置状态
        hasAnswered = false;
        tvFeedback.setText("");
        btnNext.setVisibility(View.INVISIBLE);

        // 获取当前正确答案
        currentCorrectCountry = gameCountries.get(questionIndex);

        // 生成选项列表
        currentOptions = CountryData.generateOptionsForQuestion(currentCorrectCountry, OPTIONS_PER_QUESTION);

        // 显示国旗图片
        String flagResourceName = currentCorrectCountry.getFlagImageName();
        int resourceId = getResources().getIdentifier(flagResourceName, "drawable", getPackageName());
        if (resourceId != 0) {
            ivFlag.setImageResource(resourceId);
        } else {
            // 处理图片资源不存在的情况
            ivFlag.setImageResource(R.drawable.ic_launcher_foreground);
        }

        // 设置选项按钮
        for (int i = 0; i < optionButtons.size(); i++) {
            Button button = optionButtons.get(i);
            if (i < currentOptions.size()) {
                final Country optionCountry = currentOptions.get(i);
                button.setText(optionCountry.getName());
                button.setBackgroundColor(ContextCompat.getColor(this, R.color.option_normal));
                button.setEnabled(true);
                button.setVisibility(View.VISIBLE);

                // 设置点击事件
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!hasAnswered) {
                            checkAnswer(optionCountry, (Button) v);
                        }
                    }
                });
            } else {
                // 隐藏多余的按钮
                button.setVisibility(View.GONE);
            }
        }
    }

    private void checkAnswer(Country selectedCountry, Button selectedButton) {
        hasAnswered = true;

        if (selectedCountry.getName().equals(currentCorrectCountry.getName())) {
            // 回答正确
            selectedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.option_correct));
            tvFeedback.setText(getString(R.string.feedback_correct));
            correctAnswersCount++;
        } else {
            // 回答错误
            selectedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.option_incorrect));
            selectedButton.setEnabled(false);
            tvFeedback.setText(getString(R.string.feedback_incorrect));
        }

        // 显示正确答案和下一题按钮
        for (Button button : optionButtons) {
            button.setEnabled(false);
            if (button.getText().toString().equals(currentCorrectCountry.getName())) {
                button.setBackgroundColor(ContextCompat.getColor(this, R.color.option_correct));
            }
        }

        // 如果不是最后一题，显示下一题按钮
        if (currentQuestionIndex < QUESTIONS_PER_GAME - 1) {
            btnNext.setVisibility(View.VISIBLE);
        } else {
            // 是最后一题，显示"查看结果"按钮
            btnNext.setText(getString(R.string.btn_show_result));
            btnNext.setVisibility(View.VISIBLE);
        }
    }

    private void loadNextQuestion() {
        if (currentQuestionIndex < QUESTIONS_PER_GAME - 1) {
            // 加载下一题
            currentQuestionIndex++;
            loadQuestion(currentQuestionIndex);
        } else {
            // 游戏结束，显示结果
            showResults();
        }
    }

    private void showResults() {
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra("correctAnswers", correctAnswersCount);
        intent.putExtra("totalQuestions", QUESTIONS_PER_GAME);
        startActivity(intent);
        finish(); // 结束当前Activity
    }
}