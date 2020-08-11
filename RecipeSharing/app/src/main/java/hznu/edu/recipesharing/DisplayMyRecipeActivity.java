package hznu.edu.recipesharing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class DisplayMyRecipeActivity extends AppCompatActivity {
    private ArrayList<Recipe> r_List = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_my_recipe);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new Thread(new Runnable() {
            @Override
            public void run() {
                initMyRecipe();
            }

        }).start();
        MyRecipeAdapter adapter = new MyRecipeAdapter(DisplayMyRecipeActivity.this,R.layout.my_recipe_item,r_List);
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Recipe recipe = r_List.get(position);
                Intent intent = new Intent(DisplayMyRecipeActivity.this, DisplayRecipeActivity.class);
                intent.putExtra(DisplayRecipeActivity.RECIPE_NAME, recipe.getRecipe_name());
                intent.putExtra(DisplayRecipeActivity.RECIPE_IMAGE_ID,recipe.getRecipe_image());
                startActivity(intent);
            }
        });
    }

    public void initMyRecipe(){
        UserUtils userUtils = new UserUtils();
        String sql = "select * from table_recipe where recipe_writer=?";
        ArrayList<Map<String, String>> myRecipeList = userUtils.queryTheUserInfo(sql,DisplayMyRecipeActivity.this);
        for(Map<String,String> map:myRecipeList){
            Recipe recipe = new Recipe();
            recipe.setRecipe_name(map.get("recipe_name"));
            recipe.setRecipe_image(map.get("recipe_image"));
            r_List.add(recipe);
        }
    }


}
