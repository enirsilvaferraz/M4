package com.system.m4.kotlin

import com.system.m4.infrastructure.JavaUtils
import com.system.m4.kotlin.home.HomeBusiness
import com.system.m4.kotlin.home.HomeDTO
import com.system.m4.views.vos.HomeVO
import com.system.m4.views.vos.TagVO
import com.system.m4.views.vos.TransactionVO
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
        listOfTransactions.add(mockTransaction("1", "02/01/2018", 10.10, "Alimentação", "Celular"))
        listOfTransactions.add(mockTransaction("2", "19/01/2018", 20.20, "Alimentação", "Celular"))
        listOfTransactions.add(mockTransaction("3", "10/01/2018", 30.30, "Alimentação", "Celular"))
        listOfTransactions.add(mockTransaction("4", "01/01/2018", 40.40, "Alimentação", "Celular"))

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
        listOfTransactions.add(mockTransaction("1", "21/01/2018", 30.5, "Alimentação", "Celular"))
        listOfTransactions.add(mockTransaction("2", "29/01/2018", 30.1, "Alimentação", "Celular"))
        listOfTransactions.add(mockTransaction("3", "20/01/2018", 70.1, "Moradia", "Celular"))
        listOfTransactions.add(mockTransaction("4", "20/01/2018", 70.1, "Alimentação", "Celular"))
        listOfTransactions.add(mockTransaction("5", "20/01/2018", 0.0, "Alimentação", "Cartão"))

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
        listOfTransactions.add(mockTransaction("1", "01/01/2018", 50.0, "Alimentação", "Celular"))
        listOfTransactions.add(mockTransaction("2", "19/01/2018", 50.0, "Alimentação", "Celular"))
        listOfTransactions.add(mockTransaction("3", "20/01/2018", 50.0, "Alimentação", "Celular"))
        listOfTransactions.add(mockTransaction("4", "21/01/2018", 50.0, "Alimentação", "Celular"))

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

    private fun mockTransaction(key: String, dateString: String, price: Double, parentTag: String, tag: String): TransactionVO {
        val vo = TransactionVO()
        vo.key = key
        vo.paymentDate = JavaUtils.DateUtil.parse(dateString, JavaUtils.DateUtil.DD_MM_YYYY)
        vo.price = price
        vo.tag = TagVO("", parentTag, tag)
        return vo
    }
}