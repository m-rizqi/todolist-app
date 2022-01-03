package com.rizqi.todo.domain.util

sealed class TaskOrder(val orderType: OrderType){
    class Title(orderType: OrderType) : TaskOrder(orderType)
    class Date(orderType: OrderType) : TaskOrder(orderType)
}
