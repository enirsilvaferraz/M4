package com.system.m4.businness;

import com.system.m4.infrastructure.BusinnessListener;
import com.system.m4.infrastructure.Constants;
import com.system.m4.infrastructure.ConverterUtils;
import com.system.m4.repository.dtos.TagDTO;
import com.system.m4.repository.firebase.FirebaseRepository;
import com.system.m4.repository.firebase.TagFirebaseRepository;
import com.system.m4.views.vos.TagVO;

import java.util.ArrayList;
import java.util.List;

public abstract class TagBusinness {

    private TagBusinness() {
        // Nothing to do
    }

    public static void findAll(final BusinnessListener.OnMultiResultListenner<TagVO> onMultiResultListenner) {

        new TagFirebaseRepository().findAll(new FirebaseRepository.FirebaseMultiReturnListener<TagDTO>() {

            @Override
            public void onFindAll(List<TagDTO> list) {
                List<TagVO> listVO = new ArrayList<>();
                for (TagDTO dto : list) {
                    listVO.add(ConverterUtils.fromTag(dto));
                }
                onMultiResultListenner.onSuccess(listVO, Constants.CALL_TAG_FINDALL);
            }

            @Override
            public void onError(String error) {
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