package com.system.m4.views.transaction;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.system.m4.R;
import com.system.m4.infrastructure.Constants;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.kotlin.paymenttype.PaymentTypeListContract;
import com.system.m4.kotlin.paymenttype.PaymentTypeListDialog;
import com.system.m4.kotlin.paymenttype.PaymentTypeModel;
import com.system.m4.views.BaseDialogFragment;
import com.system.m4.views.components.dialogs.NumberComponentDialog;
import com.system.m4.views.components.dialogs.TextComponentDialog;
import com.system.m4.views.components.dialogs.list.ListComponentDialog;
import com.system.m4.views.components.dialogs.list.ListTagPresenter;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.Transaction;
import com.system.m4.views.vos.VOInterface;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

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

    Unbinder unbinder;

    @BindView(R.id.transaction_manager_textview_payment_date)
    TextView tvPaymentDate;

    @BindView(R.id.transaction_manager_textview_purchase_date)
    TextView tvPurchaseDate;

    @BindView(R.id.transaction_manager_textview_value)
    TextView tvValue;

    @BindView(R.id.transaction_manager_textview_payment_type)
    TextView tvPaymentType;

    @BindView(R.id.transaction_manager_textview_content)
    TextView tvContent;

    private TransactionManagerContract.Presenter presenter;

    private DialogListener dialogListener;

    public static TransactionManagerDialog newInstance(Transaction transaction) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BUNDLE_TRANSACTION_VO, transaction);

        TransactionManagerDialog fragment = new TransactionManagerDialog();
        fragment.setArguments(bundle);
        return fragment;
    }

    /*
     * LIFECYLCE
     */

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
        Transaction transaction = getArguments().getParcelable(Constants.BUNDLE_TRANSACTION_VO);
        presenter.init(transaction);
    }

    @Override
    public void configureModel(Transaction transaction) {
        setTitle(transaction.getTag().getName());
        presenter.setTags(transaction.getTag());
        presenter.setContent(transaction.getContent());
        presenter.setPaymentDate(transaction.getPaymentDate());
        presenter.setPurchaseDate(transaction.getPurchaseDate());
        presenter.setPaymentType(transaction.getPaymentType());
        presenter.setValue(transaction.getPrice());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

   /*
    * ACTIONS
    */

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

    @OnClick(R.id.transaction_manager_action_payment_type)
    public void actionPaymentType() {
//        ListComponentDialog listComponentPaymentTypeDialog = ListComponentDialog.newInstance(R.string.transaction_payment_type, new DialogListener() {
//            @Override
//            public void onFinish(VOInterface vo) {
//                TransactionManagerDialog.this.presenter.setPaymentType(((PaymentTypeVO) vo));
//            }
//        });
//        listComponentPaymentTypeDialog.setPresenter(new ListPaymentTypePresenter(listComponentPaymentTypeDialog));
//        listComponentPaymentTypeDialog.show(getChildFragmentManager());

        PaymentTypeListDialog.Companion.instance(new PaymentTypeListContract.OnSelectedListener() {
            @Override
            public void onSelect(@NotNull PaymentTypeModel model) {
                PaymentTypeVO vo = new PaymentTypeVO();
                vo.setKey(model.getKey());
                vo.setName(model.getName());
                vo.setColor(model.getColor());
                presenter.setPaymentType(vo);

            }
        }).show(getFragmentManager(), PaymentTypeListDialog.class.getSimpleName());
    }

    @OnClick(R.id.transaction_manager_action_content)
    public void actionContent() {
        presenter.requestContentDialog(tvContent.getText().toString());
    }

    @OnLongClick(R.id.transaction_manager_action_payment_date)
    public boolean clearPaymentDate() {
        presenter.clearPaymentDate();
        tvPaymentDate.setText(R.string.system_empty_field);
        return true;
    }

    @OnLongClick(R.id.transaction_manager_action_purchase_date)
    public boolean clearPurchaseDate() {
        presenter.clearPurchaseDate();
        tvPurchaseDate.setText(R.string.system_empty_field);
        return true;
    }

    @OnLongClick(R.id.transaction_manager_action_value)
    public boolean clearValue() {
        presenter.clearPrice();
        tvValue.setText(R.string.system_empty_field);
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
        mToolbar.setTitle(value);
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
    public void showValueDialog(Double value) {
        NumberComponentDialog.newInstance(R.string.transaction_price, value, new OnFinishListener() {
            @Override
            public void onFinish(String value) {
                presenter.setValue(JavaUtils.NumberUtil.removeFormat(value));
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
                presenter.setPaymentDate(JavaUtils.DateUtil.getDate(year, month, dayOfMonth));
            }
        });
    }

    @Override
    public void showPurchaseDateDialog(Date date) {
        JavaUtils.AndroidUtil.showDatePicker(getContext(), date, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                presenter.setPurchaseDate(JavaUtils.DateUtil.getDate(year, month, dayOfMonth));
            }
        });
    }

    @Override
    public void dismissDialog(VOInterface vo) {
        dismiss();
        dialogListener.onFinish(vo);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(int template, int param) {
        Toast.makeText(getContext(), getString(template, getString(param)), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccessMessage(int template, int param) {
        String message = getString(template, getString(param));
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void setDialogListener(DialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    /*
     * LISTENERS
     */

    @Override
    public void onDoneClick() {
        presenter.save();
    }

    @Override
    public void onTitleClick() {
        ListComponentDialog listComponentTagsDialog = ListComponentDialog.newInstance(R.string.transaction_tag, new DialogListener() {
            @Override
            public void onFinish(VOInterface vo) {
                TransactionManagerDialog.this.presenter.setTags((TagVO) vo);
            }
        });
        listComponentTagsDialog.setPresenter(new ListTagPresenter(listComponentTagsDialog));
        listComponentTagsDialog.show(getChildFragmentManager());
    }
}
