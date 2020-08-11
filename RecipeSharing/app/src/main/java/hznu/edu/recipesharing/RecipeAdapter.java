package hznu.edu.recipesharing;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private Context mContext;

    private List<Recipe> mRecipeList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView recipeImage;
        TextView recipeText;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView)view;
            recipeImage = (ImageView)view.findViewById(R.id.recipe_image);
            recipeText = (TextView)view.findViewById(R.id.recipe_text);
        }
    }

    public RecipeAdapter(List<Recipe> recipeList){
        mRecipeList = recipeList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Recipe recipe = mRecipeList.get(position);
                Intent intent = new Intent(mContext, DisplayRecipeActivity.class);
                intent.putExtra(DisplayRecipeActivity.RECIPE_NAME, recipe.getRecipe_name());
                intent.putExtra(DisplayRecipeActivity.RECIPE_IMAGE_ID,recipe.getRecipe_image());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);
        //还不会从本机查找图片
        //String imagePath = "F:\\JAVA\\eclipse-workspace\\RecipeSharing\\WebContent\\" + recipe.getRecipe_image();
        holder.recipeText.setText(recipe.getRecipe_name());
        Glide.with(mContext).load(R.drawable.lajiao).into(holder.recipeImage);
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }




}
