package com.williamsel.mathstack.features.profile.streak.presentacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.williamsel.mathstack.features.profile.streak.domain.entities.ActivityDay
import com.williamsel.mathstack.features.profile.streak.domain.entities.StreakDay
import com.williamsel.mathstack.features.profile.streak.presentacion.viewmodels.StreakViewModel
import com.williamsel.mathstack.ui.theme.AmarilloTrofeo
import com.williamsel.mathstack.ui.theme.AzulPrimario
import com.williamsel.mathstack.ui.theme.FondoPantalla
import com.williamsel.mathstack.ui.theme.IndicadorInactivo
import com.williamsel.mathstack.ui.theme.NaranjaTrofeo
import com.williamsel.mathstack.ui.theme.RojoReto
import com.williamsel.mathstack.ui.theme.TextoPrincipal
import com.williamsel.mathstack.ui.theme.TextoSecundario
import com.williamsel.mathstack.ui.theme.VerdePrimario
private val HPad        = 20.dp
private val EspacioN     = 20.dp
private val EspacioC     = 10.dp
private val TituloSp     = 20f
private val DiasSp       = 36f
private val SubtituloSp  = 14f
private val SeccionSp    = 17f
private val CuerpoSp     = 13f
private val CirculoSize  = 130.dp

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
    Scaffold(containerColor = FondoPantalla) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(FondoPantalla)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = HPad)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = EspacioC)
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
                    fontSize   = TituloSp.sp,
                    fontWeight = FontWeight.Bold,
                    color      = TextoPrincipal
                )
            }

            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(320.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = AzulPrimario)
                    }
                }

                uiState.errorMessage != null -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(320.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            uiState.errorMessage,
                            color     = MaterialTheme.colorScheme.error,
                            fontSize  = CuerpoSp.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = EspacioN)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(modifier = Modifier.size(CirculoSize)) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
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
                                        modifier = Modifier.size(CirculoSize * 0.5f)
                                    )
                                }

                                Surface(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .offset(x = (CirculoSize * 0.05f), y = (CirculoSize * 0.05f))
                                        .size(44.dp),
                                    shape = CircleShape,
                                    color = AmarilloTrofeo,
                                    shadowElevation = 4.dp
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            "${uiState.currentStreak}",
                                            fontSize   = (SubtituloSp + 2f).sp,
                                            fontWeight = FontWeight.Bold,
                                            color      = Color.White
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(EspacioN))

                        Text(
                            "${uiState.currentStreak} días",
                            modifier   = Modifier.fillMaxWidth(),
                            fontSize   = DiasSp.sp,
                            fontWeight = FontWeight.Bold,
                            color      = TextoPrincipal,
                            textAlign  = TextAlign.Center
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            if (uiState.currentStreak >= 7)
                                "¡Increíble! Llevas una semana completa aprendiendo sin parar"
                            else
                                "¡Sigue así! Estás construyendo un hábito increíble",
                            modifier   = Modifier.fillMaxWidth(),
                            fontSize   = SubtituloSp.sp,
                            color      = TextoSecundario,
                            textAlign  = TextAlign.Center,
                            lineHeight = (SubtituloSp * 1.4f).sp
                        )

                        Spacer(Modifier.height(EspacioN))

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
                                            modifier = Modifier.size((SeccionSp + 2f).dp)
                                        )
                                        Spacer(Modifier.width(8.dp))
                                        Text(
                                            "Esta semana",
                                            fontSize   = SeccionSp.sp,
                                            fontWeight = FontWeight.Bold,
                                            color      = TextoPrincipal
                                        )
                                    }
                                    Text(
                                        "${uiState.completedThisWeek}/${uiState.totalThisWeek} días",
                                        fontSize   = CuerpoSp.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color      = VerdePrimario
                                    )
                                }

                                Spacer(Modifier.height(EspacioN))

                                WeekRow(weekDays = uiState.weekDays, cuerpoSp = CuerpoSp)
                            }
                        }

                        Spacer(Modifier.height(EspacioC * 1.5f))

                        Card(
                            modifier  = Modifier.fillMaxWidth(),
                            shape     = RoundedCornerShape(20.dp),
                            colors    = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(1.dp)
                        ) {
                            Column(modifier = Modifier.padding(18.dp)) {
                                Text(
                                    "Historial de actividad",
                                    fontSize   = SeccionSp.sp,
                                    fontWeight = FontWeight.Bold,
                                    color      = TextoPrincipal
                                )

                                Spacer(Modifier.height(EspacioN))

                                ActivityHeatmap(history = uiState.activityHistory)

                                Spacer(Modifier.height(EspacioC))

                                HeatmapLegend(cuerpoSp = CuerpoSp)
                            }
                        }

                        Spacer(Modifier.height(EspacioN))
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
    val rows = if (history.isEmpty()) 0 else (history.size + 6) / 7
    LazyVerticalGrid(
        columns  = GridCells.Fixed(7),
        modifier = Modifier
            .fillMaxWidth()
            .height((rows * 22).dp),
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