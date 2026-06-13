package com.williamsel.mathstack.features.public.login.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.williamsel.mathstack.features.public.login.presentation.viewmodel.LoginViewModel
import com.williamsel.mathstack.ui.theme.*

/**
 * Pantalla de login: email/password + Google Sign-In.
 *
 * Responsividad con [BoxWithConstraints]:
 * - El header ocupa entre 35–45 % del alto según el dispositivo.
 * - Paddings y tamaños de fuente escalan con el ancho/alto reales.
 * - Funciona en teléfonos pequeños (360×640), normales (412×892) y tablets.
 *
 * Correcciones vs. versión original:
 * - Eliminada dependencia de android.R (íconos del sistema poco fiables).
 * - BoxShadowBorder reemplazada por BorderStroke estándar.
 * - Agregado toggle de visibilidad de contraseña.
 * - Botón Google conectado a ViewModel.
 */
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val altoTotal  = maxHeight
        val anchoTotal = maxWidth

        // ── Proporciones adaptativas ────────────────────────────────────────
        val headerWeight   = if (altoTotal < 680.dp) 0.35f else 0.40f
        val hPad           = (anchoTotal * 0.06f).coerceIn(16.dp, 40.dp)
        val espacioNormal  = (altoTotal  * 0.025f).coerceIn(12.dp, 32.dp)
        val espacioChico   = (altoTotal  * 0.015f).coerceIn(8.dp,  20.dp)
        val botonAlto      = (altoTotal  * 0.072f).coerceIn(48.dp, 60.dp)
        val tituloSp       = (anchoTotal.value * 0.07f).coerceIn(22f, 32f)
        val subtituloSp    = (anchoTotal.value * 0.038f).coerceIn(13f, 17f)
        val cuerpoSp       = (anchoTotal.value * 0.034f).coerceIn(12f, 16f)
        val logoSize       = (anchoTotal * 0.22f).coerceIn(72.dp, 120.dp)
        val cornerHeader   = (anchoTotal * 0.12f).coerceIn(32.dp, 56.dp)

        Column(modifier = Modifier.fillMaxSize()) {

            // ── HEADER azul ─────────────────────────────────────────────────
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
                    // Logo / ícono de la app
                    Surface(
                        modifier  = Modifier.size(logoSize),
                        color     = Color.White.copy(alpha = 0.2f),
                        shape     = RoundedCornerShape(logoSize * 0.24f)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Outlined.Calculate,
                                contentDescription = "Logo MathStack",
                                modifier = Modifier.size(logoSize * 0.5f),
                                tint = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(espacioChico))

                    Text(
                        text       = "Bienvenido de nuevo",
                        color      = Color.White,
                        fontSize   = tituloSp.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text     = "Continúa tu aprendizaje",
                        color    = Color.White.copy(alpha = 0.8f),
                        fontSize = subtituloSp.sp
                    )
                }
            }

            // ── FORMULARIO ──────────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f - headerWeight)
                    .padding(horizontal = hPad)
            ) {
                Spacer(modifier = Modifier.height(espacioNormal))

                // Email
                Text(
                    "Correo electrónico",
                    color      = TextoPrincipal,
                    fontWeight = FontWeight.Medium,
                    fontSize   = cuerpoSp.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value         = viewModel.email,
                    onValueChange = viewModel::onEmailChange,
                    modifier      = Modifier.fillMaxWidth(),
                    placeholder   = { Text("tu@email.com", fontSize = cuerpoSp.sp) },
                    leadingIcon   = { Icon(Icons.Outlined.Email, contentDescription = null) },
                    shape         = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine    = true
                )

                Spacer(modifier = Modifier.height(espacioChico))

                // Contraseña
                Text(
                    "Contraseña",
                    color      = TextoPrincipal,
                    fontWeight = FontWeight.Medium,
                    fontSize   = cuerpoSp.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value         = viewModel.password,
                    onValueChange = viewModel::onPasswordChange,
                    modifier      = Modifier.fillMaxWidth(),
                    placeholder   = { Text("••••••••", fontSize = cuerpoSp.sp) },
                    leadingIcon   = { Icon(Icons.Outlined.Lock, contentDescription = null) },
                    trailingIcon  = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                imageVector = if (isPasswordVisible)
                                    Icons.Outlined.Visibility
                                else
                                    Icons.Outlined.VisibilityOff,
                                contentDescription = if (isPasswordVisible)
                                    "Ocultar contraseña" else "Mostrar contraseña"
                            )
                        }
                    },
                    shape               = RoundedCornerShape(12.dp),
                    visualTransformation = if (isPasswordVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine      = true
                )

                // Olvidé contraseña
                TextButton(
                    onClick  = { /* TODO: navegar a recuperar contraseña */ },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("¿Olvidaste tu contraseña?", color = AzulPrimario, fontSize = cuerpoSp.sp)
                }

                // Botón Iniciar sesión
                Button(
                    onClick  = { viewModel.onLoginClick(onLoginSuccess) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(botonAlto),
                    shape  = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AzulPrimario),
                    enabled = !viewModel.isLoading
                ) {
                    if (viewModel.isLoading) {
                        CircularProgressIndicator(
                            color     = Color.White,
                            modifier  = Modifier.size(22.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            "Iniciar sesión",
                            fontSize   = cuerpoSp.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(espacioNormal))

                // Divisor "o continúa con"
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

                // Botón Google
                OutlinedButton(
                    onClick  = { viewModel.onGoogleLoginClick(onLoginSuccess) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(botonAlto),
                    shape  = RoundedCornerShape(16.dp),
                    border = ButtonDefaults.outlinedButtonBorder
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

                Spacer(modifier = Modifier.weight(1f))

                // Pie: crear cuenta + términos
                Column(
                    modifier              = Modifier
                        .fillMaxWidth()
                        .padding(bottom = espacioChico),
                    horizontalAlignment   = Alignment.CenterHorizontally
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "¿No tienes cuenta? ",
                            color    = TextoSecundario,
                            fontSize = cuerpoSp.sp
                        )
                        TextButton(onClick = onNavigateToRegister) {
                            Text(
                                "Crear cuenta",
                                color      = AzulPrimario,
                                fontWeight = FontWeight.Bold,
                                fontSize   = cuerpoSp.sp
                            )
                        }
                    }
                    Text(
                        text     = "Términos y Condiciones · Privacidad",
                        color    = TextoSecundario,
                        fontSize = (cuerpoSp - 2f).sp
                    )
                }
            }
        }
    }
}

