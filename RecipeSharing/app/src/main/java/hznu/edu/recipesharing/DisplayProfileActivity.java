package hznu.edu.recipesharing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class DisplayProfileActivity extends AppCompatActivity {
    private EditText u_id;
    private EditText u_name;
    private RadioGroup rg_sex;
    private EditText u_residence;
    private EditText u_job;
    private EditText u_profile;
    private Button submit_profile;
    private Button clear;
    private String sex;
    private ArrayList<Map<String, String>> user_info;
    ArrayList<Map<String, String>> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_profile);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        u_id = (EditText)findViewById(R.id.user_id_input);
        u_name = (EditText)findViewById(R.id.user_name_input);
        u_residence = (EditText)findViewById(R.id.user_residence_input);
        u_job = (EditText)findViewById(R.id.user_job_input);
        u_profile = (EditText)findViewById(R.id.user_profile_input);
        rg_sex = (RadioGroup)findViewById(R.id.rg_sex);
        submit_profile = (Button)findViewById(R.id.submit_profile);
        clear = (Button)findViewById(R.id.user_clear);

        //回显数据库的内容
        new Thread(new Runnable() {
            @Override
            public void run() {
                String sql = "select * from user where user_id=?";
                if(user_info != null)
                    user_info.clear();
                UserUtils userUtils = new UserUtils();
                user_info = userUtils.queryTheUserInfo(sql,DisplayProfileActivity.this);
                mHandler.sendEmptyMessage(1);
            }
        }).start();

        //选择性别
        //初始
        RadioButton rb = (RadioButton)findViewById(rg_sex.getCheckedRadioButtonId());
        sex = rb.getText().toString();
        //动态
        rg_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.sex_female:
                        sex = "女";
                        break;
                    case R.id.sex_male:
                        sex = "男";
                        break;
                    default:
                        break;
                }
            }
        });

        //清空输入
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                u_name.getText().clear();
                u_residence.getText().clear();
                u_job.getText().clear();
                u_profile.getText().clear();
                Toast.makeText(DisplayProfileActivity.this,"输入已清空！",Toast.LENGTH_SHORT).show();
            }
        });

        submit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int is_save = 2;
                        String name = u_name.getText().toString();
                        //sex已选
                        String job = u_job.getText().toString();
                        String residence = u_residence.getText().toString();
                        String profile = u_profile.getText().toString();
                        if(name.equals("") || job.equals("") || residence.equals("") || profile.equals("")){
                            is_save = 0;
                        }else{
                            User user = new User();
                            user.setUser_name(name);
                            user.setUser_job(job);
                            user.setUser_residence(residence);
                            user.setUser_profile(profile);
                            user.setUser_sex(sex);
                            UserUtils userUtils = new UserUtils();
                            is_save = userUtils.ModifyInfo(user,DisplayProfileActivity.this);
                        }
                        uHandler.sendEmptyMessage(is_save);
                    }
                }).start();
            }
        });

    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    for(Map<String,String> info:user_info){
                        u_id.setText(info.get("user_id"));
                        u_name.setText(info.get("user_name"));
                        u_job.setText(info.get("job"));
                        u_residence.setText(info.get("residence"));
                        u_profile.setText(info.get("profile"));
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private Handler uHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case 2:
                    Toast.makeText(DisplayProfileActivity.this,"输入不能为空！",Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(DisplayProfileActivity.this,"信息修改成功！",Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    Toast.makeText(DisplayProfileActivity.this,"信息修改失败~",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

}
