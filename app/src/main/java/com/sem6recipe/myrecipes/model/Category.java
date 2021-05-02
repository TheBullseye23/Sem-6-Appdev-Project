package com.sem6recipe.myrecipes.model;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {

    private String name;
    private List<Recipe> recipeList;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(String name, List<Recipe> recipeList) {
        this.name = name;
        this.recipeList = recipeList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }
}
