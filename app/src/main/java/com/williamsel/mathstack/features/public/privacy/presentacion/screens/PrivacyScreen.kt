package com.williamsel.mathstack.features.public.privacy.presentacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.williamsel.mathstack.features.public.privacy.presentacion.viewmodels.PrivacyViewModel
import com.williamsel.mathstack.ui.theme.*

@Composable
fun PrivacyScreen(
    onBack: () -> Unit,
    viewModel: PrivacyViewModel = hiltViewModel()
) {
    PrivacyContent(onBack = onBack)
}
private data class StaticSection(
    val icon: ImageVector,
    val title: String,
    val body: String
)

@Composable
private fun PrivacyContent(onBack: () -> Unit) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoPantalla)
    ) {
        val anchoTotal = maxWidth
        val altoTotal  = maxHeight

        val hPad        = (anchoTotal * 0.06f).coerceIn(16.dp, 40.dp)
        val espacioN    = (altoTotal  * 0.022f).coerceIn(10.dp, 24.dp)
        val espacioC    = (altoTotal  * 0.013f).coerceIn(6.dp,  16.dp)
        val tituloSp    = (anchoTotal.value * 0.072f).coerceIn(22f, 32f)
        val subtituloSp = (anchoTotal.value * 0.034f).coerceIn(12f, 15f)
        val seccionSp   = (anchoTotal.value * 0.046f).coerceIn(15f, 20f)
        val cuerpoSp    = (anchoTotal.value * 0.036f).coerceIn(13f, 16f)
        val cornerH     = (anchoTotal * 0.1f).coerceIn(28.dp, 48.dp)
        val headerIconBox  = (anchoTotal * 0.13f).coerceIn(44.dp, 60.dp)
        val sectionIconBox = (anchoTotal * 0.105f).coerceIn(36.dp, 48.dp)

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
                            modifier = Modifier.size(headerIconBox),
                            color    = Color.White.copy(alpha = 0.2f),
                            shape    = RoundedCornerShape(14.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Outlined.Shield,
                                    contentDescription = null,
                                    tint     = Color.White,
                                    modifier = Modifier.size(headerIconBox * 0.5f)
                                )
                            }
                        }
                        Spacer(Modifier.width(12.dp))
                        Text(
                            "Política de\nPrivacidad",
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

                val secciones = listOf(
                    StaticSection(
                        icon  = Icons.Outlined.Storage,
                        title = "Información que recopilamos",
                        body  = "Recopilamos información personal como nombre, correo electrónico y datos de uso de la aplicación para mejorar tu experiencia de aprendizaje."
                    ),
                    StaticSection(
                        icon  = Icons.Outlined.Shield,
                        title = "Datos de autenticación",
                        body  = "Utilizamos Firebase Auth para gestionar de forma segura tus credenciales. Tus contraseñas están encriptadas y nunca se almacenan en texto plano."
                    ),
                    StaticSection(
                        icon  = Icons.Outlined.Visibility,
                        title = "Análisis de uso",
                        body  = "Recopilamos datos anónimos sobre cómo usas MathStack para mejorar la experiencia y personalizar tu ruta de aprendizaje."
                    ),
                    StaticSection(
                        icon  = Icons.Outlined.Notifications,
                        title = "Notificaciones",
                        body  = "Enviamos notificaciones para recordarte tus sesiones de estudio y nuevos retos. Puedes desactivarlas en cualquier momento."
                    ),
                    StaticSection(
                        icon  = Icons.Outlined.MailOutline,
                        title = "Comunicaciones por correo",
                        body  = "Podemos enviarte correos relacionados con tu cuenta, logros y actualizaciones importantes de la aplicación."
                    ),
                    StaticSection(
                        icon  = Icons.Outlined.Lock,
                        title = "Tus derechos",
                        body  = "Tienes derecho a acceder, modificar o eliminar tus datos personales en cualquier momento desde la configuración de tu cuenta."
                    )
                )

                secciones.forEachIndexed { index, section ->
                    PrivacySectionCard(
                        section     = section,
                        seccionSp   = seccionSp,
                        cuerpoSp    = cuerpoSp,
                        iconBoxSize = sectionIconBox
                    )
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
                    Text(
                        text = buildAnnotatedString {
                            append("Si tienes preguntas sobre nuestra política de privacidad, contáctanos en\n")
                            withStyle(SpanStyle(color = AzulPrimario, fontWeight = FontWeight.SemiBold)) {
                                append("privacy@mathstack.com")
                            }
                        },
                        modifier   = Modifier.padding(20.dp).fillMaxWidth(),
                        fontSize   = cuerpoSp.sp,
                        color      = TextoSecundario,
                        textAlign  = TextAlign.Center,
                        lineHeight = (cuerpoSp * 1.5f).sp
                    )
                }

                Spacer(Modifier.height(espacioN))
            }
        }
    }
}

@Composable
private fun PrivacySectionCard(
    section: StaticSection,
    seccionSp: Float,
    cuerpoSp: Float,
    iconBoxSize: Dp
) {
    Card(
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Surface(
                modifier = Modifier.size(iconBoxSize),
                color    = AzulPrimario.copy(alpha = 0.12f),
                shape    = RoundedCornerShape(12.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = section.icon,
                        contentDescription = null,
                        tint     = AzulPrimario,
                        modifier = Modifier.size(iconBoxSize * 0.5f)
                    )
                }
            }
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    section.title,
                    fontSize   = seccionSp.sp,
                    fontWeight = FontWeight.Bold,
                    color      = TextoPrincipal
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    section.body,
                    fontSize   = cuerpoSp.sp,
                    color      = TextoSecundario,
                    lineHeight = (cuerpoSp * 1.5f).sp
                )
            }
        }
    }
}
