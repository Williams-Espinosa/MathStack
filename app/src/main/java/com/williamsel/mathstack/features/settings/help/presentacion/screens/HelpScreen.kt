package com.williamsel.mathstack.features.settings.help.presentacion.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.williamsel.mathstack.features.settings.help.presentacion.viewmodels.HelpViewModel
import com.williamsel.mathstack.ui.theme.*

private val HPad = 20.dp
private val TituloSp = 28f
private val SubSp = 14f

@Composable
fun HelpScreen(
    onBack: () -> Unit,
    viewModel: HelpViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HelpContent(
        uiState = uiState,
        onBack = onBack,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onToggleExpand = viewModel::onToggleExpand
    )
}

@Composable
private fun HelpContent(
    uiState: HelpUiState,
    onBack: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onToggleExpand: (String, String) -> Unit
) {
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
                    .padding(horizontal = HPad)
                    .padding(top = 56.dp)
            ) {
                Text(
                    "Centro de Ayuda",
                    fontSize = TituloSp.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    "Encuentra respuestas a tus preguntas",
                    fontSize = SubSp.sp,
                    color = Color.White.copy(alpha = 0.85f)
                )
                
                Spacer(Modifier.height(24.dp))
                
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = onSearchQueryChange,
                    placeholder = { Text("Buscar en la ayuda...", color = Color.White.copy(alpha = 0.6f)) },
                    leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null, tint = Color.White.copy(alpha = 0.7f)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .background(Color.White.copy(alpha = 0.12f), RoundedCornerShape(50)),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White.copy(alpha = 0.4f),
                        unfocusedBorderColor = Color.Transparent,
                        cursorColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(50),
                    singleLine = true
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = HPad),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            uiState.categories.forEach { category ->
                FaqCategorySection(
                    category = category,
                    onToggleExpand = { onToggleExpand(category.title, it) }
                )
            }
            
            ContactSupportCard()
            
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
private fun FaqCategorySection(
    category: FaqCategory,
    onToggleExpand: (String) -> Unit
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Icon(
                category.icon,
                contentDescription = null,
                tint = AzulPrimario,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(10.dp))
            Text(
                category.title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )
        }
        
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            category.items.forEach { item ->
                FaqItemCard(item = item, onClick = { onToggleExpand(item.question) })
            }
        }
    }
}

@Composable
private fun FaqItemCard(
    item: FaqItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    item.question,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextoPrincipal,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    if (item.isExpanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
                    contentDescription = null,
                    tint = TextoSecundario,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            AnimatedVisibility(
                visible = item.isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column {
                    Spacer(Modifier.height(12.dp))
                    HorizontalDivider(color = FondoPantalla)
                    Spacer(Modifier.height(12.dp))
                    Text(
                        item.answer,
                        fontSize = 14.sp,
                        color = TextoSecundario,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun ContactSupportCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AzulPrimario),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                "¿No encontraste lo que buscabas?",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                lineHeight = 24.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Nuestro equipo de soporte está listo para ayudarte",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.85f)
            )
            Spacer(Modifier.height(20.dp))
            Button(
                onClick = { /* Contact Support Action */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(50),
                modifier = Modifier.height(44.dp).padding(horizontal = 4.dp)
            ) {
                Text(
                    "Contactar Soporte",
                    color = AzulPrimario,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}
