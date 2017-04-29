package com.system.m4.businness.transaction;

import com.system.m4.infrastructure.BusinnessListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enir on 22/04/2017.
 */

public class TransactionManagerBusinness {

    public static void requestTagList(BusinnessListener.OnMultiResultListenner onMultiResultListenner) {

        List<String> list = new ArrayList<>();
        list.add("Moradia");
        list.add("Aluguel");
        list.add("Celular");
        list.add("Internet");
        list.add("Automovel");
        list.add("Seguro");

        onMultiResultListenner.onSuccess(list);
    }

    public static void requestPaymentTypeList(BusinnessListener.OnMultiResultListenner onMultiResultListenner) {

        List<String> list = new ArrayList<>();
        list.add("Nubank");
        list.add("Dinheiro");
        list.add("Itaucard");
        list.add("Transf. Itaú");
        list.add("Transf. Bradesco");

        onMultiResultListenner.onSuccess(list);
    }
}
