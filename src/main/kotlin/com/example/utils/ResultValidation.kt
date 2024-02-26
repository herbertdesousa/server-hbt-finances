package com.example.utils

sealed class ResultValidation {
    class Error(val code: Any) : Throwable()
    class Success : ResultValidation()
}