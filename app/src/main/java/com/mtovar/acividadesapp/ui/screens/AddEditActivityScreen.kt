package com.mtovar.acividadesapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mtovar.acividadesapp.data.Activity
import com.mtovar.acividadesapp.viewmodel.ActivityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditActivityScreen(
    viewModel: ActivityViewModel,
    activityId: String? = null,
    onNavigateBack: () -> Unit
) {
    val isEditMode = activityId != null
    val existingActivity = remember(activityId) {
        activityId?.let { viewModel.getActivityById(it) }
    }

    var name by remember { mutableStateOf(existingActivity?.name ?: "") }
    var date by remember { mutableStateOf(existingActivity?.date ?: "") }
    var time by remember { mutableStateOf(existingActivity?.time ?: "") }
    var description by remember { mutableStateOf(existingActivity?.description ?: "") }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (isEditMode) "Editar Actividad" else "Nueva Actividad",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    errorMessage = ""
                },
                label = { Text("Nombre de la actividad") },
                leadingIcon = {
                    Icon(Icons.Default.Edit, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = date,
                onValueChange = {
                    date = it
                    errorMessage = ""
                },
                label = { Text("Fecha (dd/mm/yyyy)") },
                leadingIcon = {
                    Icon(Icons.Default.CalendarToday, contentDescription = null)
                },
                placeholder = { Text("31/12/2024") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = time,
                onValueChange = {
                    time = it
                    errorMessage = ""
                },
                label = { Text("Hora (hh:mm)") },
                leadingIcon = {
                    Icon(Icons.Default.AccessTime, contentDescription = null)
                },
                placeholder = { Text("14:30") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                    errorMessage = ""
                },
                label = { Text("Descripción") },
                leadingIcon = {
                    Icon(Icons.Default.Description, contentDescription = null)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                maxLines = 5
            )

            if (errorMessage.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            Icons.Default.Error,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Button(
                onClick = {
                    val validation = viewModel.validateActivity(name= name, date = date, time = time, description = description)
                    if (validation.isValid) {
                        if (isEditMode && existingActivity != null) {
                            viewModel.updateActivity(
                                existingActivity.copy(
                                    name = name,
                                    date = date,
                                    time = time,
                                    description = description
                                )
                            )
                        } else {
                            viewModel.addActivity(
                                Activity(
                                    name = name,
                                    date = date,
                                    time = time,
                                    description = description
                                )
                            )
                        }
                        onNavigateBack()
                    } else {
                        errorMessage = validation.errorMessage
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Icon(
                    if (isEditMode) Icons.Default.Save else Icons.Default.Add,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    if (isEditMode) "Guardar Cambios" else "Crear Actividad",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}