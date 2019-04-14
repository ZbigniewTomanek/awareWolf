package com.meetapp.ecoapp.ui.routines

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.meetapp.ecoapp.R

import com.meetapp.ecoapp.database.entities.Resource
import com.meetapp.ecoapp.database.entities.Routine
import com.meetapp.ecoapp.database.entities.RoutineResourceJoin
import kotlinx.android.synthetic.main.routine_item.view.*

class RoutineListAdapter(routineEvents: RoutineEvents, var routinesList: List<Routine>, var resourcesList: List<Resource>, var rrJoinList: List<RoutineResourceJoin>)
    : RecyclerView.Adapter<RoutineListAdapter.ViewHolder>(), Filterable {

    private var filteredRoutineList: List<Routine> = arrayListOf()
    private val listener: RoutineEvents = routineEvents

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.routine_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = filteredRoutineList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredRoutineList[position], listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(routine: Routine, listener: RoutineEvents) {
            itemView.tv_item_title.text = routine.routineName
            itemView.tv_item_content.text = "chuj"

            itemView.iv_item_delete.setOnClickListener {
                listener.onDeleteClicked(routine)
            }

            itemView.setOnClickListener {
                listener.onViewClicked(routine)
            }
        }
    }

    fun getResourcesNames(routineId: Int): List<String> {
        val ids = rrJoinList.filter { it.routineId == routineId }.map { it.resourceId }
        return resourcesList.map { if (it.resourceId in ids) it.resourceName else ""}.filter { it != "" }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charString = p0.toString()
                filteredRoutineList = if (charString.isEmpty()) {

                    routinesList

                } else {
                    val filteredList = arrayListOf<Routine>()
                    for (row in routinesList) {
                        val resources = rrJoinList.filter { it.routineId == row.routineId }

                        var inRes = false
                        for (resJoin in resources) {
                            val names =  getResourcesNames(resJoin.resourceId)
                            for (resName in names)
                                if (charString.toLowerCase() in resName)
                                    inRes = true
                        }

                        if (row.routineName.toLowerCase().contains(charString.toLowerCase())
                            || inRes) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = filteredRoutineList
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filteredRoutineList = p1?.values as List<Routine>
                notifyDataSetChanged()
            }

        }
    }

    fun setAllRoutineItems(routineItems: List<Routine>) {
        this.routinesList = routineItems
        this.filteredRoutineList = routineItems
        notifyDataSetChanged()
    }


    interface RoutineEvents {
        fun onDeleteClicked(routine: Routine)
        fun onViewClicked(routine: Routine)
    }
}