package com.rizqi.todo.presentation.task_list.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rizqi.todo.R
import com.rizqi.todo.domain.model.Task
import com.rizqi.todo.presentation.task_list.formatLongToDate
import com.rizqi.todo.ui.theme.GreyE8
import com.rizqi.todo.ui.theme.OrangeInProgress
import com.rizqi.todo.ui.theme.Poppins
import com.rizqi.todo.ui.theme.Shapes

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@Composable
fun TaskCard(
    task: Task
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 2.dp,
        onClick = {},
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = task.title,
                    style = TextStyle(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        fontSize = 18.sp
                    )
                )
                
                IconButton(
                    modifier = Modifier.size(14.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        modifier = Modifier.size(14.dp),
                        painter = painterResource(id = R.drawable.trash_alt_regular),
                        contentDescription = "Delete",
                        tint = Color.Black
                    )
                }
            }
            Text(
                text = "formatLongToDate(task.timestamp)",
                style = TextStyle(
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    color = GreyE8,
                    fontSize = 12.sp
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Slider(
                modifier = Modifier.fillMaxWidth(),
                value = 0.25f,
                enabled = false,
                valueRange = 0f..1f,
                onValueChange = {},
                colors = SliderDefaults.colors(
                    thumbColor = Color.Transparent,
                    disabledThumbColor = Color.Transparent,
                    disabledActiveTrackColor = OrangeInProgress,
                    disabledInactiveTickColor = GreyE8
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterialApi
@Preview(
    name = "Task Card #1",
    showBackground = true
)
@Composable
fun TaskCardPreview() {
    TaskCard(
        task = Task(1, "Task Card #1","Lorem Ipsum", System.currentTimeMillis())
    )
}