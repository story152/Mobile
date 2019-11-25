package com.example.mobileprogrammingtermproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class Sign_in extends AppCompatActivity {
    TextView timer;
    EditText code;
    Button btnemail;

    CountDownTimer countDownTimer;
    final int MILLISINFUTURE = 300 * 1000; //총 시간 (300초 = 5분)
    final int COUNT_DOWN_INTERVAL = 1000; //onTick 메소드를 호출할 간격 (1초)

    private DBHelper helper;
    private SQLiteDatabase db;

    EditText editid, editpw, pw_check, inputmail, email_check , editnn;

    String mailcode;
    boolean isid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() // 인터넷 사용하기 위해 권한 얻는 것
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        helper = new DBHelper(this); // DBDB
        // DBHelper 객체를 이용하여 DB 생성
        try {
            db = helper.getWritableDatabase();
        } catch (SQLiteException e) {
            db = helper.getReadableDatabase();
        }
    }

    public void insert(){ // DBDB
        editid = findViewById(R.id.input_id);
        editpw = findViewById(R.id.input_password);
        inputmail = findViewById(R.id.input_email);
        editnn = findViewById(R.id.input_nickname);
        String ID = editid.getText().toString();
        String PW = editpw.getText().toString();
        String EM = inputmail.getText().toString();
        String NN = editnn.getText().toString();

        Cursor cursor;
        cursor = db.rawQuery("SELECT ID FROM id_info ", null);

        while(cursor.moveToNext()) {
            String id = cursor.getString(0);
            if(id.equals(ID)){
                isid = true;
                break;
            }
            else
                isid = false;
        }
        cursor.close();
        if(!isid){
            // 이건 확인 버튼 누를때 들어가야지
            //db.execSQL("INSERT INTO " + "id_info" + " (ID, PW, EM, NN) VALUES ('"+ ID + "', '" + PW + "', '" + EM + "', '" + NN + "');");
            Toast.makeText(getApplicationContext(), "이 아이디는 사용가능합니다.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"아이디가 이미 존재합니다.",Toast.LENGTH_SHORT).show();
        }
    }

    public void buttonclick(View view){ // 버튼 클릭
        switch (view.getId()){
            case R.id.check_id: // 중복확인 버튼
                insert();
                break;
            case R.id.email_cer: // 인증코드 전송 버튼
                btnemail = findViewById(R.id.email_cer);
                btnemail.setEnabled(false);
                countDownTimer();
                sendmail();
                break;
            case R.id.check_email:
                countDownTimer.cancel(); // 타이머 멈춤

                break;
            case R.id.sign_up: // 완료 버튼
                if(checkall()){ // 중복확인, 메일인증이 완료되면
                    finish(); // 액티비티 종료
                }
                break;
        }
    }

    void check_email(){
        email_check = findViewById(R.id.input_email_check);
    }

    boolean checkall(){ // 중복 확인 및 메일인증됬는지 검사하는 함수
        email_check = findViewById(R.id.input_email_check);
        editpw = findViewById(R.id.input_password);
        pw_check = findViewById(R.id.input_password_check);
        boolean pwcheck = (editpw.getText().toString().equals(pw_check.getText().toString()));

        if(email_check.getText().toString().equals(mailcode) && pwcheck){ // 인증번호 확인, 비밀번호 확인
            // 중복확인도 추가해야함
            return true;
        }
        else if(!pwcheck){
            Toast.makeText(getApplicationContext(),"비밀번호가 맞지 않습니다.",Toast.LENGTH_SHORT).show();
        }
        else if(!email_check.getText().toString().equals(mailcode)){
            Toast.makeText(getApplicationContext(),"인증번호가 맞지 않습니다.",Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    void sendmail(){ // 메일보내는 메소드
        inputmail = findViewById(R.id.input_email);

        try{
            GMailSender gMailSender = new GMailSender("hyun6045@gmail.com","rkdgus147");
            String body = "인증코드: " +gMailSender.getEmailCode();
            mailcode = gMailSender.getEmailCode();
            gMailSender.sendMail("앱이름: 인증코드",body,inputmail.getText().toString());
            Toast.makeText(getApplicationContext(), "이메일을 성공적으로 보냈습니다.", Toast.LENGTH_SHORT).show();
        }catch (SendFailedException e){
            Toast.makeText(getApplicationContext(), "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
        } catch (MessagingException e) {
            Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주십시오", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void countDownTimer(){ // 타이머 메소드
        timer = findViewById(R.id.timer);
        code = findViewById(R.id.input_email_check);
        btnemail = findViewById(R.id.email_cer);

        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) { //(300초에서 1초 마다 계속 줄어듬)
                long emailAuthCount = millisUntilFinished / 1000;
                if ((emailAuthCount - ((emailAuthCount / 60) * 60)) >= 10) { //초가 10보다 크면 그냥 출력
                    timer.setText((emailAuthCount / 60) + ":" + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                } else { //초가 10보다 작으면 앞에 '0' 붙여서 같이 출력. ex) 02,03,04...
                    timer.setText((emailAuthCount / 60) + ":0" + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                }
                //emailAuthCount은 종료까지 남은 시간임. 1분 = 60초 되므로,
                // 분을 나타내기 위해서는 종료까지 남은 총 시간에 60을 나눠주면 그 몫이 분이 된다.
                // 분을 제외하고 남은 초를 나타내기 위해서는, (총 남은 시간 - (분*60) = 남은 초) 로 하면 된다.
            }
            // 타이머캔슬 countDownTimer.cancel();
            @Override
            public void onFinish() { //시간이 다 되면 다이얼로그 종료
                Toast.makeText(Sign_in.this,"인증번호 전송을 다시 해주세요",Toast.LENGTH_LONG).show();
                btnemail.setEnabled(true);
            }
        }.start();
    }
}
