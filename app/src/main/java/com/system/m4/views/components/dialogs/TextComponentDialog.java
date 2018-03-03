package com.system.m4.views.components.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.system.m4.R;
import com.system.m4.infrastructure.Constants;
import com.system.m4.views.BaseDialogFragment;
import com.system.m4.views.components.DialogToolbar;

/**
 * Created by eferraz on 14/04/17.
 * Text component dialog
 */

public class TextComponentDialog extends BaseDialogFragment {

    public static final String TAG = TextComponentDialog.class.getSimpleName();

    DialogToolbar mToolbar;
    EditText etText;

    public static TextComponentDialog newInstance(@StringRes int title, String value, OnFinishListener onFinishListener) {

        Bundle bundle = new Bundle();
        bundle.putInt(Constants.TITLE_BUNDLE, title);
        bundle.putString(Constants.VALUE_BUNDLE, value);

        TextComponentDialog dialog = new TextComponentDialog();
        dialog.setArguments(bundle);
        dialog.setOnFinishListener(onFinishListener);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_text_component, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mToolbar = view.findViewById(R.id.dialog_toolbar_title);
        mToolbar.setTitle(getArguments().getInt(Constants.TITLE_BUNDLE));
        setTextContent(getArguments().getString(Constants.VALUE_BUNDLE));

        etText = view.findViewById(R.id.dialog_edit_text);

        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
    }

    public void setTextContent(String name) {
        etText.setText(name);
        etText.requestFocus();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDoneClick() {
        dismiss();
        getOnFinishListener().onFinish(etText.getText().toString());
    }

    @Override
    public void onTitleClick() {
        // DO NOTHING
    }
}
