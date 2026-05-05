package com.androidforge.habitflow.presentation.addedit

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.androidforge.habitflow.R
import com.androidforge.habitflow.core.util.showToast
import com.androidforge.habitflow.presentation.components.CustomTopAppBar
import com.androidforge.habitflow.presentation.components.ErrorState
import com.androidforge.habitflow.presentation.components.OfflineState
import com.androidforge.habitflow.presentation.components.PrimaryButton
import com.androidforge.habitflow.presentation.components.SecondaryButton
import com.androidforge.habitflow.presentation.components.ShimmerLoadingScreen
import com.androidforge.habitflow.presentation.util.AdmobManager
import kotlinx.coroutines.launch
import timber.log.Timber
import androidx.compose.foundation.background

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditHabitScreen(
    habitId: String?,
    onBack: () -> Unit,
    admobManager: AdmobManager,
    viewModel: AddEditHabitViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val activity = LocalContext.current as Activity

    // Observe Lifecycle for AdmobManager
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            Timber.d("AddEditHabitScreen: Lifecycle STARTED, preloading interstitial ad.")
            admobManager.loadInterstitialAd(context)
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = if (habitId == null) stringResource(R.string.add_habit) else stringResource(R.string.edit_habit),
                showBackIcon = true,
                onBackClick = onBack,
                actions = {
                    if (habitId != null && uiState is AddEditHabitUiState.Success) {
                        IconButton(
                            onClick = viewModel::onDeleteClick,
                            enabled = !(uiState as? AddEditHabitUiState.Success)?.isDeleting!!
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.delete_habit_description),
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        when (val state = uiState) {
            AddEditHabitUiState.Loading -> {
                ShimmerLoadingScreen(count = 3) // Simpler shimmer for form
            }
            is AddEditHabitUiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    OutlinedTextField(
                        value = state.name,
                        onValueChange = viewModel::onNameChange,
                        label = { Text(stringResource(R.string.habit_name_label)) },
                        placeholder = { Text(stringResource(R.string.habit_name_placeholder)) },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            cursorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = state.description,
                        onValueChange = viewModel::onDescriptionChange,
                        label = { Text(stringResource(R.string.habit_description_label)) },
                        placeholder = { Text(stringResource(R.string.habit_description_placeholder)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        maxLines = 5,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            cursorColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    PrimaryButton(
                        text = stringResource(R.string.save),
                        onClick = {
                            viewModel.saveHabit {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        context.getString(
                                            if (habitId == null) R.string.add_habit else R.string.edit_habit
                                        ) + " " + context.getString(R.string.habit_marked_completed) // Reusing string, ideally a new one for "saved"
                                    )
                                    // Show ad on successful save/update, but only if not deleting
                                    if (habitId == null) { // Only show for new habit creation
                                        admobManager.showInterstitialAd(activity) {
                                            onBack()
                                        }
                                    } else {
                                        onBack()
                                    }
                                }
                            }
                        },
                        enabled = !state.isSaving && !state.isDeleting && state.name.isNotBlank()
                    )
                }
                if (state.isSaving || state.isDeleting) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.6f)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }
                if (state.showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = viewModel::dismissDeleteDialog,
                        title = { Text(stringResource(R.string.confirm_delete_title)) },
                        text = { Text(stringResource(R.string.confirm_delete_message)) },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    viewModel.confirmDelete {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(context.getString(R.string.habit_deleted))
                                            onBack()
                                        }
                                    }
                                },
                                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                            ) {
                                Text(stringResource(R.string.confirm))
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = viewModel::dismissDeleteDialog) {
                                Text(stringResource(R.string.cancel))
                            }
                        },
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                        textContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
            is AddEditHabitUiState.Error -> {
                ErrorState(
                    title = stringResource(R.string.error_loading_habits_title),
                    message = state.message,
                    onRetryClick = viewModel::refreshData
                )
            }
            AddEditHabitUiState.Offline -> {
                OfflineState(onRetryClick = viewModel::refreshData)
            }
        }
    }
}