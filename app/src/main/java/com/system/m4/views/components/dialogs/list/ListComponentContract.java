package com.system.m4.views.components.dialogs.list;

import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;

import com.system.m4.views.components.DialogToolbar;
import com.system.m4.views.vos.VOInterface;

import java.util.List;

/**
 * Created by Enir on 12/05/2017.
 * For M4
 */

interface ListComponentContract {

    interface View extends DialogToolbar.OnClickListener {

        void renderList(List<VOInterface> list);

        void showNewItemDialog(@NonNull VOInterface vo);

        void addItem(@NonNull VOInterface vo);

        void changeItem(@NonNull VOInterface vo);

        void deleteItem(@NonNull VOInterface vo);

        void selectItem(@NonNull VOInterface vo);

        void markItemOff();

        void closeDialog();

        void showError(String message);

        void configureCreateMode();

        void configureEditMode();

        String[] getStringArray(@ArrayRes int months);

        void configureNoActionMode();
    }

    interface Presenter {

        void init();

        void selectItem(VOInterface item);

        void requestAdd();

        void requestEdit();

        void requestDelete();

        void save(String value, VOInterface vo);

        void markItemOn(VOInterface item);

        void markItemOff();
    }

}
