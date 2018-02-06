package com.system.m4.kotlin

import com.system.m4.infrastructure.JavaUtils
import com.system.m4.kotlin.home.HomeBusiness
import com.system.m4.kotlin.home.HomeDTO
import com.system.m4.views.vos.*
import junit.framework.Assert
import org.junit.Test
import java.util.*
import kotlin.collections.ArrayList

class HomeBusinessTest {

    @Test
    fun splitTransactionsByDate20WithNulls() {

        val homeDTO = HomeDTO(1, 2018)
        val homeVO = HomeVO()

        homeDTO.listTransaction = arrayListOf()

        homeVO.transactions1Q = HomeBusiness().splitTransactionsByDate20(homeDTO.listTransaction!!, true, null)
        homeVO.transactions2Q = HomeBusiness().splitTransactionsByDate20(homeDTO.listTransaction!!, false, null)

        Assert.assertEquals(0, homeVO.transactions1Q.transactions.size)
        Assert.assertEquals(0, homeVO.transactions2Q.transactions.size)

        Assert.assertEquals(0.0, homeVO.transactions1Q.amount)
        Assert.assertEquals(0.0, homeVO.transactions2Q.amount)
    }

    @Test
    fun splitTransactionsByDate20With0Q() {

        val listOfTransactions = mutableListOf<TransactionVO>()

        val homeDTO = HomeDTO(1, 2018)
        homeDTO.listTransaction = ArrayList(listOfTransactions)

        val homeVO = HomeVO()

        homeVO.transactions1Q = HomeBusiness().splitTransactionsByDate20(homeDTO.listTransaction!!, true, null)
        homeVO.transactions2Q = HomeBusiness().splitTransactionsByDate20(homeDTO.listTransaction!!, false, null)

        Assert.assertEquals(0, homeVO.transactions1Q.transactions.size)
        Assert.assertEquals(0, homeVO.transactions2Q.transactions.size)

        Assert.assertEquals(0.0, homeVO.transactions1Q.amount)
        Assert.assertEquals(0.0, homeVO.transactions2Q.amount)
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

        homeVO.transactions1Q = HomeBusiness().splitTransactionsByDate20(homeDTO.listTransaction!!, true, null)
        homeVO.transactions2Q = HomeBusiness().splitTransactionsByDate20(homeDTO.listTransaction!!, false, null)

        Assert.assertEquals(4, homeVO.transactions1Q.transactions.size)
        Assert.assertEquals("4", homeVO.transactions1Q.transactions[0].key)
        Assert.assertEquals("1", homeVO.transactions1Q.transactions[1].key)
        Assert.assertEquals("3", homeVO.transactions1Q.transactions[2].key)
        Assert.assertEquals("2", homeVO.transactions1Q.transactions[3].key)

        Assert.assertEquals(0, homeVO.transactions2Q.transactions.size)

        Assert.assertEquals(101.0, homeVO.transactions1Q.amount)
        Assert.assertEquals(0.0, homeVO.transactions2Q.amount)
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

        homeVO.transactions1Q = HomeBusiness().splitTransactionsByDate20(homeDTO.listTransaction!!, true, null)
        homeVO.transactions2Q = HomeBusiness().splitTransactionsByDate20(homeDTO.listTransaction!!, false, null)

        Assert.assertEquals(0, homeVO.transactions1Q.transactions.size)

        Assert.assertEquals(5, homeVO.transactions2Q.transactions.size)
        Assert.assertEquals("5", homeVO.transactions2Q.transactions[0].key)
        Assert.assertEquals("4", homeVO.transactions2Q.transactions[1].key)
        Assert.assertEquals("3", homeVO.transactions2Q.transactions[2].key)
        Assert.assertEquals("1", homeVO.transactions2Q.transactions[3].key)
        Assert.assertEquals("2", homeVO.transactions2Q.transactions[4].key)

        Assert.assertEquals(0.0, homeVO.transactions1Q.amount)
        Assert.assertEquals(200.8, homeVO.transactions2Q.amount)
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

        homeVO.transactions1Q = HomeBusiness().splitTransactionsByDate20(homeDTO.listTransaction!!, true, null)
        homeVO.transactions2Q = HomeBusiness().splitTransactionsByDate20(homeDTO.listTransaction!!, false, null)

        Assert.assertEquals(2, homeVO.transactions1Q.transactions.size)
        Assert.assertEquals("1", homeVO.transactions1Q.transactions[0].key)
        Assert.assertEquals("2", homeVO.transactions1Q.transactions[1].key)

        Assert.assertEquals(2, homeVO.transactions2Q.transactions.size)
        Assert.assertEquals("3", homeVO.transactions2Q.transactions[0].key)
        Assert.assertEquals("4", homeVO.transactions2Q.transactions[1].key)

        Assert.assertEquals(100.0, homeVO.transactions1Q.amount)
        Assert.assertEquals(100.0, homeVO.transactions2Q.amount)
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

        homeVO.pendingTransaction = HomeBusiness().splitPendingTransactions(homeDTO.listTransaction!!)

        Assert.assertEquals(4, homeVO.pendingTransaction.size)
        Assert.assertEquals("1", homeVO.pendingTransaction[0].key)
        Assert.assertEquals("2", homeVO.pendingTransaction[1].key)
        Assert.assertEquals("3", homeVO.pendingTransaction[2].key)
        Assert.assertEquals("4", homeVO.pendingTransaction[3].key)
    }

    @Test
    fun splitGroupOfTransactionsWith2Groups() {

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

        homeVO.groups = HomeBusiness().splitGroupTransaction(homeDTO.listGroup!![0], homeDTO.listTransaction!!)

        Assert.assertEquals(2, homeVO.groups.size)

        homeVO.groups.keys.forEach {
            when (it.key) {
                "KEY_PAYMENT1" -> {
                    Assert.assertEquals(3, homeVO.groups[it]?.transactions?.size)
                    Assert.assertEquals(true, homeVO.groups[it]?.transactions!![0].isOnGroup)
                    Assert.assertEquals(150.0, homeVO.groups[it]?.amount)
                }
                "KEY_PAYMENT2" -> {
                    Assert.assertEquals(2, homeVO.groups[it]?.transactions?.size)
                    Assert.assertEquals(true, homeVO.groups[it]?.transactions!![0].isOnGroup)
                    Assert.assertEquals(100.0, homeVO.groups[it]?.amount)
                }
                "KEY_PAYMENT3" -> {
                    Assert.assertEquals(0, homeVO.groups[it]?.transactions?.size)
                    Assert.assertEquals(50.0, homeVO.groups[it]?.amount)
                }
            }
        }
    }

    @Test
    fun splitTagSummaryWith4Results() {

        val listOfTransactions = mutableListOf<TransactionVO>()
        listOfTransactions.add(mockTransaction("1", "", 50.0, "A1", "", ""))
        listOfTransactions.add(mockTransaction("2", "", 50.0, "A1", "", ""))
        listOfTransactions.add(mockTransaction("3", "", 50.0, "A2", "", ""))
        listOfTransactions.add(mockTransaction("4", "", 50.0, "A3", "", ""))
        listOfTransactions.add(mockTransaction("5", "", 50.0, "A3", "", ""))
        listOfTransactions.add(mockTransaction("6", "", 50.0, "A4", "", ""))

        val listOfTags = mutableListOf<TagVO>()
        listOfTags.add(TagVO("A1", "Alimentação", "Almoço"))
        listOfTags.add(TagVO("A2", "Alimentação", "Lanche"))
        listOfTags.add(TagVO("A3", "Habitação", "Celular"))
        listOfTags.add(TagVO("A4", "Habitação", "Aluguel"))

        val homeDTO = HomeDTO(1, 2018)
        homeDTO.listTransaction = ArrayList(listOfTransactions)
        homeDTO.listTag = ArrayList(listOfTags)

        val tagsSummary = HomeBusiness().splitTagSummary(homeDTO.listTag!!, homeDTO.listTransaction!!)

        Assert.assertEquals("A1", tagsSummary[0].key)
        Assert.assertEquals("Alimentação", tagsSummary[0].parentName)
        Assert.assertEquals("Almoço", tagsSummary[0].name)
        Assert.assertEquals(100.0, tagsSummary[0].value)

        Assert.assertEquals("A2", tagsSummary[1].key)
        Assert.assertEquals("Alimentação", tagsSummary[1].parentName)
        Assert.assertEquals("Lanche", tagsSummary[1].name)
        Assert.assertEquals(50.0, tagsSummary[1].value)

        Assert.assertEquals("A4", tagsSummary[2].key)
        Assert.assertEquals("Habitação", tagsSummary[2].parentName)
        Assert.assertEquals("Aluguel", tagsSummary[2].name)
        Assert.assertEquals(50.0, tagsSummary[2].value)

        Assert.assertEquals("A3", tagsSummary[3].key)
        Assert.assertEquals("Habitação", tagsSummary[3].parentName)
        Assert.assertEquals("Celular", tagsSummary[3].name)
        Assert.assertEquals(100.0, tagsSummary[3].value)
    }

    @Test
    fun getTransactionGroupedWithNulls() {
        Assert.assertEquals(true, HomeBusiness().splitTransactionGrouped(null, mutableListOf()).isEmpty())
        Assert.assertEquals(true, HomeBusiness().splitTransactionGrouped(GroupTransactionVO(mutableListOf()), mutableListOf()).isEmpty())
    }

    @Test
    fun getTransactionGroupedWith0Groups() {

        val listOfPayments = mutableListOf<PaymentTypeVO>()
        listOfPayments.add(mockPaymentType("PAY0", "P0"))

        val group = GroupTransactionVO()
        group.paymentTypeList = listOfPayments

        val listOfTransactions = mutableListOf<TransactionVO>()
        listOfTransactions.add(mockTransaction("KEY1", "01/01/2018", "PAY1", "P1", 50.0))
        listOfTransactions.add(mockTransaction("KEY2", "01/01/2018", "PAY1", "P1", 50.0))
        listOfTransactions.add(mockTransaction("KEY3", "01/01/2018", "PAY1", "P1", 50.0))
        listOfTransactions.add(mockTransaction("KEY4", "01/01/2018", "PAY2", "P2", 50.0))
        listOfTransactions.add(mockTransaction("KEY5", "01/01/2018", "PAY2", "P2", 50.0))
        listOfTransactions.add(mockTransaction("KEY6", "01/01/2018", "PAY3", "P3", 50.0))

        val grouped = HomeBusiness().splitTransactionGrouped(group, listOfTransactions)

        Assert.assertEquals(6, grouped.size)
    }

    @Test
    fun getTransactionGroupedWith2Groups() {

        val listOfPayments = mutableListOf<PaymentTypeVO>()
        listOfPayments.add(mockPaymentType("PAY1", "P1"))
        listOfPayments.add(mockPaymentType("PAY3", "P3"))

        val group = GroupTransactionVO()
        group.paymentTypeList = listOfPayments

        val listOfTransactions = mutableListOf<TransactionVO>()
        listOfTransactions.add(mockTransaction("KEY1", "01/01/2018", "PAY1", "P1", 50.0))
        listOfTransactions.add(mockTransaction("KEY2", "01/01/2018", "PAY1", "P1", 50.0))
        listOfTransactions.add(mockTransaction("KEY3", "01/01/2018", "PAY1", "P1", 50.0))
        listOfTransactions.add(mockTransaction("KEY4", "01/01/2018", "PAY2", "P2", 50.0))
        listOfTransactions.add(mockTransaction("KEY5", "01/01/2018", "PAY2", "P2", 50.0))
        listOfTransactions.add(mockTransaction("KEY6", "02/01/2018", "PAY3", "P3", 50.0))

        val grouped = HomeBusiness().splitTransactionGrouped(group, listOfTransactions)

        Assert.assertEquals(4, grouped.size)

        grouped.forEach {

            when (it.paymentType.key) {

                "PAY1" -> {
                    Assert.assertEquals("PAY1", it.paymentType.key)
                    Assert.assertEquals("P1", it.paymentType.name)
                    Assert.assertEquals(150.0, it.price)
                    Assert.assertEquals("P1", it.tag.name)
                    Assert.assertEquals("01/01/2018", JavaUtils.DateUtil.format(it.paymentDate, JavaUtils.DateUtil.DD_MM_YYYY))
                }

                "PAY3" -> {
                    Assert.assertEquals("PAY3", it.paymentType.key)
                    Assert.assertEquals("P3", it.paymentType.name)
                    Assert.assertEquals(50.0, it.price)
                    Assert.assertEquals("P3", it.tag.name)
                    Assert.assertEquals("02/01/2018", JavaUtils.DateUtil.format(it.paymentDate, JavaUtils.DateUtil.DD_MM_YYYY))
                }
            }
        }
    }

    private fun mockTransaction(key: String, dateString: String, paymentKey: String, paymentName: String, price: Double): TransactionVO {
        val vo = TransactionVO()
        vo.key = key
        vo.paymentDate = if (dateString.isNotBlank()) JavaUtils.DateUtil.parse(dateString, JavaUtils.DateUtil.DD_MM_YYYY) else Date()
        vo.paymentType = PaymentTypeVO(paymentKey)
        vo.paymentType.name = paymentName
        vo.price = price
        vo.tag = TagVO("K1", "N1", "N1.1")
        return vo
    }

    private fun mockPaymentType(key: String, name: String): PaymentTypeVO {
        val vo = PaymentTypeVO()
        vo.key = key
        vo.name = name
        return vo
    }

    private fun mockTransaction(key: String, dateString: String, price: Double, tagParentKey: String, tagParentName: String, tagName: String): TransactionVO {
        val vo = TransactionVO()
        vo.key = key
        vo.paymentDate = if (dateString.isNotBlank()) JavaUtils.DateUtil.parse(dateString, JavaUtils.DateUtil.DD_MM_YYYY) else Date()
        vo.price = price
        vo.tag = TagVO(tagParentKey, tagParentName, tagName)
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