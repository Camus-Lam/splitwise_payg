package com.example.splitwise_payg.db.converters

import android.icu.util.Currency
import androidx.room.TypeConverter
import com.example.splitwise_payg.enumClasses.CurrencyCode

class CurrencyConverter {
    @TypeConverter
    fun fromCurrency(currency: Currency): String {
        return currency.currencyCode ?: CurrencyCode.CAD.code
    }

    @TypeConverter
    fun toCurrency(code: String?): Currency {
        return Currency.getInstance(code ?: CurrencyCode.CAD.code)
    }
}