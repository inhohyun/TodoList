package com.example.todolist.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//어노테이션을 활용해 data class를 데이터베이스의 테이블(Entitiy)로 사용할 수 있도록 함
@Entity
data class TodoEntity (
    @PrimaryKey(autoGenerate = true) var id : Int? = null,
    @ColumnInfo(name = "title") var title : String,
    @ColumnInfo(name = "importance") var importance : Int


)