package com.system.m4.repository.firebase;

import com.system.m4.repository.dtos.FilterTransactionDTO;
import com.system.m4.repository.dtos.TransactionDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eferraz on 01/05/17.
 * For M4
 */

public class TransactionFirebaseRepository extends FirebaseRepository<TransactionDTO> {

    public TransactionFirebaseRepository(String flavor) {
        super(flavor, "Transaction");
    }

    public void findByFilter(FilterTransactionDTO filterTransactionDTO, FirebaseMultiReturnListener<TransactionDTO> firebaseMultiReturnListener) {

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

        firebaseMultiReturnListener.onFindAll(list);
    }
}
