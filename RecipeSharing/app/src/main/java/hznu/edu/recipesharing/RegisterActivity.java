package hznu.edu.recipesharing;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.regex.Pattern;

import hznu.edu.recipesharing.BaseActivity;
import hznu.edu.recipesharing.User;
import hznu.edu.recipesharing.UserUtils;

public class RegisterActivity extends BaseActivity {
    public static final String reg_name = "^[a-zA-Z0-9_]{4,16}$";
    public static final String reg_pw = "^.*(?=.{6,})(?=.*\\d)(?=.*[a-z])(?=.*[!@#$%^&*?\\.]).*$";
    //public static final String reg_name = "^[\\w]{4,16}$";
    // public static final String reg_pw = "^[a-zA-Z]\\w{5,17}$";
    private EditText name_input;
    private EditText password_input;
    private EditText password_input_double;
    private EditText question_input;
    private EditText answer_input;
    private Button register;
    private Button return_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        name_input = (EditText)findViewById(R.id.name_input);
        password_input = (EditText)findViewById(R.id.password_input);
        password_input_double = (EditText)findViewById(R.id.password_input_double);
        question_input = (EditText)findViewById(R.id.question_input);
        answer_input = (EditText)findViewById(R.id.answer_input);
        register = (Button)findViewById(R.id.register);
        return_login = (Button)findViewById(R.id.return_login);

        return_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int is_failed = 0;
                if(password_input.getText().toString().equals(password_input_double.getText().toString())){
                    Log.d("is_success12",String.valueOf(is_failed));
                    Log.d("is_success12_zhi",password_input.getText().toString());
                    Log.d("is_success12_zhi2",password_input_double.getText().toString());
                    if(!Pattern.matches(reg_name,name_input.getText().toString())){
                        Log.d("is_success21",String.valueOf(is_failed));
                        Log.d("is_success21_zhi",name_input.getText().toString());
                    }else{
                        Log.d("is_success22",String.valueOf(is_failed));
                        if(!Pattern.matches(reg_pw,password_input.getText().toString())){
                            Log.d("is_success31",String.valueOf(is_failed));
                        }else{
                            Log.d("is_success32",String.valueOf(is_failed));
                            if(question_input.getText().toString().equals("") || answer_input.getText().toString().equals("")){
                                Log.d("is_success41",String.valueOf(is_failed));
                            }else{
                                Log.d("is_success42",String.valueOf(is_failed));
                                is_failed = 1;
                            }
                        }
                    }
                }else{
                    Toast.makeText(RegisterActivity.this,"两次密码不一样！",Toast.LENGTH_SHORT).show();
                    Log.d("is_success11",String.valueOf(is_failed));
                }
                if(is_failed == 0){
                    Toast.makeText(RegisterActivity.this,"输入有误！",Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("is_success",String.valueOf(is_failed));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int is_success = 0;
                            User user = new User();
                            user.setUser_id(name_input.getText().toString());
                            user.setPassword(password_input.getText().toString());
                            user.setQuestion(question_input.getText().toString());
                            user.setAnswer(answer_input.getText().toString());
                            UserUtils utils = new UserUtils();
                            is_success = utils.register(user);
                            Log.d("is_success",String.valueOf(is_success));
                            Message message = new Message();
                            message.what = is_success;
                            handle.sendMessage(message);
                        }
                    }).start();
                }
            }
        });
    }

    private Handler handle = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                default:
                    Toast.makeText(RegisterActivity.this,"注册失败！",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


}

//regPw = /^.*(?=.{6,})(?=.*\d)(?=.*[a-z])(?=.*[!@#$%^&*?\.]).*$/;
//regName = /^[a-zA-Z0-9_]{4,16}$/;
