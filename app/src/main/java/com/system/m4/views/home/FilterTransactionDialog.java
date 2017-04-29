package com.system.m4.views.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.m4.R;
import com.system.m4.views.BaseDialogFragment;

/**
 * Created by eferraz on 27/04/17.
 */

public class FilterTransactionDialog extends BaseDialogFragment {


    public static DialogFragment newInstance() {

        Bundle bundle = new Bundle();

        FilterTransactionDialog fragment = new FilterTransactionDialog();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_transaction_filter, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.transaction_filter_title);
    }
}
