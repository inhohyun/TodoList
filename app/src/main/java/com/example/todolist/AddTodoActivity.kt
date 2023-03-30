package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todolist.DB.AppDatabase
import com.example.todolist.DB.TodoDao
import com.example.todolist.DB.TodoEntity
import com.example.todolist.databinding.ActivityAddTodoBinding
import com.example.todolist.databinding.ActivityMainBinding

class AddTodoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddTodoBinding
    //여기서도 데이터를 추가할 것이므로 db를 가져와야 함
    lateinit var db : AppDatabase
    lateinit var todoDao : TodoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        todoDao = db.getTodoDao()

        binding.btnComplete.setOnClickListener {
            insertTodo()
        }

    }

    private fun insertTodo(){
        val todoTitle = binding.edtTitle.text.toString()
        //radio 버튼에 클릭된 버튼의 id를 가져옴
        var todoImportance = binding.radioGroup.checkedRadioButtonId

        var impData = 0
        when(todoImportance){
            R.id.btn_high->{
                impData = 1
            }
            R.id.btn_medium->{
                impData = 2
            }
            R.id.btn_low->{
                impData = 3
            }
        }
        //radio 버튼이 아무 클릭이 안되었으면
        if(impData == 0 || todoTitle.isBlank()){
            Toast.makeText(this, "모든 항목을 채워주세요", Toast.LENGTH_SHORT).show()

        }else{
            //db작업은 백그라운드 Thread에서 실행하여 메인 Thread 부담 덜어주기
            Thread{
                //id 값은 자동으로 추가되므로 null로 표시
                todoDao.insertTodo(TodoEntity(null, todoTitle, impData))
                runOnUiThread {
                    Toast.makeText(this, "할 일이 추가되었습니다.", Toast.LENGTH_SHORT).show()
                    finish() //백 스택에 있던 mainActivity로 화면이 전환됩니다.

                }
            }.start()
        }

    }
}