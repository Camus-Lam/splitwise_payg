package com.example.splitwise_payg.enumClasses

import com.example.splitwise_payg.R

enum class SplitType {
    EVEN,
    FULL,
    CUSTOM;

    companion object {
        fun getDescription(splitType: SplitType): Int {
            return when (splitType) {
                EVEN -> R.string.even_split_description
                FULL -> R.string.full_split_description
                CUSTOM -> R.string.custom_split_description
            }
        }

        fun getDisplayText(splitType: SplitType): Int {
            return when (splitType) {
                EVEN -> R.string.even_split_display_text
                FULL -> R.string.full_split_display_text
                CUSTOM -> R.string.custom_split_display_text
            }
        }
    }
}