package com.williamsel.mathstack.features.private.profile.presentacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.williamsel.mathstack.features.private.profile.domain.entities.Achievement
import com.williamsel.mathstack.features.private.profile.presentacion.viewmodels.ProfileViewModel
import com.williamsel.mathstack.ui.theme.*

@Composable
fun ProfileScreen(
    onNavigateToSettings: () -> Unit = {},
    onNavigateToStreak: () -> Unit = {},
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileContent(
        uiState              = uiState,
        onNavigateToSettings = onNavigateToSettings,
        onNavigateToStreak   = onNavigateToStreak
    )
}

@Composable
private fun ProfileContent(
    uiState: ProfileUiState,
    onNavigateToSettings: () -> Unit,
    onNavigateToStreak: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoPantalla)
    ) {
        val anchoTotal = maxWidth
        val altoTotal  = maxHeight

        val hPad        = (anchoTotal * 0.05f).coerceIn(14.dp, 36.dp)
        val espacioN    = (altoTotal  * 0.022f).coerceIn(10.dp, 24.dp)
        val espacioC    = (altoTotal  * 0.012f).coerceIn(6.dp,  14.dp)
        val nombreSp    = (anchoTotal.value * 0.062f).coerceIn(20f, 28f)
        val emailSp     = (anchoTotal.value * 0.034f).coerceIn(12f, 15f)
        val seccionSp   = (anchoTotal.value * 0.048f).coerceIn(16f, 20f)
        val statSp      = (anchoTotal.value * 0.056f).coerceIn(18f, 24f)
        val labelSp     = (anchoTotal.value * 0.032f).coerceIn(11f, 14f)
        val cuerpoSp    = (anchoTotal.value * 0.036f).coerceIn(12f, 16f)
        val avatarSize  = (anchoTotal * 0.24f).coerceIn(80.dp, 120.dp)
        val cornerH     = (anchoTotal * 0.1f).coerceIn(28.dp, 48.dp)
        val headerH     = if (altoTotal < 680.dp) 0.30f else 0.34f

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(headerH)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                AzulPrimario,
                                AzulPrimario.copy(alpha = 0.85f)
                            )
                        ),
                        RoundedCornerShape(bottomStart = cornerH, bottomEnd = cornerH)
                    )
                    .padding(horizontal = hPad)
                    .padding(top = espacioC, bottom = espacioN * 2)
            ) {
                IconButton(
                    onClick  = onNavigateToSettings,
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        Icons.Outlined.Settings,
                        contentDescription = "Configuración",
                        tint = Color.White
                    )
                }

                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(avatarSize)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.25f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text     = if (uiState.isLoading) "🧑‍🎓" else uiState.avatarEmoji,
                            fontSize = (avatarSize.value * 0.45f).sp
                        )
                    }

                    Spacer(Modifier.height(espacioC))

                    Text(
                        uiState.username.ifEmpty { "—" },
                        fontSize   = nombreSp.sp,
                        fontWeight = FontWeight.Bold,
                        color      = Color.White
                    )
                    Text(
                        uiState.email.ifEmpty { "—" },
                        fontSize = emailSp.sp,
                        color    = Color.White.copy(alpha = 0.85f)
                    )

                    Spacer(Modifier.height(espacioC))

                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color.White.copy(alpha = 0.2f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("🏆", fontSize = (emailSp + 2f).sp)
                            Spacer(Modifier.width(6.dp))
                            Text(
                                "Nivel ${uiState.level}",
                                fontSize   = (emailSp + 1f).sp,
                                fontWeight = FontWeight.SemiBold,
                                color      = Color.White
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(espacioN))

            Card(
                modifier  = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = hPad),
                shape     = RoundedCornerShape(20.dp),
                colors    = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    if (uiState.isLoading) {
                        Box(
                            Modifier.fillMaxWidth().height(80.dp),
                            contentAlignment = Alignment.Center
                        ) { CircularProgressIndicator(color = AzulPrimario) }
                    } else {
                        Row(
                            modifier              = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            StatItem(emoji = "⚡", value = "${uiState.totalXp}", label = "XP Total", statSp = statSp, labelSp = labelSp, color = AmarilloTrofeo)
                            VerticalDivider(modifier = Modifier.height(56.dp))
                            StatItem(emoji = "🪙", value = "${uiState.coins}", label = "Monedas", statSp = statSp, labelSp = labelSp, color = AmarilloChip)
                            VerticalDivider(modifier = Modifier.height(56.dp))
                            StatItem(emoji = "🔥", value = "${uiState.streak}", label = "Racha", statSp = statSp, labelSp = labelSp, color = RojoReto)
                        }

                        Spacer(Modifier.height(espacioN))
                        HorizontalDivider(color = IndicadorInactivo.copy(alpha = 0.4f))
                        Spacer(Modifier.height(espacioN))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Progreso al nivel ${uiState.nextLevel}",
                                fontSize = cuerpoSp.sp,
                                color    = TextoPrincipal,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                "${(uiState.levelProgress * 100).toInt()}%",
                                fontSize   = cuerpoSp.sp,
                                fontWeight = FontWeight.Bold,
                                color      = AzulPrimario
                            )
                        }

                        Spacer(Modifier.height(8.dp))

                        LinearProgressIndicator(
                            progress        = { uiState.levelProgress },
                            modifier        = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                            color           = AzulPrimario,
                            trackColor      = IndicadorInactivo.copy(alpha = 0.4f)
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            "${uiState.xpRemaining} XP restantes",
                            fontSize = labelSp.sp,
                            color    = TextoSecundario
                        )
                    }
                }
            }

            Spacer(Modifier.height(espacioN))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = hPad),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Estadísticas",
                    fontSize   = seccionSp.sp,
                    fontWeight = FontWeight.Bold,
                    color      = TextoPrincipal
                )
                TextButton(onClick = onNavigateToStreak) {
                    Text(
                        "Ver racha",
                        color    = AzulPrimario,
                        fontSize = cuerpoSp.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(Modifier.height(espacioC))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = hPad),
                horizontalArrangement = Arrangement.spacedBy(espacioC)
            ) {
                StatCard(
                    emoji    = "📖",
                    value    = "${uiState.lessonsCompleted}",
                    label    = "Lecciones\ncompletadas",
                    cuerpoSp = cuerpoSp,
                    statSp   = statSp,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    emoji    = "🏅",
                    value    = "${uiState.achievementsUnlocked}",
                    label    = "Logros\ndesbloqueados",
                    cuerpoSp = cuerpoSp,
                    statSp   = statSp,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(espacioN))

            Text(
                "Logros",
                modifier   = Modifier.padding(horizontal = hPad),
                fontSize   = seccionSp.sp,
                fontWeight = FontWeight.Bold,
                color      = TextoPrincipal
            )

            Spacer(Modifier.height(espacioC))

            if (uiState.achievements.isEmpty() && !uiState.isLoading) {
                Text(
                    "No hay logros disponibles",
                    modifier  = Modifier.padding(horizontal = hPad),
                    color     = TextoSecundario,
                    fontSize  = cuerpoSp.sp
                )
            } else {
                val chunks = uiState.achievements.chunked(3)
                chunks.forEach { row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = hPad),
                        horizontalArrangement = Arrangement.spacedBy(espacioC)
                    ) {
                        row.forEach { achievement ->
                            AchievementCard(
                                achievement = achievement,
                                labelSp     = labelSp,
                                modifier    = Modifier.weight(1f)
                            )
                        }
                        repeat(3 - row.size) {
                            Spacer(Modifier.weight(1f))
                        }
                    }
                    Spacer(Modifier.height(espacioC))
                }
            }

            Spacer(Modifier.height(espacioN))
        }
    }
}


@Composable
private fun StatItem(
    emoji: String,
    value: String,
    label: String,
    statSp: Float,
    labelSp: Float,
    color: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Text(emoji, fontSize = (statSp * 0.7f).sp)
        }
        Spacer(Modifier.height(4.dp))
        Text(
            value,
            fontSize   = statSp.sp,
            fontWeight = FontWeight.Bold,
            color      = TextoPrincipal
        )
        Text(
            label,
            fontSize = labelSp.sp,
            color    = TextoSecundario
        )
    }
}

@Composable
private fun StatCard(
    emoji: String,
    value: String,
    label: String,
    cuerpoSp: Float,
    statSp: Float,
    modifier: Modifier = Modifier
) {
    Card(
        modifier  = modifier,
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(emoji, fontSize = (statSp * 0.8f).sp)
            Spacer(Modifier.height(6.dp))
            Text(
                value,
                fontSize   = statSp.sp,
                fontWeight = FontWeight.Bold,
                color      = TextoPrincipal
            )
            Text(
                label,
                fontSize   = (cuerpoSp - 1f).sp,
                color      = TextoSecundario,
                lineHeight = (cuerpoSp * 1.4f).sp
            )
        }
    }
}

@Composable
private fun AchievementCard(
    achievement: Achievement,
    labelSp: Float,
    modifier: Modifier = Modifier
) {
    Card(
        modifier  = modifier.aspectRatio(0.9f),
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(
            containerColor = if (achievement.isUnlocked) Color.White else Color(0xFFF3F4F6)
        ),
        elevation = CardDefaults.cardElevation(if (achievement.isUnlocked) 1.dp else 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text     = achievement.emoji,
                fontSize = (labelSp * 2.6f).sp,
                color    = if (achievement.isUnlocked) Color.Unspecified else Color(0xFFD1D5DB)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text      = achievement.title,
                fontSize  = (labelSp - 1f).sp,
                color     = if (achievement.isUnlocked) TextoPrincipal else TextoSecundario,
                textAlign = TextAlign.Center,
                lineHeight = (labelSp * 1.3f).sp,
                fontWeight = if (achievement.isUnlocked) FontWeight.Medium else FontWeight.Normal
            )
        }
    }
}
