package com.example.splitwise_payg.enumClasses

import com.example.splitwise_payg.R

enum class OwnershipType {
    CREATOR_PAID,
    TARGET_PAID;

    companion object {

        fun getDisplayText(ownershipType: OwnershipType): Int {
            return when (ownershipType) {
                CREATOR_PAID -> R.string.paid_ownership_display_text
                TARGET_PAID -> R.string.owing_ownership_display_text
            }
        }
    }
}