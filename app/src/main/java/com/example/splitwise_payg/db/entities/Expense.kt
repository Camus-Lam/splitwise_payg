package com.example.splitwise_payg.db.entities

import android.icu.util.Currency
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.splitwise_payg.enumClasses.CurrencyCode
import com.example.splitwise_payg.enumClasses.OwnershipType
import com.example.splitwise_payg.enumClasses.SplitType
import java.math.BigDecimal

@Entity(
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["creatorId"]),
        ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["targetUserId"], onDelete = ForeignKey.SET_NULL),
        ForeignKey(entity = Group::class, parentColumns = ["id"], childColumns = ["targetGroupId"], onDelete = ForeignKey.SET_NULL)
    ]
)
data class Expense(
    val amount: BigDecimal,
    val currency: Currency = Currency.getInstance(CurrencyCode.CAD.code),
    val creatorId: Int,
    val targetUserId: Int? = null,
    val targetGroupId: Int? = null,
    val ownershipType: OwnershipType,
    val splitType: SplitType,
    val createTime: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
