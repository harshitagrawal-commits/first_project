
package com.example.global_theme_jpc.ui

import android.os.Bundle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import kotlinx.coroutines.flow.map

import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TopAppBarDefaults


import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment


import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import com.example.jpc_library.ui.theme.Global_Theme_JPCTheme
import kotlinx.coroutines.flow.map
import java.time.temporal.TemporalAdjusters.next


val Context.dataStore by preferencesDataStore(name = "settings")

object PrefKeys {
    val THEME_INDEX = intPreferencesKey("theme_index")
}


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
            Global_Theme_JPCTheme()
            Global_Theme_JPC(themeIndex) {

                Scaffold(
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
                ) { padding ->

                    MainScreen(
                        modifier = Modifier.padding(padding),
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
fun Global_Theme_JPC(
    themeIndex: Int,
    content: @Composable () -> Unit
) {
    val colors = when (themeIndex) {
        2 -> lightColorScheme(
            primary = Color(0xFFEA7068),
            background = Color(0xFFA47868),
            surface = Color(0xFF7C380B)
        )
        3 -> lightColorScheme(
            primary = Color(0xFF4CAF50),
            background = Color(0xFF095548),
            surface = Color(0xFF27670C)
        )
        else -> lightColorScheme(
            surface = Color(0xFF260460)
        ) // default theme
    }

    MaterialTheme(colorScheme = colors, content = content)
}

data class ButtonStyle(
    val containerColor: Color,
    val contentColor: Color,
    //val textSize: TextUnit,
    //val shape: Shape,
    //val elevation: Dp
)



@Composable
fun StyledButton(
    text: String,
    colors: ButtonColors,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(onClick = onClick, colors = colors, modifier = modifier) {
        Text(text)
    }
}

@Composable
fun resolveButtonColors(isSelected: Boolean): ButtonColors =
    ButtonDefaults.buttonColors(
            containerColor =
            if (isSelected) MaterialTheme.colorScheme.surface
            else MaterialTheme.colorScheme.primary,
            contentColor = Color.White

            //if (isSelected) MaterialTheme.colorScheme.onPrimary
            //else MaterialTheme.colorScheme.onSurface
    )

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onToggleTheme: () -> Unit
) {
    var isBtn1Selected by remember { mutableStateOf(false) }
    var isBtn2Selected by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        val defaultBtn = Modifier.width(200.dp).height(50.dp).padding(vertical = 8.dp)


        //Box(modifier = modifier.fillMaxSize()){

        // This BOX is MUST to locate buttons at different parts on screen independently
        // Add all buttons inside it and inside Buttons argument add
        // modifier = defaultBtn.align(Alignment.X)
        // where X = TopCenter, BottomEnd, TopStart e.t.c

        // }



        StyledButton(text = "My Button1",colors = resolveButtonColors(isBtn1Selected), onClick = { isBtn1Selected = !isBtn1Selected })
        StyledButton(text = "My Button2",colors = resolveButtonColors(isBtn2Selected), onClick = { isBtn2Selected = !isBtn2Selected })

            Button(modifier = defaultBtn,onClick = onToggleTheme) { Text("Switch Themes") }




            // Theme toggle button (normal button)

            //  Box is must to ensure independent placement of buttons


            // Material buttons (ALL derive color from MaterialTheme)




    }
}