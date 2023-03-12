package com.example.vktodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vktodo.presenter.add.AddTotoItemScreen
import com.example.vktodo.presenter.list.TodoScreen
import com.example.vktodo.presenter.utils.Screen
import com.example.vktodo.ui.theme.VKTodoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VKTodoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.TodoList.destination
                    ) {
                        composable(route = Screen.TodoList.destination) {
                            TodoScreen(navController = navController)
                        }
                        composable(
                            route = "${Screen.AddEdit.destination}?id={id}",
                            arguments = listOf(
                                navArgument("id") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            AddTotoItemScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
