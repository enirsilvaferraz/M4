package com.system.m4.views;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.system.m4.R;

public class TransactionManagerDialog extends DialogFragment {

    public static DialogFragment newInstance() {
        return new TransactionManagerDialog();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_transaction_manager, container, false);
    }
}
