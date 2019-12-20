package com.urobot.droid.ui.fragments.ubot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UbotViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is U-bot Fragment"
    }
    val text: LiveData<String> = _text
}