package com.sem6recipe.myrecipes.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sem6recipe.myrecipes.R;
import com.sem6recipe.myrecipes.model.Recipe;

public class AddRecipeActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference mcategories;
    private DatabaseReference mrecipes;
    private DatabaseReference mfavorites;


    EditText e_recipename,e_ingredients,e_instructions,e_url;
    String s_recipename,s_ingredients,s_instructions,s_url;
    Button btn;
    RadioButton r1,r2,r3,r4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        mDatabase =  FirebaseDatabase.getInstance("https://recipe-app-ce449-default-rtdb.firebaseio.com/").getReference(); // referencing the firebase database
        mcategories = mDatabase.child("categories");
        mrecipes  = mDatabase.child("recipes");
        mfavorites = mDatabase.child("favorites");

            e_recipename = findViewById(R.id.recipename);
            e_ingredients = findViewById(R.id.ingredients);
            e_instructions = findViewById(R.id.instructions);
            btn = findViewById(R.id.addbtn);

            r1=findViewById(R.id.cat1);
            r2=findViewById(R.id.cat2);
            r3=findViewById(R.id.cat3);
            r4=findViewById(R.id.cat4);
            e_url = findViewById(R.id.urll);

        // handling button clicks
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp="";
                s_recipename = e_recipename.getText().toString();
                s_ingredients = e_ingredients.getText().toString();
                s_instructions = e_instructions.getText().toString();
                s_url = e_url.getText().toString();
                if((s_url=="")||(s_instructions=="")||(s_ingredients=="")||(s_recipename==""))
                    Toast.makeText(getApplicationContext(),"Please fill all details!",Toast.LENGTH_SHORT).show();
                else
                {
                    Recipe newrecipe = new Recipe(); // calling the recipe object defined earlier.
                    newrecipe.setName(s_recipename);
                    newrecipe.setIngredients(s_ingredients);
                    newrecipe.setDirection(s_instructions);
                    String ID = mrecipes.push().getKey();
                    newrecipe.setKey(ID);
                    newrecipe.setImage(s_url); // wrapping all values inside the recipe category
                    {

                        if(r1.isChecked()) temp="Indian";
                        else if(r2.isChecked()) temp="Italian";
                        else if(r3.isChecked()) temp="Chinese";
                        else temp = "American";
                        newrecipe.setCategory(temp); // setting the recipe category
                    }
                    mrecipes.child(ID).setValue(newrecipe);
                    mcategories.child(temp).child(mcategories.push().getKey()).setValue(newrecipe); // adding the object copy to the category database.
                    Toast.makeText(getApplicationContext(),"Recipe has been added",Toast.LENGTH_SHORT).show();
                }

            }
        });
        }
}