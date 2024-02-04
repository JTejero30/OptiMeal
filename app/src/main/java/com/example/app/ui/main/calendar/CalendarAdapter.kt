package com.example.app.ui.main.calendar


import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R


class CalendarAdapter(private val dates: List<DayDataClass>,private val calendarFragment: CalendarFragment) :
    RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {
    private var selectedPosition: Int = -1
    val tvSelectedDayText = ""


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calendar_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Log.d("comprobar", "dates[position] que mostrar->${dates[position]}")
        //Esto sirve para pintar as fechas correspondientes segun se va deslizando el recycler
        //Llama a la nested class ViewHolder de abajo pasandole la fecha correspondiente que debe pintar
        holder.bind(dates[position])

    }

    override fun getItemCount(): Int {
        return dates.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val calendarDayTextView: TextView = itemView.findViewById(R.id.calendarDay)

        init {
            calendarDayTextView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val date = dates[position]
                    Toast.makeText(itemView.context, date.dayOfMonthDc.toString(), Toast.LENGTH_SHORT).show()

                    if (selectedPosition != -1) {
                        // Deselect the previously selected date
                        dates[selectedPosition].isSelectedDc = false
                        notifyItemChanged(selectedPosition)
                    }

                    // Mark the clicked date as selected and update selectedPosition
                    date.isSelectedDc = true
                    selectedPosition = position

                    // Notify the adapter that the clicked date has changed
                    notifyItemChanged(position)
                    calendarFragment.updateMonthText(date)

                }
            }
        }

        fun bind(date: DayDataClass) {
            calendarDayTextView.text = date.dayOfMonthDc.toString()
            calendarDayTextView.setBackgroundColor(if (date.isCurrentDayDc) Color.GRAY else Color.WHITE)
            if (date.isSelectedDc) {
                calendarDayTextView.setBackgroundColor(Color.BLUE)

            }
        }


    }
}
