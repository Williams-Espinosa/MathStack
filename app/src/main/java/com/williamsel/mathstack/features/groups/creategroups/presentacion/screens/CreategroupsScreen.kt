package com.williamsel.mathstack.features.private.creategroups.presentacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.williamsel.mathstack.features.private.creategroups.presentacion.viewmodels.CreategroupsViewModel
import com.williamsel.mathstack.ui.theme.*

private val SUBJECTS = listOf(
    "Álgebra", "Cálculo", "Geometría"
)

@Composable
fun CreategroupsScreen(
    onNavigateBack: () -> Unit = {},
    onGroupCreated: (String) -> Unit = {},
    viewModel: CreategroupsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.successGroupId) {
        uiState.successGroupId?.let { groupId ->
            viewModel.onSuccessConsumed()
            onGroupCreated(groupId)
        }
    }

    CreategroupsContent(
        uiState             = uiState,
        onNavigateBack      = onNavigateBack,
        onNameChange        = viewModel::onNameChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onSubjectChange     = viewModel::onSubjectChange,
        onMaxMembersChange  = viewModel::onMaxMembersChange,
        onCreateGroup       = viewModel::onCreateGroup,
        onErrorDismissed    = viewModel::onErrorDismissed
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreategroupsContent(
    uiState: CreategroupsUiState,
    onNavigateBack: () -> Unit,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onSubjectChange: (String) -> Unit,
    onMaxMembersChange: (Int) -> Unit,
    onCreateGroup: () -> Unit,
    onErrorDismissed: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var subjectExpanded by remember { mutableStateOf(false) }

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
        val espacioC   = (altoTotal  * 0.016f).coerceIn(8.dp,  16.dp)
        val tituloSp   = (anchoTotal.value * 0.058f).coerceIn(18f, 24f)
        val labelSp    = (anchoTotal.value * 0.036f).coerceIn(12f, 15f)
        val bodySp     = (anchoTotal.value * 0.040f).coerceIn(14f, 16f)
        val botonAlto  = (altoTotal  * 0.062f).coerceIn(44.dp, 56.dp)

        Scaffold(
            containerColor = FondoPantalla,
            snackbarHost   = { SnackbarHost(snackbarHostState) }
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = hPad)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(espacioC)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = espacioN),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.Outlined.ArrowBack,
                            contentDescription = "Volver",
                            tint = TextoPrincipal
                        )
                    }
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "Crear Grupo",
                        fontSize   = tituloSp.sp,
                        fontWeight = FontWeight.Bold,
                        color      = TextoPrincipal
                    )
                }

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = AzulPrimario,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Outlined.Group,
                                contentDescription = null,
                                tint     = Color.White,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                        Spacer(Modifier.width(14.dp))
                        Column {
                            Text(
                                "Nuevo Grupo",
                                fontSize   = bodySp.sp,
                                fontWeight = FontWeight.Bold,
                                color      = Color.White
                            )
                            Text(
                                "Crea tu comunidad de estudio",
                                fontSize = (labelSp - 1f).sp,
                                color    = Color.White.copy(alpha = 0.85f)
                            )
                        }
                    }
                }

                FormLabel(text = "Nombre del grupo", labelSp = labelSp)
                OutlinedTextField(
                    value         = uiState.name,
                    onValueChange = onNameChange,
                    placeholder   = { Text("Ej. Matemáticas Avanzadas 2024", color = TextoSecundario) },
                    singleLine    = true,
                    shape         = RoundedCornerShape(12.dp),
                    modifier      = Modifier.fillMaxWidth(),
                    colors        = groupTextFieldColors()
                )

                FormLabel(text = "Descripción", labelSp = labelSp)
                OutlinedTextField(
                    value         = uiState.description,
                    onValueChange = onDescriptionChange,
                    placeholder   = { Text("Describe el objetivo del grupo…", color = TextoSecundario) },
                    minLines      = 3,
                    maxLines      = 5,
                    shape         = RoundedCornerShape(12.dp),
                    modifier      = Modifier.fillMaxWidth(),
                    colors        = groupTextFieldColors()
                )


                FormLabel(text = "Materia principal", labelSp = labelSp)
                ExposedDropdownMenuBox(
                    expanded        = subjectExpanded,
                    onExpandedChange = { subjectExpanded = it }
                ) {
                    OutlinedTextField(
                        value         = uiState.subject,
                        onValueChange = {},
                        readOnly      = true,
                        trailingIcon  = { ExposedDropdownMenuDefaults.TrailingIcon(subjectExpanded) },
                        shape         = RoundedCornerShape(12.dp),
                        modifier      = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        colors        = groupTextFieldColors()
                    )
                    ExposedDropdownMenu(
                        expanded        = subjectExpanded,
                        onDismissRequest = { subjectExpanded = false }
                    ) {
                        SUBJECTS.forEach { subject ->
                            DropdownMenuItem(
                                text    = { Text(subject) },
                                onClick = {
                                    onSubjectChange(subject)
                                    subjectExpanded = false
                                }
                            )
                        }
                    }
                }

                FormLabel(text = "Máximo de miembros", labelSp = labelSp)
                OutlinedTextField(
                    value         = uiState.maxMembers.toString(),
                    onValueChange = { raw ->
                        raw.toIntOrNull()?.let { onMaxMembersChange(it) }
                    },
                    singleLine    = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape         = RoundedCornerShape(12.dp),
                    modifier      = Modifier.fillMaxWidth(),
                    colors        = groupTextFieldColors()
                )
                Text(
                    "Entre 5 y 100 miembros",
                    fontSize = (labelSp - 1f).sp,
                    color    = TextoSecundario
                )

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = AzulPrimario.copy(alpha = 0.08f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text("🏆", fontSize = 18.sp)
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text(
                                "Recompensas del grupo",
                                fontSize   = labelSp.sp,
                                fontWeight = FontWeight.SemiBold,
                                color      = TextoPrincipal
                            )
                            Text(
                                "Los miembros ganarán monedas y XP adicional al completar retos grupales",
                                fontSize = (labelSp - 1f).sp,
                                color    = TextoSecundario
                            )
                        }
                    }
                }

                Button(
                    onClick  = onCreateGroup,
                    enabled  = !uiState.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(botonAlto)
                        .padding(bottom = espacioN),
                    shape  = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AzulPrimario)
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            color       = Color.White,
                            modifier    = Modifier.size(22.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            "Crear grupo",
                            fontSize   = bodySp.sp,
                            fontWeight = FontWeight.Bold,
                            color      = Color.White
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun FormLabel(text: String, labelSp: Float) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text,
            fontSize   = labelSp.sp,
            fontWeight = FontWeight.SemiBold,
            color      = TextoPrincipal
        )
    }
}

@Composable
private fun groupTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor   = AzulPrimario,
    unfocusedBorderColor = TextoSecundario.copy(alpha = 0.3f),
    focusedTextColor     = TextoPrincipal,
    unfocusedTextColor   = TextoPrincipal,
    cursorColor          = AzulPrimario
)

