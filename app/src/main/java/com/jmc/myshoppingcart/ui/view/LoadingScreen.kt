package com.jmc.myshoppingcart.ui.view

import android.annotation.SuppressLint
import android.widget.ProgressBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoadingScreen(
    isLoading: Boolean,
    textLoading: String = "",
    content: @Composable () -> Unit
) {
    if (isLoading) {
        Surface(modifier = Modifier.disableGestures(true)) {
            Surface(
                color = White,
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(1f)
            ) {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    content = {

                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {

                            CircularProgressIndicator()

                        }


                    },
                    bottomBar = {
                        Row(
                            modifier = Modifier.padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = textLoading.ifEmpty { "Procesando ...." },
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                )

            }
            content()
        }
    } else {
        content()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.disableGestures(disabled: Boolean = true) =
    if (disabled) {
        pointerInput(Unit) {
            awaitPointerEventScope {
                // we should wait for all new pointer events
                while (true) {
                    awaitPointerEvent(pass = PointerEventPass.Initial)
                        .changes
                        .forEach(PointerInputChange::consume)
                }
            }
        }
    } else {
        Modifier
    }
