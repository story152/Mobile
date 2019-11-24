package com.example.mobileprogrammingtermproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.nio.file.StandardWatchEventKinds;

public class Accept_check extends AppCompatActivity {
    CheckBox checkall;
    CheckBox is18;
    CheckBox acpt1;
    CheckBox acpt2;
    CheckBox acpt3;
    Button btnaccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_check);

        check();
    }

    public void buttonclick(View view) {
        if (check()){ // 필수항목들이 체크되었으면
            Intent sign_in = new Intent(this, Sign_in.class);
            startActivity(sign_in); // 회원가입 액티비티 실행
            finish();
        } else { // 체크가 헤제 되어있으면
            Toast.makeText(Accept_check.this, "약관을 동의해야합니다.", Toast.LENGTH_SHORT).show();
        }
    }

    boolean check() { // 체크박스가 체크 되어있는지 검사하는 함수
        // 각 체크박스 객체 불러옴
        checkall = findViewById(R.id.accept2);
        is18 = findViewById(R.id.accept1);
        acpt1 = findViewById(R.id.accept3);
        acpt2 = findViewById(R.id.accept4);
        acpt3 = findViewById(R.id.accept5);
        btnaccept = findViewById(R.id.accept_btn);

        // 모두 동의 체크박스 이벤트 리스너
        checkall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkall.isChecked()){ // 체크가 되면 모두 체크
                    is18.setChecked(true);
                    acpt1.setChecked(true);
                    acpt2.setChecked(true);
                    acpt3.setChecked(true);
                }
                else{ // 체크를 풀면 모두 체크 해제
                    is18.setChecked(false);
                    acpt1.setChecked(false);
                    acpt2.setChecked(false);
                    acpt3.setChecked(false);
                }
            }
        });

        if (is18.isChecked() && acpt1.isChecked() && acpt2.isChecked()) { // 필수항목들 체크되었으면
            return true; // true 반환
        }
        return false; // false 반환
    }
}