package com.example.splitwise_payg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.room.Room
import com.example.splitwise_payg.db.respositories.AccountRepository
import com.example.splitwise_payg.db.UserDatabase
import com.example.splitwise_payg.db.respositories.ExpenseRepository
import com.example.splitwise_payg.pages.MainScreen
import com.example.splitwise_payg.ui.theme.SplitwisepaygTheme
import com.example.splitwise_payg.viewModel.ExpenseViewModel
import com.example.splitwise_payg.viewModel.UserViewModel

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "users.db"
        ).build()
    }

    private lateinit var accountRepository: AccountRepository
    private lateinit var expenseRepository: ExpenseRepository

    private val userViewModel by viewModels<UserViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                    return UserViewModel(accountRepository) as T
                }
            }
        }
    )

    private val expenseViewModel by viewModels<ExpenseViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                    return ExpenseViewModel(expenseRepository, accountRepository) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        accountRepository = AccountRepository(db.userDao)
        expenseRepository = ExpenseRepository(db.expenseDao, db.expenseEditHistoryDao)

        setContent {
            SplitwisepaygTheme {
                MainScreen(userViewModel = userViewModel, expenseViewModel = expenseViewModel)
                }
            }
    }
}