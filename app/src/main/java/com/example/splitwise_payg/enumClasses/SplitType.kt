package com.example.splitwise_payg.enumClasses

enum class SplitType {
    EVEN,
    FULL,
    CUSTOM;

    companion object {
        fun fromString(value: String): SplitType? {
            return when (value.lowercase()) {
                "even" -> EVEN
                "full" -> FULL
                "custom" -> CUSTOM
                else -> null
            }
        }

        fun toString(splitType: SplitType): String {
            return when (splitType) {
                EVEN -> "Split evenly between payer and owee"
                FULL -> "Fully paid by owee"
                CUSTOM -> "TBD"
            }
        }
    }
}