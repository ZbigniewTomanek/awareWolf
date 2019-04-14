package com.meetapp.ecoapp.ui.routines

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.meetapp.ecoapp.R
import com.meetapp.ecoapp.database.RoutineRepository
import com.meetapp.ecoapp.database.entities.Routine
import kotlinx.android.synthetic.main.routine_item.view.*

class RoutineListAdapter(routineEvents: RoutineEvents)
    : RecyclerView.Adapter<RoutineListAdapter.ViewHolder>(), Filterable {

    private var filteredRoutineList: List<Routine> = arrayListOf()
    private val listener: RoutineEvents = routineEvents
    private val repository = RoutineRepository.instance()
    private var routinesList = repository.getAllRoutinesAsList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.routine_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = filteredRoutineList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredRoutineList[position], listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val repository = RoutineRepository.instance()

        fun bind(routine: Routine, listener: RoutineEvents) {
            itemView.tv_item_title.text = routine.routineName
            itemView.tv_item_content.text = "Raz na ${repository.getFrequencyName(routine.freqId)}" //TODO poprawić wyświetlania

            itemView.iv_item_delete.setOnClickListener {
                listener.onDeleteClicked(routine)
            }

            itemView.setOnClickListener {
                listener.onViewClicked(routine)
            }
        }
    }



    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val rrJoinList = repository.getAllrrJoins()
                val charString = p0.toString()
                filteredRoutineList = if (charString.isEmpty()) {
                    routinesList
                } else {
                    val filteredList = arrayListOf<Routine>()
                    for (row in routinesList) {
                        val resources = rrJoinList.filter { it.routineId == row.routineId }

                        var inRes = false
                        for (resJoin in resources) {
                            val names =  repository.getResourcesNames(resJoin.resourceId)
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