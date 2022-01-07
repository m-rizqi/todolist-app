package com.rizqi.todo.presentation.task_list.components

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rizqi.todo.R
import com.rizqi.todo.domain.model.Task
import com.rizqi.todo.presentation.task_list.TaskViewModel
import com.rizqi.todo.ui.theme.*
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun TaskCard(
    task: Task,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit,
    viewModel: TaskViewModel
) {
    val taskPercentage = remember {
        mutableStateOf(0F)
    }
    LaunchedEffect(key1 = true){
        this.launch {
            task.taskId?.let {
                taskPercentage.value = viewModel.getPrecentageCompleted(it)
            }

        }
    }
   Box(modifier = modifier){
       Card(
           elevation = 5.dp,
           shape = RoundedCornerShape(8.dp),
           backgroundColor = Color.White,
       ) {
           Column(
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(8.dp)
           ) {
               Row(
                   modifier = Modifier.fillMaxWidth(),
               ) {
                   Row(
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(end = 8.dp),
                       verticalAlignment = Alignment.CenterVertically,
                       horizontalArrangement = Arrangement.SpaceBetween
                   ){
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
                           onClick = onDeleteClick
                       ) {
                           Icon(
                               modifier = Modifier.size(14.dp),
                               painter = painterResource(id = R.drawable.trash_alt_regular),
                               contentDescription = "Delete",
                               tint = Color.Black
                           )
                       }
                   }
               }
               Spacer(modifier = Modifier.height(3.dp))
               Text(
                   text = if(task.timestamp == 0L) "Due date hasn't set yet" else viewModel.dateFormatter(task.timestamp),
                   style = TextStyle(
                       fontFamily = Poppins,
                       fontWeight = FontWeight.Normal,
                       color = if(task.timestamp == 0L) Color.Red else GreyE8,
                       fontSize = 12.sp
                   )
               )
               Spacer(modifier = Modifier.height(3.dp))
               Row(
                   modifier = Modifier.fillMaxWidth(),
                   verticalAlignment = Alignment.CenterVertically
               ) {
                   Slider(
                       modifier = Modifier.weight(1f),
                       value = taskPercentage.value,
                       enabled = false,
                       valueRange = 0f..1f,
                       onValueChange = {},
                       colors = SliderDefaults.colors(
                           thumbColor = Color.Transparent,
                           disabledThumbColor = Color.Transparent,
                           disabledActiveTrackColor = when(taskPercentage.value){
                               in 0F..0.3F -> {
                                   RedToDo
                               }
                               in 0.3F..0.6F -> {
                                   OrangeInProgress
                               }
                               in 0.6F..0.95F -> {
                                   GreenComplete
                               }
                               in 0.95F..1F -> {
                                   BlueSoft
                               }
                               else -> {
                                   GreyC4
                               }
                           },
                           disabledInactiveTickColor = GreyE8
                       )
                   )
                   Text(
                       modifier = Modifier,
                       text = "${(taskPercentage.value * 100).toInt()} %",
                       style = TextStyle(
                           fontFamily = Poppins,
                           fontWeight = FontWeight.Normal,
                           color = Color.Black.copy(alpha = 0.8F),
                           fontSize = 12.sp
                       )
                   )
               }
               Spacer(modifier = Modifier.height(8.dp))
           }
       }
   }
}

@ExperimentalMaterialApi
@Preview(
    name = "Task Card #1",
    showBackground = true
)
@Composable
fun TaskCardPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TaskCard(
            task = Task(1, "Task Card #1","Lorem Ipsum", System.currentTimeMillis()),
            onDeleteClick = {},
            viewModel = hiltViewModel()
        )
    }
}