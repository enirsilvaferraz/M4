package com.system.m4.views.home;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.system.m4.R;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.kotlin.infrastructure.BaseDialogFragment;
import com.system.m4.kotlin.services.ExportToCSVService;
import com.system.m4.kotlin.services.NotificationReceiver;
import com.system.m4.kotlin.tags.TagListContract;
import com.system.m4.kotlin.tags.TagListDialog;
import com.system.m4.kotlin.tags.TagModel;
import com.system.m4.kotlin.transaction.TransactionManagerDialog;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.TransactionVO;
import com.system.m4.views.vos.VOInterface;

import org.jetbrains.annotations.NotNull;

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

        Window w = getWindow(); // in Activity's onCreate() for instance
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setSupportActionBar(toolbar);

        presenter = new MainPresenter(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.requestTransactionManager();
            }
        });

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ExportToCSVService.Companion.startActionBackup(MainActivity.this);
                return true;
            }
        });

        mViewPager.setAdapter(new MainPageAdapter(getSupportFragmentManager()));
        mViewPager.setCurrentItem(MainPageAdapter.PAGE_MIDDLE);
        //mViewPager.setOffscreenPageLimit(MainPageAdapter.PAGE_MIDDLE * 2);
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

        if (!isNLServiceRunning()) {
            JavaUtils.AndroidUtil.showAlertDialog(this, "Enable notification manager?", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                }
            });
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

        TagListDialog.Companion.instance(new TagListContract.OnSelectedListener() {
            @Override
            public void onSelect(@NotNull TagModel model) {
                TagVO vo = new TagVO();
                vo.setKey(model.getKey());
                vo.setName(model.getName());
                presenter.requestTransactionDialog(vo);
            }
        }).show(getSupportFragmentManager(), TagListDialog.class.getSimpleName());
    }

    @Override
    public void showTransactionDialog(TagVO vo) {
        showTransactionDialog(new TransactionVO(vo));
    }

    @Override
    public void showTransactionDialog(TransactionVO vo) {
        TransactionManagerDialog dialogFragment = TransactionManagerDialog.Companion.newInstance(vo);
        dialogFragment.setDialogListener(new BaseDialogFragment.DialogListener() {
            @Override
            public void onFinish(VOInterface vo) {
                mViewPager.getAdapter().notifyDataSetChanged();
            }
        });
        dialogFragment.show(getSupportFragmentManager(), TransactionManagerDialog.class.getSimpleName());
    }

    public void setMainTitle(String title) {
        mCollapsingToolbar.setTitle(title);
    }

    private boolean isNLServiceRunning() {
        String enabledNotificationListeners = Settings.Secure.getString(this.getContentResolver(), "enabled_notification_listeners");
        return enabledNotificationListeners == null || enabledNotificationListeners.contains(NotificationReceiver.class.getName());
    }
}
