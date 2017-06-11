package com.system.m4.repository.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.system.m4.repository.dtos.TransactionDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eferraz on 01/05/17.
 * For M4
 */

public class TransactionFirebaseRepository extends FirebaseRepository<TransactionDTO> {

    public TransactionFirebaseRepository(int year, int month) {
        super("Register/" + year + "/" + (month + 1) + "/Transaction/");
    }

    @Deprecated
    public TransactionFirebaseRepository(String name) {
        super(name);
    }

    @Override
    protected Class<TransactionDTO> getTClass() {
        return TransactionDTO.class;
    }

    public void findByFilter(final FirebaseMultiReturnListener<TransactionDTO> multiReturnListener) {

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<TransactionDTO> list = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    list.add(getTInstance(postSnapshot));
                }
                multiReturnListener.onFindAll(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                multiReturnListener.onError(databaseError.getMessage());
            }
        };

        getDatabaseRef().orderByChild("paymentDate")
//                .startAt(JavaUtils.DateUtil.format(start, JavaUtils.DateUtil.YYYY_MM_DD))
//                .endAt(JavaUtils.DateUtil.format(end, JavaUtils.DateUtil.YYYY_MM_DD))
                .addListenerForSingleValueEvent(listener);
    }
}
