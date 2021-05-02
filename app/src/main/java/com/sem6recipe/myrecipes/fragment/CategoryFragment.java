package com.sem6recipe.myrecipes.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sem6recipe.myrecipes.R;
import com.sem6recipe.myrecipes.activity.RecipesByCategoryActivity;
import com.sem6recipe.myrecipes.adapter.CategoryRecyclerViewAdapter;
import com.sem6recipe.myrecipes.model.Category;
import com.sem6recipe.myrecipes.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment implements CategoryRecyclerViewAdapter.OnCategoryListener {

    private DatabaseReference databaseReference;

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout = null;
    CategoryRecyclerViewAdapter categoryRecyclerViewAdapter;
    private RelativeLayout relativeLayout;
    List<Category> categories = new ArrayList<Category>();
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_category, container, false);
        progressDialog = new ProgressDialog(getContext());

        databaseReference = FirebaseDatabase.getInstance("https://recipe-app-ce449-default-rtdb.firebaseio.com/").getReference().child("categories");

        getCategories();

        initRecyclerView(v);

        return v;
    }

    private void getCategories() {
        categories.clear();
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Category category = new Category(snapshot.getKey());
                    List<Recipe> recipes = new ArrayList<>();
                    for(DataSnapshot snapshot1: snapshot.getChildren()) {
                        Recipe recipe = new Recipe(snapshot1.getKey().toString(), snapshot1.child("name").getValue().toString(),
                                snapshot1.child("direction").getValue().toString(), snapshot1.child("ingredients").getValue().toString(), snapshot1.child("image").getValue().toString(), snapshot1.child("category").getValue().toString());
                        recipes.add(recipe);
                    }
                    category.setRecipeList(recipes);
                    categories.add(category);
                }
                categoryRecyclerViewAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerView(View v) {
        recyclerView = v.findViewById(R.id.recycler_view);

        swipeRefreshLayout = v.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green, R.color.blue, R.color.red);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCategories();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(0), true));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(categories, this);
        recyclerView.setAdapter(categoryRecyclerViewAdapter);
    }

    @Override
    public void onCategoryClick(int pos) {
        Category category = categories.get(pos);
        Intent intent = new Intent(getContext(), RecipesByCategoryActivity.class);
        intent.putExtra("Category", category);
        startActivity(intent);
    }
}
