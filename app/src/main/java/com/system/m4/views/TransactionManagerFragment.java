package com.system.m4.views;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.system.m4.R;
import com.system.m4.infrastructure.JavaUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Enir on 12/04/2017.
 */

public class TransactionManagerFragment extends Fragment {

    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transaction_manager, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.transaction_manager_action_payment_date)
    public void actionPaymentDate(View view) {

        JavaUtils.AndroidUtil.showDatePicker(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String result = JavaUtils.DateUtil.format(JavaUtils.DateUtil.getDate(year, month, dayOfMonth));
                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.transaction_manager_action_purchase_date)
    public void actionPurchaseDate() {

        JavaUtils.AndroidUtil.showDatePicker(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String result = JavaUtils.DateUtil.format(JavaUtils.DateUtil.getDate(year, month, dayOfMonth));
                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.transaction_manager_action_value)
    public void actionValue() {
        Toast.makeText(getContext(), "In development...", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.transaction_manager_action_tags)
    public void actionTags() {
        Toast.makeText(getContext(), "In development...", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.transaction_manager_action_payment_type)
    public void actionPaymentType() {
        Toast.makeText(getContext(), "In development...", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.transaction_manager_action_content)
    public void actionContent() {
        Toast.makeText(getContext(), "In development...", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.transaction_manager_btn_other_transaction)
    public void actionOtherTransaction() {
        Toast.makeText(getContext(), "In development...", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.transaction_manager_btn_cancel)
    public void actionCancel() {
        Toast.makeText(getContext(), "In development...", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.transaction_manager_btn_done)
    public void actionDone() {
        Toast.makeText(getContext(), "In development...", Toast.LENGTH_SHORT).show();
    }
}
