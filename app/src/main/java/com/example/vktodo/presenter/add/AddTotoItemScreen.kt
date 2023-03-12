package com.example.vktodo.presenter.add

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vktodo.presenter.utils.asFormattedString
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTotoItemScreen(
    navController: NavController,
    viewModel: AddTodoViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState()
    val context = LocalContext.current

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            TimePickerDialog(
                context,
                { _, selectedHour: Int, selectedMinute: Int ->
                    viewModel.onEvent(
                        AddTotoItemEvent.DataChanged(
                            date = Calendar.getInstance().apply {
                                set(
                                    selectedYear,
                                    selectedMonth,
                                    selectedDayOfMonth,
                                    selectedHour,
                                    selectedMinute
                                )
                            }
                        )
                    )
                }, state.value.date[Calendar.HOUR], state.value.date[Calendar.MINUTE], false
            ).show()
        },
        state.value.date[Calendar.YEAR],
        state.value.date[Calendar.MONTH],
        state.value.date[Calendar.DAY_OF_MONTH]
    )

    LaunchedEffect(key1 = true) {
        viewModel.uiActions.collect { action ->
            when (action) {
                AddItemActions.NavigateUp -> navController.navigateUp()
                AddItemActions.OpenDateTimePicker -> datePicker.show()
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { if (state.value.isSaveEnabled) viewModel.onEvent(AddTotoItemEvent.Save) },
                containerColor =
                if (state.value.isSaveEnabled) MaterialTheme.colorScheme.primaryContainer
                else Color(0xFFE0E0E0),
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        }) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                TextField(
                    placeholder = { Text(text = "Enter the title") },
                    value = state.value.title,
                    onValueChange = { viewModel.onEvent(AddTotoItemEvent.TitleChanged(it)) },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = state.value.date.asFormattedString(),
                    onValueChange = { },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    trailingIcon = {
                        IconButton(onClick = { viewModel.onEvent(AddTotoItemEvent.ShowDateTimePicker) }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Select date"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}