package com.williamsel.mathstack.core.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.williamsel.mathstack.core.domain.model.NetworkStatus
import com.williamsel.mathstack.core.session.ConnectivityViewModel

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val connectivityVm: ConnectivityViewModel = hiltViewModel()
    val networkStatus by connectivityVm.networkStatus.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        NavGraph(navController = navController)

        AnimatedVisibility(
            visible = networkStatus is NetworkStatus.Lost,
            modifier = Modifier.align(Alignment.TopCenter),
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically() + fadeOut()
        ) {
            NetworkBanner()
        }
    }
}

@Composable
fun NetworkBanner() {
    Surface(color = MaterialTheme.colorScheme.errorContainer) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.WifiOff, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Sin conexión a internet")
        }
    }
}
