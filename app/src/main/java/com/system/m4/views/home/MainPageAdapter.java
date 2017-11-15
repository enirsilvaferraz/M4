package com.system.m4.views.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * Created by eferraz on 16/07/17.
 */

class MainPageAdapter extends FragmentStatePagerAdapter {

    public static final int PAGE_MIDDLE = 6;

    private SparseArray<Fragment> registeredFragments = new SparseArray<>();

    MainPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putInt(HomeFragment.RELATIVE_POSITION, position - PAGE_MIDDLE);
        args.putInt(HomeFragment.ITEM_VIEW, HomeVisibility.ALL);

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return (PAGE_MIDDLE * 2);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    @Override
    public int getItemPosition(Object object) {
        if (object instanceof HomeFragment) {
            ((HomeFragment) object).requestListTransaction();
        }
        return super.getItemPosition(object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}
