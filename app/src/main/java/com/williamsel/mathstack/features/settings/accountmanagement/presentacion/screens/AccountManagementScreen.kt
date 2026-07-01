package com.williamsel.mathstack.features.settings.accountmanagement.presentacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.williamsel.mathstack.features.settings.accountmanagement.presentacion.viewmodels.AccountManagementViewModel
import com.williamsel.mathstack.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountManagementScreen(
    onBack: () -> Unit,
    onChangePassword: () -> Unit = {},
    onAccountDeleted: () -> Unit = {},
    viewModel: AccountManagementViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.successMessage) {
        uiState.successMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.onSuccessDismissed()
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.onErrorDismissed()
        }
    }

    LaunchedEffect(uiState.accountDeleted) {
        if (uiState.accountDeleted) onAccountDeleted()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Gestión de Cuenta", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = FondoPantalla
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = FondoPantalla
    ) { padding ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = AzulPrimario)
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            ManagementCard(title = "Información Personal") {
                FieldLabel(icon = Icons.Outlined.Person, text = "Nombre de usuario")
                OutlinedTextField(
                    value = uiState.username,
                    onValueChange = viewModel::onUsernameChange,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = managementTextFieldColors(),
                    singleLine = true
                )

                Spacer(Modifier.height(12.dp))

                FieldLabel(icon = Icons.Outlined.Email, text = "Correo electrónico")
                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = managementTextFieldColors(),
                    readOnly = true,
                    singleLine = true
                )

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = viewModel::onSaveChanges,
                    enabled = !uiState.isSaving && uiState.username.isNotBlank(),
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = AzulPrimario)
                ) {
                    if (uiState.isSaving) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                    } else {
                        Text("Guardar cambios", fontWeight = FontWeight.Bold)
                    }
                }
            }

            ManagementCard(title = "Seguridad") {
                OutlinedButton(
                    onClick = onChangePassword,
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = FondoPantalla.copy(alpha = 0.5f)),
                    border = ButtonDefaults.outlinedButtonBorder.copy(width = 0.5.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.Lock, contentDescription = null, tint = AzulPrimario)
                        Spacer(Modifier.width(12.dp))
                        Text("Cambiar contraseña", color = TextoPrincipal)
                    }
                }
            }

            ManagementCard(
                title = "Zona de Peligro",
                titleColor = Color(0xFFE53935)
            ) {
                Text(
                    "Una vez que elimines tu cuenta, no hay vuelta atrás. Por favor, asegúrate.",
                    fontSize = 14.sp,
                    color = TextoSecundario,
                    lineHeight = 20.sp
                )
                
                Spacer(Modifier.height(16.dp))

                var showDeleteDialog by remember { mutableStateOf(false) }

                if (showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog = false },
                        title = { Text("¿Eliminar cuenta?", fontWeight = FontWeight.Bold) },
                        text = { Text("Esta acción es permanente y borrará todo tu progreso, monedas y logros. ¿Estás seguro?") },
                        confirmButton = {
                            TextButton(onClick = {
                                showDeleteDialog = false
                                viewModel.onDeleteAccount()
                            }) {
                                Text("Eliminar definitivamente", color = MaterialTheme.colorScheme.error)
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDeleteDialog = false }) {
                                Text("Cancelar")
                            }
                        }
                    )
                }

                Button(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFECEC))
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Delete, contentDescription = null, tint = Color(0xFFE53935))
                        Spacer(Modifier.width(8.dp))
                        Text("Eliminar cuenta", color = Color(0xFFE53935), fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
private fun ManagementCard(
    title: String,
    titleColor: Color = TextoPrincipal,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = titleColor
            )
            Spacer(Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
private fun FieldLabel(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp), tint = TextoSecundario)
        Spacer(Modifier.width(8.dp))
        Text(text, fontSize = 14.sp, color = TextoSecundario)
    }
}

@Composable
private fun managementTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = AzulPrimario.copy(alpha = 0.5f),
    unfocusedBorderColor = Color.Transparent,
    containerColor = FondoPantalla.copy(alpha = 0.5f),
    focusedTextColor = TextoPrincipal,
    unfocusedTextColor = TextoPrincipal
)
