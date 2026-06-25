package com.williamsel.mathstack.features.private.groups.presentacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.ColorUtils
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.williamsel.mathstack.features.private.groups.domain.entities.Group
import com.williamsel.mathstack.features.private.groups.presentacion.viewmodels.GroupsViewModel
import com.williamsel.mathstack.ui.theme.*

@Composable
fun GroupsScreen(
    onNavigateToGroup: (String) -> Unit = {},
    onCreateGroup: () -> Unit = {},
    viewModel: GroupsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    GroupsContent(
        uiState          = uiState,
        onNavigateToGroup = onNavigateToGroup,
        onCreateGroup    = onCreateGroup,
        onLeaveGroup     = viewModel::onLeaveGroup,
        onErrorDismissed = viewModel::onErrorDismissed
    )
}

@Composable
private fun GroupsContent(
    uiState: GroupsUiState,
    onNavigateToGroup: (String) -> Unit,
    onCreateGroup: () -> Unit,
    onLeaveGroup: (String) -> Unit,
    onErrorDismissed: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            onErrorDismissed()
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoPantalla)
    ) {
        val anchoTotal = maxWidth
        val altoTotal  = maxHeight
        val hPad       = (anchoTotal * 0.05f).coerceIn(14.dp, 32.dp)
        val espacioN   = (altoTotal  * 0.022f).coerceIn(10.dp, 22.dp)
        val espacioC   = (altoTotal  * 0.012f).coerceIn(6.dp,  14.dp)
        val tituloSp   = (anchoTotal.value * 0.058f).coerceIn(18f, 24f)
        val grupoSp    = (anchoTotal.value * 0.052f).coerceIn(16f, 21f)
        val subSp      = (anchoTotal.value * 0.034f).coerceIn(12f, 15f)
        val statSp     = (anchoTotal.value * 0.050f).coerceIn(16f, 22f)
        val labelSp    = (anchoTotal.value * 0.032f).coerceIn(11f, 13f)
        val botonAlto  = (altoTotal  * 0.058f).coerceIn(40.dp, 52.dp)

        Scaffold(
            containerColor = FondoPantalla,
            snackbarHost   = { SnackbarHost(snackbarHostState) }
        ) { paddingValues ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = hPad),
                contentPadding       = PaddingValues(bottom = espacioN),
                verticalArrangement  = Arrangement.spacedBy(espacioC)
            ) {

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = espacioN),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment     = Alignment.CenterVertically
                    ) {
                        Text(
                            "Grupos de Estudio",
                            fontSize   = tituloSp.sp,
                            fontWeight = FontWeight.Bold,
                            color      = TextoPrincipal
                        )
                        IconButton(
                            onClick  = onCreateGroup,
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(AzulPrimario.copy(alpha = 0.12f))
                        ) {
                            Icon(
                                Icons.Outlined.Add,
                                contentDescription = "Crear grupo",
                                tint     = AzulPrimario,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                item {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = AzulPrimario
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.White.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Outlined.Group,
                                    contentDescription = null,
                                    tint     = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text(
                                    "Mis Grupos",
                                    fontSize   = (subSp + 1f).sp,
                                    fontWeight = FontWeight.Bold,
                                    color      = Color.White
                                )
                                Text(
                                    if (uiState.isLoading) "Cargando…"
                                    else "${uiState.myGroupsCount} grupos activos",
                                    fontSize = (subSp - 1f).sp,
                                    color    = Color.White.copy(alpha = 0.85f)
                                )
                            }
                        }
                    }
                }

                if (uiState.isLoading) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = AzulPrimario)
                        }
                    }
                }

                if (!uiState.isLoading) {
                    if (uiState.groups.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "Aún no perteneces a ningún grupo.\nPulsa + para crear uno.",
                                    color     = TextoSecundario,
                                    fontSize  = subSp.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    } else {
                        items(uiState.groups, key = { it.id }) { group ->
                            GroupCard(
                                group       = group,
                                isProcessing = uiState.processingGroupId == group.id,
                                grupoSp     = grupoSp,
                                subSp       = subSp,
                                statSp      = statSp,
                                labelSp     = labelSp,
                                botonAlto   = botonAlto,
                                onViewGroup = { onNavigateToGroup(group.id) },
                                onLeave     = { onLeaveGroup(group.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GroupCard(
    group: Group,
    isProcessing: Boolean,
    grupoSp: Float,
    subSp: Float,
    statSp: Float,
    labelSp: Float,
    botonAlto: androidx.compose.ui.unit.Dp,
    onViewGroup: () -> Unit,
    onLeave: () -> Unit
) {
    val groupColor = remember(group.colorHex) {
        runCatching { Color(android.graphics.Color.parseColor(group.colorHex)) }
            .getOrDefault(AzulPrimario)
    }

    val textOnColor = remember(groupColor) {
        val luminance = ColorUtils.calculateLuminance(groupColor.toArgb())
        if (luminance < 0.4f) Color.White else Color(0xFF1A1A2E)
    }

    Card(
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(20.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        groupColor,
                        RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            group.name,
                            fontSize   = grupoSp.sp,
                            fontWeight = FontWeight.Bold,
                            color      = textOnColor
                        )
                        Text(
                            "${group.subject} · ${group.level}",
                            fontSize = (subSp - 1f).sp,
                            color    = textOnColor.copy(alpha = 0.85f)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.25f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Outlined.Group,
                            contentDescription = null,
                            tint     = textOnColor,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Miembros",
                            fontSize = labelSp.sp,
                            color    = TextoSecundario
                        )
                        Text(
                            "${group.membersCount}",
                            fontSize   = statSp.sp,
                            fontWeight = FontWeight.Bold,
                            color      = TextoPrincipal
                        )
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Retos activos",
                            fontSize = labelSp.sp,
                            color    = TextoSecundario
                        )
                        Text(
                            "${group.activeChallenges}",
                            fontSize   = statSp.sp,
                            fontWeight = FontWeight.Bold,
                            color      = TextoPrincipal
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick  = onViewGroup,
                        modifier = Modifier.weight(1f).height(botonAlto),
                        shape    = RoundedCornerShape(12.dp),
                        colors   = ButtonDefaults.buttonColors(containerColor = groupColor)
                    ) {
                        if (isProcessing) {
                            CircularProgressIndicator(
                                color       = Color.White,
                                modifier    = Modifier.size(18.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                "Ver grupo",
                                color      = textOnColor,
                                fontWeight = FontWeight.SemiBold,
                                fontSize   = (labelSp + 1f).sp
                            )
                        }
                    }

                    Spacer(Modifier.width(10.dp))

                    Box(
                        modifier = Modifier
                            .size(botonAlto)
                            .clip(RoundedCornerShape(12.dp))
                            .background(FondoPantalla),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Outlined.Group,
                            contentDescription = "Miembros del grupo",
                            tint     = TextoSecundario,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }
}
