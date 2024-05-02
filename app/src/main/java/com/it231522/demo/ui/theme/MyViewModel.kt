package com.it231522.demo.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.it231522.demo.data.Contact
import com.it231522.demo.data.dao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyViewModel (private val dao: dao): ViewModel() {
    private val _isDialogOpen = MutableStateFlow(false)
    val isDialogOpen: StateFlow<Boolean> = _isDialogOpen.asStateFlow()

    private val _contactsState = MutableStateFlow(emptyList<Contact>())
    val contactsState: StateFlow<List<Contact>> = _contactsState.asStateFlow()

    fun toggleDialog() {
        _isDialogOpen.value = !(_isDialogOpen.value)
    }

    fun insertContact(contact: Contact) {
        viewModelScope.launch {
            dao.insertContact(contact)
            getContacts()
            toggleDialog()
        }
    }

    fun getContacts() {
        viewModelScope.launch {
            dao.getContacts().collect() {
                _contactsState.value = it
            }
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            dao.deleteContact(contact)
            getContacts()
        }
    }
}