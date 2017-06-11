package com.system.m4;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.system.m4.businness.TransactionBusinness;
import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.ConverterUtils;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.repository.dtos.DTOAbs;
import com.system.m4.repository.dtos.TransactionDTO;
import com.system.m4.repository.firebase.FirebaseRepository;
import com.system.m4.views.vos.Transaction;

import java.util.Calendar;
import java.util.List;

/**
 * Created by eferraz on 10/06/17.
 * For M4
 */

public class M4Service {

    public void moveTransaction() {

        TransactionBusinness.findAll(new BusinnessListener.OnMultiResultListenner<Transaction>() {

            @Override
            public void onSuccess(List<Transaction> list, int call) {

                for (Transaction transaction : list) {

                    newsave2(transaction);
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void newsave2(Transaction transaction) {

        TransactionBusinness.save(transaction, new BusinnessListener.OnPersistListener() {
            @Override
            public void onSuccess(DTOAbs dto) {

            }

            @Override
            public void onError(Exception e) {

            }
        });

    }

    private void newsave(Transaction vo) {

        TransactionDTO dto = ConverterUtils.fromTransaction(vo);

        Integer year = JavaUtils.DateUtil.get(Calendar.YEAR, JavaUtils.DateUtil.parse(dto.getPaymentDate(), JavaUtils.DateUtil.YYYY_MM_DD));
        Integer month = JavaUtils.DateUtil.get(Calendar.MONTH, JavaUtils.DateUtil.parse(dto.getPaymentDate(), JavaUtils.DateUtil.YYYY_MM_DD));

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference(BuildConfig.FLAVOR + "/Register/" + year + "/" + month + "/Transaction/");

        DatabaseReference push = databaseRef.push();
        dto.setKey(push.getKey());
        push.setValue(dto);
    }

    public void findTransaction() {

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);

        new FirebaseRepository<TransactionDTO>("Register/" + year + "/" + month + "/Transaction/") {

            @Override
            protected Class<TransactionDTO> getTClass() {
                return TransactionDTO.class;
            }

        }.findAll(new FirebaseRepository.FirebaseMultiReturnListener<TransactionDTO>() {

            @Override
            public void onFindAll(List<TransactionDTO> list) {
                list.toString();
            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
