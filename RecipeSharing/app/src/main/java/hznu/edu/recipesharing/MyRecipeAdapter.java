package hznu.edu.recipesharing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyRecipeAdapter extends ArrayAdapter<Recipe> {
    private int resourceId;
    public MyRecipeAdapter(Context context, int textViewResourceId, List<Recipe> object){
        super(context,textViewResourceId,object);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Recipe recipe = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        ImageView recipeImage = (ImageView)view.findViewById(R.id.my_recipe_image);
        TextView recipeName = (TextView)view.findViewById(R.id.my_recipe_name);
        recipeImage.setImageResource(R.drawable.lajiao);
        recipeName.setText(recipe.getRecipe_name());
        return view;
    }
}
