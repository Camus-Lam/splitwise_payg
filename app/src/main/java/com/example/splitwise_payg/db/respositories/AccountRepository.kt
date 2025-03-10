package com.example.splitwise_payg.db.respositories

import com.example.splitwise_payg.db.dao.UserDao
import com.example.splitwise_payg.db.entities.User
import com.example.splitwise_payg.errors.AccountError

class AccountRepository(private val userDao: UserDao) {
    suspend fun userLogin(emailAddress: String, password: String): Result<User> {
        try {
            val user = userDao.getUserByEmailAndPassword(emailAddress, password)
            return if (user != null) {
                Result.success(user)
            } else {
                Result.failure(AccountError.InvalidCredentials())
            }
        } catch (e: Exception) {
            return Result.failure(AccountError.UnknownError())
        }
    }

    suspend fun userSignUp(fullName: String, emailAddress: String, password: String, phoneNumber: String = ""): Result<Long> {
        try {
            if(userDao.doesEmailExist(emailAddress)) {
                return Result.failure(AccountError.EmailAlreadyExists())
            }
            val user = User(fullName, emailAddress, password, phoneNumber)
            val id = userDao.upsertUser(user)
            return Result.success(id)
        } catch (e: Exception) {
            return Result.failure(AccountError.UnknownError())
        }
    }
}