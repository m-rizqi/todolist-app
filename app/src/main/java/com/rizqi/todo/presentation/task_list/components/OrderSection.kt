package com.rizqi.todo.presentation.task_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rizqi.todo.domain.util.OrderType
import com.rizqi.todo.domain.util.TaskOrder

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    taskOrder: TaskOrder = TaskOrder.Date(OrderType.Descending),
    onOrderChange: (TaskOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
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
                onSelect = { onOrderChange(TaskOrder.Date(taskOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
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
        modifier = Modifier.fillMaxSize(),
    ) {
        OrderSection(
            onOrderChange = {}
        )
    }
}