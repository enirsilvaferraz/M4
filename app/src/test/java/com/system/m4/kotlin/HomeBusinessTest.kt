package com.system.m4.kotlin

import com.system.m4.infrastructure.JavaUtils
import com.system.m4.kotlin.home.HomeBusiness
import com.system.m4.kotlin.home.HomeDTO
import com.system.m4.views.vos.*
import junit.framework.Assert
import org.junit.Test

class HomeBusinessTest {

    @Test
    fun splitTransactionsByDate20WithNulls() {

        val homeDTO = HomeDTO(1, 2018)
        val homeVO = HomeVO()

        HomeBusiness().splitTransactionsByDate20(homeVO, homeDTO)

        Assert.assertEquals(0, homeVO.transactions1Q.size)
        Assert.assertEquals(0, homeVO.transactions2Q.size)

        Assert.assertEquals(0.0, homeVO.amount1Q)
        Assert.assertEquals(0.0, homeVO.amount2Q)
    }

    @Test
    fun splitTransactionsByDate20With0Q() {

        val listOfTransactions = mutableListOf<TransactionVO>()

        val homeDTO = HomeDTO(1, 2018)
        homeDTO.listTransaction = ArrayList(listOfTransactions)

        val homeVO = HomeVO()

        HomeBusiness().splitTransactionsByDate20(homeVO, homeDTO)

        Assert.assertEquals(0, homeVO.transactions1Q.size)
        Assert.assertEquals(0, homeVO.transactions2Q.size)

        Assert.assertEquals(0.0, homeVO.amount1Q)
        Assert.assertEquals(0.0, homeVO.amount2Q)
    }

    @Test
    fun splitTransactionsByDate20With1Q() {

        val listOfTransactions = mutableListOf<TransactionVO>()
        listOfTransactions.add(mockTransaction("1", "02/01/2018", 10.10, "KEY_ALIMENTACAO", "Alimentação", "Celular"))
        listOfTransactions.add(mockTransaction("2", "19/01/2018", 20.20, "KEY_ALIMENTACAO", "Alimentação", "Celular"))
        listOfTransactions.add(mockTransaction("3", "10/01/2018", 30.30, "KEY_ALIMENTACAO", "Alimentação", "Celular"))
        listOfTransactions.add(mockTransaction("4", "01/01/2018", 40.40, "KEY_ALIMENTACAO", "Alimentação", "Celular"))

        val homeDTO = HomeDTO(1, 2018)
        homeDTO.listTransaction = ArrayList(listOfTransactions)

        val homeVO = HomeVO()

        HomeBusiness().splitTransactionsByDate20(homeVO, homeDTO)

        Assert.assertEquals(4, homeVO.transactions1Q.size)
        Assert.assertEquals("4", homeVO.transactions1Q[0].key)
        Assert.assertEquals("1", homeVO.transactions1Q[1].key)
        Assert.assertEquals("3", homeVO.transactions1Q[2].key)
        Assert.assertEquals("2", homeVO.transactions1Q[3].key)

        Assert.assertEquals(0, homeVO.transactions2Q.size)

        Assert.assertEquals(101.0, homeVO.amount1Q)
        Assert.assertEquals(0.0, homeVO.amount2Q)
    }

    @Test
    fun splitTransactionsByDate20With2Q() {

        val listOfTransactions = mutableListOf<TransactionVO>()
        listOfTransactions.add(mockTransaction("1", "21/01/2018", 30.5, "KEY_ALIMENTACAO", "Alimentação", "Celular"))
        listOfTransactions.add(mockTransaction("2", "29/01/2018", 30.1, "KEY_ALIMENTACAO", "Alimentação", "Celular"))
        listOfTransactions.add(mockTransaction("3", "20/01/2018", 70.1, "KEY_ALIMENTACAO", "Moradia", "Celular"))
        listOfTransactions.add(mockTransaction("4", "20/01/2018", 70.1, "KEY_ALIMENTACAO", "Alimentação", "Celular"))
        listOfTransactions.add(mockTransaction("5", "20/01/2018", 0.0, "KEY_ALIMENTACAO", "Alimentação", "Cartão"))

        val homeDTO = HomeDTO(1, 2018)
        homeDTO.listTransaction = ArrayList(listOfTransactions)

        val homeVO = HomeVO()

        HomeBusiness().splitTransactionsByDate20(homeVO, homeDTO)

        Assert.assertEquals(0, homeVO.transactions1Q.size)

        Assert.assertEquals(5, homeVO.transactions2Q.size)
        Assert.assertEquals("5", homeVO.transactions2Q[0].key)
        Assert.assertEquals("4", homeVO.transactions2Q[1].key)
        Assert.assertEquals("3", homeVO.transactions2Q[2].key)
        Assert.assertEquals("1", homeVO.transactions2Q[3].key)
        Assert.assertEquals("2", homeVO.transactions2Q[4].key)

        Assert.assertEquals(0.0, homeVO.amount1Q)
        Assert.assertEquals(200.8, homeVO.amount2Q)
    }

    @Test
    fun splitTransactionsByDate20With1and2Q() {

        val listOfTransactions = mutableListOf<TransactionVO>()
        listOfTransactions.add(mockTransaction("1", "01/01/2018", 50.0, "KEY_ALIMENTACAO", "Alimentação", "Celular"))
        listOfTransactions.add(mockTransaction("2", "19/01/2018", 50.0, "KEY_ALIMENTACAO", "Alimentação", "Celular"))
        listOfTransactions.add(mockTransaction("3", "20/01/2018", 50.0, "KEY_ALIMENTACAO", "Alimentação", "Celular"))
        listOfTransactions.add(mockTransaction("4", "21/01/2018", 50.0, "KEY_ALIMENTACAO", "Alimentação", "Celular"))

        val homeDTO = HomeDTO(1, 2018)
        homeDTO.listTransaction = ArrayList(listOfTransactions)

        val homeVO = HomeVO()

        HomeBusiness().splitTransactionsByDate20(homeVO, homeDTO)

        Assert.assertEquals(2, homeVO.transactions1Q.size)
        Assert.assertEquals("1", homeVO.transactions1Q[0].key)
        Assert.assertEquals("2", homeVO.transactions1Q[1].key)

        Assert.assertEquals(2, homeVO.transactions2Q.size)
        Assert.assertEquals("3", homeVO.transactions2Q[0].key)
        Assert.assertEquals("4", homeVO.transactions2Q[1].key)

        Assert.assertEquals(100.0, homeVO.amount1Q)
        Assert.assertEquals(100.0, homeVO.amount2Q)
    }

    @Test
    fun splitPendingTransactionsWith2Transaction() {

        val listOfTransactions = mutableListOf<TransactionVO>()
        listOfTransactions.add(mockTransaction("1", "01/01/2018", 50.0, "", "", ""))
        listOfTransactions.add(mockTransaction("2", "19/01/2018", 50.0, "", "", ""))
        listOfTransactions.add(mockTransaction("3", "20/01/2018", 50.0, "", "", ""))
        listOfTransactions.add(mockTransaction("4", "21/01/2018", 50.0, "", "", ""))
        listOfTransactions.add(mockTransaction("5", "21/01/2018", 50.0, "KEY_ALIMENTACAO", "Alimentação", "Celular"))


        val homeDTO = HomeDTO(1, 2018)
        homeDTO.listTransaction = ArrayList(listOfTransactions)

        val homeVO = HomeVO()

        HomeBusiness().splitPendingTransactions(homeVO, homeDTO)

        Assert.assertEquals(4, homeVO.pendingTransaction.size)
        Assert.assertEquals("1", homeVO.pendingTransaction[0].key)
        Assert.assertEquals("2", homeVO.pendingTransaction[1].key)
        Assert.assertEquals("3", homeVO.pendingTransaction[2].key)
        Assert.assertEquals("4", homeVO.pendingTransaction[3].key)
    }

    @Test
    fun splitPendingTransactionsWith2Groups() {

        val listOfTransactions = mutableListOf<TransactionVO>()
        listOfTransactions.add(mockTransaction("1", "01/01/2018", 50.0, "KEY_PAYMENT1"))
        listOfTransactions.add(mockTransaction("2", "01/01/2018", 50.0, "KEY_PAYMENT1"))
        listOfTransactions.add(mockTransaction("3", "01/01/2018", 50.0, "KEY_PAYMENT1"))
        listOfTransactions.add(mockTransaction("4", "01/01/2018", 50.0, "KEY_PAYMENT2"))
        listOfTransactions.add(mockTransaction("5", "01/01/2018", 50.0, "KEY_PAYMENT2"))
        listOfTransactions.add(mockTransaction("6", "01/01/2018", 50.0, "KEY_PAYMENT3"))

        val listOfGroups = mutableListOf<GroupTransactionVO>()
        listOfGroups.add(GroupTransactionVO(mutableListOf(PaymentTypeVO("KEY_PAYMENT1"), PaymentTypeVO("KEY_PAYMENT2"))))

        val homeDTO = HomeDTO(1, 2018)
        homeDTO.listTransaction = ArrayList(listOfTransactions)
        homeDTO.listGroup = ArrayList(listOfGroups)

        val homeVO = HomeVO()

        HomeBusiness().splitGroupTransaction(homeVO, homeDTO)

        Assert.assertEquals(2, homeVO.groupMap.size)

        homeVO.groupMap.keys.forEach {
            when (it.key) {
                "KEY_PAYMENT1" -> Assert.assertEquals(3, homeVO.groupMap[it]?.size)
                "KEY_PAYMENT2" -> Assert.assertEquals(2, homeVO.groupMap[it]?.size)
                "KEY_PAYMENT3" -> Assert.assertEquals(0, homeVO.groupMap[it]?.size)
            }
        }
    }

    private fun mockTransaction(key: String, dateString: String, price: Double, parentKey: String, parentTag: String, tag: String): TransactionVO {
        val vo = TransactionVO()
        vo.key = key
        vo.paymentDate = JavaUtils.DateUtil.parse(dateString, JavaUtils.DateUtil.DD_MM_YYYY)
        vo.price = price
        vo.tag = TagVO(parentKey, parentTag, tag)
        return vo
    }

    private fun mockTransaction(key: String, dateString: String, price: Double, paymentTypeKey: String): TransactionVO {
        val vo = TransactionVO()
        vo.key = key
        vo.paymentDate = JavaUtils.DateUtil.parse(dateString, JavaUtils.DateUtil.DD_MM_YYYY)
        vo.price = price
        vo.paymentType = PaymentTypeVO(paymentTypeKey)
        return vo
    }
}