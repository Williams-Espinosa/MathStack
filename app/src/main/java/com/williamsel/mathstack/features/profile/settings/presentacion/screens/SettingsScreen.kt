package com.williamsel.mathstack.features.private.settings.presentacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.automirrored.outlined.Help
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.williamsel.mathstack.features.private.settings.presentacion.viewmodels.SettingsViewModel
import com.williamsel.mathstack.ui.theme.*

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onNavigateToAccountManagement: () -> Unit = {},
    onNavigateToPrivacy: () -> Unit = {},
    onNavigateToTerms: () -> Unit = {},
    onNavigateToAbout: () -> Unit = {},
    onNavigateToHelp: () -> Unit = {},
    onLogoutSuccess: () -> Unit = {},
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.logoutSuccess) {
        if (uiState.logoutSuccess) onLogoutSuccess()
    }

    SettingsContent(
        uiState                        = uiState,
        onBack                         = onBack,
        onNotificationsToggle          = viewModel::onNotificationsToggle,
        onDarkModeToggle               = viewModel::onDarkModeToggle,
        onPracticeRemindersToggle      = viewModel::onPracticeRemindersToggle,
        onNavigateToAccountManagement  = onNavigateToAccountManagement,
        onNavigateToPrivacy            = onNavigateToPrivacy,
        onNavigateToTerms              = onNavigateToTerms,
        onNavigateToAbout              = onNavigateToAbout,
        onNavigateToHelp               = onNavigateToHelp,
        onLogout                       = viewModel::onLogout,
        onErrorDismissed               = viewModel::onErrorDismissed
    )
}

@Composable
private fun SettingsContent(
    uiState: SettingsUiState,
    onBack: () -> Unit,
    onNotificationsToggle: (Boolean) -> Unit,
    onDarkModeToggle: (Boolean) -> Unit,
    onPracticeRemindersToggle: (Boolean) -> Unit,
    onNavigateToAccountManagement: () -> Unit,
    onNavigateToPrivacy: () -> Unit,
    onNavigateToTerms: () -> Unit,
    onNavigateToAbout: () -> Unit,
    onNavigateToHelp: () -> Unit,
    onLogout: () -> Unit,
    onErrorDismissed: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var showLogoutDialog  by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            onErrorDismissed()
        }
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title   = { Text("Cerrar sesión", fontWeight = FontWeight.Bold) },
            text    = { Text("¿Estás seguro de que deseas cerrar sesión?") },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    onLogout()
                }) {
                    Text("Cerrar sesión", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoPantalla)
    ) {
        val anchoTotal = maxWidth
        val altoTotal  = maxHeight

        val hPad       = (anchoTotal * 0.05f).coerceIn(14.dp, 36.dp)
        val espacioN   = (altoTotal  * 0.022f).coerceIn(10.dp, 24.dp)
        val espacioC   = (altoTotal  * 0.012f).coerceIn(6.dp,  14.dp)
        val tituloSp   = (anchoTotal.value * 0.052f).coerceIn(17f, 22f)
        val seccionSp  = (anchoTotal.value * 0.034f).coerceIn(12f, 14f)
        val itemSp     = (anchoTotal.value * 0.040f).coerceIn(13f, 16f)
        val botonAlto  = (altoTotal  * 0.068f).coerceIn(46.dp, 58.dp)

        Scaffold(
            containerColor = FondoPantalla,
            snackbarHost   = { SnackbarHost(snackbarHostState) }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = hPad)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = espacioC)
                ) {
                    IconButton(
                        onClick  = onBack,
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Volver",
                            tint = TextoPrincipal
                        )
                    }
                    Text(
                        "Configuración",
                        modifier   = Modifier.align(Alignment.Center),
                        fontSize   = tituloSp.sp,
                        fontWeight = FontWeight.Bold,
                        color      = TextoPrincipal
                    )
                }

                if (uiState.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = AzulPrimario)
                    }
                } else {

                    SectionLabel(text = "Preferencias", fontSize = seccionSp)
                    Spacer(Modifier.height(espacioC))

                    Card(
                        modifier  = Modifier.fillMaxWidth(),
                        shape     = RoundedCornerShape(16.dp),
                        colors    = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(1.dp)
                    ) {
                        Column {
                            ToggleItem(
                                icon     = Icons.Outlined.Notifications,
                                label    = "Notificaciones",
                                checked  = uiState.notificationsEnabled,
                                onToggle = onNotificationsToggle,
                                fontSize = itemSp
                            )
                            SettingsDivider()
                            ToggleItem(
                                icon     = Icons.Outlined.DarkMode,
                                label    = "Modo oscuro",
                                checked  = uiState.darkModeEnabled,
                                onToggle = onDarkModeToggle,
                                fontSize = itemSp
                            )
                            SettingsDivider()
                            ToggleItem(
                                icon     = Icons.Outlined.AccessTime,
                                label    = "Recordatorios de práctica",
                                checked  = uiState.practiceRemindersEnabled,
                                onToggle = onPracticeRemindersToggle,
                                fontSize = itemSp
                            )
                        }
                    }

                    Spacer(Modifier.height(espacioN))

                    SectionLabel(text = "Cuenta", fontSize = seccionSp)
                    Spacer(Modifier.height(espacioC))

                    Card(
                        modifier  = Modifier.fillMaxWidth(),
                        shape     = RoundedCornerShape(16.dp),
                        colors    = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(1.dp)
                    ) {
                        Column {
                            NavigationItem(
                                icon    = Icons.Outlined.PersonOutline,
                                label   = "Gestión de cuenta",
                                onClick = onNavigateToAccountManagement,
                                fontSize = itemSp
                            )
                            SettingsDivider()
                            NavigationItem(
                                icon    = Icons.Outlined.Lock,
                                label   = "Privacidad",
                                onClick = onNavigateToPrivacy,
                                fontSize = itemSp
                            )
                            SettingsDivider()
                            NavigationItem(
                                icon    = Icons.Outlined.Description,
                                label   = "Términos y condiciones",
                                onClick = onNavigateToTerms,
                                fontSize = itemSp
                            )
                        }
                    }

                    Spacer(Modifier.height(espacioN))

                    SectionLabel(text = "Soporte", fontSize = seccionSp)
                    Spacer(Modifier.height(espacioC))

                    Card(
                        modifier  = Modifier.fillMaxWidth(),
                        shape     = RoundedCornerShape(16.dp),
                        colors    = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(1.dp)
                    ) {
                        Column {
                            NavigationItem(
                                icon    = Icons.Outlined.Info,
                                label   = "Acerca de",
                                onClick = onNavigateToAbout,
                                fontSize = itemSp
                            )
                            SettingsDivider()
                            NavigationItem(
                                icon    = Icons.AutoMirrored.Outlined.Help,
                                label   = "Ayuda",
                                onClick = onNavigateToHelp,
                                fontSize = itemSp
                            )
                        }
                    }

                    Spacer(Modifier.height(espacioN))

                    Button(
                        onClick  = { showLogoutDialog = true },
                        enabled  = !uiState.isLoggingOut,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(botonAlto),
                        shape  = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFECEC)
                        )
                    ) {
                        if (uiState.isLoggingOut) {
                            CircularProgressIndicator(
                                color       = Color(0xFFE53935),
                                modifier    = Modifier.size(22.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                Icons.AutoMirrored.Outlined.ExitToApp,
                                contentDescription = null,
                                tint     = Color(0xFFE53935),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(10.dp))
                            Text(
                                "Cerrar sesión",
                                color      = Color(0xFFE53935),
                                fontWeight = FontWeight.SemiBold,
                                fontSize   = itemSp.sp
                            )
                        }
                    }

                    Spacer(Modifier.height(espacioN))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "MathStack v1.0.0",
                            fontSize = (seccionSp - 1f).sp,
                            color    = TextoSecundario,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            "© 2026 MathStack. Todos los derechos reservados.",
                            fontSize  = (seccionSp - 1f).sp,
                            color     = TextoSecundario,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(Modifier.height(espacioN))
                }
            }
        }
    }
}

@Composable
private fun SectionLabel(text: String, fontSize: Float) {
    Text(
        text       = text,
        fontSize   = fontSize.sp,
        fontWeight = FontWeight.SemiBold,
        color      = TextoSecundario
    )
}

@Composable
private fun SettingsDivider() {
    HorizontalDivider(
        modifier  = Modifier.padding(start = 56.dp),
        thickness = 0.8.dp,
        color     = IndicadorInactivo.copy(alpha = 0.5f)
    )
}

@Composable
private fun ToggleItem(
    icon: ImageVector,
    label: String,
    checked: Boolean,
    onToggle: (Boolean) -> Unit,
    fontSize: Float
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconBox(icon = icon)
        Spacer(Modifier.width(12.dp))
        Text(
            text     = label,
            fontSize = fontSize.sp,
            color    = TextoPrincipal,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked         = checked,
            onCheckedChange = onToggle,
            colors          = SwitchDefaults.colors(
                checkedThumbColor  = Color.White,
                checkedTrackColor  = AzulPrimario
            )
        )
    }
}

@Composable
private fun NavigationItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    fontSize: Float
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconBox(icon = icon)
        Spacer(Modifier.width(12.dp))
        Text(
            text     = label,
            fontSize = fontSize.sp,
            color    = TextoPrincipal,
            modifier = Modifier.weight(1f)
        )
        Icon(
            Icons.Outlined.ChevronRight,
            contentDescription = null,
            tint     = TextoSecundario,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun IconBox(icon: ImageVector) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(AzulPrimario.copy(alpha = 0.12f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint     = AzulPrimario,
            modifier = Modifier.size(18.dp)
        )
    }
}
