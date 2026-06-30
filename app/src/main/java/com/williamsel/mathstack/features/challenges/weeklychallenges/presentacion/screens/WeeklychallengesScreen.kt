package com.williamsel.mathstack.features.challenges.weeklychallenges.presentacion.screens

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.williamsel.mathstack.features.challenges.weeklychallenges.domain.entities.ChallengeDifficulty
import com.williamsel.mathstack.features.challenges.weeklychallenges.domain.entities.ChallengeSession
import com.williamsel.mathstack.features.challenges.weeklychallenges.domain.entities.WeeklyChallenge
import com.williamsel.mathstack.features.challenges.weeklychallenges.domain.entities.ExerciseOption
import com.williamsel.mathstack.features.challenges.weeklychallenges.presentacion.viewmodels.WeeklychallengesViewModel
import ru.noties.jlatexmath.JLatexMathDrawable

private val BluePrimary    = Color(0xFF3D5AFE)
private val BlueLight      = Color(0xFFEEF1FF)
private val GoldCoin       = Color(0xFFFFC107)
private val GreenCorrect   = Color(0xFF4CAF50)
private val RedIncorrect   = Color(0xFFF44336)
private val TextPrimary    = Color(0xFF1A1A2E)
private val TextSecondary  = Color(0xFF6B7280)
private val CardBackground = Color(0xFFFFFFFF)
private val PageBackground = Color(0xFFF8F9FF)

private val DifficultyColors = mapOf(
    ChallengeDifficulty.EASY   to Pair(Color(0xFFE8F5E9), Color(0xFF4CAF50)),
    ChallengeDifficulty.MEDIUM to Pair(Color(0xFFFFF8E1), Color(0xFFF59E0B)),
    ChallengeDifficulty.HARD   to Pair(Color(0xFFFFEBEE), Color(0xFFF44336))
)

@Composable
fun LatexText(
    latex: String,
    textSizeSp: Float = 18f,
    textColor: Color = TextPrimary,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val textSizePx = with(density) { textSizeSp.sp.toPx() }

    val bitmap = remember(latex, textSizePx, textColor) {
        runCatching {
            val drawable = JLatexMathDrawable.builder(latex)
                .textSize(textSizePx)
                .color(textColor.toArgb())
                .align(JLatexMathDrawable.ALIGN_CENTER)
                .build()

            val w = drawable.intrinsicWidth.takeIf { it > 0 } ?: 400
            val h = drawable.intrinsicHeight.takeIf { it > 0 } ?: 80
            val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            drawable.setBounds(0, 0, w, h)
            drawable.draw(Canvas(bmp))
            bmp
        }.getOrNull()
    }

    if (bitmap != null) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = latex,
            modifier = modifier
        )
    } else {
        Text(
            text = latex,
            fontSize = textSizeSp.sp,
            color = textColor,
            modifier = modifier
        )
    }
}

@Composable
fun WeeklychallengesScreen(
    viewModel: WeeklychallengesViewModel = hiltViewModel()
) {
    val listState by viewModel.listState.collectAsStateWithLifecycle()
    val exerciseState by viewModel.exerciseState.collectAsStateWithLifecycle()

    when (val es = exerciseState) {
        is ExerciseUiState.Idle -> ChallengesListScreen(listState, viewModel)
        is ExerciseUiState.Loading -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = BluePrimary)
        }

        is ExerciseUiState.Question -> ExerciseScreen(
            state = es,
            viewModel = viewModel
        )

        is ExerciseUiState.SessionFinished -> SessionResultScreen(
            session = es.session,
            onRetry = { viewModel.startChallenge(es.session.challenge) },
            onBack = { viewModel.resetExerciseState() }
        )

        is ExerciseUiState.Error -> ErrorScreen(es.message) { viewModel.loadChallenges() }
    }
}

@Composable
fun ChallengesListScreen(
    state: WeeklychallengesUiState,
    viewModel: WeeklychallengesViewModel
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        containerColor = PageBackground,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        when (state) {
            is WeeklychallengesUiState.Loading ->
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = BluePrimary)
                }

            is WeeklychallengesUiState.Error ->
                ErrorScreen(state.message) { viewModel.loadChallenges() }

            is WeeklychallengesUiState.Success -> {
                LaunchedEffect(state.joinToast) {
                    state.joinToast?.let {
                        snackbarHostState.showSnackbar(it)
                        viewModel.clearJoinToast()
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = "Retos Semanales",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(12.dp))
                        ActiveChallengesBanner(
                            joinedCount = state.joinedChallenges.size,
                            availableCount = state.availableChallenges.size
                        )
                        Spacer(Modifier.height(20.dp))
                    }

                    if (state.joinedChallenges.isNotEmpty()) {
                        item {
                            SectionLabel("MIS RETOS")
                        }
                        items(state.joinedChallenges, key = { it.id }) { challenge ->
                            JoinedChallengeCard(
                                challenge = challenge,
                                onContinue = { viewModel.startChallenge(challenge) }
                            )
                        }
                        item { Spacer(Modifier.height(8.dp)) }
                    }

                    if (state.availableChallenges.isNotEmpty()) {
                        item { SectionLabel("DISPONIBLES") }
                        items(state.availableChallenges, key = { it.id }) { challenge ->
                            AvailableChallengeCard(
                                challenge = challenge,
                                onJoin = { viewModel.joinChallenge(challenge) }
                            )
                        }
                    }

                    item { Spacer(Modifier.height(24.dp)) }
                }
            }
        }
    }
}

@Composable
fun ExerciseScreen(
    state: ExerciseUiState.Question,
    viewModel: WeeklychallengesViewModel
) {
    val session = state.session
    val exercise = session.challenge.exercises.getOrNull(session.currentExerciseIndex)
        ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PageBackground)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(BluePrimary)
                .padding(16.dp)
        ) {
            IconButton(
                onClick = { viewModel.resetExerciseState() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", tint = Color.White)
            }
            Row(
                Modifier.align(Alignment.CenterEnd),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("⚡", fontSize = 14.sp)
                    Spacer(Modifier.width(3.dp))
                    Text(
                        "${session.earnedXp} XP", color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("🪙", fontSize = 14.sp)
                    Spacer(Modifier.width(3.dp))
                    Text(
                        "${session.earnedCoins}", color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Box(
            Modifier
                .fillMaxWidth()
                .background(BluePrimary)
                .padding(start = 20.dp, end = 20.dp, bottom = 12.dp)
        ) {
            Column {
                Text(
                    session.challenge.title, color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp
                )
                Row(
                    Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Pregunta ${session.currentExerciseIndex + 1} de ${session.totalExercises}",
                        color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp
                    )
                    Text(
                        "${(session.sessionProgress * 100).toInt()}% completado",
                        color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp
                    )
                }
                Spacer(Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress = { session.sessionProgress },
                    modifier = Modifier.fillMaxWidth().height(5.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.3f)
                )
            }
        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Box(
                    Modifier.fillMaxWidth().padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    LatexText(
                        latex = exercise.questionLatex,
                        textSizeSp = 20f,
                        textColor = TextPrimary
                    )
                }
            }

            exercise.options.forEach { option ->
                OptionItem(
                    option = option,
                    isSelected = state.selectedOptionId == option.id,
                    answerResult = state.answerResult,
                    isAnswered = state.answerResult != null,
                    onClick = {
                        if (state.answerResult == null) viewModel.selectOption(option.id)
                    }
                )
            }

            Spacer(Modifier.weight(1f))

            if (exercise.hintLatex != null) {
                AnimatedVisibility(
                    visible = state.hintVisible,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Card(
                        Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = BlueLight)
                    ) {
                        Row(
                            Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Outlined.Lightbulb, null,
                                tint = BluePrimary, modifier = Modifier.size(18.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            LatexText(
                                exercise.hintLatex,
                                textSizeSp = 14f,
                                textColor = BluePrimary
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(visible = state.answerResult != null) {
                state.answerResult?.let { result ->
                    Card(
                        Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (result.isCorrect) Color(0xFFE8F5E9) else Color(
                                0xFFFFEBEE
                            )
                        )
                    ) {
                        Row(
                            Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(if (result.isCorrect) "✅" else "❌", fontSize = 18.sp)
                            Spacer(Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = if (result.isCorrect) "¡Correcto!" else "Incorrecto",
                                    color = if (result.isCorrect) GreenCorrect else RedIncorrect,
                                    fontWeight = FontWeight.Bold
                                )
                                if (result.isCorrect) {
                                    Text(
                                        "+${result.earnedXp} XP ganado",
                                        color = GreenCorrect, fontSize = 13.sp
                                    )
                                } else {
                                    val correct = exercise.options.first { it.isCorrect }
                                    Row {
                                        Text(
                                            "La respuesta era: ",
                                            color = RedIncorrect,
                                            fontSize = 13.sp
                                        )
                                        LatexText(
                                            correct.textLatex,
                                            textSizeSp = 13f,
                                            textColor = RedIncorrect
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (state.answerResult == null) {
                if (exercise.hintLatex != null) {
                    OutlinedButton(
                        onClick = { viewModel.toggleHint() },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Outlined.Lightbulb, null, Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Ver pista")
                    }
                }
                Button(
                    onClick = { viewModel.checkAnswer() },
                    enabled = state.selectedOptionId != null,
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
                ) {
                    Text("Comprobar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            } else {
                Button(
                    onClick = { viewModel.nextExercise() },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
                ) {
                    Text("Siguiente", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(6.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, null)
                }
            }
        }
    }
}

@Composable
fun SessionResultScreen(
    session: ChallengeSession,
    onRetry: () -> Unit,
    onBack: () -> Unit
) {
    val isPassing = session.correctCount >= (session.totalExercises / 2)

    Column(
        Modifier.fillMaxSize().background(PageBackground)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(BluePrimary)
                .padding(vertical = 40.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    Icons.Default.EmojiEvents, null,
                    tint = Color.White.copy(alpha = 0.5f),
                    modifier = Modifier.size(64.dp)
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    text = if (isPassing) "¡Excelente trabajo!" else "¡Sigue practicando!",
                    color = Color.White, fontWeight = FontWeight.Bold, fontSize = 24.sp
                )
                Text(
                    session.challenge.title,
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
            }
        }

        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ResultStatCard(
                    "${session.correctCount}/${session.totalExercises}",
                    "Correctas", Modifier.weight(1f)
                )
                ResultStatCard(
                    "${session.earnedXp}", "XP ganado",
                    Modifier.weight(1f), valueColor = BluePrimary
                )
                ResultStatCard(
                    "${session.earnedCoins}", "Monedas",
                    Modifier.weight(1f), valueColor = GoldCoin
                )
            }

            Card(
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Tu progreso", color = TextSecondary, fontSize = 14.sp)
                        Text(
                            "${(session.sessionProgress * 100).toInt()}%",
                            color = TextPrimary, fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { session.sessionProgress },
                        modifier = Modifier.fillMaxWidth().height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = BluePrimary,
                        trackColor = Color(0xFFE5E7EB)
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
            ) {
                Text("Volver a retos", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.width(6.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, null)
            }

            OutlinedButton(
                onClick = onRetry,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = BluePrimary)
            ) {
                Text("Reintentar reto", fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun ActiveChallengesBanner(joinedCount: Int, availableCount: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = GoldCoin)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.EmojiEvents, contentDescription = null,
                tint = Color.White, modifier = Modifier.size(32.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    "Retos Activos", fontWeight = FontWeight.Bold,
                    color = Color.White, fontSize = 16.sp
                )
                Text(
                    "$joinedCount unidos · $availableCount disponibles",
                    color = Color.White.copy(alpha = 0.9f), fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
fun SectionLabel(text: String) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextSecondary,
        letterSpacing = 1.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun JoinedChallengeCard(challenge: WeeklyChallenge, onContinue: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier.size(10.dp).clip(CircleShape)
                        .background(BluePrimary)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    challenge.title, fontWeight = FontWeight.Bold,
                    color = TextPrimary, fontSize = 16.sp, modifier = Modifier.weight(1f)
                )
                DifficultyBadge(challenge.difficulty)
            }
            Spacer(Modifier.height(4.dp))
            Text(challenge.description, color = TextSecondary, fontSize = 13.sp)
            Spacer(Modifier.height(10.dp))
            ChallengeStats(challenge)
            Spacer(Modifier.height(10.dp))
            Column {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Progreso", color = TextSecondary, fontSize = 13.sp)
                    Text(
                        "${(challenge.progress * 100).toInt()}%",
                        color = TextPrimary, fontWeight = FontWeight.Medium, fontSize = 13.sp
                    )
                }
                Spacer(Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = { challenge.progress },
                    modifier = Modifier.fillMaxWidth().height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = if (challenge.progress >= 0.5f) GreenCorrect else BluePrimary,
                    trackColor = Color(0xFFE5E7EB)
                )
            }
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Group, contentDescription = null,
                    tint = TextSecondary, modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    "${challenge.participants} participantes",
                    color = TextSecondary, fontSize = 13.sp, modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = onContinue,
                    colors = ButtonDefaults.buttonColors(containerColor = BluePrimary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Continuar", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun AvailableChallengeCard(challenge: WeeklyChallenge, onJoin: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    challenge.title, fontWeight = FontWeight.Bold,
                    color = TextPrimary, fontSize = 16.sp, modifier = Modifier.weight(1f)
                )
                DifficultyBadge(challenge.difficulty)
            }
            Spacer(Modifier.height(4.dp))
            Text(challenge.description, color = TextSecondary, fontSize = 13.sp)
            Spacer(Modifier.height(10.dp))
            ChallengeStats(challenge)
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.Group, contentDescription = null,
                    tint = TextSecondary, modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    "${challenge.participants} participantes",
                    color = TextSecondary, fontSize = 13.sp, modifier = Modifier.weight(1f)
                )
                OutlinedButton(
                    onClick = onJoin,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = BluePrimary)
                ) {
                    Text("Unirse")
                }
            }
        }
    }
}

@Composable
fun ChallengeStats(challenge: WeeklyChallenge) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        StatChip(
            icon = {
                Icon(
                    Icons.Default.Timer,
                    null,
                    Modifier.size(14.dp),
                    tint = TextSecondary
                )
            },
            label = "${challenge.durationDays} días"
        )
        StatChip(
            icon = { Text("🪙", fontSize = 13.sp) },
            label = "${challenge.coins}",
            labelColor = GoldCoin
        )
        StatChip(
            icon = { Text("⚡", fontSize = 13.sp) },
            label = "${challenge.xp} XP",
            labelColor = BluePrimary
        )
    }
}

@Composable
fun StatChip(
    icon: @Composable () -> Unit,
    label: String,
    labelColor: Color = TextSecondary
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        icon()
        Spacer(Modifier.width(3.dp))
        Text(label, color = labelColor, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun DifficultyBadge(difficulty: ChallengeDifficulty) {
    val (bg, fg) = DifficultyColors[difficulty] ?: Pair(Color.LightGray, Color.DarkGray)
    Box(
        Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bg)
            .padding(horizontal = 10.dp, vertical = 3.dp)
    ) {
        Text(difficulty.label, color = fg, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun OptionItem(
    option: ExerciseOption,
    isSelected: Boolean,
    answerResult: AnswerResult?,
    isAnswered: Boolean,
    onClick: () -> Unit
) {
    val (bgColor, borderColor, labelBg) = when {
        isAnswered && option.isCorrect ->
            Triple(Color(0xFFE8F5E9), GreenCorrect, GreenCorrect)

        isAnswered && isSelected && !option.isCorrect ->
            Triple(Color(0xFFFFEBEE), RedIncorrect, RedIncorrect)

        isSelected ->
            Triple(BlueLight, BluePrimary, BluePrimary)

        else ->
            Triple(CardBackground, Color(0xFFE5E7EB), Color(0xFFE5E7EB))
    }

    val textColor = when {
        isAnswered && (option.isCorrect || (isSelected && !option.isCorrect)) -> Color.White
        isSelected -> BluePrimary
        isAnswered -> Color(0xFFBDBDBD)
        else -> TextPrimary
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .border(1.5.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable(enabled = !isAnswered, onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(labelBg),
            contentAlignment = Alignment.Center
        ) {
            Text(
                option.label,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }
        Spacer(Modifier.width(12.dp))
        LatexText(option.textLatex, textSizeSp = 15f, textColor = textColor)
    }
}

@Composable
fun ResultStatCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    valueColor: Color = TextPrimary
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            Modifier.padding(12.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(value, color = valueColor, fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Text(label, color = TextSecondary, fontSize = 12.sp, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun ErrorScreen(message: String, onRetry: () -> Unit) {
    Column(
        Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("⚠️", fontSize = 48.sp)
        Spacer(Modifier.height(16.dp))
        Text(message, color = TextSecondary, textAlign = TextAlign.Center)
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
        ) {
            Text("Reintentar")
        }
    }
}
