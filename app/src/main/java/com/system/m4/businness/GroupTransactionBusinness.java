package com.system.m4.businness;

import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.repository.dtos.GroupTransactionDTO;
import com.system.m4.repository.firebase.FirebaseRepository;
import com.system.m4.repository.firebase.GroupTransactionRepository;

import java.util.List;

/**
 * Created by eferraz on 09/06/17.
 * For M4
 */

public class GroupTransactionBusinness {

    public static void findAll(final BusinnessListener.OnMultiResultListenner<GroupTransactionDTO> onMultiResultListenner) {

        new GroupTransactionRepository().findAll(new FirebaseRepository.FirebaseMultiReturnListener<GroupTransactionDTO>() {

            @Override
            public void onFindAll(List<GroupTransactionDTO> list) {
                onMultiResultListenner.onSuccess(list);
            }

            @Override
            public void onError(String error) {
                onMultiResultListenner.onError(new Exception(error));
            }
        });
    }
}
