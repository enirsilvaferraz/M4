package com.system.m4.views.home;

import com.system.m4.R;
import com.system.m4.businness.TagBusinness;
import com.system.m4.businness.TransactionBusinness;
import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.repository.dtos.TagDTO;
import com.system.m4.repository.dtos.TransactionDTO;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.TransactionVO;
import com.system.m4.views.vos.VOInterface;

import java.util.List;

/**
 * Created by eferraz on 29/04/17.
 * For M4
 */

class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View view;

    HomePresenter(HomeContract.View view) {
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

        view.showTransactionManagerDialog(); // Colocar PLaceholder e remover if list == null do adapter

        TagBusinness.requestTagList(new BusinnessListener.OnMultiResultListenner<TagDTO>() {

            @Override
            public void onSuccess(List<TagDTO> list) {
                view.configureListTagsTransactionManager(TagVO.asList(list));
            }

            @Override
            public void onError(Exception e) {
                view.showError(e.getMessage());
            }
        });
    }

    @Override
    public void saveTag(VOInterface vo) {

        TagBusinness.save(new TagDTO(((TagVO) vo)), new BusinnessListener.OnPersistListener() {

            @Override
            public void onSuccess() {
                view.showSuccessMessage(R.string.system_message_saved, R.string.transaction_tag);
            }

            @Override
            public void onError(Exception e) {
                view.showError(e.getMessage());
            }
        });
    }

    @Override
    public void deleteTag(VOInterface vo) {

        TagBusinness.delete(new TagDTO(((TagVO) vo)), new BusinnessListener.OnPersistListener() {

            @Override
            public void onSuccess() {
                view.showSuccessMessage(R.string.system_message_deleted, R.string.transaction_tag);
            }

            @Override
            public void onError(Exception e) {
                view.showError(e.getMessage());
            }
        });
    }
}
