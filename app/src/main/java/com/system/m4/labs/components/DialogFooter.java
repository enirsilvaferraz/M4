package com.system.m4.labs.components;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.system.m4.R;

/**
 * Created by Enir on 20/05/2017.
 * For M4
 */

public class DialogFooter extends LinearLayout {

    Button mBtnCancel;
    Button mBtnDone;

    private OnClickListener listener;

    public DialogFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.base_tab_buttom_dialog, this);

        mBtnCancel = findViewById(R.id.base_dialog_btn_cancel);
        mBtnDone = findViewById(R.id.base_dialog_btn_done);

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCancelClick();
            }
        });
        mBtnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDoneClick();
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void onDoneClick() {
        if (listener != null) {
            listener.onDoneClick();
        }
    }

    public void onCancelClick() {
        if (listener != null) {
            listener.onCancelClick();
        }
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void showLoading() {
        mBtnCancel.setVisibility(INVISIBLE);
        mBtnDone.setVisibility(INVISIBLE);
    }

    public void hideLoading() {
        mBtnCancel.setVisibility(VISIBLE);
        mBtnDone.setVisibility(VISIBLE);
    }

    /**
     * Listenner
     */
    public interface OnClickListener {

        void onDoneClick();

        void onCancelClick();

    }
}
