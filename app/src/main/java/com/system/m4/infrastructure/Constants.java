package com.system.m4.infrastructure;

/**
 * Created by Enir on 21/04/2017.
 */

public interface Constants {
    String EMPTY_FIELD = "--";
    String TITLE_BUNDLE = "TITLE_BUNDLE";
    String PARCELABLE_BUNDLE = "PARCELABLE_BUNDLE";
    String VALUE_BUNDLE = "VALUE_BUNDLE";

    String BUNDLE_TRANSACTION_VO = "BUNDLE_TRANSACTION_VO";
    String BUNDLE_TAG_VO = "BUNDLE_TAG_VO";
    String FLAVOR_DEV = "dev";
    int CALL_DEFAULT = 1;
    
    int CALL_TRANSACTION_BY_FILTER = 1000;
    int CALL_TRANSACTION_FIXED = 1001;
    int CALL_GROUP_FINDALL = 2000;
    int CALL_PAYMENTTYPE_FINDALL = 3000;
    int CALL_TAG_FINDALL = 4000;
}
