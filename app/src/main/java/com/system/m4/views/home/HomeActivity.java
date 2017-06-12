package com.system.m4.views.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.system.m4.R;
import com.system.m4.views.BaseDialogFragment;
import com.system.m4.views.filter.FilterTransactionDialog;
import com.system.m4.views.vos.VOInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    private static final int READ_SMS_PERMISSIONS_REQUEST = 1;

    @BindView(R.id.home_activity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.home_activity_fab)
    FloatingActionButton fab;

    private HomeContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.requestTransactionManager();
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            getPermissionToReadSMS();
        }
    }

    public void getPermissionToReadSMS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_SMS)) {
                Toast.makeText(this, "Please allow permission!", Toast.LENGTH_SHORT).show();
            }
            requestPermissions(new String[]{Manifest.permission.READ_SMS}, READ_SMS_PERMISSIONS_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == READ_SMS_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read SMS permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Read SMS permission denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);

        if (fragment instanceof HomeContract.View) {
            HomeContract.View view = (HomeContract.View) fragment;
            presenter = new HomePresenter(view);
            view.setPresenter(presenter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        menu.findItem(R.id.action_delete).setVisible(false);
        menu.findItem(R.id.action_copy).setVisible(false);
        menu.findItem(R.id.action_pin).setVisible(false);
        menu.findItem(R.id.action_unpin).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            showFilter();
            return true;
        }
        if (item.getItemId() == R.id.action_delete) {
            presenter.requestDelete();
            return true;
        }
        if (item.getItemId() == R.id.action_copy) {
            presenter.requestCopy();
            return true;
        }
        if (item.getItemId() == R.id.action_pin) {
            presenter.pinTransaction(true);
            return true;
        }
        if (item.getItemId() == R.id.action_unpin) {
            presenter.pinTransaction(false);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showFilter() {
        FilterTransactionDialog.newInstance(new BaseDialogFragment.DialogListener() {
            @Override
            public void onFinish(VOInterface vo) {
                presenter.requestListTransaction();
            }
        }).show(getSupportFragmentManager(), FilterTransactionDialog.class.getSimpleName());
    }

    public void configureEditMode(boolean canPin) {
        toolbar.getMenu().findItem(R.id.action_filter).setVisible(false);
        toolbar.getMenu().findItem(R.id.action_delete).setVisible(true);
        toolbar.getMenu().findItem(R.id.action_copy).setVisible(true);
        toolbar.getMenu().findItem(R.id.action_pin).setVisible(canPin);
        toolbar.getMenu().findItem(R.id.action_unpin).setVisible(!canPin);
    }

    public void configureReadMode() {
        toolbar.getMenu().findItem(R.id.action_filter).setVisible(true);
        toolbar.getMenu().findItem(R.id.action_delete).setVisible(false);
        toolbar.getMenu().findItem(R.id.action_copy).setVisible(false);
        toolbar.getMenu().findItem(R.id.action_pin).setVisible(false);
        toolbar.getMenu().findItem(R.id.action_unpin).setVisible(false);
    }
}
