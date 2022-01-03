package com.rizqi.todo.domain.util

sealed class InvalidTaskException() : Exception("") {
    object TitleInvalidTaskException : InvalidTaskException()
    object DateInvalidTaskException : InvalidTaskException()
}