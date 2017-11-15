package com.system.m4.kotlin.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.system.m4.R
import com.system.m4.views.home.HomeFragment
import com.system.m4.views.home.HomePresenter
import com.system.m4.views.home.HomeVisibility

class HomeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_detail)

        var relativePosition = intent.getIntExtra(HomeFragment.RELATIVE_POSITION, 0)
        var homeVisibility = intent.getIntExtra(HomeFragment.ITEM_VIEW, HomeVisibility.ALL)

        val arguments = Bundle()
        arguments.putInt(HomeFragment.RELATIVE_POSITION, relativePosition)
        arguments.putInt(HomeFragment.ITEM_VIEW, homeVisibility)

        var fragment = HomeFragment()
        fragment.arguments = arguments

        supportFragmentManager.beginTransaction().replace(R.id.container_fragment, fragment).commit()
    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)

        if (fragment is HomeFragment) {
            fragment.presenter = HomePresenter(fragment)
        }
    }
}
