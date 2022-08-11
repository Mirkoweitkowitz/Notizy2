package de.syntaxinsitut.myapplication.adapter

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.syntaxinsitut.myapplication.R
import de.syntaxinsitut.myapplication.model.data.Task
import de.syntaxinsitut.myapplication.model.data.TaskGroup
import de.syntaxinsitut.myapplication.ui.TaskFragmentDirections

class TaskGroupAdapter(
    val dataset: List<TaskGroup>,
    val context: Context,
    val deleteCallback: (Long) -> Unit,
    var taskList: List<Task>?
) : RecyclerView.Adapter<TaskGroupAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val titleTV = itemview.findViewById<TextView>(R.id.tv_task_group_title)
        val doneTV = itemview.findViewById<TextView>(R.id.done_tv)
        val doneLabel = itemview.findViewById<TextView>(R.id.done_label)
        val openTV = itemview.findViewById<TextView>(R.id.open_tv)
        val openLabel = itemview.findViewById<TextView>(R.id.open_label)
        val deleteButton = itemview.findViewById<FloatingActionButton>(R.id.delete_task_group_button)
        val cl = itemview.findViewById<ConstraintLayout>(R.id.cl_task_group)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.taskgroup_item, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val taskGroup = dataset[position]
        val colorDrawable = ColorDrawable()

        var doneCount = 0
        var openCount = 0

        if (taskList != null) {

            val filtered = taskList!!.filter {
                it.groupId == taskGroup.id
            }

            for (task in filtered) {
                if (task.done) {
                    doneCount += 1
                } else {
                    openCount += 1
                }
            }
        }

        colorDrawable.color = (context.getColor(taskGroup.color))
//        holder.openLabel.background = colorDrawable
//        holder.openTV.background = colorDrawable
//        holder.doneTV.background = colorDrawable
//        holder.doneLabel.background = colorDrawable

        holder.cl.background = colorDrawable

        holder.titleTV.text = taskGroup.title

        holder.openTV.text = openCount.toString()
        holder.doneTV.text = doneCount.toString()

        holder.deleteButton.setOnClickListener {
            deleteCallback(taskGroup.id)
        }

        holder.cl.setOnClickListener {
            holder.itemView.findNavController().navigate(TaskFragmentDirections.actionTaskFragmentToTaskDetailFragment(taskGroup.id, taskGroup.title))
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}
