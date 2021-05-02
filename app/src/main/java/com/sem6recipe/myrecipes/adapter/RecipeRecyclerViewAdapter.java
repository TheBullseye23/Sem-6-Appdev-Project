package com.sem6recipe.myrecipes.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sem6recipe.myrecipes.R;
import com.sem6recipe.myrecipes.activity.RecipeDetailsActivity;
import com.sem6recipe.myrecipes.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder> {

    private List<Recipe> mRecipes;
    private Context mContext;
    private Recipe recipe;

    public RecipeRecyclerViewAdapter(Context context, List<Recipe> recipes) {
        this.mContext = context;
        this.mRecipes = recipes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        recipe = mRecipes.get(position);

        holder.title.setText(recipe.getName());
        Picasso
                .get()
                .load(recipe.getImage())
                .error(android.R.drawable.gallery_thumb)
                .into(holder.image);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RecipeDetailsActivity.class);
                intent.putExtra("recipe", mRecipes.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    public void updateList(List<Recipe> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title;
        public RelativeLayout relativeLayout;

        public ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.news_title);
            image = view.findViewById(R.id.news_image);
            relativeLayout = view.findViewById(R.id.relativeLayout);
        }
    }
}
