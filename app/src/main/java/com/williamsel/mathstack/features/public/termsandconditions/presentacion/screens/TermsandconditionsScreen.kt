package com.williamsel.mathstack.features.auth.termsandconditions.presentacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.williamsel.mathstack.features.auth.termsandconditions.presentacion.viewmodels.TermsandconditionsViewModel
import com.williamsel.mathstack.ui.theme.*

@Composable
fun TermsandconditionsScreen(
    onBack: () -> Unit,
    onContactClick: () -> Unit = {},
    viewModel: TermsandconditionsViewModel = hiltViewModel()
) {
    TermsandconditionsContent(
        onBack = onBack,
        onContactClick = onContactClick
    )
}

@Composable
private fun TermsandconditionsContent(
    onBack: () -> Unit,
    onContactClick: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoPantalla)
    ) {
        val anchoTotal = this.maxWidth
        val altoTotal  = this.maxHeight

        val hPad        = (anchoTotal * 0.06f).coerceIn(16.dp, 40.dp)
        val espacioN    = (altoTotal  * 0.022f).coerceIn(10.dp, 24.dp)
        val espacioC    = (altoTotal  * 0.013f).coerceIn(6.dp,  16.dp)
        val tituloSp    = (anchoTotal.value * 0.072f).coerceIn(22f, 32f)
        val subtituloSp = (anchoTotal.value * 0.034f).coerceIn(12f, 15f)
        val seccionSp   = (anchoTotal.value * 0.046f).coerceIn(15f, 20f)
        val cuerpoSp    = (anchoTotal.value * 0.036f).coerceIn(13f, 16f)
        val cornerH     = (anchoTotal * 0.1f).coerceIn(28.dp, 48.dp)
        val iconBoxSize = (anchoTotal * 0.13f).coerceIn(44.dp, 60.dp)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        AzulPrimario,
                        RoundedCornerShape(bottomStart = cornerH, bottomEnd = cornerH)
                    )
                    .padding(horizontal = hPad)
                    .padding(top = espacioN, bottom = espacioN * 1.5f)
            ) {
                Column {
                    IconButton(
                        onClick  = onBack,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.2f))
                    ) {
                        Icon(
                            Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }

                    Spacer(Modifier.height(espacioC))

                    Row(verticalAlignment = Alignment.Top) {
                        Surface(
                            modifier = Modifier.size(iconBoxSize),
                            color    = Color.White.copy(alpha = 0.2f),
                            shape    = RoundedCornerShape(14.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Outlined.Description,
                                    contentDescription = null,
                                    tint     = Color.White,
                                    modifier = Modifier.size(iconBoxSize * 0.5f)
                                )
                            }
                        }
                        Spacer(Modifier.width(12.dp))
                        Text(
                            "Términos y\nCondiciones",
                            color      = Color.White,
                            fontSize   = tituloSp.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = (tituloSp * 1.15f).sp
                        )
                    }

                    Spacer(Modifier.height(espacioC))

                    Text(
                        text     = "Última actualización: Junio 2026",
                        color    = Color.White.copy(alpha = 0.8f),
                        fontSize = subtituloSp.sp
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = hPad)
            ) {
                Spacer(Modifier.height(espacioN))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape    = RoundedCornerShape(16.dp),
                    colors   = CardDefaults.cardColors(
                        containerColor = AzulPrimario.copy(alpha = 0.10f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            Icons.Outlined.CheckCircle,
                            contentDescription = null,
                            tint     = AzulPrimario,
                            modifier = Modifier.size((cuerpoSp + 6f).dp)
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            "Al usar MathStack, aceptas cumplir con estos términos y condiciones. Lee cuidadosamente.",
                            fontSize   = cuerpoSp.sp,
                            color      = TextoSecundario,
                            lineHeight = (cuerpoSp * 1.5f).sp
                        )
                    }
                }

                Spacer(Modifier.height(espacioN))

                val secciones = listOf(
                    "1. Aceptación de los términos" to
                            "Al usar MathStack, aceptas estos términos y condiciones. Si no estás de acuerdo, no utilices la aplicación.",
                    "2. Responsabilidades del usuario" to
                            "Eres responsable de mantener la confidencialidad de tu cuenta y contraseña. Debes notificarnos inmediatamente cualquier uso no autorizado.",
                    "3. Actividades prohibidas" to
                            "No puedes usar MathStack para: compartir contenido inapropiado, hacer trampa en los ejercicios, intentar hackear el sistema, o vender tu cuenta.",
                    "4. Retos y grupos" to
                            "Los retos grupales deben ser educativos y respetuosos. Nos reservamos el derecho de eliminar grupos o retos que violen estas normas.",
                    "5. Recompensas virtuales" to
                            "Las monedas, XP y otros elementos virtuales no tienen valor monetario real y no son transferibles fuera de la aplicación.",
                    "6. Terminación de cuenta" to
                            "Podemos suspender o terminar tu cuenta si violas estos términos. Puedes eliminar tu cuenta en cualquier momento desde la configuración.",
                    "7. Modificaciones" to
                            "Nos reservamos el derecho de modificar estos términos en cualquier momento. Te notificaremos de cambios importantes.",
                    "8. Propiedad intelectual" to
                            "Todo el contenido de MathStack, incluyendo lecciones, ejercicios y diseño, está protegido por derechos de autor."
                )

                secciones.forEachIndexed { index, (titulo, cuerpo) ->
                    Card(
                        modifier  = Modifier.fillMaxWidth(),
                        shape     = RoundedCornerShape(16.dp),
                        colors    = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(1.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                titulo,
                                fontSize   = seccionSp.sp,
                                fontWeight = FontWeight.Bold,
                                color      = TextoPrincipal
                            )
                            Spacer(Modifier.height(6.dp))
                            Text(
                                cuerpo,
                                fontSize   = cuerpoSp.sp,
                                color      = TextoSecundario,
                                lineHeight = (cuerpoSp * 1.5f).sp
                            )
                        }
                    }
                    if (index != secciones.lastIndex) {
                        Spacer(Modifier.height(espacioC))
                    }
                }

                Spacer(Modifier.height(espacioN))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape    = RoundedCornerShape(16.dp),
                    colors   = CardDefaults.cardColors(
                        containerColor = AzulPrimario.copy(alpha = 0.08f)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "¿Preguntas sobre los términos?",
                            fontSize  = cuerpoSp.sp,
                            color     = TextoSecundario,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(4.dp))
                        TextButton(onClick = onContactClick) {
                            Text(
                                "Contáctanos",
                                color      = AzulPrimario,
                                fontWeight = FontWeight.Bold,
                                fontSize   = cuerpoSp.sp
                            )
                        }
                    }
                }

                Spacer(Modifier.height(espacioN))
            }
        }
    }
}
