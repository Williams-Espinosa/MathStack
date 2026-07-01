package com.williamsel.mathstack.features.profile.profile.presentacion.screens

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.williamsel.mathstack.features.profile.profile.domain.entities.Achievement
import com.williamsel.mathstack.features.profile.profile.presentacion.viewmodels.ProfileViewModel
import com.williamsel.mathstack.ui.theme.AmarilloChip
import com.williamsel.mathstack.ui.theme.AmarilloTrofeo
import com.williamsel.mathstack.ui.theme.AzulPrimario
import com.williamsel.mathstack.ui.theme.FondoPantalla
import com.williamsel.mathstack.ui.theme.IndicadorInactivo
import com.williamsel.mathstack.ui.theme.RojoReto
import com.williamsel.mathstack.ui.theme.TextoPrincipal
import com.williamsel.mathstack.ui.theme.TextoSecundario

private val HPad       = 20.dp
private val EspacioN    = 18.dp
private val EspacioC    = 10.dp
private val NombreSp    = 24f
private val EmailSp     = 13f
private val SeccionSp   = 18f
private val StatSp      = 20f
private val LabelSp     = 12f
private val CuerpoSp    = 14f
private val AvatarSize  = 100.dp
private val CornerH     = 36.dp
private const val HeaderHeightFraction = 0.32f

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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoPantalla)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(HeaderHeightFraction)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                AzulPrimario,
                                AzulPrimario.copy(alpha = 0.85f)
                            )
                        ),
                        RoundedCornerShape(bottomStart = CornerH, bottomEnd = CornerH)
                    )
                    .padding(horizontal = HPad)
                    .padding(top = EspacioC, bottom = EspacioN * 2)
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
                            .size(AvatarSize)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.25f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text     = if (uiState.isLoading) "🧑‍🎓" else uiState.avatarEmoji,
                            fontSize = (AvatarSize.value * 0.45f).sp
                        )
                    }

                    Spacer(Modifier.height(EspacioC))

                    Text(
                        uiState.username.ifEmpty { "—" },
                        fontSize   = NombreSp.sp,
                        fontWeight = FontWeight.Bold,
                        color      = Color.White
                    )
                    Text(
                        uiState.email.ifEmpty { "—" },
                        fontSize = EmailSp.sp,
                        color    = Color.White.copy(alpha = 0.85f)
                    )

                    Spacer(Modifier.height(EspacioC))

                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color.White.copy(alpha = 0.2f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("🏆", fontSize = (EmailSp + 2f).sp)
                            Spacer(Modifier.width(6.dp))
                            Text(
                                "Nivel ${uiState.level}",
                                fontSize   = (EmailSp + 1f).sp,
                                fontWeight = FontWeight.SemiBold,
                                color      = Color.White
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(EspacioN))

            Card(
                modifier  = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = HPad),
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
                            StatItem(emoji = "⚡", value = "${uiState.totalXp}", label = "XP Total", statSp = StatSp, labelSp = LabelSp, color = AmarilloTrofeo)
                            VerticalDivider(modifier = Modifier.height(56.dp))
                            StatItem(emoji = "🪙", value = "${uiState.coins}", label = "Monedas", statSp = StatSp, labelSp = LabelSp, color = AmarilloChip)
                            VerticalDivider(modifier = Modifier.height(56.dp))
                            StatItem(emoji = "🔥", value = "${uiState.streak}", label = "Racha", statSp = StatSp, labelSp = LabelSp, color = RojoReto)
                        }

                        Spacer(Modifier.height(EspacioN))
                        HorizontalDivider(color = IndicadorInactivo.copy(alpha = 0.4f))
                        Spacer(Modifier.height(EspacioN))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Progreso al nivel ${uiState.nextLevel}",
                                fontSize = CuerpoSp.sp,
                                color    = TextoPrincipal,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                "${(uiState.levelProgress * 100).toInt()}%",
                                fontSize   = CuerpoSp.sp,
                                fontWeight = FontWeight.Bold,
                                color      = AzulPrimario
                            )
                        }

                        Spacer(Modifier.height(8.dp))

                        LinearProgressIndicator(
                            progress   = { uiState.levelProgress },
                            modifier   = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                            color      = AzulPrimario,
                            trackColor = IndicadorInactivo.copy(alpha = 0.4f)
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            "${uiState.xpRemaining} XP restantes",
                            fontSize = LabelSp.sp,
                            color    = TextoSecundario
                        )
                    }
                }
            }

            Spacer(Modifier.height(EspacioN))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = HPad),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Estadísticas",
                    fontSize   = SeccionSp.sp,
                    fontWeight = FontWeight.Bold,
                    color      = TextoPrincipal
                )
                TextButton(onClick = onNavigateToStreak) {
                    Text(
                        "Ver racha",
                        color    = AzulPrimario,
                        fontSize = CuerpoSp.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(Modifier.height(EspacioC))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = HPad),
                horizontalArrangement = Arrangement.spacedBy(EspacioC)
            ) {
                StatCard(
                    emoji    = "📖",
                    value    = "${uiState.lessonsCompleted}",
                    label    = "Lecciones\ncompletadas",
                    cuerpoSp = CuerpoSp,
                    statSp   = StatSp,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    emoji    = "🏅",
                    value    = "${uiState.achievementsUnlocked}",
                    label    = "Logros\ndesbloqueados",
                    cuerpoSp = CuerpoSp,
                    statSp   = StatSp,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(EspacioN))

            Text(
                "Logros",
                modifier   = Modifier.padding(horizontal = HPad),
                fontSize   = SeccionSp.sp,
                fontWeight = FontWeight.Bold,
                color      = TextoPrincipal
            )

            Spacer(Modifier.height(EspacioC))

            if (uiState.achievements.isEmpty() && !uiState.isLoading) {
                Text(
                    "No hay logros disponibles",
                    modifier  = Modifier.padding(horizontal = HPad),
                    color     = TextoSecundario,
                    fontSize  = CuerpoSp.sp
                )
            } else {
                val chunks = uiState.achievements.chunked(3)
                chunks.forEach { row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = HPad),
                        horizontalArrangement = Arrangement.spacedBy(EspacioC)
                    ) {
                        row.forEach { achievement ->
                            AchievementCard(
                                achievement = achievement,
                                labelSp     = LabelSp,
                                modifier    = Modifier.weight(1f)
                            )
                        }
                        repeat(3 - row.size) {
                            Spacer(Modifier.weight(1f))
                        }
                    }
                    Spacer(Modifier.height(EspacioC))
                }
            }

            Spacer(Modifier.height(EspacioN))
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
                text       = achievement.title,
                fontSize   = (labelSp - 1f).sp,
                color      = if (achievement.isUnlocked) TextoPrincipal else TextoSecundario,
                textAlign  = TextAlign.Center,
                lineHeight = (labelSp * 1.3f).sp,
                fontWeight = if (achievement.isUnlocked) FontWeight.Medium else FontWeight.Normal
            )
        }
    }
}