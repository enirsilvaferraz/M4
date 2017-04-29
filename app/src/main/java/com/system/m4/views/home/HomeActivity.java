package com.system.m4.views.home;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.system.m4.R;
import com.system.m4.views.BaseDialogFragment;
import com.system.m4.views.components.dialogs.list.ItemList;
import com.system.m4.views.components.dialogs.list.ListComponentDialog;
import com.system.m4.views.components.dialogs.list.OnAddItemListenner;
import com.system.m4.views.components.dialogs.list.OnItemSelectedListener;
import com.system.m4.views.components.dialogs.text.TextComponentDialog;
import com.system.m4.views.filter.FilterTransactionDialog;
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

                ListComponentDialog.newInstance(R.string.transaction_tag, ItemList.asList(list), new OnItemSelectedListener() {
                    @Override
                    public void onSelect(ItemList item) {
                        TransactionManagerDialog.newInstance(item).show(getSupportFragmentManager(), "dialog");
                    }
                }).addOnAddItemListenner(new OnAddItemListenner() {
                    @Override
                    public void onItemAdded(ItemList item) {

                        TextComponentDialog.newInstance(R.string.transaction_tag, null, new BaseDialogFragment.OnFinishListener() {
                            @Override
                            public void onFinish(String value) {
                                TransactionManagerDialog.newInstance(new ItemList(value)).show(getSupportFragmentManager(), "dialog");
                            }
                        }).show(getSupportFragmentManager(), TextComponentDialog.TAG);

                    }
                }).show(getSupportFragmentManager());
            }
        });
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
