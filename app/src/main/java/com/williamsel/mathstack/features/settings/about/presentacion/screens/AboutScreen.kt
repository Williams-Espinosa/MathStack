package com.williamsel.mathstack.features.settings.about.presentacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.williamsel.mathstack.features.settings.about.presentacion.viewmodels.AboutViewModel
import com.williamsel.mathstack.ui.theme.*

@Composable
fun AboutScreen(
    onBack: () -> Unit,
    viewModel: AboutViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AboutContent(
        uiState = uiState,
        onBack = onBack,
        onTabSelected = viewModel::onTabSelected
    )
}

@Composable
private fun AboutContent(
    uiState: AboutUiState,
    onBack: () -> Unit,
    onTabSelected: (AboutTab) -> Unit
) {
    val hPad = 20.dp
    val tituloSp = 24f
    val subSp = 14f
    val bodySp = 16f
    val logoSize = 110.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoPantalla)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(AzulPrimario, AzulPrimario.copy(alpha = 0.85f))
                    ),
                    RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                )
                .padding(bottom = 32.dp)
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp)
            ) {
                Icon(
                    Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(logoSize)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        modifier = Modifier.size(logoSize * 0.7f),
                        shape = CircleShape,
                        color = Color.White
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Row(verticalAlignment = Alignment.Bottom) {
                                Box(Modifier.size(10.dp, 20.dp).background(Color(0xFFFFC107)))
                                Spacer(Modifier.width(4.dp))
                                Box(Modifier.size(10.dp, 35.dp).background(Color(0xFF4CAF50)))
                                Spacer(Modifier.width(4.dp))
                                Box(Modifier.size(10.dp, 45.dp).background(Color.White))
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    "MathStack",
                    fontSize = tituloSp.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    "Versión 1.0.0",
                    fontSize = subSp.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
                Text(
                    "© 2026 MathStack",
                    fontSize = (subSp - 1f).sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }

        // Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = hPad)
                .padding(top = 20.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TabItem(
                text = "Información",
                selected = uiState.selectedTab == AboutTab.INFO,
                onClick = { onTabSelected(AboutTab.INFO) },
                modifier = Modifier.weight(1f)
            )
            TabItem(
                text = "Equipo",
                selected = uiState.selectedTab == AboutTab.TEAM,
                onClick = { onTabSelected(AboutTab.TEAM) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = hPad),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (uiState.selectedTab == AboutTab.INFO) {
                InfoSection()
            } else {
                TeamSection(bodySp, subSp)
            }

            if (uiState.selectedTab == AboutTab.INFO) {
                FeedbackCard(bodySp)
            }

            Spacer(Modifier.height(16.dp))
            
            Text(
                "MathStack - Todos los derechos reservados - 2026",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = TextoSecundario
            )
            
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun TabItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(44.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) AzulPrimario else Color.White,
            contentColor = if (selected) Color.White else TextoSecundario
        ),
        elevation = ButtonDefaults.buttonElevation(if (selected) 2.dp else 0.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(text, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
    }
}

@Composable
private fun InfoSection() {
    AboutCard(
        icon = Icons.Outlined.CenterFocusStrong,
        title = "Nuestra Misión",
        content = "Ayudar a estudiantes universitarios a mejorar sus habilidades matemáticas a través del aprendizaje adaptativo, la gamificación y una comunidad de apoyo."
    )
    
    AboutCard(
        icon = Icons.Outlined.FavoriteBorder,
        title = "Hecho con ❤️",
        content = "MathStack es desarrollado por un equipo apasionado de educadores y tecnólogos dedicados a hacer las matemáticas accesibles para todos."
    )
}

@Composable
private fun AboutCard(
    icon: ImageVector,
    title: String,
    content: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(AzulPrimario.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = AzulPrimario, modifier = Modifier.size(22.dp))
                }
                Spacer(Modifier.width(16.dp))
                Text(
                    title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoPrincipal
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                content,
                fontSize = 14.sp,
                color = TextoSecundario,
                lineHeight = 22.sp
            )
        }
    }
}

@Composable
private fun TeamSection(bodySp: Float, subSp: Float) {
    Text(
        "Nuestro Equipo",
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = TextoPrincipal,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    
    val team = listOf(
        TeamMember("Williams Espinosa Lopez", "Android Developer", "👨‍💻"),
        TeamMember("Alexis Garcia Rojas", "Lead Developer & UI/UX", "👨‍🏫"),
        TeamMember("Daniel Camacho Morales", "Backend Developer", "💻"),
        TeamMember("Alonso Guadalupe Hernandez Mendoza", "Asesor Academico", "👨‍🎓")
    )
    
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        team.chunked(2).forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                row.forEach { member ->
                    TeamMemberCard(member, bodySp, subSp, Modifier.weight(1f))
                }
                if (row.size == 1) {
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun TeamMemberCard(
    member: TeamMember,
    bodySp: Float,
    subSp: Float,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(160.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(FondoPantalla),
                contentAlignment = Alignment.Center
            ) {
                Text(member.emoji, fontSize = 28.sp)
            }
            Spacer(Modifier.height(12.dp))
            Text(
                member.name,
                fontSize = (bodySp - 1f).sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal,
                textAlign = TextAlign.Center,
                maxLines = 2,
                lineHeight = 16.sp
            )
            Spacer(Modifier.height(4.dp))
            Text(
                member.role,
                fontSize = (subSp - 1f).sp,
                color = TextoSecundario,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp
            )
        }
    }
}

@Composable
private fun FeedbackCard(bodySp: Float) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AzulPrimario),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Outlined.Code,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text(
                "¿Tienes feedback o sugerencias?",
                color = Color.White,
                fontSize = bodySp.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(20.dp))
            Button(
                onClick = { /* Contact Us Action */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(50),
                modifier = Modifier.height(44.dp).padding(horizontal = 16.dp)
            ) {
                Text("Contáctanos", color = AzulPrimario, fontWeight = FontWeight.Bold)
            }
        }
    }
}

private data class TeamMember(val name: String, val role: String, val emoji: String)
