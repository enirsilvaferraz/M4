package com.system.m4.views.components;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.system.m4.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Enir on 07/05/2017.
 * For M4
 */

public class DialogToolbar extends RelativeLayout {

    @BindView(R.id.dialog_title)
    TextView mTitle;

    @BindView(R.id.dialog_add_item)
    ImageButton mAddItem;

    @BindView(R.id.dialog_edit_item)
    ImageButton mEditItem;

    @BindView(R.id.dialog_delete_item)
    ImageButton mDeleteItem;

    private OnClickListener onClickListener;

    public DialogToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.base_toolbar_dialog, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        configureCreateMode();
    }

    public void configureEditMode() {
        mAddItem.setVisibility(GONE);
        mEditItem.setVisibility(VISIBLE);
        mDeleteItem.setVisibility(VISIBLE);
    }

    public void configureCreateMode() {
        mAddItem.setVisibility(VISIBLE);
        mEditItem.setVisibility(GONE);
        mDeleteItem.setVisibility(GONE);
    }

    public void setOnClickListener(DialogToolbar.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setTitle(String titleString) {
        mTitle.setText(titleString);
    }

    public void setTitle(@StringRes int titleRes) {
        mTitle.setText(titleRes);
    }

    @OnClick(R.id.dialog_add_item)
    public void onClickAdd() {
        if (onClickListener != null) {
            onClickListener.onAddClick();
        }
    }

    @OnClick(R.id.dialog_edit_item)
    public void onClickEdit() {
        if (onClickListener != null) {
            onClickListener.onEditClick();
        }
    }

    @OnClick(R.id.dialog_delete_item)
    public void onClickDelete() {
        if (onClickListener != null) {
            onClickListener.onDeleteClick();
        }
    }

    /**
     * Listenner
     */
    public interface OnClickListener {

        void onAddClick();

        void onEditClick();

        void onDeleteClick();
    }
}
