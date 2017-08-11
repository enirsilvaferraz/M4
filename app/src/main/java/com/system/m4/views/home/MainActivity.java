package com.system.m4.views.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.system.m4.R;
import com.system.m4.views.BaseDialogFragment;
import com.system.m4.views.components.dialogs.list.ListComponentDialog;
import com.system.m4.views.components.dialogs.list.ListTagPresenter;
import com.system.m4.views.transaction.TransactionManagerDialog;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.Transaction;
import com.system.m4.views.vos.VOInterface;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final int READ_SMS_PERMISSIONS_REQUEST = 1;

    @BindView(R.id.home_activity_toolbar)
    Toolbar toolbar;

    @BindView(R.id.home_activity_fab)
    FloatingActionButton fab;

    @BindView(R.id.home_view_pager)
    ViewPager mViewPager;

    @BindView(R.id.main_collapsingToolbar)
    CollapsingToolbarLayout mCollapsingToolbar;

    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        presenter = new MainPresenter(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.requestTransactionManager();
            }
        });

        mViewPager.setAdapter(new MainPageAdapter(getSupportFragmentManager()));
        mViewPager.setCurrentItem(MainPageAdapter.PAGE_MIDDLE);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {/* DO NOTHING */}

            @Override
            public void onPageSelected(int position) {
                presenter.configureTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {/* DO NOTHING */}
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
            view.setPresenter(new HomePresenter(view));
        }
    }

    @Override
    public void requestTransactionManagerDialog() {

        ListComponentDialog listComponentDialog = ListComponentDialog.newInstance(R.string.transaction_tag, new BaseDialogFragment.DialogListener() {
            @Override
            public void onFinish(VOInterface vo) {
                presenter.requestTransactionDialog((TagVO) vo);
            }
        });

        listComponentDialog.setPresenter(new ListTagPresenter(listComponentDialog));
        listComponentDialog.show(getSupportFragmentManager(), ListComponentDialog.class.getSimpleName());
    }

    @Override
    public void showTransactionDialog(TagVO vo) {
        showTransactionDialog(new Transaction(vo));
    }

    @Override
    public void showTransactionDialog(Transaction vo) {
        TransactionManagerDialog dialogFragment = TransactionManagerDialog.newInstance(vo);
        dialogFragment.setDialogListener(new BaseDialogFragment.DialogListener() {
            @Override
            public void onFinish(VOInterface vo) {
                mViewPager.getAdapter().notifyDataSetChanged();
            }
        });
        dialogFragment.show(getSupportFragmentManager(), TransactionManagerDialog.class.getSimpleName());
    }

    public void setMainTitle(String title){
        mCollapsingToolbar.setTitle(title);
    }
}
