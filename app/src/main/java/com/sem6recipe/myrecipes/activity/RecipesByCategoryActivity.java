package com.sem6recipe.myrecipes.activity;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.sem6recipe.myrecipes.R;
import com.sem6recipe.myrecipes.adapter.RecipeRecyclerViewAdapter;
import com.sem6recipe.myrecipes.model.Category;
import com.sem6recipe.myrecipes.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipesByCategoryActivity extends AppCompatActivity {

    private Category category;
    private RecyclerView recycler_view;
    private RecipeRecyclerViewAdapter recipeRecyclerViewAdapter;
    private List<Recipe> recipes = new ArrayList<Recipe>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_by_category);
        category = (Category) getIntent().getSerializableExtra("Category");
        recipes = category.getRecipeList();
        recycler_view = findViewById(R.id.recycler_view);
        initToolbar(category.getName());
        initRecyclerView();
    }

    private void initToolbar(String name) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Category: "+name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private void initRecyclerView() {
        recycler_view.setLayoutManager(new GridLayoutManager(this, 2));
        recycler_view.setItemAnimator(new DefaultItemAnimator());

        recipeRecyclerViewAdapter = new RecipeRecyclerViewAdapter(this, recipes);
        recycler_view.setAdapter(recipeRecyclerViewAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
