package hznu.edu.recipesharing;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Recipe> recipeList = new ArrayList<>();
    private RecipeAdapter recipeAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                initRecipe();
            }
        }).start();
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recipeAdapter = new RecipeAdapter(recipeList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recipeAdapter);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        return view;
    }

    public void initRecipe(){
        recipeList.clear();
        ArrayList<Map<String, String>> data;
        String sql = "select * from table_recipe order by recipe_editdate desc";
        UserUtils userUtils = new UserUtils();
        data = userUtils.queryForList(sql);
        if(!data.isEmpty()){
            for(Map<String,String> r:data){
                Recipe recipe = new Recipe(r.get("recipe_id"),r.get("recipe_content"),r.get("recipe_name"),r.get("recipe_writer"),r.get("recipe_tag"),r.get("recipe_editdate"),r.get("recipe_image"));
                recipeList.add(recipe);
            }
        }
    }

    public void refresh(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                initRecipe();
                try {
                    Thread.sleep(1000); //避免看不清刷新过程
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(1);
            }
        }).start();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
        case 1:
            swipeRefreshLayout.setRefreshing(false);
            recipeAdapter.notifyDataSetChanged();
            Toast.makeText(getContext(),"刷新成功！",Toast.LENGTH_SHORT).show();
            break;
        default:
            break;
    }
}
    };


}
