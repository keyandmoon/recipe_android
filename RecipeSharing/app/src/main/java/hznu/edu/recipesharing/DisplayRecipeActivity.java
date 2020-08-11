package hznu.edu.recipesharing;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class DisplayRecipeActivity extends AppCompatActivity {
    public static final String RECIPE_NAME = "recipe_name";
    public static final String RECIPE_IMAGE_ID = "recipe_image_id";
    private String recipeName;
    private TextView recipeContentText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe);
        Intent intent = getIntent();
        recipeName = intent.getStringExtra(RECIPE_NAME);
        int recipeImageId = intent.getIntExtra(RECIPE_IMAGE_ID,0);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        ImageView recipeImageView = (ImageView)findViewById(R.id.recipe_detail_imege);
        recipeContentText = (TextView)findViewById(R.id.recipe_content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(recipeName);
        Glide.with(this).load(recipeImageId).into(recipeImageView);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String recipeContent = generateRecipeContent(recipeName);
                recipeContentText.setText(recipeContent);
            }
        }).start();

    }

    private String generateRecipeContent(String recipeName){
        UserUtils userUtils = new UserUtils();
        return userUtils.queryRecipeContent(recipeName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
