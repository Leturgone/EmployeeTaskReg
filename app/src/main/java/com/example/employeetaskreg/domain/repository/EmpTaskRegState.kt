package com.example.employeetaskreg.domain.repository

sealed class EmpTaskRegState<out R>  {
    data class Success<out R>(val result: R): EmpTaskRegState<R>()

    data object Waiting : EmpTaskRegState<Nothing>()

    data class Failure(val exception: Exception) :EmpTaskRegState<Nothing>()

    data object Loading : EmpTaskRegState<Nothing>()
}