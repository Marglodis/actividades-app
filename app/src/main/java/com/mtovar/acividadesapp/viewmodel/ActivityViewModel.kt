package com.mtovar.acividadesapp.viewmodel

import androidx.lifecycle.ViewModel
import com.mtovar.acividadesapp.data.Activity
import com.mtovar.acividadesapp.data.ValidationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ActivityViewModel : ViewModel() {
    private val _activities = MutableStateFlow<List<Activity>>(emptyList())
    val activities: StateFlow<List<Activity>> = _activities.asStateFlow()

    private val _selectedActivity = MutableStateFlow<Activity?>(null)
    val selectedActivity: StateFlow<Activity?> = _selectedActivity.asStateFlow()

    fun addActivity(activity: Activity) {
        _activities.update { currentList ->
            currentList + activity
        }
    }

    fun updateActivity(activity: Activity) {
        _activities.update { currentList ->
            currentList.map {
                if (it.id == activity.id) activity else it
            }
        }
    }

    fun deleteActivity(id: String) {
        _activities.update { currentList ->
            currentList.filter { it.id != id }
        }
    }

    fun getActivityById(id: String): Activity? {
        return _activities.value.find { it.id == id }
    }

    fun validateActivity(
        name: String,
        description: String,
        date: String,
        time: String
    ): ValidationResult {

        val trimmedDate = date.trim()
        val trimmedTime = time.trim()

        if (name.isBlank()) {
            return ValidationResult(false, "El nombre no puede estar vacío")
        }
        if (description.isBlank()) {
            return ValidationResult(false, "La descripción no puede estar vacía")
        }
        if (trimmedDate.isBlank()) {
            return ValidationResult(false, "La fecha no puede estar vacía")
        }
        if (trimmedTime.isBlank()) {
            return ValidationResult(false, "La hora no puede estar vacía")
        }
        if (!trimmedDate.matches(Regex("\\d{2}/\\d{2}/\\d{4}"))) {
            return ValidationResult(false, "La fecha debe estar en el formato dd/mm/yyyy")
        }
        if (!trimmedTime.matches(Regex("\\d{2}:\\d{2}"))) {
            return ValidationResult(false, "La hora debe estar en el formato hh:mm")
        }
        if (description.length < 10) {
            return ValidationResult(false, "La descripción debe tener al menos 10 caracteres")
        }
        if (name.length < 5) {
            return ValidationResult(false, "El nombre debe tener al menos 5 caracteres")
        }
        return ValidationResult(true, "")
    }
}
