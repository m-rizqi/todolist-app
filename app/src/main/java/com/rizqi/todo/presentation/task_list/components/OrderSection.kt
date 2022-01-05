package com.rizqi.todo.presentation.task_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rizqi.todo.domain.util.OrderType
import com.rizqi.todo.domain.util.TaskOrder
import com.rizqi.todo.ui.theme.Poppins

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    taskOrder: TaskOrder = TaskOrder.Date(OrderType.Descending),
    onOrderChange: (TaskOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Sort by",
            style = TextStyle(
                fontFamily = Poppins,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color.Black
            )
        )
        Row(
            modifier = Modifier.wrapContentSize()
        ) {
            SortRadioButton(
                text = "Date",
                selected = taskOrder is TaskOrder.Date,
                onSelect = { onOrderChange(TaskOrder.Date(taskOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            SortRadioButton(
                text = "Title",
                selected = taskOrder is TaskOrder.Title,
                onSelect = { onOrderChange(TaskOrder.Title(taskOrder.orderType)) }
            )
        }
        Row(
            modifier = Modifier.wrapContentSize()
        ) {
            SortRadioButton(
                text = "Ascending",
                selected = taskOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(taskOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            SortRadioButton(
                text = "Descending",
                selected = taskOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(taskOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}

@Preview
@Composable
fun OrderSectionPreview() {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
    ) {
        OrderSection(
            onOrderChange = {}
        )
    }
}