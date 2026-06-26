package com.williamsel.mathstack.features.private.groupranking.presentacion.screens

import com.williamsel.mathstack.features.private.groupranking.domain.entities.GlobalRanking
import com.williamsel.mathstack.features.private.groupranking.domain.entities.GroupRanking

enum class RankingTab { GROUP, GLOBAL }

data class GrouprankingUiState(
    val selectedTab: RankingTab    = RankingTab.GROUP,
    val groupRanking: GroupRanking? = null,
    val globalRanking: GlobalRanking? = null,
    val isLoading: Boolean         = true,
    val errorMessage: String?      = null
)
