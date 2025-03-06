package com.example.splitwise_payg.db.converters

import android.icu.util.Currency
import androidx.room.TypeConverter

class CurrencyConverter {
    @TypeConverter
    fun fromCurrency(currency: Currency): String {
        return currency.currencyCode ?: "CAD"
    }

    @TypeConverter
    fun toCurrency(code: String?): Currency {
        return Currency.getInstance(code ?: "CAD")
    }
}