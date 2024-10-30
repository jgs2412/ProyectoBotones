package com.example.botones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface {
                botones()
            }
        }
    }
}

@Composable
fun botones() {
    var buttonText by remember { mutableStateOf("Presionar") }
    var loading by remember { mutableStateOf(false) }
    var isTextVisible by remember { mutableStateOf(false) }
    var switchState by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }
    var currentImage by remember { mutableIntStateOf(R.drawable.im1) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxHeight()
                .padding(top = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                PressButton(
                    onPress = {
                        buttonText = "Botón presionado"
                        loading = true
                        coroutineScope.launch {
                            kotlinx.coroutines.delay(5000)
                            loading = false
                            buttonText = "Presionar"
                        }
                    },
                    buttonText = buttonText
                )

                if (loading) {
                    Spacer(modifier = Modifier.width(16.dp))
                    CircularProgressIndicator()
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Mensaje oculto
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (isTextVisible) {
                    Text("Mensaje oculto")
                }

                Spacer(modifier = Modifier.width(16.dp))

                ButtonThree(
                    isChecked = isTextVisible,
                    onCheckedChange = { isTextVisible = it }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Fila con el icono y el switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tu),
                    contentDescription = "Descripción del ícono",
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))

                Switch(
                    checked = switchState,
                    onCheckedChange = {
                        switchState = it
                        if (!it) {
                            selectedOption = ""
                        }
                    }
                )
            }

            if (switchState) {
                Spacer(modifier = Modifier.height(32.dp))
                Column {
                    RadioButtonRow(
                        option = "Opción 1",
                        selectedOption = selectedOption,
                        onOptionSelected = {
                            selectedOption = it
                            buttonText = "Seleccionaste: Opción 1"
                        }
                    )
                    RadioButtonRow(
                        option = "Opción 2",
                        selectedOption = selectedOption,
                        onOptionSelected = {
                            selectedOption = it
                            buttonText = "Seleccionaste: Opción 2"
                        }
                    )
                    RadioButtonRow(
                        option = "Opción 3",
                        selectedOption = selectedOption,
                        onOptionSelected = {
                            selectedOption = it
                            buttonText = "Seleccionaste: Opción 3"
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Image(
                painter = painterResource(id = currentImage),
                contentDescription = "Imagen actual",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                currentImage = when (currentImage) {
                    R.drawable.im1 -> R.drawable.im2
                    R.drawable.im2 -> R.drawable.im3
                    else -> R.drawable.im1
                }
            }) {
                Text("Cambiar Imagen")
            }
        }
    }
}

@Composable
fun PressButton(onPress: () -> Unit, buttonText: String) {
    Button(onClick = onPress) {
        Text(buttonText)
    }
}

@Composable
fun ButtonThree(isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )
        Text("Mostrar Texto")
    }
}

@Composable
fun RadioButtonRow(option: String, selectedOption: String, onOptionSelected: (String) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = selectedOption == option,
            onClick = { onOptionSelected(option) }
        )
        Text(option)
    }
}
