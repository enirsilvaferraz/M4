package com.system.m4.views.components.dialogs.number;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.system.m4.R;
import com.system.m4.views.BaseDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by eferraz on 14/04/17.
 */

public class NumberComponentDialog extends BaseDialogFragment {

    @BindView(R.id.dialog_edit_number)
    EditText etNumber;

    Unbinder unbinder;

    public static DialogFragment newInstance(String title, OnFinishListener onFinishListener) {

        Bundle bundle = new Bundle();
        bundle.putString("TITLE", title);

        NumberComponentDialog dialog = new NumberComponentDialog();
        dialog.setArguments(bundle);
        dialog.setOnFinishListener(onFinishListener);
        return dialog;
    }

    public static void show(FragmentManager fragmentManager, String title, OnFinishListener onFinishListener) {

        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag("dialog2");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment newFragment = NumberComponentDialog.newInstance(title, onFinishListener);
        newFragment.show(ft, "dialog2");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_number_component, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle(getArguments().getString("TITLE"));
        etNumber.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void actionDone() {
        dismiss();
        getOnFinishListener().onFinish(etNumber.getText().toString());
    }
}
