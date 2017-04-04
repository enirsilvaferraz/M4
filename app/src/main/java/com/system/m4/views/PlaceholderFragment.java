package com.system.m4.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.system.m4.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "ARG_SECTION_NUMBER";

    public PlaceholderFragment() {
    }

    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(configureView(getArguments().getInt(ARG_SECTION_NUMBER)), container, false);
        return rootView;
    }

    private int configureView(int anInt) {
        switch (anInt){
            case 0:
                return R.layout.fragment_transaction_manager_buy_date;
            case 1:
                return R.layout.fragment_transaction_manager_payment_date;
            case 2:
                return R.layout.fragment_transaction_manager_value;
            default:
                return -1;
        }
    }
}