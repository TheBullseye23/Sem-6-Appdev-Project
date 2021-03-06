package com.sem6recipe.myrecipes.model;

import java.io.Serializable;

public class Recipe implements Serializable {
    private String key;
    //private String category_id;
    private String name;
    private String direction;
    private String ingredients;
    private String image;
    private String category;

    public Recipe() {
    }

    public Recipe(String key, String name, String direction, String ingredients, String image, String category) {
        this.key = key;
        this.name = name;
        this.direction = direction;
        this.ingredients = ingredients;
        this.image = image;
        this.category = category;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
