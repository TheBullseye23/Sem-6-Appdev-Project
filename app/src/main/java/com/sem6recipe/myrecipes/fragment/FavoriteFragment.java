package com.sem6recipe.myrecipes.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.sem6recipe.myrecipes.R;
import com.sem6recipe.myrecipes.activity.MainActivity;
import com.sem6recipe.myrecipes.adapter.FavoriteRecyclerViewAdapter;
import com.sem6recipe.myrecipes.dbhelper.FavouriteDbHelper;
import com.sem6recipe.myrecipes.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    RecyclerView recyclerView;
    FavoriteRecyclerViewAdapter favoriteRecyclerViewAdapter;
    private RelativeLayout relativeLayout;
    List<Recipe> favorites = new ArrayList<Recipe>();
    private MainActivity mainActivity;
    private Toolbar toolbar;
    private FavouriteDbHelper favouriteDbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorite, container, false);

        toolbar = v.findViewById(R.id.toolbar);
        setupToolbar();
        favouriteDbHelper = new FavouriteDbHelper(getContext());

        favorites.addAll(favouriteDbHelper.getAll());
        //Toast.makeText(getContext(), favorites.get(0).getImage(), Toast.LENGTH_SHORT).show();

        initRecyclerView(v);

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity) activity;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity.setupNavigationDrawer(toolbar);
    }

    private void initRecyclerView(View v) {
        recyclerView = v.findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        favoriteRecyclerViewAdapter = new FavoriteRecyclerViewAdapter(getActivity(), favorites);
        recyclerView.setAdapter(favoriteRecyclerViewAdapter);
    }

    private void setupToolbar() {
        toolbar.setTitle("My Favorites");
        mainActivity.setSupportActionBar(toolbar);
    }
}
