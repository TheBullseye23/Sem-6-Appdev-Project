package com.sem6recipe.myrecipes.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sem6recipe.myrecipes.R;
import com.sem6recipe.myrecipes.adapter.RecipeRecyclerViewAdapter;
import com.sem6recipe.myrecipes.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipesFragment extends Fragment {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout = null;
    RecipeRecyclerViewAdapter recipeRecyclerViewAdapter;
    List<Recipe> recipes = new ArrayList<Recipe>();
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);

        progressDialog = new ProgressDialog(getContext());

        databaseReference = FirebaseDatabase.getInstance("https://recipe-app-ce449-default-rtdb.firebaseio.com/").getReference().child("recipes");

        getRecipes();

        initRecyclerView(view);

        return view;
    }

    private void getRecipes() {
        recipes.clear();
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                    Recipe recipe = new Recipe(snapshot1.getKey().toString(), snapshot1.child("name").getValue().toString(),
                            snapshot1.child("direction").getValue().toString(), snapshot1.child("ingredients").getValue().toString(), snapshot1.child("image").getValue().toString(), snapshot1.child("category").getValue().toString());
                    recipes.add(recipe);
                }
                recipeRecyclerViewAdapter.notifyDataSetChanged();
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
                getRecipes();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recipeRecyclerViewAdapter = new RecipeRecyclerViewAdapter(getActivity(), recipes);
        recyclerView.setAdapter(recipeRecyclerViewAdapter);
    }

   public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search, menu);

        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView)
                MenuItemCompat.getActionView(menu.findItem(R.id.search));

        final MenuItem searchMenuItem = menu.findItem(R.id.search);
        searchView.setQueryHint("Search recipe");

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    searchMenuItem.collapseActionView();
                    searchView.setQuery("", false);
                }
            }
        });

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {

                int textLength = newText.length();
                filter(newText);
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }
        });
    }

    private void filter(String text){
        List<Recipe> temp = new ArrayList();
        for(Recipe d: recipes){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getName().toLowerCase().contains(text)){
                temp.add(d);
            }
        }
        //update recyclerview
        recipeRecyclerViewAdapter.updateList(temp);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                return true;

            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}
