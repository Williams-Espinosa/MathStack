package com.tuapp.exercises.presentacion.screens

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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LightbulbOutline
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tuapp.exercises.domain.entities.Lesson
import com.tuapp.exercises.domain.entities.LessonStatus
import com.tuapp.exercises.domain.entities.TheorySection
import com.tuapp.exercises.presentacion.viewmodels.ExercisesViewModel
import ru.noties.jlatexmath.JLatexMathDrawable

// ─────────────────────────────────────────────────────────────────────────────
//  Design tokens
// ─────────────────────────────────────────────────────────────────────────────

private val BluePrimary   = Color(0xFF3D5AFE)
private val BlueLight     = Color(0xFFEEF1FF)
private val GreenDone     = Color(0xFF4CAF50)
private val GreenLight    = Color(0xFFE8F5E9)
private val GoldXp        = Color(0xFFF59E0B)
private val RedError      = Color(0xFFF44336)
private val RedLight      = Color(0xFFFFEBEE)
private val Grey500       = Color(0xFF9E9E9E)
private val TextPrimary   = Color(0xFF1A1A2E)
private val TextSecondary = Color(0xFF6B7280)
private val CardBg        = Color(0xFFFFFFFF)
private val PageBg        = Color(0xFFF8F9FF)
@Composable
fun LatexView(
    latex: String,
    textSizeSp: Float = 18f,
    textColor: Color = TextPrimary,
    modifier: Modifier = Modifier
) {
    val density    = LocalDensity.current
    val textSizePx = with(density) { textSizeSp.sp.toPx() }

    val bitmap = remember(latex, textSizePx, textColor) {
        runCatching {
            val drawable = JLatexMathDrawable.builder(latex)
                .textSize(textSizePx)
                .color(textColor.toArgb())
                .align(JLatexMathDrawable.ALIGN_CENTER)
                .build()

            val w = drawable.intrinsicWidth.coerceAtLeast(10)
            val h = drawable.intrinsicHeight.coerceAtLeast(10)
            val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            drawable.setBounds(0, 0, w, h)
            drawable.draw(Canvas(bmp))
            bmp
        }.getOrNull()
    }

    if (bitmap != null) {
        Image(
            bitmap             = bitmap.asImageBitmap(),
            contentDescription = latex,
            modifier           = modifier
        )
    } else {
        Text(latex, fontSize = textSizeSp.sp, color = textColor, modifier = modifier)
    }
}

@Composable
fun ExercisesScreen(
    pathId: String = "default",
    viewModel: ExercisesViewModel = hiltViewModel()
) {
    val pathState     by viewModel.pathState.collectAsStateWithLifecycle()
    val theoryState   by viewModel.theoryState.collectAsStateWithLifecycle()
    val exerciseState by viewModel.exerciseState.collectAsStateWithLifecycle()

    LaunchedEffect(pathId) { viewModel.loadPath(pathId) }

    when {
        exerciseState is ExerciseUiState.Active || exerciseState is ExerciseUiState.Finished ->
            ExerciseSessionScreen(exerciseState, viewModel)

        theoryState !is TheoryUiState.Idle ->
            TheoryScreen(theoryState, viewModel)

        else ->
            LearningPathScreen(pathState, viewModel)
    }
}

@Composable
fun LearningPathScreen(
    state: LearningPathUiState,
    viewModel: ExercisesViewModel
) {
    when (state) {
        is LearningPathUiState.Loading ->
            LoadingScreen()

        is LearningPathUiState.Error ->
            ErrorScreen(state.message) { viewModel.loadPath("default") }

        is LearningPathUiState.Success -> {
            val path = state.path
            Scaffold(
                containerColor = PageBg,
                topBar = {
                    TopBar(title = "Ruta de Aprendizaje", onBack = null)
                }
            ) { padding ->
                LazyColumn(
                    Modifier.fillMaxSize().padding(padding).padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    item {
                        Spacer(Modifier.height(12.dp))
                        Card(
                            Modifier.fillMaxWidth(),
                            shape  = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = BluePrimary)
                        ) {
                            Column(Modifier.padding(20.dp)) {
                                Text(path.title, color = Color.White,
                                    fontWeight = FontWeight.Bold, fontSize = 22.sp)
                                Spacer(Modifier.height(4.dp))
                                Text("${path.totalLessons} lecciones · ${path.totalXp} XP total",
                                    color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                                Spacer(Modifier.height(12.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    LinearProgressIndicator(
                                        progress  = { path.progressFraction },
                                        modifier  = Modifier.weight(1f).height(6.dp)
                                            .clip(RoundedCornerShape(3.dp)),
                                        color     = Color.White,
                                        trackColor = Color.White.copy(alpha = 0.3f)
                                    )
                                    Spacer(Modifier.width(10.dp))
                                    Text("${path.completedLessons}/${path.totalLessons}",
                                        color = Color.White, fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp)
                                }
                            }
                        }
                        Spacer(Modifier.height(20.dp))
                    }
                    items(path.lessons, key = { it.id }) { lesson ->
                        LessonRow(lesson = lesson, onClick = {
                            if (lesson.status != LessonStatus.LOCKED) {
                                viewModel.openLesson(lesson)
                            }
                        })
                    }

                    item { Spacer(Modifier.height(32.dp)) }
                }
            }
        }
    }
}

@Composable
private fun LessonRow(lesson: Lesson, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(
                        when (lesson.status) {
                            LessonStatus.COMPLETED -> GreenDone
                            LessonStatus.AVAILABLE -> BluePrimary
                            LessonStatus.LOCKED    -> Grey500.copy(alpha = 0.3f)
                        }
                    )
                    .border(3.dp,
                        if (lesson.status == LessonStatus.AVAILABLE) BluePrimary
                        else Color.Transparent, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                when (lesson.status) {
                    LessonStatus.COMPLETED -> Icon(Icons.Default.CheckCircle, null,
                        tint = Color.White, modifier = Modifier.size(22.dp))
                    LessonStatus.AVAILABLE -> Box(
                        Modifier.size(16.dp).clip(CircleShape)
                            .border(3.dp, Color.White, CircleShape)
                    )
                    LessonStatus.LOCKED    -> Icon(Icons.Default.Lock, null,
                        tint = Grey500, modifier = Modifier.size(20.dp))
                }
            }
        }

        Spacer(Modifier.width(14.dp))
        Card(
            modifier  = Modifier.weight(1f).clickable(
                enabled = lesson.status != LessonStatus.LOCKED,
                onClick = onClick
            ),
            shape     = RoundedCornerShape(16.dp),
            colors    = CardDefaults.cardColors(
                containerColor = if (lesson.status == LessonStatus.LOCKED)
                    CardBg.copy(alpha = 0.5f) else CardBg
            ),
            elevation = CardDefaults.cardElevation(if (lesson.status == LessonStatus.LOCKED) 0.dp else 2.dp)
        ) {
            Row(
                Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text("Lección ${lesson.number}", fontWeight = FontWeight.Bold,
                        fontSize = 15.sp, color = if (lesson.status == LessonStatus.LOCKED)
                            TextSecondary else TextPrimary)
                    Text(lesson.description, fontSize = 13.sp, color = TextSecondary)
                    Spacer(Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("⚡", fontSize = 12.sp)
                        Spacer(Modifier.width(3.dp))
                        Text("+${lesson.xpReward} XP", fontSize = 13.sp,
                            color = GoldXp, fontWeight = FontWeight.Medium)
                    }
                }
                if (lesson.status == LessonStatus.COMPLETED) {
                    Icon(Icons.Default.CheckCircle, null,
                        tint = GreenDone, modifier = Modifier.size(22.dp))
                }
            }
        }
    }
}

@Composable
fun TheoryScreen(
    state: TheoryUiState,
    viewModel: ExercisesViewModel
) {
    when (state) {
        is TheoryUiState.Idle    -> Unit
        is TheoryUiState.Loading -> LoadingScreen()
        is TheoryUiState.Error   -> ErrorScreen(state.message) { viewModel.resetTheoryState() }
        is TheoryUiState.Success -> {
            val listState = rememberLazyListState()

            val isAtBottom by remember {
                derivedStateOf {
                    val info = listState.layoutInfo
                    val lastVisible = info.visibleItemsInfo.lastOrNull()?.index ?: 0
                    lastVisible >= info.totalItemsCount - 1
                }
            }
            LaunchedEffect(isAtBottom) {
                if (isAtBottom) viewModel.markTheoryRead()
            }

            Scaffold(
                containerColor = PageBg,
                topBar = {
                    TopBar(title = state.lesson.title, onBack = { viewModel.resetTheoryState() })
                }
            ) { padding ->
                Box(Modifier.fillMaxSize().padding(padding)) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        item {
                            Spacer(Modifier.height(12.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    Modifier.size(40.dp).clip(RoundedCornerShape(12.dp))
                                        .background(BlueLight),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.MenuBook, null,
                                        tint = BluePrimary, modifier = Modifier.size(22.dp))
                                }
                                Spacer(Modifier.width(12.dp))
                                Text(state.lesson.title, fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp, color = TextPrimary)
                                Spacer(Modifier.weight(1f))
                                Icon(Icons.Default.Timer, null,
                                    tint = TextSecondary, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(3.dp))
                                Text("${state.theory.estimatedMinutes} min",
                                    color = TextSecondary, fontSize = 13.sp)
                            }
                        }

                        items(state.theory.sections) { section ->
                            when (section) {
                                is TheorySection.Introduction -> IntroCard(section.text)
                                is TheorySection.WorkedExample -> ExampleCard(section)
                                is TheorySection.PropertyBox  -> PropertyCard(section)
                            }
                        }

                        item {
                            AnimatedVisibility(visible = state.readyToStart) {
                                ReadyBanner(onStart = { viewModel.startExercises() })
                            }
                            Spacer(Modifier.height(32.dp))
                        }
                    }

                    if (!state.readyToStart) {
                        Button(
                            onClick  = { viewModel.markTheoryRead() },
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .padding(16.dp)
                                .height(52.dp),
                            shape  = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = GreenDone)
                        ) {
                            Text("¡Listo para practicar!", fontSize = 16.sp,
                                fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun IntroCard(text: String) {
    Card(
        Modifier.fillMaxWidth(),
        shape  = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Text(text, modifier = Modifier.padding(16.dp),
            color = TextPrimary, fontSize = 14.sp, lineHeight = 22.sp)
    }
}

@Composable
private fun ExampleCard(section: TheorySection.WorkedExample) {
    Card(
        Modifier.fillMaxWidth(),
        shape  = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(section.title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
            Spacer(Modifier.height(6.dp))
            LatexView(
                latex      = section.problemLatex,
                textSizeSp = 15f,
                textColor  = TextPrimary,
                modifier   = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(10.dp))
            section.steps.forEachIndexed { i, step ->
                Row(Modifier.padding(vertical = 2.dp)) {
                    Box(
                        Modifier
                            .width(3.dp)
                            .height(20.dp)
                            .background(BluePrimary, RoundedCornerShape(2.dp))
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("${i + 1}. $step", fontSize = 13.sp, color = TextPrimary)
                }
            }
        }
    }
}

@Composable
private fun PropertyCard(section: TheorySection.PropertyBox) {
    Card(
        Modifier.fillMaxWidth(),
        shape  = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardBg),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(section.title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = TextPrimary)
            Spacer(Modifier.height(6.dp))
            Text(section.body, fontSize = 13.sp, color = TextPrimary, lineHeight = 20.sp)
        }
    }
}

@Composable
private fun ReadyBanner(onStart: () -> Unit) {
    Card(
        Modifier.fillMaxWidth(),
        shape  = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = GreenDone)
    ) {
        Column(Modifier.padding(20.dp)) {
            Text("¡Listo para practicar!", color = Color.White,
                fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(Modifier.height(4.dp))
            Text("Pon en práctica lo que aprendiste con ejercicios interactivos",
                color = Color.White.copy(alpha = 0.9f), fontSize = 13.sp)
            Spacer(Modifier.height(14.dp))
            OutlinedButton(
                onClick = onStart,
                modifier = Modifier.fillMaxWidth(),
                shape  = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, Color.White)
            ) {
                Text("Comenzar ejercicios", fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.width(6.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, null, Modifier.size(16.dp))
            }
        }
    }
}
@Composable
fun ExerciseSessionScreen(
    state: ExerciseUiState,
    viewModel: ExercisesViewModel
) {
    when (state) {
        is ExerciseUiState.Active   -> ActiveExercise(state, viewModel)
        is ExerciseUiState.Finished -> ExerciseFinishedScreen(state.session.earnedXp, onBack = {
            viewModel.resetExerciseState()
            viewModel.resetTheoryState()
        })
        else -> Unit
    }
}

@Composable
private fun ActiveExercise(
    state: ExerciseUiState.Active,
    viewModel: ExercisesViewModel
) {
    val session  = state.session
    val exercise = session.current ?: return
    val keyboard = LocalSoftwareKeyboardController.current

    Column(
        Modifier
            .fillMaxSize()
            .background(PageBg)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.resetExerciseState() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", tint = TextPrimary)
            }
            Spacer(Modifier.weight(1f))
            Box(
                Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(GoldXp.copy(alpha = 0.15f))
                    .padding(horizontal = 12.dp, vertical = 5.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("⚡", fontSize = 13.sp)
                    Spacer(Modifier.width(3.dp))
                    Text("+${exercise.xpReward} XP", color = GoldXp,
                        fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text("Ejercicio ${exercise.number}", fontWeight = FontWeight.Bold,
                fontSize = 20.sp, color = TextPrimary)
            Text(exercise.instructionText, fontSize = 14.sp, color = TextSecondary)

            Card(
                Modifier.fillMaxWidth(),
                shape     = RoundedCornerShape(20.dp),
                colors    = CardDefaults.cardColors(containerColor = CardBg),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(Modifier.padding(20.dp)) {
                    LatexView(
                        latex      = exercise.questionLatex,
                        textSizeSp = 22f,
                        textColor  = TextPrimary,
                        modifier   = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(16.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("x\n=", color = TextSecondary, fontSize = 13.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(end = 8.dp))
                        OutlinedTextField(
                            value         = session.userInput,
                            onValueChange = { viewModel.onUserInput(it) },
                            placeholder   = { Text("Tu respuesta", color = Grey500) },
                            singleLine    = true,
                            modifier      = Modifier.weight(1f),
                            shape         = RoundedCornerShape(12.dp),
                            colors        = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor   = BluePrimary,
                                unfocusedBorderColor = Color(0xFFE5E7EB)
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction    = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(onDone = {
                                keyboard?.hide()
                                viewModel.checkAnswer()
                            })
                        )
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            AnimatedVisibility(visible = state.hintVisible && exercise.hintLatex != null,
                enter = fadeIn(), exit = fadeOut()) {
                exercise.hintLatex?.let { hint ->
                    Card(
                        Modifier.fillMaxWidth(),
                        shape  = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = BlueLight)
                    ) {
                        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LightbulbOutline, null,
                                tint = BluePrimary, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Column {
                                Text("Pista", color = BluePrimary,
                                    fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                                LatexView(hint, textSizeSp = 13f, textColor = TextPrimary)
                            }
                        }
                    }
                }
            }

            AnimatedVisibility(visible = state.checkResult != null) {
                state.checkResult?.let { result ->
                    val isCorrect = result == CheckResult.CORRECT
                    Card(
                        Modifier.fillMaxWidth(),
                        shape  = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isCorrect) GreenLight else RedLight
                        )
                    ) {
                        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(if (isCorrect) "✅" else "❌", fontSize = 18.sp)
                            Spacer(Modifier.width(10.dp))
                            Column {
                                Text(
                                    if (isCorrect) "¡Correcto!" else "Incorrecto",
                                    color = if (isCorrect) GreenDone else RedError,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    if (isCorrect) "+${exercise.xpReward} XP ganado"
                                    else "Inténtalo de nuevo",
                                    color = if (isCorrect) GreenDone else RedError,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }

            if (state.checkResult == CheckResult.CORRECT) {
                Button(
                    onClick  = { viewModel.nextExercise() },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape    = RoundedCornerShape(14.dp),
                    colors   = ButtonDefaults.buttonColors(containerColor = BluePrimary)
                ) {
                    Text("Siguiente", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(6.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, null)
                }
            } else {
                if (exercise.hintLatex != null) {
                    TextButton(
                        onClick  = { viewModel.toggleHint() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.LightbulbOutline, null, Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Ver pista")
                    }
                }
                Button(
                    onClick  = {
                        keyboard?.hide()
                        viewModel.checkAnswer()
                    },
                    enabled  = session.userInput.isNotBlank(),
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape    = RoundedCornerShape(14.dp),
                    colors   = ButtonDefaults.buttonColors(containerColor = BluePrimary)
                ) {
                    Text("Comprobar respuesta", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ExerciseFinishedScreen(earnedXp: Int, onBack: () -> Unit) {
    Column(
        Modifier.fillMaxSize().background(PageBg).padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🎉", fontSize = 64.sp)
        Spacer(Modifier.height(16.dp))
        Text("¡Lección completada!", fontWeight = FontWeight.Bold,
            fontSize = 24.sp, color = TextPrimary)
        Spacer(Modifier.height(8.dp))
        Text("+$earnedXp XP ganados", color = GoldXp, fontSize = 18.sp,
            fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(32.dp))
        Button(
            onClick  = onBack,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape    = RoundedCornerShape(14.dp),
            colors   = ButtonDefaults.buttonColors(containerColor = BluePrimary)
        ) {
            Text("Volver a la ruta", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(title: String, onBack: (() -> Unit)?) {
    TopAppBar(
        title = { Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp) },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = PageBg,
            titleContentColor = TextPrimary
        )
    )
}

@Composable
private fun LoadingScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = BluePrimary)
    }
}

@Composable
private fun ErrorScreen(message: String, onRetry: () -> Unit) {
    Column(
        Modifier.fillMaxSize().padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("⚠️", fontSize = 48.sp)
        Spacer(Modifier.height(12.dp))
        Text(message, color = TextSecondary, textAlign = TextAlign.Center)
        Spacer(Modifier.height(24.dp))
        Button(onClick = onRetry,
            colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)) {
            Text("Reintentar")
        }
    }
}
