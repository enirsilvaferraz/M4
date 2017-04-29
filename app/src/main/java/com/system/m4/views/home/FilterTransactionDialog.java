package com.system.m4.views.home;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.system.m4.R;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.views.BaseDialogFragment;
import com.system.m4.views.components.dialogs.list.ItemList;
import com.system.m4.views.components.dialogs.list.ListComponentDialog;
import com.system.m4.views.components.dialogs.list.OnItemSelectedListener;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.Unbinder;

/**
 * Created by eferraz on 27/04/17.
 */

public class FilterTransactionDialog extends BaseDialogFragment implements TransactionFilterContract.View {


    public static final String TAG = FilterTransactionDialog.class.getSimpleName();

    @BindView(R.id.transaction_manager_textview_tag)
    TextView tvTag;
    @BindView(R.id.transaction_manager_textview_payment_type)
    TextView tvPaymentType;
    @BindView(R.id.transaction_manager_textview_payment_date_start)
    TextView tvPaymentDateStart;
    @BindView(R.id.transaction_manager_textview_payment_date_end)
    TextView tvPaymentDateEnd;

    Unbinder unbinder;
    private TransactionFilterContract.Presenter presenter;

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
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.transaction_filter_title);
        presenter = new TransactionFilterPresenter(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.transaction_manager_action_tag)
    public void showTagDialog() {
        presenter.requestTagDialog();
    }

    @OnClick(R.id.transaction_manager_action_payment_type)
    public void showPaymentTypeDialog() {
        presenter.requestPaymentTypeDialog();
    }

    @OnClick(R.id.transaction_manager_action_payment_date_start)
    public void showPaymentDateStartDialog() {
        presenter.requestPaymentDateStartDialog(tvPaymentDateStart.getText().toString());
    }

    @OnClick(R.id.transaction_manager_action_payment_date_end)
    public void showPaymentDateEndDialog() {
        presenter.requestPaymentDateEndDialog(tvPaymentDateEnd.getText().toString());
    }

    @OnLongClick(R.id.transaction_manager_action_tag)
    public boolean clearTagDialog() {
        presenter.clearTag();
        tvTag.setText(R.string.system_empty_field);
        return true;
    }

    @OnLongClick(R.id.transaction_manager_action_payment_type)
    public boolean clearPaymentTypeDialog() {
        presenter.clearPaymentType();
        tvPaymentType.setText(R.string.system_empty_field);
        return true;
    }

    @OnLongClick(R.id.transaction_manager_action_payment_date_start)
    public boolean clearPaymentDateStartDialog() {
        presenter.clearPaymentDateStart();
        tvPaymentDateStart.setText(R.string.system_empty_field);
        return true;
    }

    @OnLongClick(R.id.transaction_manager_action_payment_date_end)
    public boolean clearPaymentDateEndDialog() {
        presenter.clearPaymentDateEnd();
        tvPaymentDateEnd.setText(R.string.system_empty_field);
        return true;
    }

    @Override
    public void setPaymentDateStart(String value) {
        tvPaymentDateStart.setText(value);
    }

    @Override
    public void setPaymentDateEnd(String value) {
        tvPaymentDateEnd.setText(value);
    }

    @Override
    public void setTag(String value) {
        tvTag.setText(value);
    }

    @Override
    public void setPaymentType(String value) {
        tvPaymentType.setText(value);
    }


    @Override
    public void showTagsDialog(List<String> list) {
        ListComponentDialog.newInstance(R.string.transaction_manager_tags, ItemList.asList(list), new OnItemSelectedListener() {
            @Override
            public void onSelect(ItemList item) {
                presenter.setTags(item.getName());
            }
        }).show(getChildFragmentManager());
    }

    @Override
    public void showPaymentTypeDialog(List<String> list) {
        ListComponentDialog.newInstance(R.string.transaction_manager_payment_type, ItemList.asList(list), new OnItemSelectedListener() {
            @Override
            public void onSelect(ItemList item) {
                presenter.setPaymentType(item.getName());
            }
        }).show(getChildFragmentManager());
    }


    @Override
    public void showPaymentDateStartDialog(Date date) {
        JavaUtils.AndroidUtil.showDatePicker(getContext(), date, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                presenter.setPaymentDateStart(year, month, dayOfMonth);
            }
        });
    }

    @Override
    public void showPaymentDateEndDialog(Date date) {
        JavaUtils.AndroidUtil.showDatePicker(getContext(), date, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                presenter.setPaymentDateEnd(year, month, dayOfMonth);
            }
        });
    }

    @Override
    public void dismissDialog() {
        Toast.makeText(getContext(), "Transaction saved!", Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
        dismiss();
    }
}
