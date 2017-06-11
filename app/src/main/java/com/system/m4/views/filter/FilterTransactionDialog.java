package com.system.m4.views.filter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.system.m4.R;
import com.system.m4.views.BaseDialogFragment;
import com.system.m4.views.components.dialogs.list.ListComponentDialog;
import com.system.m4.views.components.dialogs.list.ListMonthPresenter;
import com.system.m4.views.components.dialogs.list.ListPaymentTypePresenter;
import com.system.m4.views.components.dialogs.list.ListTagPresenter;
import com.system.m4.views.components.dialogs.list.ListYearPresenter;
import com.system.m4.views.vos.FilterTransactionVO;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.SimpleItemListVO;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.VOInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.Unbinder;

/**
 * Created by eferraz on 27/04/17.
 * For M4
 */

public class FilterTransactionDialog extends BaseDialogFragment implements FilterTransactionContract.View {

    @BindView(R.id.transaction_manager_textview_tag)
    TextView tvTag;

    @BindView(R.id.transaction_manager_textview_payment_type)
    TextView tvPaymentType;

    @BindView(R.id.transaction_manager_textview_payment_date_start)
    TextView tvYear;

    @BindView(R.id.transaction_manager_textview_payment_date_end)
    TextView tvMonth;

    Unbinder unbinder;

    private FilterTransactionContract.Presenter presenter;

    public static DialogFragment newInstance(DialogListener dialogListener) {
        Bundle bundle = new Bundle();

        FilterTransactionDialog fragment = new FilterTransactionDialog();
        fragment.setArguments(bundle);
        fragment.setDialogListener(dialogListener);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_transaction_filter, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        mFooter.setListener(this);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setTitle(R.string.transaction_filter_title);
        presenter = new FilterTransactionPresenter(this);
        presenter.init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.transaction_manager_action_tag)
    public void showTagDialog() {
        ListComponentDialog listComponentTagsDialog = ListComponentDialog.newInstance(R.string.transaction_tag, new DialogListener() {
            @Override
            public void onFinish(VOInterface vo) {
                presenter.setTag((TagVO) vo);
            }
        });
        listComponentTagsDialog.setPresenter(new ListTagPresenter(listComponentTagsDialog));
        listComponentTagsDialog.show(getChildFragmentManager());
    }

    @OnClick(R.id.transaction_manager_action_payment_type)
    public void showPaymentTypeDialog() {
        ListComponentDialog listComponentPaymentTypeDialog = ListComponentDialog.newInstance(R.string.transaction_payment_type, new DialogListener() {
            @Override
            public void onFinish(VOInterface vo) {
                presenter.setPaymentType(((PaymentTypeVO) vo));
            }
        });
        listComponentPaymentTypeDialog.setPresenter(new ListPaymentTypePresenter(listComponentPaymentTypeDialog));
        listComponentPaymentTypeDialog.show(getChildFragmentManager());
    }

    @OnClick(R.id.transaction_manager_action_payment_date_start)
    public void showYearDialog() {
        ListComponentDialog listDialog = ListComponentDialog.newInstance(R.string.transaction_year, new DialogListener() {
            @Override
            public void onFinish(VOInterface vo) {
                presenter.setYear(((SimpleItemListVO) vo));
            }
        });
        listDialog.setPresenter(new ListYearPresenter(listDialog));
        listDialog.show(getChildFragmentManager());
    }

    @OnClick(R.id.transaction_manager_action_payment_date_end)
    public void showMonthDialog() {
        ListComponentDialog listDialog = ListComponentDialog.newInstance(R.string.transaction_month, new DialogListener() {
            @Override
            public void onFinish(VOInterface vo) {
                presenter.setMonth(((SimpleItemListVO) vo));
            }
        });
        listDialog.setPresenter(new ListMonthPresenter(listDialog));
        listDialog.show(getChildFragmentManager());
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
    public boolean clearYear() {
        presenter.clearYear();
        tvYear.setText(R.string.system_empty_field);
        return true;
    }

    @OnLongClick(R.id.transaction_manager_action_payment_date_end)
    public boolean clearMonth() {
        presenter.clearMonth();
        tvMonth.setText(R.string.system_empty_field);
        return true;
    }

    @Override
    public void setYear(String value) {
        tvYear.setText(value);
    }

    @Override
    public void setMonth(String value) {
        tvMonth.setText(value);
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
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String[] getStringArray(int months) {
        return getResources().getStringArray(months);
    }

    @Override
    public void showError(@StringRes int template, @StringRes int... params) {
        String[] arrayString = new String[params.length];
        for (int i = 0; i < params.length; i++) {
            arrayString[i] = getString(params[i]);
        }
        Toast.makeText(getContext(), getString(template, (Object[]) arrayString), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dismissDialog(FilterTransactionVO vo) {
        dismiss();
        getDialogListener().onFinish(vo);
    }

    @Override
    public void onDoneClick() {
        presenter.done();
    }

    @Override
    public void onCancelClick() {
        dismiss();
    }

    @Override
    public void onDeleteClick() {
        presenter.delete();
    }
}
