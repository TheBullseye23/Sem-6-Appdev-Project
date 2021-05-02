package com.sem6recipe.myrecipes.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sem6recipe.myrecipes.fragment.CategoryFragment;
import com.sem6recipe.myrecipes.fragment.RecipesFragment;

public class TabPagerAdapter extends FragmentPagerAdapter {

    private static final int total_pages = 2;

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RecipesFragment();
            case 1:
                return new CategoryFragment();
        }
        return null;
    }

    @Override
    public int getCount() {

        return total_pages;

    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Recipes";
            case 1:
                return "Categories";
        }
        return null;
    }
}