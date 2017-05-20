package com.system.m4.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.system.m4.R;
import com.system.m4.views.components.DialogFooter;
import com.system.m4.views.components.DialogToolbar;
import com.system.m4.views.vos.VOInterface;

import butterknife.BindView;

/**
 * Created by Enir on 20/04/2017.
 * Base dialog
 */

public abstract class BaseDialogFragment extends DialogFragment implements DialogFooter.OnClickListener, DialogToolbar.OnClickListener {

    public static final String TITLE_BUNDLE = "TITLE_BUNDLE";

    @BindView(R.id.dialog_toolbar_title)
    public DialogToolbar mToolbar;

    @BindView(R.id.base_dialog_container_action)
    public DialogFooter mFooter;

    @Deprecated
    private OnFinishListener onFinishListener;

    private DialogListener dialogListener;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar.setListener(this);
        mFooter.setListener(this);
    }

    protected void hideFooter() {
        mFooter.setVisibility(View.GONE);
    }

    protected void setTitle(@StringRes int titleId) {
        mToolbar.setTitle(titleId);
    }

    protected void setTitle(String titleString) {
        mToolbar.setTitle(titleString);
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

    @Override
    public void onAddClick() {
        Toast.makeText(getContext(), "Not implemented yet!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditClick() {
        Toast.makeText(getContext(), "Not implemented yet!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClick() {
        Toast.makeText(getContext(), "Not implemented yet!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDoneClick() {
        Toast.makeText(getContext(), "Not implemented yet!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelClick() {
        dismiss();
    }

    public DialogListener getDialogListener() {
        return dialogListener;
    }

    public void setDialogListener(DialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    public interface OnFinishListener {
        void onFinish(String value);
    }

    public interface DialogListener {
        void onFinish(VOInterface vo);
    }
}
