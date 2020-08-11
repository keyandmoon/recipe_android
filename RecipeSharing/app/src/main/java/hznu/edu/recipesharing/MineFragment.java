package hznu.edu.recipesharing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class MineFragment extends Fragment {
private Button logout;
private Button my_profile;
private Button my_recipe;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.mine_fragment, container, false);
        my_profile = (Button)view.findViewById(R.id.my_profile);
        my_recipe = (Button)view.findViewById(R.id.my_recipe);

        //个人资料
        my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),DisplayProfileActivity.class);
                startActivity(intent);
            }
        });

        //我的菜谱
        my_recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),DisplayMyRecipeActivity.class);
                startActivity(intent);
            }
        });

        //登出
        logout = (Button)view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        UserUtils userUtils = new UserUtils();
                        userUtils.logout(getContext());
                        mHandler.sendEmptyMessage(1);
                    }
                }).start();
            }
        });
        return view;

    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Toast.makeText(getContext(),"退出登录~",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
}
