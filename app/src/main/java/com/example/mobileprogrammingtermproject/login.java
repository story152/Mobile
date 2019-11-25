package com.example.mobileprogrammingtermproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {
    static final int GET_STRING = 1;
    private DBHelper helper;
    private SQLiteDatabase db;
    String Cname;
    String Cpassword;
    EditText idEditText;
    EditText pwEditText;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        helper = new DBHelper(this);
        try {
            db = helper.getWritableDatabase();
        } catch (SQLiteException e) {
            db = helper.getReadableDatabase();
        }
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
                idEditText=(EditText) findViewById(R.id.id);
                pwEditText=(EditText) findViewById(R.id.password);

                String id = idEditText.getText().toString();
                String pw = pwEditText.getText().toString();
                if(id.length() == 0 || pw.length() == 0) {
                    //아이디와 비밀번호는 필수 입력사항입니다.
                    Toast toast = Toast.makeText(login.this, "아이디와 비밀번호는 필수 입력사항입니다.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                Cursor cursor = db.rawQuery("SELECT ID, PW FROM id_info WHERE ID='" + id + "';", null);
                cursor.moveToNext();
                Cname = cursor.getString(0);
                Cpassword = cursor.getString(1);

                if (id.equals(Cname) && pw.equals(Cpassword)) {
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(login.this,id+"님 환영합니다.",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(login.this, "아이디와 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
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

