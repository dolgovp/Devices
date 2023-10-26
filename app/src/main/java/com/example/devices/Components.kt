package com.example.devices

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
@Preview
@Composable
fun Status(){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box (
            contentAlignment= Alignment.Center,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Color.Red)

        ){}
        Text("text")
    }
}
@Preview
@Composable
fun LoadingCard(){
    val paddingModifier = Modifier.padding(10.dp)
    Card(
        elevation = cardElevation(10.dp),
        modifier = paddingModifier
            //.fillMaxWidth()
            .width(160.dp)
            .height(100.dp)
    ) {
        Column(
            modifier = paddingModifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween) {
            Column(){
                Box(
                    Modifier
                        .background(Color.Gray)
                        .size(width = 40.dp, height = 20.dp)
                )
                Spacer(Modifier.size(8.dp))
                Box(
                    Modifier
                        .background(Color.Gray)
                        .size(width = 80.dp, height = 28.dp)
                )
            }
            Box(
                Modifier
                    .background(Color.Gray)
                    .size(width = 40.dp, height = 20.dp)
            )
        }
    }
}

