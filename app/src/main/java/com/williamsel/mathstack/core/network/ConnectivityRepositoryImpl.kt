package com.williamsel.mathstack.core.network

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.williamsel.mathstack.core.domain.model.NetworkStatus
import com.williamsel.mathstack.core.domain.repository.IConnectivityRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class ConnectivityRepositoryImpl @Inject constructor(
    private val connectivityManager: ConnectivityManager
) : IConnectivityRepository {

    override fun observeConnectivity(): Flow<NetworkStatus> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(NetworkStatus.Available)
            }
            override fun onLost(network: Network) {
                trySend(NetworkStatus.Lost)
            }
        }
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, callback)
        
        val isConnected = connectivityManager.activeNetwork?.let {
            connectivityManager.getNetworkCapabilities(it)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } ?: false
        
        trySend(if (isConnected) NetworkStatus.Available else NetworkStatus.Lost)

        awaitClose { connectivityManager.unregisterNetworkCallback(callback) }
    }.distinctUntilChanged()
}
