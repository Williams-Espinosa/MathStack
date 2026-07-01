package com.williamsel.mathstack.features.auth.register.presentacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.williamsel.mathstack.features.auth.register.presentacion.viewmodels.RegisterViewModel
import com.williamsel.mathstack.ui.theme.*

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToTerms: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val anchoTotal = this.maxWidth
        val altoTotal  = this.maxHeight

        val headerWeight    = if (altoTotal < 680.dp) 0.28f else 0.32f
        val hPad            = (anchoTotal * 0.06f).coerceIn(16.dp, 40.dp)
        val espacioNormal   = (altoTotal  * 0.022f).coerceIn(10.dp, 28.dp)
        val espacioChico    = (altoTotal  * 0.013f).coerceIn(6.dp,  16.dp)
        val botonAlto       = (altoTotal  * 0.07f).coerceIn(48.dp, 58.dp)
        val tituloSp        = (anchoTotal.value * 0.072f).coerceIn(22f, 34f)
        val subtituloSp     = (anchoTotal.value * 0.038f).coerceIn(13f, 17f)
        val cuerpoSp        = (anchoTotal.value * 0.034f).coerceIn(12f, 16f)
        val cornerHeader    = (anchoTotal * 0.12f).coerceIn(32.dp, 56.dp)

        Column(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(headerWeight)
                    .background(
                        color = AzulPrimario,
                        shape = RoundedCornerShape(
                            bottomStart = cornerHeader,
                            bottomEnd   = cornerHeader
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text       = "Crea tu cuenta",
                        color      = Color.White,
                        fontSize   = tituloSp.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign  = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text     = "Comienza tu viaje en matemáticas",
                        color    = Color.White.copy(alpha = 0.85f),
                        fontSize = subtituloSp.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f - headerWeight)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = hPad)
            ) {
                Spacer(modifier = Modifier.height(espacioNormal))

                if (uiState.errorMessage != null) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text     = uiState.errorMessage!!,
                            color    = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.padding(12.dp),
                            fontSize = cuerpoSp.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(espacioChico))
                }

                Text(
                    "Nombre de usuario",
                    color      = TextoPrincipal,
                    fontWeight = FontWeight.Medium,
                    fontSize   = cuerpoSp.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value         = uiState.username,
                    onValueChange = viewModel::onUsernameChange,
                    modifier      = Modifier.fillMaxWidth(),
                    placeholder   = { Text("usuario123", fontSize = cuerpoSp.sp) },
                    leadingIcon   = { Icon(Icons.Outlined.Person, contentDescription = null) },
                    shape         = RoundedCornerShape(12.dp),
                    singleLine    = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                Spacer(modifier = Modifier.height(espacioChico))

                Text(
                    "Correo electrónico",
                    color      = TextoPrincipal,
                    fontWeight = FontWeight.Medium,
                    fontSize   = cuerpoSp.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value         = uiState.email,
                    onValueChange = viewModel::onEmailChange,
                    modifier      = Modifier.fillMaxWidth(),
                    placeholder   = { Text("tu@email.com", fontSize = cuerpoSp.sp) },
                    leadingIcon   = { Icon(Icons.Outlined.Email, contentDescription = null) },
                    shape         = RoundedCornerShape(12.dp),
                    singleLine    = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                Spacer(modifier = Modifier.height(espacioChico))

                Text(
                    "Contraseña",
                    color      = TextoPrincipal,
                    fontWeight = FontWeight.Medium,
                    fontSize   = cuerpoSp.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value         = uiState.password,
                    onValueChange = viewModel::onPasswordChange,
                    modifier      = Modifier.fillMaxWidth(),
                    placeholder   = { Text("••••••••", fontSize = cuerpoSp.sp) },
                    leadingIcon   = { Icon(Icons.Outlined.Lock, contentDescription = null) },
                    trailingIcon  = {
                        IconButton(onClick = viewModel::onTogglePasswordVisibility) {
                            Icon(
                                imageVector = if (uiState.isPasswordVisible)
                                    Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                contentDescription = if (uiState.isPasswordVisible)
                                    "Ocultar contraseña" else "Mostrar contraseña"
                            )
                        }
                    },
                    shape               = RoundedCornerShape(12.dp),
                    singleLine          = true,
                    visualTransformation = if (uiState.isPasswordVisible)
                        VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                Spacer(modifier = Modifier.height(espacioChico))

                Text(
                    "Confirmar contraseña",
                    color      = TextoPrincipal,
                    fontWeight = FontWeight.Medium,
                    fontSize   = cuerpoSp.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value         = uiState.confirmPassword,
                    onValueChange = viewModel::onConfirmPasswordChange,
                    modifier      = Modifier.fillMaxWidth(),
                    placeholder   = { Text("••••••••", fontSize = cuerpoSp.sp) },
                    leadingIcon   = { Icon(Icons.Outlined.Lock, contentDescription = null) },
                    trailingIcon  = {
                        IconButton(onClick = viewModel::onToggleConfirmPasswordVisibility) {
                            Icon(
                                imageVector = if (uiState.isConfirmPasswordVisible)
                                    Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                contentDescription = if (uiState.isConfirmPasswordVisible)
                                    "Ocultar" else "Mostrar"
                            )
                        }
                    },
                    shape               = RoundedCornerShape(12.dp),
                    singleLine          = true,
                    visualTransformation = if (uiState.isConfirmPasswordVisible)
                        VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    isError = (uiState.confirmPassword.isNotEmpty()
                            && uiState.password != uiState.confirmPassword)
                )

                Spacer(modifier = Modifier.height(espacioChico))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier          = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked         = uiState.acceptedTerms,
                        onCheckedChange = viewModel::onAcceptedTermsChange,
                        colors = CheckboxDefaults.colors(
                            checkedColor = AzulPrimario
                        )
                    )
                    Text(
                        text = buildAnnotatedString {
                            append("Acepto los ")
                            withStyle(
                                SpanStyle(
                                    color      = AzulPrimario,
                                    fontWeight = FontWeight.SemiBold
                                )
                            ) {
                                append("Términos y Condiciones")
                            }
                        },
                        fontSize = cuerpoSp.sp,
                        color    = TextoSecundario,
                        modifier = Modifier.clickable { onNavigateToTerms() }
                    )
                }

                Spacer(modifier = Modifier.height(espacioNormal))

                Button(
                    onClick  = { viewModel.onRegisterClick(onRegisterSuccess) },
                    enabled  = uiState.isFormValid,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(botonAlto),
                    shape  = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor         = AzulPrimario,
                        disabledContainerColor = AzulPrimario.copy(alpha = 0.4f)
                    )
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            color       = Color.White,
                            modifier    = Modifier.size(22.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            "Crear cuenta",
                            fontSize   = cuerpoSp.sp,
                            fontWeight = FontWeight.Bold,
                            color      = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(espacioNormal))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier          = Modifier.fillMaxWidth()
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f))
                    Text(
                        text     = "  o continúa con  ",
                        color    = TextoSecundario,
                        fontSize = (cuerpoSp - 1f).sp
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(espacioNormal))

                OutlinedButton(
                    onClick  = { viewModel.onGoogleRegisterClick(onRegisterSuccess) },
                    enabled  = !uiState.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(botonAlto),
                    shape  = RoundedCornerShape(16.dp),
                    border = ButtonDefaults.outlinedButtonBorder(enabled = !uiState.isLoading)
                ) {
                    Icon(
                        imageVector  = Icons.Outlined.AccountCircle,
                        contentDescription = "Google",
                        tint         = AzulPrimario,
                        modifier     = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Continuar con Google",
                        color    = TextoPrincipal,
                        fontSize = cuerpoSp.sp
                    )
                }

                Spacer(modifier = Modifier.height(espacioNormal))

                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Text(
                        "¿Ya tienes cuenta? ",
                        color    = TextoSecundario,
                        fontSize = cuerpoSp.sp
                    )
                    TextButton(onClick = onNavigateToLogin) {
                        Text(
                            "Iniciar sesión",
                            color      = AzulPrimario,
                            fontWeight = FontWeight.Bold,
                            fontSize   = cuerpoSp.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(espacioChico))
            }
        }
    }
}
