package com.example.splitwise_payg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.splitwise_payg.pages.MainScreen
import com.example.splitwise_payg.ui.theme.SplitwisepaygTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplitwisepaygTheme {
                MainScreen()
                }
            }
    }
}