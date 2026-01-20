package com.example.parametrable.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun currentRoute(nav: NavHostController): String? =
    nav.currentBackStackEntryAsState().value?.destination?.route
