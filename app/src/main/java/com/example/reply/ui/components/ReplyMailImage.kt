package com.example.reply.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import coil.compose.AsyncImage
import com.example.reply.data.Email


/*
Displays image just from url
*/
@Composable
fun ReplyMailImage(
    url: String?
) {
    if (url != null) {
        AsyncImage(
            model = url,
            contentDescription = "Mail preview" /*fosdfj*/
        )
    }
}
