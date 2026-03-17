package org.ayaz.finance.presentation.util

sealed interface FinanceExceptions {
    data class MissingBodyException(override val message: String = "Request body is required."): Exception(), FinanceExceptions
    data class MissingFieldException(override val message: String = "All of the field(s) are required for your request body."): Exception(), FinanceExceptions
}