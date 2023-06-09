package com.example.todolist

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.DB.AppDatabase
import com.example.todolist.DB.TodoDao
import com.example.todolist.DB.TodoEntity
import com.example.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() , OnItemLongClickLietener{
    private lateinit var binding : ActivityMainBinding
    //db에서 가져올 데이터를 저장할 변수 지정
    private lateinit var db : AppDatabase
    private lateinit var todoDao : TodoDao
    private lateinit var todoList: ArrayList<TodoEntity>
    private lateinit var adapter : TodoRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddTodo.setOnClickListener {
            val intent = Intent(this, AddTodoActivity::class.java)
            startActivity(intent)
        }
// 데이터베이스 객체를 싱글톤 패턴으로 만들었기 떄문에 getInstance 함수를 호출하여 사용 가능

        //1. db를 설정해줌
        db == AppDatabase.getInstance(this)!!
        todoDao = db.getTodoDao()
        //2. todoList를 가져옴
        getAllTodoList()

    }

    private fun getAllTodoList(){
        //db관련된 작업은 백그라운드 Thread에서 처리해 줄 것임 : mainThread의 부담을 덜어주기 위함
        Thread{
            todoList = ArrayList(todoDao.getAllTodo())
            //3. 가져온 todoList를 recyclerView에 보여줌
            setRecyclerView()
            //Thread를 start를 해줘야 실행이 됨
        }.start()
    }

    //setRecyclerView는 view resource에 접근을 합니다. 그런데 위를 보면 Thread에서 호출하므로 백그라운드 Thread에서 불립니다.
    //따라서 runOnUiThread를 활용해 메인 Thread에서 실행하여야 합니다.
    private fun setRecyclerView(){
        //아래에서 this는 MainActivity 객체를 의미합니다. 이때 MainAcitivty는 OnItemLongClickLietener를 구현하고 있기 때문에 LongClick 대신 this로 보내면 됨
        adapter = TodoRecyclerViewAdapter(todoList, this)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
    }
    //액티비티 생명주기 중 다른 액티비티로 갔다가 다시 돌아왔을 때 불러오는 메소드
    override fun onRestart(){
        super.onRestart()
        getAllTodoList()

    }

    override fun onLongClick(positon: Int) {
        //recyckerview를 길게 누를시 팝업창 객체를 생성
      val builder : AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.alert_title))
        builder.setMessage(getString(R.string.alert_message))
        builder.setNegativeButton(getString(R.string.alert_no), null)
        builder.setPositiveButton(getString(R.string.alert_yes),
            //object를 활용해 익명클래스 작성, 버튼을 클릭시 할 일 작성
            object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                deleteTodo(positon)
            }
        }
        )
        builder.show()

    }
    private fun deleteTodo(positon: Int){
        Thread{
            todoDao.deleteTodo(todoList[positon])
            //저장된 리스트에서 삭제할 부분 삭제하기
            todoList.removeAt(positon)
            //데이터를 수정해줬으니 view도 수정해줌
            runOnUiThread {
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "삭제되었습니다", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }
}
