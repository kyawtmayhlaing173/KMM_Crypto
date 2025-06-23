package com.pinky.kmm_crypto.presentation.market

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pinky.kmm_crypto.model.StatisticModel
import com.pinky.kmm_crypto.utility.formatWithAbbreviations

@Composable
fun StatisticView(
    stat: StatisticModel,
) {
    Column {
        Text(
            stat.title,
            style = TextStyle(
                fontSize = 14.sp,
                color = Color.Gray,
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            stat.value,
            style = TextStyle(
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        if (stat.percentageChange != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Triangle Icon",
                    modifier = Modifier.size(24.dp),
                    tint = if (stat.percentageChange >= 0) Color.Green else Color.Red
                )
                Text(
                    "${stat.percentageChange.formatWithAbbreviations()} %",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = if (stat.percentageChange >= 0) Color.Green else Color.Red
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun StatisticViewPreview() {
//    StatisticView()
}