package hznu.edu.recipesharing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class HomeActivity extends BaseActivity {
    private Button logout;
    private RadioGroup rg;
    static private String user_id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rg = (RadioGroup)findViewById(R.id.rg);
        replaceFragment(new HomeFragment());
        //默认切换到主页
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_home:
                        replaceFragment(new HomeFragment());
                        break;
                    case R.id.rb_add_r:
                        replaceFragment(new AddRecipeFragment());
                        break;
                    case R.id.rb_mine:
                        replaceFragment(new MineFragment());
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_body, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
