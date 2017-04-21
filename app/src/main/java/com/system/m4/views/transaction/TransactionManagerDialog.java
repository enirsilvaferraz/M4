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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Enir on 12/04/2017.
 * Dialog de manutencao de transacoes
 */

public class TransactionManagerDialog extends BaseDialogFragment implements TransactionManagerContract.View {

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

    private TransactionManagerContract.Presenter presenter;

    public static DialogFragment newInstance() {
        return new TransactionManagerDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_transaction_manager, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        presenter = new TransactionManagerPresenter(this);
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
                presenter.setPaymentDate(year, month, dayOfMonth);
            }
        });
    }

    @OnClick(R.id.transaction_manager_action_purchase_date)
    public void actionPurchaseDate() {
        JavaUtils.AndroidUtil.showDatePicker(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                presenter.setPurchaseDate(year, month, dayOfMonth);
            }
        });
    }

    @OnClick(R.id.transaction_manager_action_value)
    public void actionValue() {
        NumberComponentDialog.show(getChildFragmentManager(), "Value", new BaseDialogFragment.OnFinishListener() {
            @Override
            public void onFinish(String value) {
                presenter.setValue(value);
            }
        });
    }

    @OnClick(R.id.transaction_manager_action_tags)
    public void actionTags() {
        presenter.requestTagList();
    }

    @OnClick(R.id.transaction_manager_action_payment_type)
    public void actionPaymentType() {
        presenter.requestPaymentTypeList();
    }

    @OnClick(R.id.transaction_manager_action_content)
    public void actionContent() {
        TextComponentDialog.show(getChildFragmentManager(), "Content", new BaseDialogFragment.OnFinishListener() {
            @Override
            public void onFinish(String value) {
                presenter.setContent(value);
            }
        });
    }

    @Override
    public void setPaymentDate(String value) {
        tvPaymentDate.setText(value);
    }

    @Override
    public void setPurchaseDate(String value) {
        tvPurchaseDate.setText(value);
    }

    @Override
    public void setValue(String value) {
        tvValue.setText(value);
    }

    @Override
    public void setTags(String value) {
        tvTags.setText(value);
    }

    @Override
    public void setPaymentType(String value) {
        tvPaymentType.setText(value);
    }

    @Override
    public void setContent(String value) {
        tvContent.setText(value);
    }

    @Override
    public void showTagsList(List<String> list) {
        ListComponentDialog.newInstance("Tags", ItemList.asList(list), new OnItemSelectedListener() {
            @Override
            public void onSelect(ItemList item) {
                presenter.setTags(item.getName());
            }
        }).show(getChildFragmentManager(), "Tags");
    }

    @Override
    public void showPaymentTypeList(List<String> list) {
        ListComponentDialog.newInstance("Payment Type", ItemList.asList(list), new OnItemSelectedListener() {
            @Override
            public void onSelect(ItemList item) {
                presenter.setPaymentType(item.getName());
            }
        }).show(getChildFragmentManager(), "dialog");
    }
}
