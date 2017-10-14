package com.system.m4.kotlin.services;

import com.system.m4.infrastructure.Constants;
import com.system.m4.infrastructure.JavaUtils;
import com.system.m4.views.vos.PaymentTypeVO;
import com.system.m4.views.vos.TagVO;
import com.system.m4.views.vos.TransactionVO;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by enirs on 11/10/2017.
 * Unit test
 */

public class SmsReaderBusinessUnitTest {

    /**
     * Template : Realizado pagamento de TITULOS ITAU no valor de R$ 442,77 na sua conta XXX53-3 em 10/10 as 19:48
     */
    @Test
    public void readSMSTextPayment_smsReceived_isCorrect() {

        TransactionVO vo = new TransactionVO();
        vo.setContent("TITULOS ITAU");
        vo.setPrice(442.77);
        vo.setPaymentDate(JavaUtils.DateUtil.parse("10/10/2017", JavaUtils.DateUtil.DD_MM_YYYY));
        vo.setTag(new TagVO());
        vo.getTag().setKey(Constants.TAG_UNKNOWN);
        vo.setPaymentType(new PaymentTypeVO());
        vo.getPaymentType().setKey("-KkgOxzck3cnJjQqum-S");

        String message = "Realizado pagamento de TITULOS ITAU no valor de R$ 442,77 na sua conta XXX53-3 em 10/10 as 19:48";

        Assert.assertEquals(vo, SmsReaderBusiness.Companion.readSMSTextPayment(message, "-KkgOxzck3cnJjQqum-S"));
    }
}
