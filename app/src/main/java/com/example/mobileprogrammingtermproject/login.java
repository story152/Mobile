package com.example.mobileprogrammingtermproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class login extends AppCompatActivity {
    static final int GET_STRING = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GET_STRING) {
            if(resultCode == RESULT_OK) {
                //text.setText(data.getStringExtra("INPUT_TEXT"));
            } else if(resultCode == RESULT_CANCELED) {
                //
            }
        }
    }*/

    public void buttonclick(View view){
        switch (view.getId()){
            case R.id.login_btn:
                Intent login = new Intent(this, MainActivity.class);
                startActivity(login);
                break;
            case R.id.Find_userInfo:
                Intent find = new Intent(this, Find_UserInfo.class);
                startActivity(find);
                break;
            case R.id.signIn:
                Intent check = new Intent(this, Accept_check.class);
                startActivity(check);
                break;
        }
    }
}
