package com.system.m4.infrastructure;

import com.system.m4.repository.dtos.PaymentTypeDTO;
import com.system.m4.repository.dtos.TagDTO;
import com.system.m4.repository.dtos.TransactionDTO;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.TransactionVO;

import java.util.List;

/**
 * Created by eferraz on 10/05/17.
 * For M4
 */

public final class ConveterUtils {

    private ConveterUtils() {
        // Do nothing
    }

    public static TransactionDTO fromTransaction(TransactionVO vo) {
        TransactionDTO dto = new TransactionDTO();
        dto.setTag(vo.getTag().getKey());
        dto.setPaymentType(vo.getPaymentType().getKey());
        dto.setPaymentDate(JavaUtils.DateUtil.format(vo.getPaymentDate(), JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY, JavaUtils.DateUtil.DD_MM_YYYY ));
        dto.setPurchaseDate(JavaUtils.DateUtil.format(vo.getPurchaseDate(), JavaUtils.DateUtil.DD_DE_MMMM_DE_YYYY, JavaUtils.DateUtil.DD_MM_YYYY ));
        dto.setContent(vo.getContent());
        dto.setPrice(JavaUtils.NumberUtil.removeFormat(vo.getPrice()));
        return dto;
    }

    public static TransactionVO fromTransaction(TransactionDTO mDTO, List<TagDTO> tags, List<PaymentTypeDTO> paymentTypes) {

        TagDTO tagDTO = tags.get(tags.indexOf(new TagDTO(mDTO.getTag())));
        PaymentTypeDTO paymentTypeDTO = paymentTypes.get(paymentTypes.indexOf(new PaymentTypeDTO(mDTO.getPaymentType())));

        TransactionVO vo = new TransactionVO();
        vo.setTag(fromTag(tagDTO));
        vo.setPaymentType(fromPatmentType(paymentTypeDTO));
        vo.setPaymentDate(mDTO.getPaymentDate());
        vo.setPurchaseDate(mDTO.getPurchaseDate());
        vo.setContent(mDTO.getContent());
        vo.setPrice(JavaUtils.NumberUtil.currencyFormat(mDTO.getPrice()));
        return vo;
    }

    private static PaymentTypeVO fromPatmentType(PaymentTypeDTO dto) {
        PaymentTypeVO vo = new PaymentTypeVO();
        vo.setKey(dto.getKey());
        vo.setName(dto.getName());
        return vo;
    }

    public static TagVO fromTag(TagDTO dto) {
        TagVO vo = new TagVO();
        vo.setKey(dto.getKey());
        vo.setName(dto.getName());
        return vo;
    }
}
