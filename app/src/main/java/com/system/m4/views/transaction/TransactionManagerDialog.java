package com.system.m4.views.transaction;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.system.m4.R;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.views.BaseDialogFragment;
import com.system.m4.views.components.dialogs.list.ItemList;
import com.system.m4.views.components.dialogs.list.ListComponentDialog;
import com.system.m4.views.components.dialogs.list.OnItemSelectedListener;
import com.system.m4.views.components.dialogs.number.NumberComponentDialog;
import com.system.m4.views.components.dialogs.text.TextComponentDialog;

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

public class TransactionManagerDialog extends BaseDialogFragment {

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

    public static DialogFragment newInstance() {
        return new TransactionManagerDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_transaction_manager, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.transaction_manager_title);
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
        NumberComponentDialog.show(getChildFragmentManager(), "Value", new BaseDialogFragment.OnFinishListener() {
            @Override
            public void onFinish(String value) {
                tvValue.setText(JavaUtils.NumberUtil.currencyFormat(value));
            }
        });
    }

    @OnClick(R.id.transaction_manager_action_tags)
    public void actionTags() {

        List<ItemList> list = new ArrayList<>();
        list.add(new ItemList("Moradia"));
        list.add(new ItemList("Aluguel"));
        list.add(new ItemList("Celular"));
        list.add(new ItemList("Internet"));
        list.add(new ItemList("Automovel"));
        list.add(new ItemList("Seguro"));

        ListComponentDialog.newInstance("Tags", list, new OnItemSelectedListener() {
            @Override
            public void onSelect(ItemList item) {
                tvTags.setText(item.getName());
            }
        }).show(getChildFragmentManager(), "Tags");
    }

    @OnClick(R.id.transaction_manager_action_payment_type)
    public void actionPaymentType() {

        List<ItemList> list = new ArrayList<>();
        list.add(new ItemList("Nubank"));
        list.add(new ItemList("Dinheiro"));
        list.add(new ItemList("Itaucard"));
        list.add(new ItemList("Transferência Itaú"));
        list.add(new ItemList("Transferência Bradesco"));

        ListComponentDialog.newInstance("Payment Type", list, new OnItemSelectedListener() {
            @Override
            public void onSelect(ItemList item) {
                tvPaymentType.setText(item.getName());
            }
        }).show(getChildFragmentManager(), "dialog");
    }

    @OnClick(R.id.transaction_manager_action_content)
    public void actionContent() {
        TextComponentDialog.show(getChildFragmentManager(), "Content", new BaseDialogFragment.OnFinishListener() {
            @Override
            public void onFinish(String value) {
                tvContent.setText(value);
            }
        });
    }
}
