package com.example.todolist.DB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TodoDao {
    //데이터베이스에서 하고싶은 작업 3개를 함수를 통해서 작성해줌

    //get All, TodoEntity의 데이터를 리스트로 받음
    @Query("SELECT * FROM TodoEntity")
    fun getAllTodo() : List<TodoEntity>

    // insert todo
    @Insert
    fun insertTodo(todo : TodoEntity)
    //delete todo
    @Delete
    fun deleteTodo(todo : TodoEntity)
}