package com.system.m4.kotlin.view.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.WindowManager
import android.widget.Toast
import com.system.m4.R
import com.system.m4.infrastructure.JavaUtils
import com.system.m4.kotlin.contracts.HomeContract
import com.system.m4.kotlin.contracts.MainContract
import com.system.m4.kotlin.contracts.TagListContract
import com.system.m4.kotlin.model.entity.TagModel
import com.system.m4.kotlin.model.services.ExportToCSVService
import com.system.m4.kotlin.model.services.NotificationReceiver
import com.system.m4.kotlin.presenters.HomePresenter
import com.system.m4.kotlin.presenters.MainPresenter
import com.system.m4.kotlin.view.adapters.MainPageAdapter
import com.system.m4.kotlin.view.fragments.TagListDialog
import com.system.m4.kotlin.view.fragments.TransactionManagerDialog
import com.system.m4.labs.vos.TagVO
import com.system.m4.labs.vos.TransactionVO

class MainActivity : AppCompatActivity(), MainContract.View {

    lateinit var toolbar: Toolbar
    lateinit var fab: FloatingActionButton
    lateinit var mViewPager: ViewPager
    lateinit var mCollapsingToolbar: CollapsingToolbarLayout

    private var presenter: MainContract.Presenter? = null

    private val isNLServiceRunning: Boolean
        get() {
            val enabledNotificationListeners = Settings.Secure.getString(this.contentResolver, "enabled_notification_listeners")
            return enabledNotificationListeners == null || enabledNotificationListeners.contains(NotificationReceiver::class.java.name)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.home_activity_toolbar)
        fab = findViewById(R.id.home_activity_fab)
        mViewPager = findViewById(R.id.home_view_pager)
        mCollapsingToolbar = findViewById(R.id.main_collapsingToolbar)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        setSupportActionBar(toolbar)

        presenter = MainPresenter(this)

        fab.setOnClickListener { presenter!!.requestTransactionManager() }

        fab.setOnLongClickListener {
            ExportToCSVService.startActionBackup(this@MainActivity)
            true
        }

        mViewPager.adapter = MainPageAdapter(supportFragmentManager)
        mViewPager.currentItem = MainPageAdapter.PAGE_MIDDLE
        //mViewPager.setOffscreenPageLimit(MainPageAdapter.PAGE_MIDDLE * 2);
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {/* DO NOTHING */
            }

            override fun onPageSelected(position: Int) {
                presenter!!.configureTitle(position)
            }

            override fun onPageScrollStateChanged(state: Int) {/* DO NOTHING */
            }
        })

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            getPermissionToReadSMS()
        }

        if (!isNLServiceRunning) {
            JavaUtils.AndroidUtil.showAlertDialog(this, "Enable notification manager?") { dialog, which -> startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")) }
        }
    }

    fun getPermissionToReadSMS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_SMS)) {
                Toast.makeText(this, "Please allow permission!", Toast.LENGTH_SHORT).show()
            }
            requestPermissions(arrayOf(Manifest.permission.READ_SMS), READ_SMS_PERMISSIONS_REQUEST)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == READ_SMS_PERMISSIONS_REQUEST) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Read SMS permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Read SMS permission denied", Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private lateinit var homeFragment: Fragment

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)

        if (fragment is HomeContract.View) {
            val view = fragment as HomeContract.View?
            view!!.setPresenter(HomePresenter(view))
            this@MainActivity.homeFragment = fragment
        }
    }

    override fun requestTransactionManagerDialog() {

        TagListDialog.instance(object : TagListContract.OnSelectedListener {
            override fun onSelect(model: TagModel) {
                presenter!!.requestTransactionDialog(TagVO(model.key, null, model.name))
            }
        }).show(supportFragmentManager, TagListDialog::class.java.simpleName)
    }

    override fun showTransactionDialog(vo: TagVO) {
        showTransactionDialog(TransactionVO(vo))
    }

    override fun showTransactionDialog(vo: TransactionVO) {
        TransactionManagerDialog.newInstance(vo, homeFragment, 1).show(supportFragmentManager, TransactionManagerDialog::class.java.simpleName)
    }

    override fun setMainTitle(title: String) {
        mCollapsingToolbar.title = title
    }

    companion object {

        private val READ_SMS_PERMISSIONS_REQUEST = 1
    }
}
