package com.example.remote.navigation

import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.remote.api.RemoteViewModel
import com.example.remote.screens.ScreenRemote

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraphSetup(
    navController: NavHostController,
    remoteViewModel: RemoteViewModel
) {
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = Screens.Information.route){

        composable(
            route = Screens.Information.route,
        ){
//            menuViewModel.getMenu()
//            ScreenHome(
//                storeViewModel = storeViewModel,
//                navController = navController,
//                menuViewModel = menuViewModel,
//                priceViewModel = priceViewModel
//            )
        }

        composable(
            route = Screens.Remote.route,
        ){
            ScreenRemote(remoteViewModel = remoteViewModel)
        }
    }
}