package com.sem6recipe.myrecipes.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sem6recipe.myrecipes.R;
import com.sem6recipe.myrecipes.model.Category;

import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {

    Category mCategory;
    private List<Category> mCategories;
    private OnCategoryListener mCategoryListener;

    public CategoryRecyclerViewAdapter(List<Category> categories, OnCategoryListener mCategoryListener) {
        this.mCategories = categories;
        this.mCategoryListener = mCategoryListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_row, parent, false);

        return new ViewHolder(view, mCategoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mCategory = mCategories.get(position);
        holder.title.setText(mCategory.getName());
        String cat = mCategory.getName();
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title;
        public ImageView image;
        public RelativeLayout relativeLayout;
        OnCategoryListener onCategoryListener;

        public ViewHolder(View view, OnCategoryListener onCategoryListener) {
            super(view);
            this.onCategoryListener = onCategoryListener;
            title = view.findViewById(R.id.category_title);
            image = view.findViewById(R.id.category_image);
            relativeLayout = view.findViewById(R.id.relativeLayout);

            relativeLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onCategoryListener.onCategoryClick(getAdapterPosition());
        }
    }

    public interface OnCategoryListener{
        void onCategoryClick(int pos);
    }
}
