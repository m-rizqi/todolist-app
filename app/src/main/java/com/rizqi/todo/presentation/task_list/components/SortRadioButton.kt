package com.rizqi.todo.presentation.task_list.components

import android.widget.RadioButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rizqi.todo.ui.theme.BlueSoft
import com.rizqi.todo.ui.theme.GreyC4

@Composable
fun SortRadioButton(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = BlueSoft,
                unselectedColor = GreyC4
            )
        )
        Text(text = text, style = MaterialTheme.typography.body1)
    }
}


@Preview
@Composable
fun SortRadioButtonPreview() {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SortRadioButton(
            "Date",false, {}, Modifier
        )
    }
}