@file:OptIn(ExperimentalMaterial3Api::class)

package com.it231522.demo.ui.theme

import android.view.KeyEvent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.it231522.demo.data.Contact

@Composable
fun UserFormView(vm: MyViewModel) {
    var name by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
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
            label = { Text(text = "Name") }
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
            value = phone,
            onValueChange = {
                    newText -> phone = newText
            },
            label = { Text(text = "Phone") }
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
                text = { Text(text = "Hallo $name, $phone!") },
                confirmButton = {
                    Button(
                        onClick = {
                            vm.insertContact(Contact(name, phone))
                        }
                    ) {
                        Text(text = "schließen")
                    }
                }
            )
        }
    }
}

@Composable
fun BottomNavBar(nc: NavController) {
    NavigationBar {
        NavigationBarItem(
            selected = true,
            onClick = { nc.navigate("first")},
            icon = { Icon(imageVector = Icons.Default.Edit, contentDescription =  "Form") },
            label = { Text(text = "First") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { nc.navigate("second")},
            icon = { Icon(imageVector = Icons.Default.List, contentDescription =  "List") },
            label = { Text(text = "Second") }
        )
    }
}

@Composable
fun ViewContacts(vm: MyViewModel){
    val state = vm.contactsState.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item { Text(text = "CONTACTS") }
        items(state.value) {
            Row (modifier = Modifier.fillParentMaxWidth()) {
                Column {
                    Text(text = "Name: " + it.name)
                    Text(text = "Phone: " + it.phone)
                }
            }
        }
    }
}

@Composable
fun MainView(vm: MyViewModel) {
    val navController = rememberNavController()
    Scaffold (
        bottomBar = {BottomNavBar(nc = navController)}
    ) {padding ->
        NavHost(
            navController = navController,
            startDestination = "first",
            modifier = Modifier.padding(padding)
        ) {
            composable("first") {
                UserFormView(vm)
            }
            composable("second") {
                vm.getContacts()
                ViewContacts(vm)
            }
        }
    }
}
