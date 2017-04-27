package com.system.m4.views.transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enir on 22/04/2017.
 */

class TransactionManagerBusinness {

    public static void requestTagList(OnResultListenner onResultListenner) {

        List<String> list = new ArrayList<>();
        list.add("Moradia");
        list.add("Aluguel");
        list.add("Celular");
        list.add("Internet");
        list.add("Automovel");
        list.add("Seguro");

        onResultListenner.onSuccess(list);
    }

    public static void requestPaymentTypeList(OnResultListenner onResultListenner) {

        List<String> list = new ArrayList<>();
        list.add("Nubank");
        list.add("Dinheiro");
        list.add("Itaucard");
        list.add("Transf. Itaú");
        list.add("Transf. Bradesco");

        onResultListenner.onSuccess(list);
    }

    public interface OnResultListenner {

        void onSuccess(List<String> list);

        void onError(Exception e);
    }
}
