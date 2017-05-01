package com.system.m4.businness;

import com.system.m4.repository.dtos.TagDTO;
import com.system.m4.repository.firebase.DTOAbs;
import com.system.m4.infrastructure.BusinnessListener;

import java.util.ArrayList;
import java.util.List;

public class TagBusinness {
    public static void requestTagList(BusinnessListener.OnMultiResultListenner onMultiResultListenner) {

        List<DTOAbs> list = new ArrayList<DTOAbs>();
        list.add(new TagDTO("Moradia"));
        list.add(new TagDTO("Aluguel"));
        list.add(new TagDTO("Celular"));
        list.add(new TagDTO("Internet"));
        list.add(new TagDTO("Automovel"));
        list.add(new TagDTO("Seguro"));

        onMultiResultListenner.onSuccess(list);
    }

    public static void saveTag(TagDTO tagDTO, BusinnessListener.OnPersistListener persistListener) {
        persistListener.onSuccess();
    }
}