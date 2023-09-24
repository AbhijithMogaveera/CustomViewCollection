package com.abhijith.customviewcollection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abhijith.customviewcollection.navigation.SimpleRoutes
import com.abhijith.customviewcollection.screens.ClockView
import com.abhijith.customviewcollection.screens.CylinderGraphComp
import com.abhijith.customviewcollection.screens.MainMenu
import com.abhijith.customviewcollection.ui.theme.CustomViewCollectionTheme

@ExperimentalMaterialApi
class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomViewCollectionTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(navController = navController, startDestination = SimpleRoutes.MENU_SCREEN) {
                        composable(SimpleRoutes.MENU_SCREEN) {
                            MainMenu {
                                navController.navigate(it)
                            }
                        }

                        composable(SimpleRoutes.CLOCK_SCREEN) {
                            ClockView()
                        }

                        composable(SimpleRoutes.CYLINDER_SCREEN) {
                            CylinderGraphComp()
                        }

                        composable(SimpleRoutes.WHATS_APP_MESSAGE_SCREEN){

                        }
                    }

                }

            }

        }

    }
}

