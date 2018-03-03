package com.system.m4.views.components.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.system.m4.R;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.views.BaseDialogFragment;

/**
 * Created by eferraz on 14/04/17.
 * Number compoenent dialog
 */

public class NumberComponentDialog extends BaseDialogFragment {

    CurrencyEditText etNumber;

    public static NumberComponentDialog newInstance(@StringRes int title, Double value, OnFinishListener onFinishListener) {

        Bundle bundle = new Bundle();
        bundle.putInt("TITLE", title);

        if (value != null) {
            bundle.putDouble("VALUE", value);
        }

        NumberComponentDialog dialog = new NumberComponentDialog();
        dialog.setArguments(bundle);
        dialog.setOnFinishListener(onFinishListener);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_number_component, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle(getArguments().getInt("TITLE"));

        etNumber = view.findViewById(R.id.dialog_edit_number);
        etNumber.setDefaultHintEnabled(false);
        etNumber.requestFocus();

        double value = getArguments().containsKey("VALUE") ? getArguments().getDouble("VALUE") : 0d;
        etNumber.setText(JavaUtils.NumberUtil.currencyFormat(value));

        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDoneClick() {
        dismiss();
        getOnFinishListener().onFinish(etNumber.getText().toString());
    }

    @Override
    public void onTitleClick() {
        // DO NOTHING
    }
}
