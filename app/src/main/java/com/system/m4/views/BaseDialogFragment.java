package com.system.m4.views;

import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.system.m4.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Enir on 20/04/2017.
 */

public abstract class BaseDialogFragment extends DialogFragment {

    @BindView(R.id.base_dialog_btn_done)
    Button btnDone;

    @BindView(R.id.dialog_title)
    TextView tvTitle;

    private OnFinishListener onFinishListener;

    @OnClick(R.id.base_dialog_btn_cancel)
    public void actionCancel() {
       dismiss();
    }

    @OnClick(R.id.base_dialog_btn_done)
    public abstract void actionDone();

    protected void hideDoneBtn(){
        btnDone.setVisibility(View.GONE);
    }

    protected void setTitle(String title){
        tvTitle.setText(title);
    }

    public void setOnFinishListener(OnFinishListener onFinishListener) {
        this.onFinishListener = onFinishListener;
    }

    public OnFinishListener getOnFinishListener() {
        return onFinishListener;
    }

    public interface OnFinishListener{
        void onFinish(String value);
    }
}
