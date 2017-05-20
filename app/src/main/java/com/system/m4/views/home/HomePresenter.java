package com.system.m4.views.home;

import com.system.m4.R;
import com.system.m4.businness.TagBusinness;
import com.system.m4.businness.TransactionBusinness;
import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.repository.dtos.DTOAbs;
import com.system.m4.repository.dtos.TagDTO;
import com.system.m4.views.vos.FilterTransactionVO;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.TransactionVO;
import com.system.m4.views.vos.VOInterface;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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

        FilterTransactionVO vo = new FilterTransactionVO();
        vo.setPaymentDateStart(JavaUtils.DateUtil.getActualMinimum(new Date()));
        vo.setPaymentDateEnd(JavaUtils.DateUtil.getActualMaximum(new Date()));

        TransactionBusinness.findByFilter(vo, new BusinnessListener.OnMultiResultListenner<TransactionVO>() {

            @Override
            public void onSuccess(List<TransactionVO> list) {

                Collections.sort(list, new Comparator<TransactionVO>() {
                    @Override
                    public int compare(TransactionVO vo0, TransactionVO vo1) {
                        return vo0.getPaymentDate().compareTo(vo1.getPaymentDate());
                    }
                });

                view.setListTransactions(list);
            }

            @Override
            public void onError(Exception e) {
                view.showError(e.getMessage());
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
            public void onSuccess(DTOAbs dto) {
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
            public void onSuccess(DTOAbs dto) {
                view.showSuccessMessage(R.string.system_message_deleted, R.string.transaction_tag);
            }

            @Override
            public void onError(Exception e) {
                view.showError(e.getMessage());
            }
        });
    }
}
