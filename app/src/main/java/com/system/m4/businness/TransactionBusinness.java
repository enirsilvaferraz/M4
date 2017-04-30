package com.system.m4.businness;

import com.system.m4.businness.dtos.FilterTransactionDTO;
import com.system.m4.businness.dtos.TransactionDTO;
import com.system.m4.infrastructure.BusinnessListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enir on 22/04/2017.
 * For M4
 */

public class TransactionBusinness {

    public static void requestTransactions(final BusinnessListener.OnMultiResultListenner<TransactionDTO> onMultiResultListenner) {

        final List<TransactionDTO> list = new ArrayList<>();
        list.add(new TransactionDTO("10 de Outubro de 2017", "10 de Outubro de 2017", "R$ 1000,00", "Transporte", "Nubank", "2x sem juros"));
        list.add(new TransactionDTO("10 de Outubro de 2017", "10 de Outubro de 2017", "R$ 1000,00", "Transporte", "Nubank", "2x sem juros"));
        list.add(new TransactionDTO("10 de Outubro de 2017", "10 de Outubro de 2017", "R$ 1000,00", "Transporte", "Nubank", "2x sem juros"));
        list.add(new TransactionDTO("10 de Outubro de 2017", "10 de Outubro de 2017", "R$ 1000,00", "Transporte", "Nubank", "2x sem juros"));
        list.add(new TransactionDTO("10 de Outubro de 2017", "10 de Outubro de 2017", "R$ 1000,00", "Transporte", "Nubank", "2x sem juros"));
        list.add(new TransactionDTO("10 de Outubro de 2017", "10 de Outubro de 2017", "R$ 1000,00", "Transporte", "Nubank", "2x sem juros"));
        list.add(new TransactionDTO("10 de Outubro de 2017", "10 de Outubro de 2017", "R$ 1000,00", "Transporte", "Nubank", "2x sem juros"));
        list.add(new TransactionDTO("10 de Outubro de 2017", "10 de Outubro de 2017", "R$ 1000,00", "Transporte", "Nubank", "2x sem juros"));
        list.add(new TransactionDTO("10 de Outubro de 2017", "10 de Outubro de 2017", "R$ 1000,00", "Transporte", "Nubank", "2x sem juros"));
        list.add(new TransactionDTO("10 de Outubro de 2017", "10 de Outubro de 2017", "R$ 1000,00", "Transporte", "Nubank", "2x sem juros"));
        list.add(new TransactionDTO("10 de Outubro de 2017", "10 de Outubro de 2017", "R$ 1000,00", "Transporte", "Nubank", "2x sem juros"));
        list.add(new TransactionDTO("10 de Outubro de 2017", "10 de Outubro de 2017", "R$ 1000,00", "Transporte", "Nubank", "2x sem juros"));
        list.add(new TransactionDTO("10 de Outubro de 2017", "10 de Outubro de 2017", "R$ 1000,00", "Transporte", "Nubank", "2x sem juros"));
        list.add(new TransactionDTO("10 de Outubro de 2017", "10 de Outubro de 2017", "R$ 1000,00", "Transporte", "Nubank", "2x sem juros"));

        FilterTransactionBusinness.requestFilter(new BusinnessListener.OnSingleResultListener<FilterTransactionDTO>() {

            @Override
            public void onSuccess(FilterTransactionDTO dto) {
                onMultiResultListenner.onSuccess(list);
            }

            @Override
            public void onError(Exception e) {

            }
        });


    }
}
