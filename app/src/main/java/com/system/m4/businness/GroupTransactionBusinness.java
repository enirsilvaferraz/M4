package com.system.m4.businness;

import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.Constants;
import com.system.m4.infrastructure.ConverterUtils;
import com.system.m4.repository.dtos.GroupTransactionDTO;
import com.system.m4.repository.firebase.FirebaseRepository;
import com.system.m4.repository.firebase.GroupTransactionRepository;
import com.system.m4.views.vos.GroupTransactionVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eferraz on 09/06/17.
 * For M4
 */

public class GroupTransactionBusinness {

    public static void findAll(final BusinnessListener.OnMultiResultListenner<GroupTransactionVO> onMultiResultListenner) {

        new GroupTransactionRepository().findAll(new FirebaseRepository.FirebaseMultiReturnListener<GroupTransactionDTO>() {

            @Override
            public void onFindAll(List<GroupTransactionDTO> list) {
                List<GroupTransactionVO> listVo = new ArrayList<>();
                for (GroupTransactionDTO dto : list) {
                    listVo.add(ConverterUtils.INSTANCE.fromGroupTransaction(dto));
                }
                onMultiResultListenner.onSuccess(listVo, Constants.CALL_GROUP_FINDALL);
            }

            @Override
            public void onError(String error) {
                onMultiResultListenner.onError(new Exception(error));
            }
        });
    }
}
