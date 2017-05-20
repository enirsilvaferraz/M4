package com.system.m4.views.components.dialogs.list;

import com.system.m4.businness.TagBusinness;
import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.ConverterUtils;
import com.system.m4.repository.dtos.TagDTO;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.VOInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eferraz on 18/05/17.
 * For M4
 */

class ListTagPresenter implements ListComponentContract.Presenter {

    private ListComponentContract.View mView;
    private VOInterface selectedItem;

    ListTagPresenter(ListComponentContract.View view) {
        mView = view;
    }

    @Override
    public void requestList() {

        TagBusinness.requestTagList(new BusinnessListener.OnMultiResultListenner<TagDTO>() {

            @Override
            public void onSuccess(List<TagDTO> list) {
                List<VOInterface> voList = new ArrayList<>();
                for (TagDTO dto : list) {
                    voList.add(ConverterUtils.fromTag(dto));
                }
                mView.renderList(voList);
            }

            @Override
            public void onError(Exception e) {
                mView.showError(e.getMessage());
            }
        });
    }

    @Override
    public void selectItem(VOInterface item) {
        mView.selectItem(item);
    }

    @Override
    public void requestAdd() {
        mView.showNewItemDialog(new TagVO());
    }

    @Override
    public void requestEdit() {
        mView.showNewItemDialog(selectedItem);
    }

    @Override
    public void requestDelete() {

        TagBusinness.delete(ConverterUtils.fromTag((TagVO) selectedItem), new BusinnessListener.OnPersistListener() {

            @Override
            public void onSuccess() {
                mView.deleteItem(selectedItem);
                markItemOff();
            }

            @Override
            public void onError(Exception e) {
                mView.showError(e.getMessage());
            }
        });
    }

    @Override
    public void save(String value, final VOInterface vo) {

        vo.setName(value);
        TagBusinness.save(ConverterUtils.fromTag((TagVO) vo), new BusinnessListener.OnPersistListener() {

            @Override
            public void onSuccess() {
                if (vo.getKey() != null) {
                    mView.changeItem(vo);
                } else {
                    mView.addItem(vo);
                }
                mView.markItemOff();
            }

            @Override
            public void onError(Exception e) {
                mView.showError(e.getMessage());
            }
        });

    }

    @Override
    public void markItemOn(VOInterface item) {
        this.selectedItem = item;
        mView.configureEditMode();
    }

    @Override
    public void markItemOff() {
        this.selectedItem = null;
        mView.configureCreateMode();
    }
}
