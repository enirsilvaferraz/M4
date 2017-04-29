package com.system.m4.views.home;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.system.m4.R;
import com.system.m4.views.components.dialogs.list.ItemList;
import com.system.m4.views.components.dialogs.list.ListComponentDialog;
import com.system.m4.views.components.dialogs.list.OnItemSelectedListener;
import com.system.m4.views.transaction.TransactionManagerDialog;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<String> list = new ArrayList<>();
                list.add("Moradia");
                list.add("Aluguel");
                list.add("Celular");
                list.add("Internet");
                list.add("Automovel");
                list.add("Seguro");

                ListComponentDialog.newInstance(R.string.transaction_manager_tags, ItemList.asList(list), new OnItemSelectedListener() {
                    @Override
                    public void onSelect(ItemList item) {
                        TransactionManagerDialog.newInstance(item).show(getSupportFragmentManager(), "dialog");
                    }
                }).show(getSupportFragmentManager());
            }
        });
    }
}
