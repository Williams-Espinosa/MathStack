package com.williamsel.mathstack.core.domain.model

sealed class NetworkStatus {
    object Available : NetworkStatus()
    object Lost : NetworkStatus()
}
