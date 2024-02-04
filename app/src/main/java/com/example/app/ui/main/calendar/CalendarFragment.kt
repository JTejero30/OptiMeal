package com.example.app.ui.main.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {
    private lateinit var monthTextView: TextView
    private lateinit var tvDaySelected: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)
        monthTextView = view.findViewById(R.id.monthTextView)
        tvDaySelected = view.findViewById(R.id.tvDaySelected)


        val position = arguments?.getInt("position", 0) ?: 0

        Log.d("comprobar", "----------------------------------------")

        Log.d("comprobar", "CalendarFragment onCreateView()")


        Log.d("comprobar", "Position?->$position")

        val calendar = Calendar.getInstance()

        //Calendar.getInstance recoge el calendario del telefono
        Log.d("comprobar", "calendar->${calendar}")


        // Con esto ajustamos el mes que corresponde a la posicion a la que se encuentra este fragment
        calendar.add(Calendar.MONTH, position - Integer.MAX_VALUE / 2)
        //Si no quisiera tener scroll infinito, habria que cambiar esto tambien, para que se ajuste bien el año a la posicion
        //calendar.add(Calendar.MONTH, position )


        //Fecha comleta
        Log.d("comprobar", "calendarFechaCompleta->${calendar.time}")

        //Mes y año
        val sdfMesyAno = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        Log.d("comprobar", "calendar mes->${sdfMesyAno.format(calendar.time)}")

        //Mes
        val sdfMes = SimpleDateFormat("MMMM", Locale.getDefault())
        Log.d("comprobar", "calendar mes->${sdfMes.format(calendar.time)}")
        Log.d("comprobar", "----------------------------------------")


        monthTextView.text = sdfMesyAno.format(calendar.time)

        //Aqui le indico que va a ir dentro del recyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.calendarRecyclerView)
        //Aqui le indico que el recyclerView va a ser horizontal
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        //Aqui le digo el contenido del recycller, llamo a la clase CalendarAdapter, pasandole las fechas del mes correspondiente
        recyclerView.adapter = CalendarAdapter(getDates(calendar),this)
        return view
    }

    private fun getDates(calendar: Calendar): List<DayDataClass> {
        //recojo el calendar que le paso de arriba
        val dates = mutableListOf<DayDataClass>()
        //Esto sirve para ver el numero total de dias
        val totalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        Log.d("comprobar", "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee")
        Log.d("comprobar", "Calendar.DAY_OF_MONTH->${Calendar.DAY_OF_MONTH}")
        Log.d("comprobar", "Calendar.DAY_OF_MONTH->${Calendar.DAY_OF_MONTH.toString()}")


        Log.d("comprobar", "calendar.minimalDaysInFirstWeek->${calendar.minimalDaysInFirstWeek}")

        Log.d("comprobar", "DatesTotalDays->$totalDays")

        //Aqui se le suma 1 porque los meses van de 0 a 11, y asi se guardan de forma convencional
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)

        Log.d("comprobar", "dates->$month")
        Log.d("comprobar", "dates->$year")

        Log.d("comprobar", "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee")


        //Esto me devulve el numero del dia y el nombre
        val dateFormat = SimpleDateFormat("dd EEE", Locale.getDefault())
        Log.d("comprobar", "dateFormat Borrar?->${dateFormat.format(calendar.time)}")
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentCalendar = Calendar.getInstance()

        val currentMonth = currentCalendar.get(Calendar.MONTH)+1
        Log.d("comprobar", "CurrentCalendar------>$currentMonth")


        for (i in 1..totalDays) {

            val dayDC = DayDataClass(dayOfMonthDc = i, monthDc = month, yearDc = year, isCurrentDayDc = false, isSelectedDc = false)
            if (i == currentDay && dayDC.monthDc==currentMonth) {
                dayDC.isCurrentDayDc = true
            }
            dates.add(dayDC)

            //Le restamos 1 porque el calendar set espera valores entre 0 y 11, y anteriormente lo guardamos de forma convencional
            //Cuando hacemos el set, estamos mofificando el objeto Calendar, modificando su estado interno para decirle exactamente el dia
            //calendar.set(year, month-1, i)
            //Aqui añadimos al arrayList el dia en formato adecuado
            //dates.add(dateFormat.format(calendar.time))
        }
        Log.d("comprobar", "dates->$dates")

        return dates
    }
    fun updateMonthText(selectedDay: DayDataClass) {
        tvDaySelected.text = ""+selectedDay.dayOfMonthDc+"/"+selectedDay.monthDc+"/"+selectedDay.yearDc
    }

    //Con el companion object creo una estacnia con cada mes, al llamar desde el CalendarPageAdapter, le paso la position
    //Y al meterlo en los argumentos del fragmento, lo recojo en el metodo onCreateView de arriba, para printear correctamente la posicion del mes actual
    companion object {
        fun newInstance(position: Int): CalendarFragment {
            val fragment = CalendarFragment()
            /**/
            val args = Bundle()
            args.putInt("position", position)
            fragment.arguments = args
            Log.d("comprobar", "fragment.arguments->${fragment.arguments}")

            return fragment
        }
    }
}
