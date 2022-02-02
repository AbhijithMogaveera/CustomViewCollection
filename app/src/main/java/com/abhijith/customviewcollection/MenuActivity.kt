package com.abhijith.customviewcollection

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.setPadding
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abhijith.customviewcollection.navigation.SimpleRoutes
import com.abhijith.customviewcollection.ui.theme.CustomViewCollectionTheme
import com.abhijith.miui_cylinder_graph.ui.CylinderGraphView
import com.example.simple_clock.MyClockView
import java.time.Clock

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
                            MainMenu{
                                navController.navigate(it)
                            }
                        }
                        composable(SimpleRoutes.CLOCK_SCREEN) {
                            ClockView()
                        }
                        composable(SimpleRoutes.CYLINDER_SCREEN) {
                            CylinderGraphView()
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MainMenu(
    onRouteSelection:(String)->Unit
) {
    Column(modifier = Modifier) {
        MenuItem("CLock 🕑") {
            onRouteSelection(SimpleRoutes.CLOCK_SCREEN)
        }
        MenuItem("CylinderGraph 📊") {
            onRouteSelection(SimpleRoutes.CYLINDER_SCREEN)
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun MenuItem(
    name: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Text(
            text = name, modifier = Modifier.padding(20.dp)
        )
    }
}

@Composable
fun CylinderGraphView() {
    AndroidView(factory = {
        CylinderGraphView(it)
    }, modifier = Modifier.padding(horizontal = 100.dp, vertical = 200.dp))
}

@Composable
fun ClockView() {
    AndroidView(factory = {
        MyClockView(it).apply {
            setPadding(50)
        }
    }, modifier = Modifier.padding(horizontal = 30.dp))
}