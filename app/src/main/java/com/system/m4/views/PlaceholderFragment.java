package com.system.m4.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    @BindView(R.id.section_label)
    TextView sectionLabel;

    Unbinder unbinder;

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
        View rootView = inflater.inflate(R.layout.fragment_transaction_manager, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        configureView(getArguments().getInt(ARG_SECTION_NUMBER));
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void configureView(Integer position) {

        switch (position){
            case 0:
                sectionLabel.setText("Data da Compra");

                break;
        }
    }
}