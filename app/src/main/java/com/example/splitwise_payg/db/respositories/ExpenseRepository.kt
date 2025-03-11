package com.example.splitwise_payg.db.respositories

import android.icu.util.Currency
import com.example.splitwise_payg.db.converters.BigDecimalConverter
import com.example.splitwise_payg.db.converters.CurrencyConverter
import com.example.splitwise_payg.db.dao.ExpenseDao
import com.example.splitwise_payg.db.dao.ExpenseEditHistoryDao
import com.example.splitwise_payg.db.domainModel.UserExpenseList
import com.example.splitwise_payg.db.entities.Expense
import com.example.splitwise_payg.db.entities.ExpenseEditHistory
import com.example.splitwise_payg.db.entities.ExpenseEditHistoryFieldChange
import com.example.splitwise_payg.db.entities.relations.ExpenseEditHistoryWithFieldChange
import com.example.splitwise_payg.enumClasses.OwnershipType
import com.example.splitwise_payg.enumClasses.SplitType
import com.example.splitwise_payg.errors.ExpenseError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal

class ExpenseRepository(private val expenseDao: ExpenseDao, private val expenseEditHistoryDao: ExpenseEditHistoryDao) {
    suspend fun addExpense(
        amount: BigDecimal,
        currency: Currency,
        creatorId: Int,
        ownershipType: OwnershipType,
        splitType: SplitType,
        targetUserId: Int? = null,
        targetGroupId: Int? = null
    ): Result<Long> {
        try {
            val expense = Expense(
                amount,
                currency,
                creatorId,
                targetUserId,
                targetGroupId,
                ownershipType,
                splitType
            )
            val id = expenseDao.upsertExpense(expense)
            return Result.success(id)
        } catch (e: Exception) {
            return Result.failure(ExpenseError.AddExpenseError())
        }
    }

    fun getUserExpenses(userId: Int): Flow<UserExpenseList> {
        return expenseDao.getUserWithExpenses(userId).map { userWithExpenses ->
            UserExpenseList.fromUserWithExpenses(userWithExpenses).sortedByTime()
        }
    }

    suspend fun deleteExpense(expense: Expense): Result<Unit> {
        try {
            expenseDao.deleteExpense(expense)
            return Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(ExpenseError.UnknownError())
        }
    }

    fun getExpenseEditHistory(expenseId: Int) : Flow<List<ExpenseEditHistoryWithFieldChange>> {
        return expenseEditHistoryDao.getExpenseEditHistoryWithFieldChanges(expenseId)
    }

    suspend fun editExpense(
        amount: BigDecimal,
        currency: Currency,
        ownershipType: OwnershipType,
        splitType: SplitType,
        targetUserId: Int? = null,
        targetGroupId: Int? = null,
        expense: Expense
    ): Result<Long?> {
        try{
            val fieldChanges = detectChanges(
                oldExpense = expense,
                newAmount = amount,
                newCurrency = currency,
                newOwnershipType = ownershipType,
                newSplitType = splitType,
                newTargetUserId = targetUserId,
                newTargetGroupId = targetGroupId
            )

            if (fieldChanges.isNotEmpty()) {
                val newExpense = expense.copy(
                    amount = amount,
                    currency = currency,
                    targetUserId = targetUserId,
                    targetGroupId = targetGroupId,
                    ownershipType = ownershipType,
                    splitType = splitType
                )
                expenseDao.upsertExpense(newExpense)
                val expenseId = expense.id

                val expenseEditHistory = ExpenseEditHistory(expenseId.toLong())
                val expenseEditHistoryId = expenseEditHistoryDao.upsertExpenseEditHistory(expenseEditHistory)
                recordFieldChanges(expenseEditHistoryId, fieldChanges)

                return Result.success(expenseId.toLong())
            }
            return Result.success(null)
        } catch (e: Exception) {
            return Result.failure(ExpenseError.UnknownError())
        }
    }

    private fun detectChanges(
        oldExpense: Expense,
        newAmount: BigDecimal,
        newCurrency: Currency,
        newOwnershipType: OwnershipType,
        newSplitType: SplitType,
        newTargetUserId: Int?,
        newTargetGroupId: Int?
    ): Map<String, String?> {
        val changes = mutableMapOf<String, String?>()
        if (newAmount != oldExpense.amount) changes["amount"] = BigDecimalConverter().fromBigDecimal(oldExpense.amount)
        if (newCurrency != oldExpense.currency) changes["currency"] = CurrencyConverter().fromCurrency(oldExpense.currency)
        if (newOwnershipType != oldExpense.ownershipType) changes["ownershipType"] = oldExpense.ownershipType.name
        if (newSplitType != oldExpense.splitType) changes["splitType"] = oldExpense.splitType.name
        if (newTargetUserId != oldExpense.targetUserId) changes["targetUserId"] = oldExpense.targetUserId?.toString()
        if (newTargetGroupId != oldExpense.targetGroupId) changes["targetGroupId"] = oldExpense.targetGroupId?.toString()
        return changes
    }

    private suspend fun recordFieldChanges(historyId: Long, changes: Map<String, String?>) {
        for ((fieldName, oldValue) in changes) {
            val fieldChange = ExpenseEditHistoryFieldChange(
                editHistoryId = historyId,
                fieldName = fieldName,
                oldValue = oldValue
            )
            expenseEditHistoryDao.upsertExpenseEditHistoryFieldChange(fieldChange)
        }
    }
}