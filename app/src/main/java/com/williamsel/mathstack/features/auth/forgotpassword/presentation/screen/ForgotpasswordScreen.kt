package com.williamsel.mathstack.features.auth.forgotpassword.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.williamsel.mathstack.features.auth.forgotpassword.presentation.viewmodel.ForgotpasswordViewModel
import com.williamsel.mathstack.ui.theme.*

@Composable
fun ForgotpasswordScreen(
    onBack: () -> Unit,
    onGoToLogin: () -> Unit,
    viewModel: ForgotpasswordViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState.step) {
        ForgotPasswordStep.ENTER_EMAIL -> EnterEmailContent(
            uiState  = uiState,
            onBack   = onBack,
            onEmailChange = viewModel::onEmailChange,
            onSendCode    = viewModel::onSendCode
        )
        ForgotPasswordStep.ENTER_CODE -> EnterCodeContent(
            uiState        = uiState,
            onBack         = { viewModel.onErrorDismissed() /* vuelve al paso 1 vía VM */ },
            onDigitChange  = viewModel::onDigitChange,
            onVerifyCode   = viewModel::onVerifyCode,
            onResendCode   = viewModel::onResendCode
        )
        ForgotPasswordStep.SUCCESS -> SuccessContent(
            email       = uiState.email,
            onGoToLogin = onGoToLogin
        )
    }
}

@Composable
private fun EnterEmailContent(
    uiState: ForgotpasswordUiState,
    onBack: () -> Unit,
    onEmailChange: (String) -> Unit,
    onSendCode: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoPantalla)
    ) {
        val anchoTotal  = this.maxWidth
        val altoTotal   = this.maxHeight
        val hPad        = (anchoTotal * 0.06f).coerceIn(16.dp, 40.dp)
        val espacioN    = (altoTotal  * 0.025f).coerceIn(12.dp, 28.dp)
        val espacioC    = (altoTotal  * 0.015f).coerceIn(8.dp,  18.dp)
        val botonAlto   = (altoTotal  * 0.07f).coerceIn(48.dp, 58.dp)
        val tituloSp    = (anchoTotal.value * 0.062f).coerceIn(20f, 30f)
        val subtituloSp = (anchoTotal.value * 0.036f).coerceIn(13f, 17f)
        val cuerpoSp    = (anchoTotal.value * 0.034f).coerceIn(12f, 16f)
        val cornerH     = (anchoTotal * 0.1f).coerceIn(28.dp, 48.dp)
        val headerW     = if (altoTotal < 680.dp) 0.30f else 0.35f

        Column(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(headerW)
                    .background(
                        AzulPrimario,
                        RoundedCornerShape(bottomStart = cornerH, bottomEnd = cornerH)
                    )
            ) {
                IconButton(
                    onClick  = onBack,
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.TopStart)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                ) {
                    Icon(
                        Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White
                    )
                }

                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier.size((anchoTotal * 0.22f).coerceIn(72.dp, 110.dp)),
                        color    = Color.White.copy(alpha = 0.2f),
                        shape    = RoundedCornerShape(20.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Outlined.MailOutline,
                                contentDescription = null,
                                tint     = Color.White,
                                modifier = Modifier.size((anchoTotal * 0.11f).coerceIn(36.dp, 56.dp))
                            )
                        }
                    }
                    Spacer(Modifier.height(espacioC))
                    Text(
                        "¿Olvidaste tu contraseña?",
                        color      = Color.White,
                        fontSize   = tituloSp.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign  = TextAlign.Center
                    )
                    Text(
                        "Te enviaremos un código de verificación",
                        color    = Color.White.copy(alpha = 0.85f),
                        fontSize = subtituloSp.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f - headerW)
                    .padding(horizontal = hPad)
            ) {
                Spacer(Modifier.height(espacioN))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape    = RoundedCornerShape(16.dp),
                    colors   = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Text(
                        text      = "Ingresa el correo electrónico asociado a tu cuenta y te enviaremos un código para restablecer tu contraseña.",
                        modifier  = Modifier.padding(16.dp),
                        fontSize  = cuerpoSp.sp,
                        color     = TextoSecundario,
                        textAlign = TextAlign.Center,
                        lineHeight = (cuerpoSp * 1.5f).sp
                    )
                }

                Spacer(Modifier.height(espacioN))

                if (uiState.errorMessage != null) {
                    Text(
                        uiState.errorMessage!!,
                        color    = MaterialTheme.colorScheme.error,
                        fontSize = cuerpoSp.sp
                    )
                    Spacer(Modifier.height(espacioC))
                }

                Text(
                    "Correo electrónico",
                    color      = TextoPrincipal,
                    fontWeight = FontWeight.Medium,
                    fontSize   = cuerpoSp.sp
                )
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(
                    value         = uiState.email,
                    onValueChange = onEmailChange,
                    modifier      = Modifier.fillMaxWidth(),
                    placeholder   = { Text("tu@email.com", fontSize = cuerpoSp.sp) },
                    leadingIcon   = { Icon(Icons.Outlined.Email, contentDescription = null) },
                    shape         = RoundedCornerShape(12.dp),
                    singleLine    = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                Spacer(Modifier.height(espacioN))

                Button(
                    onClick  = onSendCode,
                    enabled  = uiState.isEmailValid && !uiState.isLoading,
                    modifier = Modifier.fillMaxWidth().height(botonAlto),
                    shape    = RoundedCornerShape(16.dp),
                    colors   = ButtonDefaults.buttonColors(
                        containerColor         = AzulPrimario,
                        disabledContainerColor = AzulPrimario.copy(alpha = 0.4f)
                    )
                ) {
                    if (uiState.isLoading)
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(22.dp), strokeWidth = 2.dp)
                    else
                        Text("Enviar código", fontSize = cuerpoSp.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }

                Spacer(Modifier.height(espacioC))

                TextButton(
                    onClick  = onBack,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Volver al inicio de sesión", color = TextoSecundario, fontSize = cuerpoSp.sp)
                }
            }
        }
    }
}

@Composable
private fun EnterCodeContent(
    uiState: ForgotpasswordUiState,
    onBack: () -> Unit,
    onDigitChange: (Int, String) -> Unit,
    onVerifyCode: () -> Unit,
    onResendCode: () -> Unit
) {
    val focusRequesters = remember { List(6) { FocusRequester() } }
    val focusManager    = LocalFocusManager.current

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoPantalla)
    ) {
        val anchoTotal  = this.maxWidth
        val altoTotal   = this.maxHeight
        val hPad        = (anchoTotal * 0.06f).coerceIn(16.dp, 40.dp)
        val espacioN    = (altoTotal  * 0.025f).coerceIn(12.dp, 28.dp)
        val espacioC    = (altoTotal  * 0.015f).coerceIn(8.dp,  18.dp)
        val botonAlto   = (altoTotal  * 0.07f).coerceIn(48.dp, 58.dp)
        val tituloSp    = (anchoTotal.value * 0.062f).coerceIn(20f, 30f)
        val subtituloSp = (anchoTotal.value * 0.036f).coerceIn(13f, 17f)
        val cuerpoSp    = (anchoTotal.value * 0.034f).coerceIn(12f, 16f)
        val cornerH     = (anchoTotal * 0.1f).coerceIn(28.dp, 48.dp)
        val headerW     = if (altoTotal < 680.dp) 0.28f else 0.33f
        val celdaSize   = ((anchoTotal - hPad * 2 - 10.dp * 5) / 6).coerceIn(40.dp, 56.dp)

        Column(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(headerW)
                    .background(
                        AzulPrimario,
                        RoundedCornerShape(bottomStart = cornerH, bottomEnd = cornerH)
                    )
            ) {
                IconButton(
                    onClick  = onBack,
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.TopStart)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                ) {
                    Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Volver", tint = Color.White)
                }

                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier.size((anchoTotal * 0.22f).coerceIn(72.dp, 110.dp)),
                        color    = Color.White.copy(alpha = 0.2f),
                        shape    = RoundedCornerShape(20.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Outlined.MailOutline,
                                contentDescription = null,
                                tint     = Color.White,
                                modifier = Modifier.size((anchoTotal * 0.11f).coerceIn(36.dp, 56.dp))
                            )
                        }
                    }
                    Spacer(Modifier.height(espacioC))
                    Text(
                        "Código enviado",
                        color      = Color.White,
                        fontSize   = tituloSp.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Revisa tu bandeja de entrada",
                        color    = Color.White.copy(alpha = 0.85f),
                        fontSize = subtituloSp.sp
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f - headerW)
                    .padding(horizontal = hPad),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(espacioN))

                Card(
                    modifier  = Modifier.fillMaxWidth(),
                    shape     = RoundedCornerShape(16.dp),
                    colors    = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("Enviamos un código de 6 dígitos a\n")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = TextoPrincipal)) {
                                append(uiState.email)
                            }
                        },
                        modifier  = Modifier.padding(16.dp).fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize  = cuerpoSp.sp,
                        color     = TextoSecundario,
                        lineHeight = (cuerpoSp * 1.5f).sp
                    )
                }

                Spacer(Modifier.height(espacioN))

                Text(
                    "Ingresa el código de verificación",
                    color    = TextoPrincipal,
                    fontSize = cuerpoSp.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(Modifier.height(espacioC))

                if (uiState.errorMessage != null) {
                    Text(
                        uiState.errorMessage!!,
                        color    = MaterialTheme.colorScheme.error,
                        fontSize = cuerpoSp.sp
                    )
                    Spacer(Modifier.height(espacioC))
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(6) { index ->
                        OutlinedTextField(
                            value         = uiState.codeDigits[index],
                            onValueChange = { value ->
                                val digit = value.filter { it.isDigit() }.takeLast(1)
                                onDigitChange(index, digit)
                                if (digit.isNotEmpty() && index < 5)
                                    focusRequesters[index + 1].requestFocus()
                                else if (digit.isEmpty() && index > 0)
                                    focusRequesters[index - 1].requestFocus()
                            },
                            modifier = Modifier
                                .size(celdaSize)
                                .focusRequester(focusRequesters[index]),
                            singleLine      = true,
                            textStyle       = LocalTextStyle.current.copy(
                                textAlign  = TextAlign.Center,
                                fontSize   = (cuerpoSp + 2f).sp,
                                fontWeight = FontWeight.Bold
                            ),
                            shape           = RoundedCornerShape(12.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            colors          = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor   = AzulPrimario,
                                unfocusedBorderColor = IndicadorInactivo
                            )
                        )
                    }
                }

                Spacer(Modifier.height(espacioN))

                Button(
                    onClick  = {
                        focusManager.clearFocus()
                        onVerifyCode()
                    },
                    enabled  = uiState.isCodeComplete && !uiState.isLoading,
                    modifier = Modifier.fillMaxWidth().height(botonAlto),
                    shape    = RoundedCornerShape(16.dp),
                    colors   = ButtonDefaults.buttonColors(
                        containerColor         = AzulPrimario,
                        disabledContainerColor = AzulPrimario.copy(alpha = 0.4f)
                    )
                ) {
                    if (uiState.isLoading)
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(22.dp), strokeWidth = 2.dp)
                    else
                        Text("Verificar código", fontSize = cuerpoSp.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }

                Spacer(Modifier.height(espacioC))

                TextButton(
                    onClick  = onResendCode,
                    enabled  = uiState.canResend
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("Reenviar código en ")
                            withStyle(SpanStyle(color = AzulPrimario, fontWeight = FontWeight.Bold)) {
                                if (uiState.canResend) append("Reenviar")
                                else append("${uiState.resendCountdown}s")
                            }
                        },
                        fontSize = cuerpoSp.sp,
                        color    = TextoSecundario
                    )
                }
            }
        }
    }
}

@Composable
private fun SuccessContent(
    email: String,
    onGoToLogin: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoPantalla)
    ) {
        val anchoTotal  = this.maxWidth
        val altoTotal   = this.maxHeight
        val hPad        = (anchoTotal * 0.06f).coerceIn(16.dp, 40.dp)
        val espacioN    = (altoTotal  * 0.025f).coerceIn(12.dp, 28.dp)
        val espacioC    = (altoTotal  * 0.015f).coerceIn(8.dp,  18.dp)
        val botonAlto   = (altoTotal  * 0.07f).coerceIn(48.dp, 58.dp)
        val tituloSp    = (anchoTotal.value * 0.062f).coerceIn(20f, 30f)
        val subtituloSp = (anchoTotal.value * 0.036f).coerceIn(13f, 17f)
        val cuerpoSp    = (anchoTotal.value * 0.034f).coerceIn(12f, 16f)
        val cornerH     = (anchoTotal * 0.1f).coerceIn(28.dp, 48.dp)
        val headerW     = if (altoTotal < 680.dp) 0.25f else 0.28f
        val circuloSize = (anchoTotal * 0.24f).coerceIn(80.dp, 120.dp)

        Column(modifier = Modifier.fillMaxSize()) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(headerW)
                    .background(
                        AzulPrimario,
                        RoundedCornerShape(bottomStart = cornerH, bottomEnd = cornerH)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Surface(
                        modifier = Modifier.size((anchoTotal * 0.22f).coerceIn(72.dp, 110.dp)),
                        color    = Color.White.copy(alpha = 0.2f),
                        shape    = RoundedCornerShape(20.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Outlined.CheckCircle,
                                contentDescription = null,
                                tint     = Color.White,
                                modifier = Modifier.size((anchoTotal * 0.11f).coerceIn(36.dp, 56.dp))
                            )
                        }
                    }
                    Spacer(Modifier.height(espacioC))
                    Text(
                        "¡Listo!",
                        color      = Color.White,
                        fontSize   = tituloSp.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Tu contraseña ha sido restablecida",
                        color    = Color.White.copy(alpha = 0.85f),
                        fontSize = subtituloSp.sp
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f - headerW)
                    .padding(horizontal = hPad),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(espacioN))

                Box(
                    modifier = Modifier
                        .size(circuloSize)
                        .clip(CircleShape)
                        .background(VerdePrimario),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Check,
                        contentDescription = null,
                        tint     = Color.White,
                        modifier = Modifier.size(circuloSize * 0.45f)
                    )
                }

                Spacer(Modifier.height(espacioN))

                Card(
                    modifier  = Modifier.fillMaxWidth(),
                    shape     = RoundedCornerShape(16.dp),
                    colors    = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Contraseña restablecida\nexitosamente",
                            fontWeight = FontWeight.Bold,
                            fontSize   = (cuerpoSp + 2f).sp,
                            color      = TextoPrincipal,
                            textAlign  = TextAlign.Center,
                            lineHeight = ((cuerpoSp + 2f) * 1.3f).sp
                        )
                        Spacer(Modifier.height(espacioC))
                        Text(
                            text = buildAnnotatedString {
                                append("Tu nueva contraseña ha sido enviada a ")
                                withStyle(SpanStyle(fontWeight = FontWeight.SemiBold, color = TextoPrincipal)) {
                                    append(email)
                                }
                                append(". Ya puedes iniciar sesión con ella.")
                            },
                            fontSize  = cuerpoSp.sp,
                            color     = TextoSecundario,
                            textAlign = TextAlign.Center,
                            lineHeight = (cuerpoSp * 1.5f).sp
                        )
                    }
                }

                Spacer(Modifier.weight(1f))

                Button(
                    onClick  = onGoToLogin,
                    modifier = Modifier.fillMaxWidth().height(botonAlto),
                    shape    = RoundedCornerShape(16.dp),
                    colors   = ButtonDefaults.buttonColors(containerColor = AzulPrimario)
                ) {
                    Text(
                        "Ir a iniciar sesión",
                        fontSize   = cuerpoSp.sp,
                        fontWeight = FontWeight.Bold,
                        color      = Color.White
                    )
                }

                Spacer(Modifier.height(espacioC))

                Text(
                    "¿Necesitas ayuda? Contáctanos en\nsoporte@mathstack.app",
                    fontSize  = (cuerpoSp - 1f).sp,
                    color     = TextoSecundario,
                    textAlign = TextAlign.Center,
                    lineHeight = ((cuerpoSp - 1f) * 1.5f).sp
                )

                Spacer(Modifier.height(espacioN))
            }
        }
    }
}
