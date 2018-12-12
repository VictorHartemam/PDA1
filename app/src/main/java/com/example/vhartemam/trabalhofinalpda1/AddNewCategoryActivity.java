package com.example.vhartemam.trabalhofinalpda1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vhartemam.trabalhofinalpda1.entity.CategoriaTransacao;

import java.util.List;

public class AddNewCategoryActivity extends AppCompatActivity {

    private EditText editTextNewCategory;
    private Button buttonNewCategory;
    private LinearLayout linearLayoutCategories;
    List<CategoriaTransacao> categoriaTransacaoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_category);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextNewCategory = findViewById(R.id.editTextNewCategory);
        buttonNewCategory = findViewById(R.id.buttonNewCategory);
        linearLayoutCategories = findViewById(R.id.linearLayoutCategories);

        showCategories();

        buttonNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCategoryEvent();
            }
        });

        MainActivity.newItensList.clear();

    }

    private void showCategories(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                categoriaTransacaoList = MainActivity.db.categoriaTransacaoDao().select();
                return null;
            }
        }.execute();
        try{
            Thread.sleep(500);
        }
        catch(Exception e){
            Log.e("DEBUG",e.getMessage());
        }

        for(CategoriaTransacao categoria:categoriaTransacaoList){
            addCategoryInLinearLayout(categoria);
        }
    }

    private void addCategoryInLinearLayout(CategoriaTransacao categoria){
        TextView temp = new TextView(this);
        temp.setText(categoria.getDescricao());
        linearLayoutCategories.addView(temp);
    }

    private void newCategoryEvent(){
        final CategoriaTransacao categoria = new CategoriaTransacao(editTextNewCategory.getText().toString());
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                MainActivity.db.categoriaTransacaoDao().insert(categoria);
                return null;
            }
        }.execute();
        addCategoryInLinearLayout(categoria);
        editTextNewCategory.setText("");
        MainActivity.newItensList.add(categoria.getDescricao());
    }
}
