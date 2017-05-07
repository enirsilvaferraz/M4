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
import android.widget.Toast;

import com.system.m4.R;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.views.BaseDialogFragment;
import com.system.m4.views.components.dialogs.NumberComponentDialog;
import com.system.m4.views.components.dialogs.TextComponentDialog;
import com.system.m4.views.components.dialogs.list.ListComponentDialog;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.TransactionVO;
import com.system.m4.views.vos.VOInterface;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.Unbinder;

/**
 * Created by Enir on 12/04/2017.
 * Dialog de manutencao de transacoes
 */

public class TransactionManagerDialog extends BaseDialogFragment implements TransactionManagerContract.View {

    public static final String TAG = TransactionManagerDialog.class.getSimpleName();
    private static final String BUNDLE_VO = "BUNDLE_VO";
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

    public static DialogFragment newInstance(TransactionVO transactionVO) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_VO, transactionVO);

        TransactionManagerDialog fragment = new TransactionManagerDialog();
        fragment.setArguments(bundle);
        return fragment;
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
        configureModel(((TransactionVO) getArguments().getParcelable(BUNDLE_VO)));
    }

    private void configureModel(TransactionVO transactionVO) {
        setTitle(transactionVO.getTags());
        presenter.setTags(transactionVO.getTags());
        presenter.setContent(transactionVO.getContent());
        presenter.setPaymentDate(transactionVO.getPaymentDate());
        presenter.setPurchaseDate(transactionVO.getPurchaseDate());
        presenter.setPaymentType(transactionVO.getPaymentType());
        presenter.setValue(transactionVO.getValue());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void actionDone() {
        presenter.validateForm();
    }

    @OnClick(R.id.transaction_manager_action_payment_date)
    public void actionPaymentDate() {
        presenter.requestPaymentDateDialog(tvPaymentDate.getText().toString());
    }

    @OnClick(R.id.transaction_manager_action_purchase_date)
    public void actionPurchaseDate() {
        presenter.requestPurchaseDateDialog(tvPurchaseDate.getText().toString());
    }

    @OnClick(R.id.transaction_manager_action_value)
    public void actionValue() {
        presenter.requestValueDialog(tvValue.getText().toString());
    }

    @OnClick(R.id.transaction_manager_action_tags)
    public void actionTags() {
        presenter.requestTagDialog();
    }

    @OnClick(R.id.transaction_manager_action_payment_type)
    public void actionPaymentType() {
        presenter.requestPaymentTypeDialog();
    }

    @OnClick(R.id.transaction_manager_action_content)
    public void actionContent() {
        presenter.requestContentDialog(tvContent.getText().toString());
    }

    @OnLongClick(R.id.transaction_manager_action_payment_date)
    public boolean clearPaymentDate() {
        presenter.clearPaymentDateDialog();
        tvPaymentDate.setText(R.string.system_empty_field);
        return true;
    }

    @OnLongClick(R.id.transaction_manager_action_purchase_date)
    public boolean clearPurchaseDate() {
        presenter.clearPurchaseDateDialog();
        tvPurchaseDate.setText(R.string.system_empty_field);
        return true;
    }

    @OnLongClick(R.id.transaction_manager_action_value)
    public boolean clearValue() {
        presenter.clearValueDialog();
        tvValue.setText(R.string.system_empty_field);
        return true;
    }

    @OnLongClick(R.id.transaction_manager_action_tags)
    public boolean clearTags() {
        presenter.clearTagDialog();
        tvTags.setText(R.string.system_empty_field);
        return true;
    }

    @OnLongClick(R.id.transaction_manager_action_payment_type)
    public boolean clearPaymentType() {
        presenter.clearPaymentType();
        tvPaymentType.setText(R.string.system_empty_field);
        return true;
    }

    @OnLongClick(R.id.transaction_manager_action_content)
    public boolean clearContent() {
        presenter.clearContent();
        tvContent.setText(R.string.system_empty_field);
        return true;
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
    public void showTagsDialog(List<TagVO> list) {
        ListComponentDialog.newInstance(R.string.transaction_tag, new ArrayList<VOInterface>(list)).addOnItemListenner(new ListComponentDialog.OnItemListenner() {

            @Override
            public VOInterface onIntanceRequested() {
                return null;
            }

            @Override
            public void onItemAdded(VOInterface item) {
                presenter.saveTag(item.getName());
            }

            @Override
            public void onItemDeleted(VOInterface item) {
                presenter.deleteTag(item.getKey());
            }

            @Override
            public void onItemSelected(VOInterface item) {
                presenter.setTags(item.getName());
            }

        }).show(getChildFragmentManager());
    }

    @Override
    public void showPaymentTypeDialog(List<PaymentTypeVO> list) {
        ListComponentDialog.newInstance(R.string.transaction_payment_type, new ArrayList<VOInterface>(list)).addOnItemListenner(new ListComponentDialog.OnItemListenner() {

            @Override
            public VOInterface onIntanceRequested() {
                return null;
            }

            @Override
            public void onItemAdded(VOInterface item) {
                presenter.savePaymentType(item.getName());
            }

            @Override
            public void onItemDeleted(VOInterface item) {
                presenter.deletePaymentType(item.getKey());
            }

            @Override
            public void onItemSelected(VOInterface item) {
                presenter.setPaymentType(item.getName());
            }

        }).show(getChildFragmentManager());
    }

    @Override
    public void showValueDialog(Double value) {
        NumberComponentDialog.newInstance(R.string.transaction_price, value, new OnFinishListener() {
            @Override
            public void onFinish(String value) {
                presenter.setValue(value);
            }
        }).show(getChildFragmentManager());
    }

    @Override
    public void showContentDialog(String value) {
        TextComponentDialog.newInstance(R.string.transaction_content, value, new OnFinishListener() {
            @Override
            public void onFinish(String value) {
                presenter.setContent(value);
            }
        }).show(getChildFragmentManager());
    }

    @Override
    public void showPaymentDateDialog(Date date) {
        JavaUtils.AndroidUtil.showDatePicker(getContext(), date, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                presenter.setPaymentDate(JavaUtils.DateUtil.getDateString(year, month, dayOfMonth));
            }
        });
    }

    @Override
    public void showPurchaseDateDialog(Date date) {
        JavaUtils.AndroidUtil.showDatePicker(getContext(), date, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                presenter.setPurchaseDate(JavaUtils.DateUtil.getDateString(year, month, dayOfMonth));
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
