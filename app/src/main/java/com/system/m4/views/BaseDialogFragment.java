package com.system.m4.views;

import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.system.m4.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Enir on 20/04/2017.
 * Base dialog
 */

public abstract class BaseDialogFragment extends DialogFragment {

    public static final String TITLE_BUNDLE = "TITLE_BUNDLE";
    public static final String LIST_BUNDLE = "LIST_ARG";

    @BindView(R.id.base_dialog_container_action)
    LinearLayout containerAction;

    @BindView(R.id.base_dialog_btn_done)
    Button btnDone;

    private OnFinishListener onFinishListener;

    @OnClick(R.id.base_dialog_btn_cancel)
    public void actionCancel() {
        dismiss();
    }

    @OnClick(R.id.base_dialog_btn_done)
    public void actionDone() {
        Toast.makeText(getContext(), "Not implemented yet!", Toast.LENGTH_SHORT).show();
    }

    protected void hideDoneBtn() {
        containerAction.setVisibility(View.GONE);
    }

    protected void setTitle(@StringRes int titleId) {

    }

    protected void setTitle(String titleString) {
    }

    public OnFinishListener getOnFinishListener() {
        return onFinishListener;
    }

    public void setOnFinishListener(OnFinishListener onFinishListener) {
        this.onFinishListener = onFinishListener;
    }

    public void show(FragmentManager fragmentManager) {

        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag(getClass().getSimpleName());
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        super.show(ft, getClass().getSimpleName());
    }

    public interface OnFinishListener {
        void onFinish(String value);
    }
}
