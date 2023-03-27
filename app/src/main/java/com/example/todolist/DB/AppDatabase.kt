package com.example.todolist.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = arrayOf(TodoEntity::class), version = 1) // 조건1
abstract class AppDatabase : RoomDatabase(){

    companion object {
        val databaseName = "db_todo"
        var appDatabase : AppDatabase? = null

        //싱글톤 패턴
        fun getInstance(context : Context) : AppDatabase? {
            if (appDatabase == null){
                //null이면 databaseBuilder로 database 객체를 생성해줌
                appDatabase = Room.databaseBuilder(context, AppDatabase::class.java,
                databaseName).
                    //migration을 실패하면 db테이블을 재생성해줌, 이때 모든 데이터가 사라질 수도 있긴함
                        fallbackToDestructiveMigration().build()
            }
            //null이 아니면 바로 appDatabase를 반환해줌, 따라서 한 번 생성되고나면 계속 반환만 해주고 생성은 안되므로 객체 하나만 사용 가능

            return appDatabase
        }
    }

}