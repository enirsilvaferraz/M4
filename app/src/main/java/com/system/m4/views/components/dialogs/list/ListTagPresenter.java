package com.system.m4.views.components.dialogs.list;

import com.system.m4.businness.TagBusinness;
import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.ConverterUtils;
import com.system.m4.repository.dtos.DTOAbs;
import com.system.m4.repository.dtos.TagDTO;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.VOInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Enir on 19/05/2017.
 * For M4
 */

public class ListTagPresenter extends ListComponentPresenterAbs {

    public ListTagPresenter(ListComponentContract.View view) {
        super(view);
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
                Collections.sort(voList);
                getView().renderList(voList);
            }

            @Override
            public void onError(Exception e) {
                getView().showError(e.getMessage());
            }
        });
    }

    @Override
    public void requestDelete() {

        TagBusinness.delete(ConverterUtils.fromTag((TagVO) getSelectedItem()), new BusinnessListener.OnPersistListener() {

            @Override
            public void onSuccess(DTOAbs dto) {
                getView().deleteItem(getSelectedItem());
                markItemOff();
            }

            @Override
            public void onError(Exception e) {
                getView().showError(e.getMessage());
            }
        });
    }

    @Override
    public void save(String value, final VOInterface vo) {

        vo.setName(value);
        TagBusinness.save(ConverterUtils.fromTag((TagVO) vo), new BusinnessListener.OnPersistListener() {

            @Override
            public void onSuccess(DTOAbs dto) {
                if (vo.getKey() != null) {
                    getView().changeItem(ConverterUtils.fromTag((TagDTO) dto));
                } else {
                    getView().addItem(ConverterUtils.fromTag((TagDTO) dto));
                }
                getView().markItemOff();
            }

            @Override
            public void onError(Exception e) {
                getView().showError(e.getMessage());
            }
        });
    }

    protected TagVO getVoInstance() {
        return new TagVO();
    }
}
