package com.system.m4.labs.components;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.system.m4.R;

/**
 * Created by Enir on 07/05/2017.
 * For M4
 */

public class DialogToolbar extends RelativeLayout {

    TextView mTitle;
    ImageButton mAddItem;
    ImageButton mEditItem;
    ImageButton mDeleteItem;

    private OnClickListener listener;

    public DialogToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.base_toolbar_dialog, this);

        mTitle = findViewById(R.id.dialog_title);
        mAddItem = findViewById(R.id.dialog_add_item);
        mEditItem = findViewById(R.id.dialog_edit_item);
        mDeleteItem = findViewById(R.id.dialog_delete_item);

        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickTitle();
            }
        });
        mAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAdd();
            }
        });
        mEditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEdit();
            }
        });
        mDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDelete();
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        configureNoActionMode();
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

    public void configureNoActionMode() {
        mAddItem.setVisibility(GONE);
        mEditItem.setVisibility(GONE);
        mDeleteItem.setVisibility(GONE);
    }

    public void setListener(DialogToolbar.OnClickListener listener) {
        this.listener = listener;
    }

    public void setTitle(String titleString) {
        mTitle.setText(titleString);
    }

    public void setTitle(@StringRes int titleRes) {
        mTitle.setText(titleRes);
    }

    public void onClickTitle() {
        if (listener != null) {
            listener.onTitleClick();
        }
    }

    public void onClickAdd() {
        if (listener != null) {
            listener.onAddClick();
        }
    }

    public void onClickEdit() {
        if (listener != null) {
            listener.onEditClick();
        }
    }

    public void onClickDelete() {
        if (listener != null) {
            listener.onDeleteClick();
        }
    }

    /**
     * Listenner
     */
    public interface OnClickListener {

        void onTitleClick();

        void onAddClick();

        void onEditClick();

        void onDeleteClick();
    }
}
