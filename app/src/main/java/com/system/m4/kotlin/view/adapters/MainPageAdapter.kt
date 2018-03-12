package com.system.m4.kotlin.view.adapters

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.SparseArray
import android.view.ViewGroup
import com.system.m4.kotlin.view.fragments.HomeFragment

/**
 * Created by eferraz on 16/07/17.
 */

internal class MainPageAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val registeredFragments = SparseArray<Fragment>()

    override fun getItem(position: Int): Fragment {
        val args = Bundle()
        args.putInt(HomeFragment.RELATIVE_POSITION, position - PAGE_MIDDLE)

        val fragment = HomeFragment()
        fragment.arguments = args
        return fragment
    }

    override fun getCount(): Int {
        return PAGE_MIDDLE * 2
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        registeredFragments.put(position, fragment)
        return fragment
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any) {
        registeredFragments.remove(position)
        super.destroyItem(container, position, `object`)
    }

    override fun getItemPosition(`object`: Any?): Int {
        if (`object` is HomeFragment) {
            `object`.requestListTransaction()
        }
        return super.getItemPosition(`object`)
    }

    fun getRegisteredFragment(position: Int): Fragment {
        return registeredFragments.get(position)
    }

    companion object {

        val PAGE_MIDDLE = 6
    }
}
