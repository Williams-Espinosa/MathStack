package com.williamsel.mathstack.features.private.streak.presentacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.outlined.CalendarMonth
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
import com.williamsel.mathstack.features.private.streak.domain.entities.ActivityDay
import com.williamsel.mathstack.features.private.streak.domain.entities.StreakDay
import com.williamsel.mathstack.features.private.streak.presentacion.viewmodels.StreakViewModel
import com.williamsel.mathstack.ui.theme.*
import java.time.LocalDate

@Composable
fun StreakScreen(
    onBack: () -> Unit,
    viewModel: StreakViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    StreakContent(uiState = uiState, onBack = onBack)
}

@Composable
private fun StreakContent(
    uiState: StreakUiState,
    onBack: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoPantalla)
    ) {
        val anchoTotal = maxWidth
        val altoTotal  = maxHeight

        val hPad        = (anchoTotal * 0.06f).coerceIn(16.dp, 40.dp)
        val espacioN    = (altoTotal  * 0.025f).coerceIn(12.dp, 28.dp)
        val espacioC    = (altoTotal  * 0.013f).coerceIn(6.dp,  16.dp)
        val tituloSp    = (anchoTotal.value * 0.05f).coerceIn(16f, 22f)
        val diasSp      = (anchoTotal.value * 0.10f).coerceIn(28f, 44f)
        val subtituloSp = (anchoTotal.value * 0.036f).coerceIn(13f, 16f)
        val seccionSp   = (anchoTotal.value * 0.044f).coerceIn(15f, 19f)
        val cuerpoSp    = (anchoTotal.value * 0.032f).coerceIn(11f, 14f)
        val circuloSize = (anchoTotal * 0.30f).coerceIn(96.dp, 160.dp)

        Scaffold(containerColor = FondoPantalla) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
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
                        "Tu Racha",
                        modifier   = Modifier.align(Alignment.Center),
                        fontSize   = tituloSp.sp,
                        fontWeight = FontWeight.Bold,
                        color      = TextoPrincipal
                    )
                }

                when {
                    uiState.isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = AzulPrimario)
                        }
                    }

                    uiState.errorMessage != null -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                uiState.errorMessage,
                                color    = MaterialTheme.colorScheme.error,
                                fontSize = cuerpoSp.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    else -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = espacioN)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(circuloSize)
                                        .clip(CircleShape)
                                        .background(
                                            Brush.radialGradient(
                                                colors = listOf(NaranjaTrofeo, RojoReto)
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Filled.LocalFireDepartment,
                                        contentDescription = null,
                                        tint     = Color.White,
                                        modifier = Modifier.size(circuloSize * 0.5f)
                                    )
                                }

                                Surface(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .offset(
                                            x = -(anchoTotal * 0.5f - circuloSize / 2 - (circuloSize * 0.12f)),
                                            y = (circuloSize * 0.06f)
                                        )
                                        .size((circuloSize * 0.32f).coerceIn(36.dp, 52.dp)),
                                    shape = CircleShape,
                                    color = AmarilloTrofeo,
                                    shadowElevation = 4.dp
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            "${uiState.currentStreak}",
                                            fontSize   = (subtituloSp + 2f).sp,
                                            fontWeight = FontWeight.Bold,
                                            color      = Color.White
                                        )
                                    }
                                }
                            }

                            Spacer(Modifier.height(espacioN))

                            Text(
                                "${uiState.currentStreak} días",
                                modifier  = Modifier.fillMaxWidth(),
                                fontSize  = diasSp.sp,
                                fontWeight = FontWeight.Bold,
                                color     = TextoPrincipal,
                                textAlign = TextAlign.Center
                            )

                            Spacer(Modifier.height(6.dp))

                            Text(
                                "¡Increíble! Llevas una semana completa aprendiendo sin parar",
                                modifier  = Modifier.fillMaxWidth(),
                                fontSize  = subtituloSp.sp,
                                color     = TextoSecundario,
                                textAlign = TextAlign.Center,
                                lineHeight = (subtituloSp * 1.4f).sp
                            )

                            Spacer(Modifier.height(espacioN))

                            Card(
                                modifier  = Modifier.fillMaxWidth(),
                                shape     = RoundedCornerShape(20.dp),
                                colors    = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(1.dp)
                            ) {
                                Column(modifier = Modifier.padding(18.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                Icons.Outlined.CalendarMonth,
                                                contentDescription = null,
                                                tint     = AzulPrimario,
                                                modifier = Modifier.size((seccionSp + 2f).dp)
                                            )
                                            Spacer(Modifier.width(8.dp))
                                            Text(
                                                "Esta semana",
                                                fontSize   = seccionSp.sp,
                                                fontWeight = FontWeight.Bold,
                                                color      = TextoPrincipal
                                            )
                                        }
                                        Text(
                                            "${uiState.completedThisWeek}/${uiState.totalThisWeek} días",
                                            fontSize   = cuerpoSp.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color      = VerdePrimario
                                        )
                                    }

                                    Spacer(Modifier.height(espacioN))

                                    WeekRow(weekDays = uiState.weekDays, cuerpoSp = cuerpoSp)
                                }
                            }

                            Spacer(Modifier.height(espacioC * 1.5f))

                            Card(
                                modifier  = Modifier.fillMaxWidth(),
                                shape     = RoundedCornerShape(20.dp),
                                colors    = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(1.dp)
                            ) {
                                Column(modifier = Modifier.padding(18.dp)) {
                                    Text(
                                        "Historial de actividad",
                                        fontSize   = seccionSp.sp,
                                        fontWeight = FontWeight.Bold,
                                        color      = TextoPrincipal
                                    )

                                    Spacer(Modifier.height(espacioN))

                                    ActivityHeatmap(history = uiState.activityHistory)

                                    Spacer(Modifier.height(espacioC))

                                    HeatmapLegend(cuerpoSp = cuerpoSp)
                                }
                            }

                            Spacer(Modifier.height(espacioN))
                        }
                    }
                }
            }
        }
    }
}
@Composable
private fun WeekRow(weekDays: List<StreakDay>, cuerpoSp: Float) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        weekDays.forEach { day ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(if (day.isCompleted) VerdePrimario else IndicadorInactivo),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.LocalFireDepartment,
                        contentDescription = null,
                        tint     = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    day.label,
                    fontSize   = cuerpoSp.sp,
                    color      = TextoSecundario,
                    fontWeight = if (day.isToday) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}
@Composable
private fun ActivityHeatmap(history: List<ActivityDay>) {
    LazyVerticalGrid(
        columns  = GridCells.Fixed(7),
        modifier = Modifier
            .fillMaxWidth()
            .height(((history.size / 7 + 1) * 22).dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement   = Arrangement.spacedBy(6.dp),
        userScrollEnabled = false
    ) {
        items(history, key = { it.date.toString() }) { day ->
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(colorForIntensity(day.intensity))
            )
        }
    }
}

private fun colorForIntensity(intensity: Int): Color = when (intensity) {
    0 -> Color(0xFFE5E7EB)
    1 -> VerdePrimario.copy(alpha = 0.25f)
    2 -> VerdePrimario.copy(alpha = 0.5f)
    3 -> VerdePrimario.copy(alpha = 0.75f)
    else -> VerdePrimario
}
@Composable
private fun HeatmapLegend(cuerpoSp: Float) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Menos", fontSize = (cuerpoSp - 1f).sp, color = TextoSecundario)
        Spacer(Modifier.width(6.dp))
        repeat(5) { level ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .size(12.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(colorForIntensity(level))
            )
        }
        Spacer(Modifier.width(6.dp))
        Text("Más", fontSize = (cuerpoSp - 1f).sp, color = TextoSecundario)
    }
}
