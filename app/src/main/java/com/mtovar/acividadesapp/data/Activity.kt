package com.mtovar.acividadesapp.data

import java.util.UUID

data class Activity(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val date: String,
    val time: String,
)
