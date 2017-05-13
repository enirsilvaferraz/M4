package com.system.m4.views.components.dialogs.list;

import android.support.annotation.NonNull;

import com.system.m4.views.vos.VOInterface;

import java.util.List;

/**
 * Created by Enir on 12/05/2017.
 * For M4
 */

public interface ListComponentContract {

    interface View {

        void renderList(List<VOInterface> list);

        void showNewItemDialog(@NonNull VOInterface vo);

        void addNewItem(@NonNull VOInterface vo);

        void deleteItem(@NonNull VOInterface vo);

        void selectItem(@NonNull VOInterface vo);

        void markItemOn();

        void markItemOff();
    }

    interface Presenter {

        void init();

        void requestAdd();

        void requestEdit();

        void requestDelete();

        void save(String value, VOInterface vo);

        void selectItem(VOInterface item);

        void markItemOn(VOInterface item);

        void markItemOff();
    }
}
