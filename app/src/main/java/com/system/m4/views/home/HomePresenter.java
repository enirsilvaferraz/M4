package com.system.m4.views.home;

import com.system.m4.businness.TagBusinness;
import com.system.m4.businness.TransactionBusinness;
import com.system.m4.repository.dtos.TagDTO;
import com.system.m4.repository.dtos.TransactionDTO;
import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.TransactionVO;

import java.util.List;

/**
 * Created by eferraz on 29/04/17.
 * For M4
 */

public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void requestListTransaction() {

        TransactionBusinness.requestTransactions(new BusinnessListener.OnMultiResultListenner<TransactionDTO>() {

            @Override
            public void onSuccess(List<TransactionDTO> list) {
                view.setListTransactions(TransactionVO.asList(list));
            }

            @Override
            public void onError(Exception e) {

            }
        });

    }

    @Override
    public void requestTransactionManager() {

        TagBusinness.requestTagList(new BusinnessListener.OnMultiResultListenner<TagDTO>() {

            @Override
            public void onSuccess(List<TagDTO> list) {
                view.showTransactionManager(TagVO.asList(list));
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    public void saveTag(String tag) {

        TagBusinness.saveTag(new TagDTO(tag), new BusinnessListener.OnPersistListener() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
