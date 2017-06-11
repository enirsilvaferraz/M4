package com.system.m4.views.components.dialogs.list;

import com.system.m4.R;
import com.system.m4.views.vos.SimpleItemListVO;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.VOInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enir on 19/05/2017.
 * For M4
 */

public class ListMonthPresenter extends ListComponentPresenterAbs {

    public ListMonthPresenter(ListComponentContract.View view) {
        super(view);
    }

    @Override
    public void init() {

        getView().configureNoActionMode();

        String [] arrayMonth = getView().getResources().getStringArray(R.array.months);
        List<VOInterface> voList = new ArrayList<>();
        for (int index = 0; index < arrayMonth.length; index++) {
            voList.add(new SimpleItemListVO(String.valueOf(index), arrayMonth[index]));
        }

        getView().renderList(voList);
    }

    @Override
    public void requestDelete() {
        // DO NOTHING
    }

    @Override
    public void save(String value, final VOInterface vo) {
        // DO NOTHING
    }

    @Override
    protected PaymentTypeVO getVoInstance() {
        return null;
    }
}
