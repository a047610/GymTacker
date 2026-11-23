package com.example.gymtrackermobile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Ecra01(nome: MutableState<String>) {

    Column(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)) {
        TextField(value = nome.value, onValueChange = { nome.value = it }, label = {})
        Text(text = stringResource(id = R.string.nome),
            fontWeight = FontWeight.Bold, color = Color.Gray,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center, fontSize = 18.sp
        )
        Button(onClick = {}, enabled = true) {
            Text(text= stringResource(R.string.Search))
        }
    }
}

@Composable
fun Ecra02(nome: MutableState<String>) {
    val numero = remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(all = 16.dp)) {
        Row(

            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ){
            TextField(value = nome.value, onValueChange = { nome.value = it }, label = {})
            Text(text = stringResource(id = R.string.nome),
                fontWeight = FontWeight.Bold, color = Color.Gray,
                textAlign = TextAlign.Center, fontSize = 18.sp
            )

        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ){
            TextField(value = numero.value, onValueChange = { nome.value = it }, label = {})
            Text(text = stringResource(id = R.string.numero),
                fontWeight = FontWeight.Bold, color = Color.Gray,
                textAlign = TextAlign.Left, fontSize = 18.sp
            )
        }
        Button(onClick = {}, enabled = true) {
            Text(text= stringResource(R.string.Add))
        }

    }
}



