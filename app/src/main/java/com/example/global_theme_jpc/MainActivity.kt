
package com.example.global_theme_jpc.ui

import android.os.Bundle
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.setValue
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.rememberCoroutineScope

import androidx.compose.runtime.*

import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api


import androidx.compose.ui.platform.LocalContext


import androidx.compose.foundation.layout.*

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.datastore.preferences.core.edit


import com.example.global_theme_jpc.ui.data.*

import com.example.global_theme_jpc.ui.*
import com.example.global_theme_jpc.ui.theme.*

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

import com.example.jpc_library.ui.theme.*




@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            val context = LocalContext.current
            val scope = rememberCoroutineScope()

            val themeIndex by context.dataStore.data
                .map { it[PrefKeys.THEME_INDEX] ?: 1 }
                .collectAsState(initial = 1)

            Custom_Theme_JPC(themeIndex) {

            Scaffold(
                    /*
                    topBar = {
                        val appBarColor by animateColorAsState(
                            targetValue = MaterialTheme.colorScheme.primary,
                            label = ""
                        )

                        TopAppBar(
                            title = { Text("Global Theme JPC") },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = appBarColor
                            )
                        )
                    }
                    */


                ) { innerPadding ->

                    MainScreen(
                        //modifier = Modifier.padding(innerPadding),
                        onToggleTheme = {
                            val nextTheme = when (themeIndex) {
                                1 -> 2
                                2 -> 3
                                else -> 1
                            }

                            scope.launch {
                                context.dataStore.edit {
                                    it[PrefKeys.THEME_INDEX] = nextTheme
                                }
                            }
                        }
                    )
                }
            }
        }



    }
}






@Composable
fun MainScreen(
    //modifier: Modifier = Modifier,
    onToggleTheme: () -> Unit
) {
    var isBtn1Selected by remember { mutableStateOf(false) }
    var isBtn2Selected by remember { mutableStateOf(false) }

    /*
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {}

     */


        //val defaultBtn = Modifier.width(200.dp).height(50.dp).padding(vertical = 8.dp)


        //Box(modifier = modifier.fillMaxSize()){

        // This BOX is MUST to locate buttons at different parts on screen independently
        // Add all buttons inside it and inside Buttons argument add
        // modifier = defaultBtn.align(Alignment.X)
        // where X = TopCenter, BottomEnd, TopStart e.t.c

        // }



        StyledButton(text = "My Button1",colors = resolveButtonColors(isBtn1Selected), onClick = { isBtn1Selected = !isBtn1Selected })
        StyledButton(text = "My Button2",colors = resolveButtonColors(isBtn2Selected), onClick = { isBtn2Selected = !isBtn2Selected })

        Button(onClick = onToggleTheme) { Text("Switch Themes") }

        // Theme toggle button (normal button)

        //  Box is must to ensure independent placement of buttons


        // Material buttons (ALL derive color from MaterialTheme)


}
