package com.williamsel.mathstack.features.groupranking.presentacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.williamsel.mathstack.features.groupranking.domain.entities.*
import com.williamsel.mathstack.features.groupranking.presentacion.viewmodels.GrouprankingViewModel
import com.williamsel.mathstack.ui.theme.AzulPrimario
import com.williamsel.mathstack.ui.theme.FondoPantalla
import com.williamsel.mathstack.ui.theme.TextoPrincipal
import com.williamsel.mathstack.ui.theme.TextoSecundario
import java.text.NumberFormat
import java.util.Locale
private val HPad     = 20.dp
private val EspacioN  = 16.dp
private val EspacioC  = 10.dp
private val TituloSp  = 21f
private val NameSp    = 17f
private val SubSp     = 13f
private val XpSp      = 18f
private val StatSp    = 19f

@Composable
fun GrouprankingScreen(
    onNavigateBack: () -> Unit = {},
    viewModel: GrouprankingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    GrouprankingContent(
        uiState          = uiState,
        onNavigateBack   = onNavigateBack,
        onTabSelected    = viewModel::onTabSelected,
        onErrorDismissed = viewModel::onErrorDismissed
    )
}

@Composable
private fun GrouprankingContent(
    uiState: GrouprankingUiState,
    onNavigateBack: () -> Unit,
    onTabSelected: (RankingTab) -> Unit,
    onErrorDismissed: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            onErrorDismissed()
        }
    }

    Scaffold(
        containerColor = FondoPantalla,
        snackbarHost   = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(FondoPantalla)
                .padding(padding)
                .padding(horizontal = HPad),
            contentPadding      = PaddingValues(bottom = EspacioN),
            verticalArrangement = Arrangement.spacedBy(EspacioC)
        ) {

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = EspacioN),
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
                        "Ranking del Grupo",
                        fontSize   = TituloSp.sp,
                        fontWeight = FontWeight.Bold,
                        color      = TextoPrincipal
                    )
                }
            }

            item {
                TabSelector(
                    selected  = uiState.selectedTab,
                    onSelect  = onTabSelected,
                    subSp     = SubSp
                )
            }

            if (uiState.isLoading) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 48.dp),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator(color = AzulPrimario) }
                }
                return@LazyColumn
            }

            if (uiState.selectedTab == RankingTab.GROUP) {
                val gr = uiState.groupRanking
                if (gr == null) return@LazyColumn

                item {
                    GroupHeroBanner(
                        info   = gr.groupInfo,
                        subSp  = SubSp,
                        statSp = StatSp
                    )
                }

                item {
                    Column {
                        Text(
                            "Miembros del Grupo",
                            fontSize   = (TituloSp - 2f).sp,
                            fontWeight = FontWeight.Bold,
                            color      = TextoPrincipal
                        )
                        Text(
                            "Ranking por XP acumulado",
                            fontSize = SubSp.sp,
                            color    = TextoSecundario
                        )
                    }
                }

                items(gr.members, key = { it.userId }) { member ->
                    GroupMemberCard(
                        member        = member,
                        isCurrentUser = member.userId == gr.currentUserId,
                        nameSp        = NameSp,
                        subSp         = SubSp,
                        xpSp          = XpSp
                    )
                }
            }

            if (uiState.selectedTab == RankingTab.GLOBAL) {
                val glr = uiState.globalRanking
                if (glr == null) return@LazyColumn

                item { GlobalHeroBanner(subSp = SubSp, statSp = StatSp) }

                item {
                    Column {
                        Text(
                            "Top Jugadores",
                            fontSize   = (TituloSp - 2f).sp,
                            fontWeight = FontWeight.Bold,
                            color      = TextoPrincipal
                        )
                        Text(
                            "Clasificación general por XP",
                            fontSize = SubSp.sp,
                            color    = TextoSecundario
                        )
                    }
                }

                items(glr.players, key = { it.userId }) { player ->
                    GlobalPlayerCard(
                        player        = player,
                        isCurrentUser = player.userId == glr.currentUserId,
                        nameSp        = NameSp,
                        subSp         = SubSp,
                        xpSp          = XpSp
                    )
                }
            }
        }
    }
}

@Composable
private fun TabSelector(
    selected: RankingTab,
    onSelect: (RankingTab) -> Unit,
    subSp: Float
) {
    Surface(
        shape = RoundedCornerShape(50),
        color = Color(0xFFEEEEF5),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            RankingTab.values().forEach { tab ->
                val isActive = selected == tab
                Surface(
                    shape    = RoundedCornerShape(50),
                    color    = if (isActive) AzulPrimario else Color.Transparent,
                    modifier = Modifier.weight(1f)
                ) {
                    TextButton(onClick = { onSelect(tab) }) {
                        Text(
                            text       = if (tab == RankingTab.GROUP) "Ranking Grupo" else "Ranking Global",
                            color      = if (isActive) Color.White else TextoSecundario,
                            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                            fontSize   = subSp.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GroupHeroBanner(info: GroupInfo, subSp: Float, statSp: Float) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF7B5EA7), Color(0xFF9B6ED4))
                    )
                )
                .padding(16.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
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
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            info.name,
                            fontSize   = (statSp - 2f).sp,
                            fontWeight = FontWeight.Bold,
                            color      = Color.White
                        )
                        Text(
                            "${info.subject} · ${info.level}",
                            fontSize = (subSp - 1f).sp,
                            color    = Color.White.copy(alpha = 0.85f)
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    BannerStat(
                        value = info.membersCount.toString(),
                        label = "Miembros",
                        icon  = "👥",
                        subSp = subSp,
                        statSp = statSp
                    )
                    BannerStat(
                        value = info.activeChallenges.toString(),
                        label = "Retos",
                        icon  = "🏆",
                        subSp = subSp,
                        statSp = statSp
                    )
                    BannerStat(
                        value = formatXp(info.totalXp),
                        label = "XP Total",
                        icon  = "⚡",
                        subSp = subSp,
                        statSp = statSp
                    )
                }
            }
        }
    }
}

@Composable
private fun GlobalHeroBanner(subSp: Float, statSp: Float) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFFF5A623), Color(0xFFF7C948))
                    )
                )
                .padding(20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White.copy(alpha = 0.25f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.EmojiEvents,
                        contentDescription = null,
                        tint     = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(Modifier.width(14.dp))
                Column {
                    Text(
                        "Ranking Global",
                        fontSize   = (statSp - 1f).sp,
                        fontWeight = FontWeight.Bold,
                        color      = Color.White
                    )
                    Text(
                        "Todos los jugadores",
                        fontSize = (subSp - 1f).sp,
                        color    = Color.White.copy(alpha = 0.9f)
                    )
                }
            }
        }
    }
}

@Composable
private fun GroupMemberCard(
    member: GroupMemberRank,
    isCurrentUser: Boolean,
    nameSp: Float,
    subSp: Float,
    xpSp: Float
) {
    val borderColor = if (isCurrentUser) AzulPrimario else Color.Transparent
    val bgColor     = if (isCurrentUser) AzulPrimario.copy(alpha = 0.04f) else Color.White

    Card(
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(if (isCurrentUser) 0.dp else 2.dp),
        border    = if (isCurrentUser)
            androidx.compose.foundation.BorderStroke(2.dp, borderColor)
        else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RankAvatar(rank = member.rank, badge = member.badge, size = 40.dp)

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (isCurrentUser) "${member.name} (Tú)" else member.name,
                        fontSize   = nameSp.sp,
                        fontWeight = FontWeight.Bold,
                        color      = if (isCurrentUser) AzulPrimario else TextoPrincipal
                    )
                    Spacer(Modifier.width(4.dp))
                    BadgeEmoji(badge = member.badge)
                }
                Spacer(Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    StatChip(label = "Nivel ${member.level}", subSp = subSp)
                    StatDot()
                    StatChip(
                        label = "${member.streakDays} días",
                        subSp = subSp,
                        iconEmoji = "🔥"
                    )
                    StatDot()
                    StatChip(label = "${member.lessonsCompleted} lecciones", subSp = subSp)
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    formatXp(member.xp),
                    fontSize   = xpSp.sp,
                    fontWeight = FontWeight.Bold,
                    color      = AzulPrimario
                )
                Text("XP", fontSize = (subSp - 1f).sp, color = TextoSecundario)
            }
        }
    }
}

@Composable
private fun GlobalPlayerCard(
    player: GlobalPlayerRank,
    isCurrentUser: Boolean,
    nameSp: Float,
    subSp: Float,
    xpSp: Float
) {
    val borderColor = if (isCurrentUser) AzulPrimario else Color.Transparent
    val bgColor     = if (isCurrentUser) AzulPrimario.copy(alpha = 0.04f) else Color.White

    Card(
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(if (isCurrentUser) 0.dp else 2.dp),
        border    = if (isCurrentUser)
            androidx.compose.foundation.BorderStroke(2.dp, borderColor)
        else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RankAvatar(rank = player.rank, badge = player.badge, size = 40.dp)

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (isCurrentUser) "${player.name} (Tú)" else player.name,
                        fontSize   = nameSp.sp,
                        fontWeight = FontWeight.Bold,
                        color      = if (isCurrentUser) AzulPrimario else TextoPrincipal
                    )
                    Spacer(Modifier.width(4.dp))
                    BadgeEmoji(badge = player.badge)
                }
                Spacer(Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    StatChip(label = "Nivel ${player.level}", subSp = subSp)
                    StatDot()
                    StatChip(
                        label = "${player.streakDays} días de racha",
                        subSp = subSp,
                        iconEmoji = "🔥"
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    formatXp(player.xp),
                    fontSize   = xpSp.sp,
                    fontWeight = FontWeight.Bold,
                    color      = AzulPrimario
                )
                Text("XP", fontSize = (subSp - 1f).sp, color = TextoSecundario)
            }
        }
    }
}

@Composable
private fun RankAvatar(rank: Int, badge: RankBadge, size: Dp) {
    when (badge) {
        RankBadge.GOLD -> Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(Color(0xFFFFF3CC)),
            contentAlignment = Alignment.Center
        ) { Text("👑", fontSize = (size.value * 0.5f).sp, textAlign = TextAlign.Center) }

        RankBadge.SILVER -> Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(Color(0xFFEEEEEE)),
            contentAlignment = Alignment.Center
        ) { Text("#$rank", fontSize = (size.value * 0.34f).sp, fontWeight = FontWeight.Bold, color = Color(0xFF888888)) }

        RankBadge.BRONZE -> Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(Color(0xFFFFE8D6)),
            contentAlignment = Alignment.Center
        ) { Text("#$rank", fontSize = (size.value * 0.34f).sp, fontWeight = FontWeight.Bold, color = Color(0xFFCD7F32)) }

        RankBadge.NONE -> Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(Color(0xFFEEEEF5)),
            contentAlignment = Alignment.Center
        ) { Text("#$rank", fontSize = (size.value * 0.34f).sp, fontWeight = FontWeight.Bold, color = TextoSecundario) }
    }
}

@Composable
private fun BadgeEmoji(badge: RankBadge) {
    val emoji = when (badge) {
        RankBadge.GOLD   -> "🥇"
        RankBadge.SILVER -> "🥈"
        RankBadge.BRONZE -> "🥉"
        RankBadge.NONE   -> return
    }
    Text(emoji, fontSize = 14.sp)
}

@Composable
private fun StatChip(label: String, subSp: Float, iconEmoji: String? = null) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (iconEmoji != null) {
            Text(iconEmoji, fontSize = (subSp - 1f).sp)
            Spacer(Modifier.width(2.dp))
        }
        Text(label, fontSize = subSp.sp, color = TextoSecundario)
    }
}

@Composable
private fun StatDot() {
    Text(" · ", fontSize = 10.sp, color = TextoSecundario)
}

@Composable
private fun BannerStat(
    value: String,
    label: String,
    icon: String,
    subSp: Float,
    statSp: Float
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = Color.White.copy(alpha = 0.15f)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(icon, fontSize = 18.sp)
            Spacer(Modifier.height(4.dp))
            Text(
                value,
                fontSize   = statSp.sp,
                fontWeight = FontWeight.Bold,
                color      = Color.White
            )
            Text(
                label,
                fontSize = (subSp - 1f).sp,
                color    = Color.White.copy(alpha = 0.85f)
            )
        }
    }
}

private fun formatXp(xp: Int): String =
    NumberFormat.getNumberInstance(Locale("es", "MX")).format(xp)