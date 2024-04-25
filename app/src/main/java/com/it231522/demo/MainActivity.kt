@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)

package com.it231522.demo

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import com.it231522.demo.ui.theme.DemoTheme
import com.it231522.demo.ui.theme.MyViewModel

class MainActivity : ComponentActivity() {
    val vm = MyViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    myView(vm)
                }
            }
        }
        val myIntent = Intent(this, MainActivity2::class.java)
        //startActivity(myIntent)
    }
}

@Composable
fun myView(vm: MyViewModel) {
    var name by rememberSaveable { mutableStateOf("") }
    var uid by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            modifier = Modifier
                .onPreviewKeyEvent {
                    if (it.key == Key.Tab && it.nativeKeyEvent.action == KeyEvent.ACTION_DOWN){
                        focusManager.moveFocus(FocusDirection.Down)
                        true
                    } else {
                        false
                    }
                },
            value = name,
            onValueChange = {
                    newText -> name = newText
            },
            label = { Text(text = "Name")}
        )
        OutlinedTextField(
            modifier = Modifier
                .onPreviewKeyEvent {
                    if (it.key == Key.Tab && it.nativeKeyEvent.action == KeyEvent.ACTION_DOWN){
                        focusManager.moveFocus(FocusDirection.Down)
                        true
                    } else {
                        false
                    }
                },
            value = uid,
            onValueChange = {
                    newText -> uid = newText
            },
            label = { Text(text = "UID")}
        )

        Button(
            onClick = {
                vm.toggleDialog()
        }) {
            Text(text = "fertig")
        }

        if (vm.isDialogOpen.collectAsState().value) {
            AlertDialog(
                onDismissRequest = {vm.toggleDialog()},
                text = { Text(text = "Hallo $name, $uid!") },
                confirmButton = {
                    Button(
                        onClick = { vm.toggleDialog() }
                    ) {
                        Text(text = "schließen")
                    }
                }
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DemoTheme {
        Greeting("Android")
    }
}