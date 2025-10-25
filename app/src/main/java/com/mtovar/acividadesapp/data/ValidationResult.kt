package com.mtovar.acividadesapp.data

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)
