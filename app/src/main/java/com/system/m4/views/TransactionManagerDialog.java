package com.system.m4.views;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.m4.R;

public class TransactionManagerDialog extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_transaction_manager, container, false);
        return v;
    }

    public static DialogFragment newInstance() {
        return new TransactionManagerDialog();
    }
}
