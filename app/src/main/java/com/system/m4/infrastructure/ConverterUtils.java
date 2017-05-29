package com.system.m4.infrastructure;

import com.system.m4.repository.dtos.FilterTransactionDTO;
import com.system.m4.repository.dtos.PaymentTypeDTO;
import com.system.m4.repository.dtos.TagDTO;
import com.system.m4.repository.dtos.TransactionDTO;
import com.system.m4.views.vos.FilterTransactionVO;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.TransactionVO;

import java.util.List;

/**
 * Created by eferraz on 10/05/17.
 * For M4
 */

public final class ConverterUtils {

    private ConverterUtils() {
        // Do nothing
    }

    public static TransactionDTO fromTransaction(TransactionVO vo) {
        TransactionDTO dto = new TransactionDTO();
        dto.setKey(vo.getKey());
        dto.setTag(vo.getTag().getKey());
        dto.setPaymentType(vo.getPaymentType().getKey());
        dto.setPaymentDate(JavaUtils.DateUtil.format(vo.getPaymentDate(), JavaUtils.DateUtil.YYYY_MM_DD));
        dto.setPurchaseDate(vo.getPurchaseDate() != null ? JavaUtils.DateUtil.format(vo.getPurchaseDate(), JavaUtils.DateUtil.YYYY_MM_DD) : null);
        dto.setContent(vo.getContent());
        dto.setPrice(JavaUtils.NumberUtil.removeFormat(vo.getPrice()));
        dto.setPinned(vo.isPinned());
        return dto;
    }

    public static TransactionVO fromTransaction(TransactionDTO mDTO, List<TagDTO> tags, List<PaymentTypeDTO> paymentTypes) {

        TagDTO tagDTO = tags.get(tags.indexOf(new TagDTO(mDTO.getTag())));
        PaymentTypeDTO paymentTypeDTO = paymentTypes.get(paymentTypes.indexOf(new PaymentTypeDTO(mDTO.getPaymentType())));

        TransactionVO vo = new TransactionVO();
        vo.setKey(mDTO.getKey());
        vo.setTag(fromTag(tagDTO));
        vo.setPaymentType(fromPaymentType(paymentTypeDTO));
        vo.setPaymentDate(JavaUtils.DateUtil.parse(mDTO.getPaymentDate(), JavaUtils.DateUtil.YYYY_MM_DD));
        vo.setPurchaseDate(mDTO.getPurchaseDate() != null ? JavaUtils.DateUtil.parse(mDTO.getPurchaseDate(), JavaUtils.DateUtil.YYYY_MM_DD) : null);
        vo.setContent(mDTO.getContent());
        vo.setPrice(JavaUtils.NumberUtil.currencyFormat(mDTO.getPrice()));
        vo.setPinned(mDTO.isPinned());
        return vo;
    }

    public static PaymentTypeVO fromPaymentType(PaymentTypeDTO dto) {
        PaymentTypeVO vo = new PaymentTypeVO();
        vo.setKey(dto.getKey());
        vo.setName(dto.getName());
        return vo;
    }

    public static PaymentTypeDTO fromPaymentType(PaymentTypeVO vo) {
        PaymentTypeDTO dto = new PaymentTypeDTO();
        dto.setKey(vo.getKey());
        dto.setName(vo.getName());
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
        dto.setTags(vo.getTag() != null ? vo.getTag().getKey() : null);
        dto.setPaymentDateStart(vo.getPaymentDateStart() != null ? JavaUtils.DateUtil.format(vo.getPaymentDateStart(), JavaUtils.DateUtil.YYYY_MM_DD) : null);
        dto.setPaymentDateEnd(vo.getPaymentDateEnd() != null ? JavaUtils.DateUtil.format(vo.getPaymentDateEnd(), JavaUtils.DateUtil.YYYY_MM_DD) : null);
        return dto;
    }

    public static FilterTransactionVO fromFilterTransaction(FilterTransactionDTO dto, List<TagDTO> tags, List<PaymentTypeDTO> paymentTypes) {
        int indexOf = tags.indexOf(new TagDTO(dto.getTags()));
        TagDTO tagDTO = indexOf != -1 ? tags.get(indexOf) : new TagDTO();

        indexOf = paymentTypes.indexOf(new PaymentTypeDTO(dto.getPaymentType()));
        PaymentTypeDTO paymentTypeDTO = indexOf != -1 ? paymentTypes.get(indexOf) : new PaymentTypeDTO();

        FilterTransactionVO vo = new FilterTransactionVO();
        vo.setKey(dto.getKey());
        vo.setPaymentType(fromPaymentType(paymentTypeDTO));
        vo.setPaymentDateStart(dto.getPaymentDateStart() != null ? JavaUtils.DateUtil.parse(dto.getPaymentDateStart(), JavaUtils.DateUtil.YYYY_MM_DD) : null);
        vo.setPaymentDateEnd(dto.getPaymentDateEnd() != null ? JavaUtils.DateUtil.parse(dto.getPaymentDateEnd(), JavaUtils.DateUtil.YYYY_MM_DD) : null);
        vo.setTag(fromTag(tagDTO));
        return vo;
    }
}
