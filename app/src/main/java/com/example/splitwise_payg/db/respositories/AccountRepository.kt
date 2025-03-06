package com.example.splitwise_payg.db.respositories

import com.example.splitwise_payg.db.dao.UserDao
import com.example.splitwise_payg.db.entities.User
import kotlinx.coroutines.flow.firstOrNull

class AccountRepository(private val userDao: UserDao) {
    suspend fun userLogin(emailAddress: String, password: String): Result<User> {
        try {
            val user = userDao.getUserByEmailAndPassword(emailAddress, password)
            return if (user != null) {
                Result.success(user)
            } else {
                Result.failure(Exception("Invalid email or password."))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    suspend fun userSignUp(fullName: String, emailAddress: String, password: String, phoneNumber: String = ""): Result<Long> {
        try {
            if(userDao.doesEmailExist(emailAddress)) {
                return Result.failure(Exception("This email already has an account."))
            }
            val user = User(fullName, emailAddress, password, phoneNumber)
            val id = userDao.upsertUser(user)
            return Result.success(id)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    suspend fun getFullNameByUserId(userId: Int): Result<String> {
        try {
            val user = userDao.getUserById(userId).firstOrNull()
            return if (user != null) {
                Result.success(user.fullName)
            } else {
                Result.failure(Exception("User with ID $userId not found"))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}