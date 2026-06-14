package com.williamsel.mathstack.features.private.store.presentacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.williamsel.mathstack.features.private.store.domain.entities.Avatar
import com.williamsel.mathstack.features.private.store.presentacion.viewmodels.StoreViewModel
import com.williamsel.mathstack.ui.theme.*

@Composable
fun StoreScreen(
    viewModel: StoreViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    StoreContent(
        uiState        = uiState,
        onPurchase     = viewModel::onPurchaseAvatar,
        onEquip        = viewModel::onEquipAvatar,
        onErrorShown   = viewModel::onErrorDismissed
    )
}

@Composable
private fun StoreContent(
    uiState: StoreUiState,
    onPurchase: (String) -> Unit,
    onEquip: (String) -> Unit,
    onErrorShown: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            onErrorShown()
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoPantalla)
    ) {
        val anchoTotal = maxWidth

        val hPad        = (anchoTotal * 0.05f).coerceIn(12.dp, 32.dp)
        val espacioN    = (anchoTotal.value * 0.045f).coerceIn(12f, 24f).dp
        val espacioC    = (anchoTotal.value * 0.025f).coerceIn(6f, 14f).dp
        val tituloSp    = (anchoTotal.value * 0.058f).coerceIn(18f, 26f)
        val subtituloSp = (anchoTotal.value * 0.034f).coerceIn(12f, 15f)
        val nombreSp    = (anchoTotal.value * 0.040f).coerceIn(13f, 17f)
        val precioSp    = (anchoTotal.value * 0.038f).coerceIn(13f, 16f)
        val emojiSp     = (anchoTotal.value * 0.11f).coerceIn(36f, 56f)
        val columnaMinima = (anchoTotal * 0.40f).coerceIn(140.dp, 180.dp)

        Scaffold(
            containerColor = FondoPantalla,
            snackbarHost   = { SnackbarHost(snackbarHostState) },
            topBar = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = hPad, vertical = espacioC)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Tienda de Avatares",
                            fontSize   = tituloSp.sp,
                            fontWeight = FontWeight.Bold,
                            color      = TextoPrincipal
                        )
                        CoinBadge(amount = uiState.coinBalance, fontSize = subtituloSp)
                    }
                    Spacer(Modifier.height(espacioC))
                    Text(
                        "Personaliza tu perfil con avatares únicos",
                        fontSize = subtituloSp.sp,
                        color    = TextoSecundario
                    )
                }
            }
        ) { paddingValues ->

            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = AzulPrimario)
                    }
                }

                uiState.avatars.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "No hay avatares disponibles",
                            color    = TextoSecundario,
                            fontSize = subtituloSp.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = columnaMinima),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentPadding = PaddingValues(
                            start  = hPad,
                            end    = hPad,
                            top    = espacioN,
                            bottom = espacioN
                        ),
                        horizontalArrangement = Arrangement.spacedBy(espacioC),
                        verticalArrangement   = Arrangement.spacedBy(espacioC)
                    ) {
                        items(uiState.avatars, key = { it.id }) { avatar ->
                            AvatarCard(
                                avatar       = avatar,
                                isProcessing = uiState.processingAvatarId == avatar.id,
                                emojiSp      = emojiSp,
                                nombreSp     = nombreSp,
                                precioSp     = precioSp,
                                onPurchase   = { onPurchase(avatar.id) },
                                onEquip      = { onEquip(avatar.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}
@Composable
private fun CoinBadge(amount: Int, fontSize: Float) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = AmarilloChip.copy(alpha = 0.25f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("🪙", fontSize = (fontSize + 2f).sp)
            Spacer(Modifier.width(6.dp))
            Text(
                "$amount",
                fontSize   = fontSize.sp,
                fontWeight = FontWeight.Bold,
                color      = TextoPrincipal
            )
        }
    }
}

@Composable
private fun AvatarCard(
    avatar: Avatar,
    isProcessing: Boolean,
    emojiSp: Float,
    nombreSp: Float,
    precioSp: Float,
    onPurchase: () -> Unit,
    onEquip: () -> Unit
) {
    val borderColor = if (avatar.isOwned) VerdePrimario else Color.Transparent

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = if (avatar.isOwned) 2.dp else 0.dp,
                color = borderColor,
                shape = RoundedCornerShape(20.dp)
            ),
        shape  = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (avatar.isOwned) VerdePrimario.copy(alpha = 0.06f) else Color.White
        ),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(FondoPantalla),
                contentAlignment = Alignment.Center
            ) {
                Text(avatar.emoji, fontSize = emojiSp.sp)
            }

            Spacer(Modifier.height(10.dp))

            Text(
                avatar.name,
                fontSize   = nombreSp.sp,
                fontWeight = FontWeight.Medium,
                color      = TextoPrincipal,
                textAlign  = TextAlign.Center
            )

            Spacer(Modifier.height(10.dp))

            when {
                isProcessing -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color       = AzulPrimario,
                            modifier    = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    }
                }

                avatar.isOwned -> {
                    Button(
                        onClick  = onEquip,
                        enabled  = !avatar.isEquipped,
                        modifier = Modifier.fillMaxWidth().height(40.dp),
                        shape    = RoundedCornerShape(20.dp),
                        colors   = ButtonDefaults.buttonColors(
                            containerColor         = VerdePrimario,
                            disabledContainerColor = VerdePrimario
                        )
                    ) {
                        Text(
                            text     = if (avatar.isEquipped) "En inventario" else "Equipar",
                            fontSize = precioSp.sp,
                            fontWeight = FontWeight.SemiBold,
                            color    = Color.White
                        )
                    }
                }

                else -> {
                    Button(
                        onClick  = onPurchase,
                        modifier = Modifier.fillMaxWidth().height(40.dp),
                        shape    = RoundedCornerShape(20.dp),
                        colors   = ButtonDefaults.buttonColors(containerColor = AzulPrimario)
                    ) {
                        Text("🪙", fontSize = precioSp.sp)
                        Spacer(Modifier.width(6.dp))
                        Text(
                            "${avatar.price}",
                            fontSize   = precioSp.sp,
                            fontWeight = FontWeight.SemiBold,
                            color      = Color.White
                        )
                    }
                }
            }
        }
    }
}
