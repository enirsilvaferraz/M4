package com.system.m4.businness;

import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.Constants;
import com.system.m4.kotlin.infrastructure.listeners.MultResultListener;
import com.system.m4.kotlin.tags.TagBusiness;
import com.system.m4.kotlin.tags.TagModel;
import com.system.m4.repository.dtos.TagDTO;
import com.system.m4.repository.firebase.FirebaseRepository;
import com.system.m4.repository.firebase.TagFirebaseRepository;
import com.system.m4.views.vos.TagVO;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class TagBusinness {

    private TagBusinness() {
        // Nothing to do
    }

    public static void findAll(final BusinnessListener.OnMultiResultListenner<TagVO> onMultiResultListenner) {

        TagBusiness.Companion.findAll(new MultResultListener<TagModel>() {
            @Override
            public void onSuccess(@NotNull ArrayList<TagModel> list) {

                List<TagVO> newList = new ArrayList<>();
                for (TagModel model : list) {
                    newList.add(new TagVO(model.getKey(), model.getParentName(), model.getName()));
                }

                onMultiResultListenner.onSuccess(newList, Constants.CALL_TAG_FINDALL);
            }

            @Override
            public void onError(@NotNull String error) {
                onMultiResultListenner.onError(new Exception(error));
            }
        });
    }

    public static void save(final TagDTO tagDTO, final BusinnessListener.OnPersistListener persistListener) {

        new TagFirebaseRepository().save(tagDTO, new FirebaseRepository.FirebaseSingleReturnListener<TagDTO>() {

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

        new TagFirebaseRepository().delete(tagDTO, new FirebaseRepository.FirebaseSingleReturnListener<TagDTO>() {

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