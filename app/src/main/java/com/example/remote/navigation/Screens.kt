package com.example.remote.navigation

sealed class Screens(val route: String){
    object Information: Screens(route = "info_screen")
    object Remote: Screens(route = "remote_screen")
}