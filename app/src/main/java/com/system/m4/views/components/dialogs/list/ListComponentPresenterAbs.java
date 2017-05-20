package com.system.m4.views.components.dialogs.list;

import com.system.m4.views.vos.VOInterface;

/**
 * Created by eferraz on 18/05/17.
 * For M4
 */

abstract class ListComponentPresenterAbs implements ListComponentContract.Presenter {

    private ListComponentContract.View mView;
    private VOInterface mSelectedItem;

    ListComponentPresenterAbs(ListComponentContract.View view) {
        mView = view;
    }

    @Override
    public void selectItem(VOInterface item) {
        mView.selectItem(item);
        mView.closeDialog();
    }

    @Override
    public void requestAdd() {
        mView.showNewItemDialog(getVoInstance());
    }

    protected abstract VOInterface getVoInstance();

    @Override
    public void requestEdit() {
        mView.showNewItemDialog(mSelectedItem);
    }

    @Override
    public void markItemOn(VOInterface item) {
        this.mSelectedItem = item;
        mView.configureEditMode();
    }

    @Override
    public void markItemOff() {
        this.mSelectedItem = null;
        mView.configureCreateMode();
    }

    public ListComponentContract.View getView() {
        return mView;
    }

    public VOInterface getSelectedItem() {
        return mSelectedItem;
    }
}
