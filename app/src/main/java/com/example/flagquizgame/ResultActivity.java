package com.example.flagquizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private TextView tvScore;
    private TextView tvAccuracy;
    private Button btnPlayAgain;
    private Button btnExit;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 初始化视图
        tvScore = findViewById(R.id.tvScore);
        tvAccuracy = findViewById(R.id.tvAccuracy);
        btnPlayAgain = findViewById(R.id.btnPlayAgain);
        btnExit = findViewById(R.id.btnExit);

        // 获取游戏结果数据
        Intent intent = getIntent();
        int correctAnswers = intent.getIntExtra("correctAnswers", 0);
        int totalQuestions = intent.getIntExtra("totalQuestions", 10);

        // 计算正确率
        float accuracy = (float) correctAnswers / totalQuestions * 100;

        // 显示结果
        tvScore.setText(getString(R.string.score_format, correctAnswers, totalQuestions));
        tvAccuracy.setText(getString(R.string.accuracy_format, accuracy));

        // 设置按钮点击事件
        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动新游戏
                Intent newGameIntent = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(newGameIntent);
                finish();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 退出应用
                finish();
            }
        });
    }
}