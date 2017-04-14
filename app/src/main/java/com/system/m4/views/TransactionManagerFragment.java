package com.system.m4.views;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.system.m4.R;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.views.components.ListComponentDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Enir on 12/04/2017.
 */

public class TransactionManagerFragment extends Fragment {

    Unbinder unbinder;

    @BindView(R.id.transaction_manager_textview_payment_date)
    TextView tvPaymentDate;

    @BindView(R.id.transaction_manager_textview_purchase_date)
    TextView tvPurchaseDate;

    @BindView(R.id.transaction_manager_textview_value)
    TextView tvValue;

    @BindView(R.id.transaction_manager_textview_tags)
    TextView tvTags;

    @BindView(R.id.transaction_manager_textview_payment_type)
    TextView tvPaymentType;

    @BindView(R.id.transaction_manager_textview_content)
    TextView tvContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transaction_manager, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.transaction_manager_action_payment_date)
    public void actionPaymentDate() {

        JavaUtils.AndroidUtil.showDatePicker(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Date date = JavaUtils.DateUtil.getDate(year, month, dayOfMonth);
                tvPaymentDate.setText(JavaUtils.DateUtil.format(date, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY));
            }
        });
    }

    @OnClick(R.id.transaction_manager_action_purchase_date)
    public void actionPurchaseDate() {

        JavaUtils.AndroidUtil.showDatePicker(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Date date = JavaUtils.DateUtil.getDate(year, month, dayOfMonth);
                tvPurchaseDate.setText(JavaUtils.DateUtil.format(date, JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY));
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


        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        Fragment prev = getChildFragmentManager().findFragmentByTag("dialog2");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);


        List<String> list = new ArrayList<>();
        list.add("Moradia");
        list.add("Aluguel");
        list.add("Celular");
        list.add("Internet");
        list.add("Automovel");
        list.add("Seguro");

        DialogFragment newFragment = ListComponentDialog.newInstance(list);
        newFragment.show(ft, "dialog2");

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
