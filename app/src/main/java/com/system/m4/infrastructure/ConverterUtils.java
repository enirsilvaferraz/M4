package com.system.m4.infrastructure;

import com.system.m4.kotlin.paymenttype.PaymentTypeModel;
import com.system.m4.kotlin.tags.TagModel;
import com.system.m4.kotlin.transaction.TransactionModel;
import com.system.m4.repository.dtos.FilterTransactionDTO;
import com.system.m4.repository.dtos.GroupTransactionDTO;
import com.system.m4.views.vos.FilterTransactionVO;
import com.system.m4.views.vos.GroupTransactionVO;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.Transaction;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by eferraz on 10/05/17.
 * For M4
 */

public final class ConverterUtils {

    private ConverterUtils() {
        // Do nothing
    }

    public static TransactionModel fromTransaction(Transaction vo) {
        TransactionModel dto = new TransactionModel();
        dto.setKey(vo.getKey());
        dto.setTag(vo.getTag().getKey());
        dto.setPaymentType(vo.getPaymentType().getKey());
        dto.setPaymentDate(JavaUtils.DateUtil.format(vo.getPaymentDate(), JavaUtils.DateUtil.YYYY_MM_DD));
        dto.setPurchaseDate(vo.getPurchaseDate() != null ? JavaUtils.DateUtil.format(vo.getPurchaseDate(), JavaUtils.DateUtil.YYYY_MM_DD) : null);
        dto.setContent(vo.getContent());
        dto.setPrice(vo.getPrice());
        return dto;
    }

    public static Transaction fromTransaction(TransactionModel dto) {
        Transaction vo = new Transaction();
        vo.setKey(dto.getKey());
        vo.setTag(new TagVO());
        vo.getTag().setKey(dto.getTag());
        vo.setPaymentType(new PaymentTypeVO());
        vo.getPaymentType().setKey(dto.getPaymentType());
        vo.setPaymentDate(JavaUtils.DateUtil.parse(dto.getPaymentDate(), JavaUtils.DateUtil.YYYY_MM_DD));
        vo.setPurchaseDate(dto.getPurchaseDate() != null ? JavaUtils.DateUtil.parse(dto.getPurchaseDate(), JavaUtils.DateUtil.YYYY_MM_DD) : null);
        vo.setContent(dto.getContent());
        vo.setPrice(dto.getPrice());

        // Usado para saber onde é o path, não é armazenado no Firebase
        vo.setPaymentDateOrigin(vo.getPaymentDate());
        return vo;
    }

    public static Transaction fillTransaction(Transaction vo, List<TagVO> tags, List<PaymentTypeVO> paymentTypes) {
        int index = tags.indexOf(vo.getTag());
        if (index > -1) {
            vo.setTag(tags.get(index));
        } else {
            TagVO tag = new TagVO();
            tag.setName("(Pendente de Avaliação)");
            vo.setTag(tag);
        }
        vo.setPaymentType(paymentTypes.get(paymentTypes.indexOf(vo.getPaymentType())));
        return vo;
    }

    public static PaymentTypeVO fromPaymentType(PaymentTypeModel dto) {
        PaymentTypeVO vo = new PaymentTypeVO();
        vo.setKey(dto.getKey());
        vo.setName(dto.getName());
        vo.setColor(dto.getColor());
        return vo;
    }

    public static PaymentTypeModel fromPaymentType(PaymentTypeVO vo) {
        PaymentTypeModel dto = new PaymentTypeModel();
        dto.setKey(vo.getKey());
        dto.setName(vo.getName());
        dto.setColor(vo.getColor());
        return dto;
    }

    public static TagModel fromTag(TagVO mVO) {
        TagModel dto = new TagModel();
        dto.setKey(mVO.getKey());
        dto.setName(mVO.getName());
        dto.setParentName(mVO.getParentName());
        return dto;
    }

    public static TagVO fromTag(TagModel dto) {
        TagVO vo = new TagVO();
        vo.setKey(dto.getKey());
        vo.setName(dto.getName());
        vo.setParentName(dto.getParentName());
        return vo;
    }

    public static FilterTransactionDTO fromFilterTransaction(FilterTransactionVO vo) {
        FilterTransactionDTO dto = new FilterTransactionDTO();
        dto.setKey(vo.getKey());
        dto.setPaymentType(vo.getPaymentType() != null ? vo.getPaymentType().getKey() : null);
        dto.setTag(vo.getTag() != null ? vo.getTag().getKey() : null);
        dto.setYear(vo.getYear());
        dto.setMonth(vo.getMonth());
        return dto;
    }

    public static FilterTransactionVO fromFilterTransaction(FilterTransactionDTO dto) {
        FilterTransactionVO vo = new FilterTransactionVO();
        vo.setKey(dto.getKey());
        vo.setYear(dto.getYear());
        vo.setMonth(dto.getMonth());

        if (dto.getTag() != null) {
            vo.setTag(new TagVO());
            vo.getTag().setKey(dto.getTag());
        }

        if (dto.getPaymentType() != null) {
            vo.setPaymentType(new PaymentTypeVO());
            vo.getPaymentType().setKey(dto.getPaymentType());
        }

        return vo;
    }

    public static GroupTransactionVO fromGroupTransaction(GroupTransactionDTO dto) {
        GroupTransactionVO vo = new GroupTransactionVO();
        vo.setPaymentTypeList(new ArrayList<PaymentTypeVO>());
        vo.setKey(dto.getKey());
        for (String key : dto.getListPaymentType()) {
            PaymentTypeVO paymentTypeVO = new PaymentTypeVO();
            paymentTypeVO.setKey(key);
            vo.getPaymentTypeList().add(paymentTypeVO);
        }
        return vo;
    }

    public static GroupTransactionVO fillGroupTransaction(GroupTransactionVO group, List<PaymentTypeVO> listPaymentType) {
        for (PaymentTypeVO typeVO : group.getPaymentTypeList()) {
            typeVO.setName(listPaymentType.get(listPaymentType.indexOf(typeVO)).getName());
        }
        return group;
    }

    public static FilterTransactionVO fillFilterTransaction(FilterTransactionVO vo, List<TagVO> tags, List<PaymentTypeVO> paymentTypes) {

        if (vo.getTag() != null) {
            int index = tags.indexOf(vo.getTag());
            if (index > -1) {
                vo.setTag(tags.get(index));
            } else {
                TagVO tag = new TagVO();
                tag.setName("(Pendente de Avaliação)");
                vo.setTag(tag);
            }
        }

        if (vo.getPaymentType() != null) {
            vo.setPaymentType(paymentTypes.get(paymentTypes.indexOf(vo.getPaymentType())));
        }
        return vo;
    }

    public static ArrayList<Transaction> fromTransaction(@NotNull List<TransactionModel> list) {
        ArrayList<Transaction> listVO = new ArrayList<>();
        for (TransactionModel model : list) {
            listVO.add(fromTransaction(model));
        }
        return listVO;
    }

    public static ArrayList<Transaction> fromFixedTransaction(@NotNull List<TransactionModel> list, int month, int year) {
        ArrayList<Transaction> listVO = new ArrayList<>();
        for (TransactionModel model : list) {
            Transaction transaction = fromTransaction(model);
            transaction.setPinned(true);
            transaction.setApproved(false);

            Calendar instance = Calendar.getInstance();
            instance.setTime(transaction.getPaymentDate());
            instance.set(Calendar.MONTH, month);
            instance.set(Calendar.YEAR, year);

            transaction.setPaymentDate(instance.getTime());

            listVO.add(transaction);
        }
        return listVO;
    }

    public static ArrayList<TagVO> fromTag(@NotNull List<TagModel> list) {
        ArrayList<TagVO> listVO = new ArrayList<>();
        for (TagModel model : list) {
            listVO.add(fromTag(model));
        }
        return listVO;
    }

    public static ArrayList<PaymentTypeVO> fromPaymentType(@NotNull ArrayList<PaymentTypeModel> list) {
        ArrayList<PaymentTypeVO> listVO = new ArrayList<>();
        for (PaymentTypeModel model : list) {
            listVO.add(fromPaymentType(model));
        }
        return listVO;
    }

    public static ArrayList<GroupTransactionVO> fromGroupTransaction(@NotNull ArrayList<GroupTransactionDTO> list) {
        ArrayList<GroupTransactionVO> listVO = new ArrayList<>();
        for (GroupTransactionDTO model : list) {
            listVO.add(fromGroupTransaction(model));
        }
        return listVO;
    }
}
