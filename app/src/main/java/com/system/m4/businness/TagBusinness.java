package com.system.m4.businness;

import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.repository.dtos.TagDTO;
import com.system.m4.repository.firebase.FirebaseRepository;
import com.system.m4.repository.firebase.TagFirebaseRepository;

import java.util.List;

public abstract class TagBusinness {

    private TagBusinness() {
        // Nothing to do
    }

    public static void requestTagList(final BusinnessListener.OnMultiResultListenner<TagDTO> onMultiResultListenner) {

        new TagFirebaseRepository("dev").findAll(new FirebaseRepository.FirebaseMultiReturnListener<TagDTO>() {

            @Override
            public void onFindAll(List<TagDTO> list) {
                onMultiResultListenner.onSuccess(list);
            }

            @Override
            public void onError(String error) {
                onMultiResultListenner.onError(new Exception(error));
            }
        });
    }

    public static void save(final TagDTO tagDTO, final BusinnessListener.OnPersistListener persistListener) {

        new TagFirebaseRepository("dev").save(tagDTO, new FirebaseRepository.FirebaseSingleReturnListener<TagDTO>() {

            @Override
            public void onFind(TagDTO dto) {
                persistListener.onSuccess(dto);
            }

            @Override
            public void onError(String error) {
                persistListener.onError(new Exception(error));
            }
        });
    }

    public static void delete(TagDTO tagDTO, final BusinnessListener.OnPersistListener persistListener) {

        new TagFirebaseRepository("dev").delete(tagDTO, new FirebaseRepository.FirebaseSingleReturnListener<TagDTO>() {

            @Override
            public void onFind(TagDTO dto) {
                persistListener.onSuccess(dto);
            }

            @Override
            public void onError(String error) {
                persistListener.onError(new Exception(error));
            }
        });
    }
}