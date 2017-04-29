package com.system.m4.views.home;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.system.m4.R;
import com.system.m4.views.components.dialogs.list.ListComponentAdapter;
import com.system.m4.views.components.dialogs.list.ItemList;
import com.system.m4.views.components.dialogs.list.ListComponentDialog;
import com.system.m4.views.filter.FilterTransactionDialog;
import com.system.m4.views.transaction.ItemVO;
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

                showTransactionManager();
            }
        });
    }

    private void showTransactionManager() {
        List<String> list = new ArrayList<>();
        list.add("Moradia");
        list.add("Aluguel");
        list.add("Celular");
        list.add("Internet");
        list.add("Automovel");
        list.add("Seguro");

        ListComponentDialog.newInstance(R.string.transaction_tag, ItemList.asList(list)).addOnItemSelectedListener(new ListComponentAdapter.OnItemSelectedListener() {
            @Override
            public void onSelect(ItemList item) {
                ItemVO vo = new ItemVO(item.getName());
                TransactionManagerDialog.newInstance(vo).show(getSupportFragmentManager(), "dialog");
            }
        }).addOnAddItemListenner(new ListComponentAdapter.OnAddItemListenner() {
            @Override
            public void onItemAdded(String content) {
                Toast.makeText(HomeActivity.this, "Item Added: " + content, Toast.LENGTH_SHORT).show();
            }
        }).show(getSupportFragmentManager());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                showFilter();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showFilter() {
        FilterTransactionDialog.newInstance().show(getSupportFragmentManager(), FilterTransactionDialog.TAG);
    }
}
