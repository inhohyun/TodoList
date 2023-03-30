package com.example.todolist

interface OnItemLongClickLietener {
    //MainAcitivity에서 override 하여 사용할 얘정
    fun onLongClick(positon : Int)
}