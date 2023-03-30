package com.example.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.DB.TodoEntity
import com.example.todolist.databinding.ItemTodoBinding

class TodoRecyclerViewAdapter(private val todoList : ArrayList<TodoEntity>) : RecyclerView.Adapter<TodoRecyclerViewAdapter.MyviewHolder>(){
    //viewHolder 패턴이란? : 각 view 객체를 viewHolder에 보관해서 반복 메소드 호출을 줄여 성능을 향상
    inner class MyviewHolder(binding : ItemTodoBinding) : RecyclerView.ViewHolder(binding.root){
        val tv_importance = binding.tvImportance
        val tv_title = binding.tvTitle

        val root = binding.root
    }
    //view 객체를 만듦
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        //parent : recyclerview가 위치한 부모 레이아웃(현재 constraint layout)
        val binding : ItemTodoBinding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyviewHolder(binding)
    }
    //아이템의 개수를 반환해줌

    // 만든 viewHolder객체에 데이터를 넣어줌
    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        val todoData = todoList[position]

        //중요도에 따라 배경색을 변경해주기 위함
        when (todoData.importance){
            1->{
                holder.tv_importance.setBackgroundResource(R.color.red)
            }
            2->{
                //Q 기본값이 yellow인데 yellow를 지정해줘야하나? -> recyclerview는 view가 재활용되기 때문에 이전에 다른 색이 들어와 있었을 수 있음
                holder.tv_importance.setBackgroundResource(R.color.yellow)

            }
            3->{
                holder.tv_importance.setBackgroundResource(R.color.green)

            }

        }
        holder.tv_importance.text = todoData.importance.toString()
        holder.tv_title.text = todoData.title

    }
    override fun getItemCount(): Int {

        return todoList.size
    }



}