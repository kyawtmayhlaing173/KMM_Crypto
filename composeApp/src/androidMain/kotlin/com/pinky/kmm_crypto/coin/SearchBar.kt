package com.pinky.kmm_crypto.coin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SearchTextField(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            singleLine = true,
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon", tint = Color.DarkGray)
            },
            trailingIcon = {
                if (text.isNotEmpty())
                    IconButton(onClick = { onCloseClick() }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close Icon")
                    }
            },
            shape = RoundedCornerShape(20.dp),
            placeholder = { Text(text = "Search by name or symbol ...", color = Color.LightGray) },
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}