package com.system.m4.repository.firebase;

import com.system.m4.repository.dtos.TagDTO;

/**
 * Created by eferraz on 01/05/17.
 * For M4
 */

public class TagFirebaseRepository extends FirebaseRepository<TagDTO> {

    public TagFirebaseRepository(String flavor) {
        super(flavor, "Tag");
    }

    @Override
    protected Class<TagDTO> getTClass() {
        return TagDTO.class;
    }
}
