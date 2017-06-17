package com.system.m4.infrastructure;

import com.system.m4.repository.dtos.FilterTransactionDTO;
import com.system.m4.repository.dtos.GroupTransactionDTO;
import com.system.m4.repository.dtos.PaymentTypeDTO;
import com.system.m4.repository.dtos.TagDTO;
import com.system.m4.repository.dtos.TransactionDTO;
import com.system.m4.views.vos.FilterTransactionVO;
import com.system.m4.views.vos.GroupTransactionVO;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eferraz on 10/05/17.
 * For M4
 */

public final class ConverterUtils {

    private ConverterUtils() {
        // Do nothing
    }

    public static TransactionDTO fromTransaction(Transaction vo) {
        TransactionDTO dto = new TransactionDTO();
        dto.setKey(vo.getKey());
        dto.setTag(vo.getTag().getKey());
        dto.setPaymentType(vo.getPaymentType().getKey());
        dto.setPaymentDate(JavaUtils.DateUtil.format(vo.getPaymentDate(), JavaUtils.DateUtil.YYYY_MM_DD));
        dto.setPurchaseDate(vo.getPurchaseDate() != null ? JavaUtils.DateUtil.format(vo.getPurchaseDate(), JavaUtils.DateUtil.YYYY_MM_DD) : null);
        dto.setContent(vo.getContent());
        dto.setPrice(vo.getPrice());
        return dto;
    }

    public static Transaction fromTransaction(TransactionDTO dto) {
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
        vo.setTag(tags.get(tags.indexOf(vo.getTag())));
        vo.setPaymentType(paymentTypes.get(paymentTypes.indexOf(vo.getPaymentType())));
        return vo;
    }

    public static PaymentTypeVO fromPaymentType(PaymentTypeDTO dto) {
        PaymentTypeVO vo = new PaymentTypeVO();
        vo.setKey(dto.getKey());
        vo.setName(dto.getName());
        vo.setColor(dto.getColor());
        return vo;
    }

    public static PaymentTypeDTO fromPaymentType(PaymentTypeVO vo) {
        PaymentTypeDTO dto = new PaymentTypeDTO();
        dto.setKey(vo.getKey());
        dto.setName(vo.getName());
        dto.setColor(vo.getColor());
        return dto;
    }

    public static TagDTO fromTag(TagVO mVO) {
        TagDTO dto = new TagDTO();
        dto.setKey(mVO.getKey());
        dto.setName(mVO.getName());
        return dto;
    }

    public static TagVO fromTag(TagDTO dto) {
        TagVO vo = new TagVO();
        vo.setKey(dto.getKey());
        vo.setName(dto.getName());
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
            vo.setTag(tags.get(tags.indexOf(vo.getTag())));
        }

        if (vo.getPaymentType() != null) {
            vo.setPaymentType(paymentTypes.get(paymentTypes.indexOf(vo.getPaymentType())));
        }
        return vo;
    }
}
