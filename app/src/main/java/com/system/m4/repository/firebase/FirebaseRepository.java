package com.system.m4.repository.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.system.m4.BuildConfig;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.repository.dtos.DTOAbs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Enir on 07/09/2016.
 * For AndroidPigBank
 */

public abstract class FirebaseRepository<T extends DTOAbs> {

    private DatabaseReference databaseRef;

    public FirebaseRepository(String databaseName) {
        databaseRef = FirebaseDatabase.getInstance().getReference(BuildConfig.FLAVOR + "/" + databaseName);
    }


    public void save(T dto, FirebaseSingleReturnListener<T> listener) {

        if (JavaUtils.StringUtil.isEmpty(dto.getKey())) {
            DatabaseReference push = getDatabaseRef().push();
            dto.setKey(push.getKey());
            push.setValue(dto);
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put(dto.getKey(), JavaUtils.ClassUtil.pojo2Map(dto));
            getDatabaseRef().updateChildren(map);
        }

        listener.onFind(dto);
    }

    public void findByKey(String key, final FirebaseSingleReturnListener<T> firebaseSingleReturnListener) {

        Query reference = getDatabaseRef().child(key).orderByKey();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firebaseSingleReturnListener.onFind(getTInstance(dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                firebaseSingleReturnListener.onError(databaseError.getMessage());
            }
        });
    }

    T getTInstance(DataSnapshot postSnapshot) {
        T entity = postSnapshot.getValue(getTClass());
        entity.setKey(postSnapshot.getKey());
        return entity;
    }

    protected abstract Class<T> getTClass();

    public void findAll(final FirebaseMultiReturnListener<T> firebaseMultiReturnListener) {

        Query reference = getDatabaseRef().orderByKey();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<T> list = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    list.add(getTInstance(postSnapshot));
                }
                firebaseMultiReturnListener.onFindAll(list);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                firebaseMultiReturnListener.onError(databaseError.getMessage());
            }
        });
    }

    public void delete(final T entity, final FirebaseSingleReturnListener<T> listener) {
        getDatabaseRef().child(entity.getKey()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                listener.onFind(entity);
            }
        });
    }

    DatabaseReference getDatabaseRef() {
        return databaseRef;
    }

    /**
     *
     */
    public interface FirebaseMultiReturnListener<T extends DTOAbs> {

        void onFindAll(List<T> list);

        void onError(String error);
    }

    /**
     *
     */
    public interface FirebaseSingleReturnListener<T extends DTOAbs> {

        void onFind(T list);

        void onError(String error);
    }
}
