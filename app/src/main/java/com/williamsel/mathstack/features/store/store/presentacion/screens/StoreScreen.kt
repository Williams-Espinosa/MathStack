package com.williamsel.mathstack.features.store.presentacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.williamsel.mathstack.features.store.domain.entities.Avatar
import com.williamsel.mathstack.features.store.presentacion.viewmodels.StoreViewModel
import com.williamsel.mathstack.ui.theme.*

@Composable
fun StoreScreen(
    viewModel: StoreViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    StoreContent(
        uiState             = uiState,
        onPurchaseClick     = viewModel::onPurchaseClick,
        onEquip             = viewModel::onEquipAvatar,
        onConfirmPurchase   = viewModel::onConfirmPurchase,
        onDismissConfirmation = viewModel::onDismissConfirmation,
        onErrorShown        = viewModel::onErrorDismissed
    )
}

@Composable
private fun StoreContent(
    uiState: StoreUiState,
    onPurchaseClick: (String) -> Unit,
    onEquip: (String) -> Unit,
    onConfirmPurchase: () -> Unit,
    onDismissConfirmation: () -> Unit,
    onErrorShown: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            onErrorShown()
        }
    }

    uiState.confirmingAvatar?.let { avatar ->
        PurchaseConfirmDialog(
            avatar          = avatar,
            coinBalance     = uiState.coinBalance,
            onConfirm       = onConfirmPurchase,
            onDismiss       = onDismissConfirmation
        )
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(FondoPantalla)
    ) {
        val anchoTotal = this.maxWidth

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
                        verticalAlignment     = Alignment.CenterVertically
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
                        modifier = Modifier.fillMaxSize().padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = AzulPrimario)
                    }
                }

                uiState.avatars.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "No hay avatares disponibles",
                            color     = TextoSecundario,
                            fontSize  = subtituloSp.sp,
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
                                onPurchaseClick = { onPurchaseClick(avatar.id) },
                                onEquip         = { onEquip(avatar.id) }
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
        color = NaranjaTrofeo.copy(alpha = 0.15f)
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
                color      = NaranjaTrofeo
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
    onPurchaseClick: () -> Unit,
    onEquip: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (avatar.isOwned) Modifier.border(2.dp, VerdePrimario, RoundedCornerShape(20.dp))
                else Modifier
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
                        modifier = Modifier.fillMaxWidth().height(40.dp),
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
                            disabledContainerColor = if (avatar.isEquipped) VerdePrimario.copy(alpha = 0.5f) else VerdePrimario
                        )
                    ) {
                        Text(
                            if (avatar.isEquipped) "Equipado" else "Equipar",
                            fontSize   = precioSp.sp,
                            fontWeight = FontWeight.SemiBold,
                            color      = Color.White
                        )
                    }
                }

                else -> {
                    Button(
                        onClick  = onPurchaseClick,
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

@Composable
private fun PurchaseConfirmDialog(
    avatar: Avatar,
    coinBalance: Int,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val saldoRestante = coinBalance - avatar.price

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape  = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Text(
                        "Confirmar compra",
                        fontSize   = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color      = TextoPrincipal
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(
                            Icons.Outlined.Close,
                            contentDescription = "Cerrar",
                            tint = TextoSecundario
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = FondoPantalla
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(avatar.emoji, fontSize = 40.sp)
                        Spacer(Modifier.width(14.dp))
                        Column {
                            Text(
                                avatar.name,
                                fontSize   = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color      = TextoPrincipal
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("🪙", fontSize = 14.sp)
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    "${avatar.price} monedas",
                                    fontSize   = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color      = NaranjaTrofeo
                                )
                            }
                            Text(
                                "Saldo restante: $saldoRestante monedas",
                                fontSize = 12.sp,
                                color    = TextoSecundario
                            )
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    "¿Seguro que quieres comprar este avatar?",
                    fontSize  = 14.sp,
                    color     = TextoSecundario,
                    textAlign = TextAlign.Center,
                    modifier  = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick  = onDismiss,
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape    = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            "No, cancelar",
                            color      = TextoPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize   = 14.sp
                        )
                    }
                    Button(
                        onClick  = onConfirm,
                        enabled  = saldoRestante >= 0,
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape    = RoundedCornerShape(12.dp),
                        colors   = ButtonDefaults.buttonColors(
                            containerColor         = AzulPrimario,
                            disabledContainerColor = AzulPrimario.copy(alpha = 0.4f)
                        )
                    ) {
                        Icon(
                            Icons.Outlined.ShoppingCart,
                            contentDescription = null,
                            tint     = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            "Sí, comprar",
                            color      = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize   = 14.sp
                        )
                    }
                }
            }
        }
    }
}
