package com.example.splitwise_payg.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.splitwise_payg.db.converters.BigDecimalConverter
import com.example.splitwise_payg.db.converters.CurrencyConverter
import com.example.splitwise_payg.db.dao.ExpenseDao
import com.example.splitwise_payg.db.dao.UserDao
import com.example.splitwise_payg.db.entities.Expense
import com.example.splitwise_payg.db.entities.User
import com.example.splitwise_payg.db.entities.UserGroup

@Database(
    entities = [User::class, Expense::class, com.example.splitwise_payg.db.entities.Group::class, UserGroup::class],
    version = 2
)
@TypeConverters(
    CurrencyConverter::class,
    BigDecimalConverter::class
)
abstract class UserDatabase: RoomDatabase() {

    abstract val userDao: UserDao
    abstract val expenseDao: ExpenseDao
}