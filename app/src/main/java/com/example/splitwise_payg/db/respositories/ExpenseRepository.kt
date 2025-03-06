package com.example.splitwise_payg.db.respositories

import android.icu.util.Currency
import com.example.splitwise_payg.db.dao.ExpenseDao
import com.example.splitwise_payg.db.domainModel.UserExpenseList
import com.example.splitwise_payg.db.entities.Expense
import com.example.splitwise_payg.enumClasses.OwnershipType
import com.example.splitwise_payg.enumClasses.SplitType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal

class ExpenseRepository(private val expenseDao: ExpenseDao) {
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
            return Result.failure(e)
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
            return Result.failure(e)
        }
    }
}