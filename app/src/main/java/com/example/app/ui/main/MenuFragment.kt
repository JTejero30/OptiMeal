package com.example.app.ui.main

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.app.R
import com.example.app.databinding.FragmentMenuBinding
import com.example.app.ui.main.adapterWeek.WeekAdapter
import com.example.app.ui.main.model.DayModel
import com.example.app.ui.main.model.MenuModel
import com.example.app.ui.recipes.adapter.RecipesAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class MenuFragment : Fragment() {

    private var _binding:FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var menuViewModel: MenuViewModel

    private val TAG = "ComprobarSemana"




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater,container,false)

        menuViewModel = ViewModelProvider(this).get(MenuViewModel::class.java)


        val weekManager = WeekManager()
        val startDate = LocalDate.now()
        val daysOfWeek = weekManager.getDaysOfWeek(startDate)


        var weekModelList: MutableList<DayModel> = mutableListOf()

        val dateFormatter = DateTimeFormatter.ofPattern("E", Locale("es"))

        for (day in daysOfWeek) {
            day.format(dateFormatter)
            val dayModel = DayModel(
                calendarDayOfWeek = day.dayOfMonth,
                calendarMonth = day.month.getDisplayName(TextStyle.SHORT, Locale("es")), // Month name in Spanish
                dayOfWeek = day.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("es")), // Day of week name in Spanish
                year = day.year
            )
            weekModelList.add(dayModel)

            Log.d(TAG, day.toString())
            Log.d(TAG, day.dayOfMonth.toString())
            Log.d(TAG, day.dayOfWeek.toString())
            Log.d(TAG, day.year.toString())
            Log.d(TAG, day.month.toString())
            Log.d(TAG, day.monthValue.toString())
        }
/**/

        val rv = binding.weekRV
        rv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        rv.adapter = WeekAdapter(weekModelList)



/*
        for (day in daysOfWeek) {
            val textView = TextView(requireContext())
            textView.text = day.format(dateFormatter)
            textView.layoutParams = layoutParams
           binding.daysHL.addView(textView)
        }*/

        binding.loadingIndicator.visibility = View.VISIBLE
      //  binding.menuRV.visibility = View.GONE
        Log.d("MenuFragment", "MenuFragment rv=MenuAdapter ")
        lifecycleScope.launch(Dispatchers.Main) {
            menuViewModel.fetchData()
        }

        observeViewModel()

       /* lifecycleScope.launch(Dispatchers.Main) {
            /*val rv = binding.menuRV*/
            val data = menuViewModel.fetchData()
            binding.loadingIndicator.visibility = View.GONE
           // binding.menuRV.visibility = View.VISIBLE
            Log.d("MenuFragment", "MenuFragment rv=MenuAdapter $data")

            menuViewModel.menuModelL.observe()

            data?.let {
                /*rv.layoutManager = LinearLayoutManager(context)
                rv.adapter = RecipesAdapter(it)*/
                Log.d("MenuFragment", "MenuFragment rv=MenuAdapter $data")

                binding.nombreCard.text = it.toString()

               /* for (ingrediente in it.menu_del_dia.desayuno.ingredientes) {
                    val textView = TextView(binding.listaIngredientesCard.context)
                    textView.text = ingrediente.toString()
                    binding.listaIngredientesCard.addView(textView)
                }*/

                binding.displayIngredientes.setOnClickListener() {

                    if (binding.listaIngredientesCard.visibility == View.VISIBLE) {
                        binding.listaIngredientesCard.visibility = View.GONE
                        binding.displayIngredientes.setIconResource(R.drawable.baseline_arrow_drop_down_24)
                    } else {
                        binding.listaIngredientesCard.visibility = View.VISIBLE
                        binding.displayIngredientes.setIconResource(R.drawable.baseline_arrow_drop_up_24)
                    }
                }

                binding.displayMacros.setOnClickListener() {

                    if (binding.listaMacrosCard.visibility == View.VISIBLE) {
                        binding.listaMacrosCard.visibility = View.GONE
                        binding.displayMacros.setIconResource(R.drawable.baseline_arrow_drop_down_24)
                    } else {
                        binding.listaMacrosCard.visibility = View.VISIBLE
                        binding.displayMacros.setIconResource(R.drawable.baseline_arrow_drop_up_24)
                    }
                }



            }
        }
*/
        return binding.root
    }
    private fun observeViewModel() {
        menuViewModel.menuModelL.observe(viewLifecycleOwner) { menuModel ->
            // Update UI elements here with the new menuModel data
            binding.loadingIndicator.visibility = View.GONE

            menuModel?.let {

                Glide.with(binding.ivDesayuno.context).load(it.menu_del_dia.desayuno.imagen).into(binding.ivDesayuno)


                binding.nombreCardDesayuno.text = it.menu_del_dia.desayuno.plato

                 for (ingrediente in it.menu_del_dia.desayuno.ingredientes) {
                     val textView = TextView(binding.listaIngredientesCardDesayuno.context)
                     textView.text = ingrediente.toString()
                     binding.listaIngredientesCardDesayuno.addView(textView)
                 }

                binding.displayIngredientesDesayuno.setOnClickListener() {

                    if (binding.listaIngredientesCardDesayuno.visibility == View.VISIBLE) {
                        binding.listaIngredientesCardDesayuno.visibility = View.GONE
                        binding.displayIngredientesDesayuno.setIconResource(R.drawable.baseline_arrow_drop_down_24)
                    } else {
                        binding.listaIngredientesCardDesayuno.visibility = View.VISIBLE
                        binding.displayIngredientesDesayuno.setIconResource(R.drawable.baseline_arrow_drop_up_24)
                    }
                }

                binding.displayMacrosDesayuno.setOnClickListener() {

                    if (binding.listaMacrosCardDesayuno.visibility == View.VISIBLE) {
                        binding.listaMacrosCardDesayuno.visibility = View.GONE
                        binding.displayMacrosDesayuno.setIconResource(R.drawable.baseline_arrow_drop_down_24)
                    } else {
                        binding.listaMacrosCardDesayuno.visibility = View.VISIBLE
                        binding.displayMacrosDesayuno.setIconResource(R.drawable.baseline_arrow_drop_up_24)
                    }
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    // Inside MenuFragment class



    companion object {
        fun newInstance(): MenuFragment {
            val fragment = MenuFragment()

            return fragment
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)

class WeekManager {
    private val TAG = "ComprobarSemana"

    fun getCurrentWeek(): Pair<LocalDate, LocalDate> {
        val today = LocalDate.now()
        val startOfWeek = today.with(DayOfWeek.MONDAY)
        val endOfWeek = today.with(DayOfWeek.SUNDAY)
        Log.d(TAG, "getCurrentWeek: $startOfWeek - $endOfWeek")
        return Pair(startOfWeek, endOfWeek)
    }

    fun getPreviousWeek(): Pair<LocalDate, LocalDate> {
        val (startOfWeek, _) = getCurrentWeek()
        val startOfPreviousWeek = startOfWeek.minusWeeks(1)
        val endOfPreviousWeek = startOfPreviousWeek.with(DayOfWeek.SUNDAY)
        Log.d(TAG, "getPreviousWeek: $startOfPreviousWeek - $endOfPreviousWeek")
        return Pair(startOfPreviousWeek, endOfPreviousWeek)
    }

    fun getNextWeek(): Pair<LocalDate, LocalDate> {
        val (_, endOfWeek) = getCurrentWeek()
        val startOfNextWeek = endOfWeek.plusDays(1)
        val endOfNextWeek = startOfNextWeek.with(DayOfWeek.SUNDAY)
        Log.d(TAG, "getNextWeek: $startOfNextWeek - $endOfNextWeek")
        return Pair(startOfNextWeek, endOfNextWeek)
    }

    fun getDaysOfWeek(startDate: LocalDate): List<LocalDate> {
        val startOfWeek = startDate.with(DayOfWeek.MONDAY)
        val endOfWeek = startDate.with(DayOfWeek.SUNDAY)

        val daysOfWeek = mutableListOf<LocalDate>()
        var currentDay = startOfWeek
        while (currentDay.isBefore(endOfWeek) || currentDay == endOfWeek) {
            daysOfWeek.add(currentDay)
            currentDay = currentDay.plusDays(1)
        }

        return daysOfWeek
    }
}