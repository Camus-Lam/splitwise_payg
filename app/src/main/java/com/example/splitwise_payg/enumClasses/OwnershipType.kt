package com.example.splitwise_payg.enumClasses

enum class OwnershipType {
    CREATOR_PAID,
    TARGET_PAID;

    companion object {
        fun fromString(value: String): OwnershipType? {
            return when (value.lowercase()) {
                "owing" -> TARGET_PAID
                "paid" -> CREATOR_PAID
                else -> null
            }
        }

        fun toString(ownershipType: OwnershipType): String {
            return when (ownershipType) {
                CREATOR_PAID -> "Paid"
                TARGET_PAID -> "Owing"
            }
        }
    }
}