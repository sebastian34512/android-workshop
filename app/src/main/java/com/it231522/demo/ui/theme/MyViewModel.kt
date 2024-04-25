package com.it231522.demo.ui.theme

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MyViewModel {
    private val _isDialogOpen = MutableStateFlow(false)
    val isDialogOpen: StateFlow<Boolean> = _isDialogOpen.asStateFlow()

    fun toggleDialog() {
        _isDialogOpen.value = !(_isDialogOpen.value)
    }
}