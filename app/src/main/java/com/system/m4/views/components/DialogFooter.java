package com.system.m4.views.components;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

import com.system.m4.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Enir on 20/05/2017.
 * For M4
 */

public class DialogFooter extends LinearLayout {

    @BindView(R.id.base_dialog_btn_cancel)
    Button mBtnCancel;

    @BindView(R.id.base_dialog_btn_done)
    Button mBtnDone;

    private OnClickListener listener;

    public DialogFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.base_tab_buttom_dialog, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.base_dialog_btn_done)
    public void onDoneClick() {
        if (listener != null) {
            listener.onDoneClick();
        }
    }

    @OnClick(R.id.base_dialog_btn_cancel)
    public void onCancelClick() {
        if (listener != null) {
            listener.onCancelClick();
        }
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    /**
     * Listenner
     */
    public interface OnClickListener {

        void onDoneClick();

        void onCancelClick();

    }
}
