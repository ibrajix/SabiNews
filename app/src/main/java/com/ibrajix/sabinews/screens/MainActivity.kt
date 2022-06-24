package com.ibrajix.sabinews.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ibrajix.sabinews.NavGraphs
import com.ibrajix.sabinews.datastore.StorageViewModel
import com.ibrajix.sabinews.style.theme.SabiNewsTheme
import com.ibrajix.sabinews.style.theme.statusBarColorDark
import com.ibrajix.sabinews.style.theme.statusBarColorLight
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: StorageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {

            val systemUiController = rememberSystemUiController()

            val darkTheme = viewModel.selectedTheme.observeAsState(initial = false)

            if(darkTheme.value){
                systemUiController.setStatusBarColor(
                    color = statusBarColorDark
                )
            }else
            {
                systemUiController.setStatusBarColor(
                    color = statusBarColorLight
                )
            }

            SabiNewsTheme(darkTheme.value) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root)
                    //start screen -> PermissionScreen (screens/HomeScreen)
                }
            }
        }
    }
}
