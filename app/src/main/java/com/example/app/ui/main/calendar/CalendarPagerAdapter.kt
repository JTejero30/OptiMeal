package com.example.app.ui.main.calendar

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class CalendarPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

    override fun getItem(position: Int): Fragment {
        Log.d("comprobar", "postionItem->$position")
        Log.d("comprobar", "CalendarPagerAdapter Creating fragment at position: $position")

        //return CalendarFragment.newInstance(position)
        return CalendarFragment.newInstance(position)

    }

    override fun getCount(): Int {
        // Esto sirve para limitar el scroll, ponemos el mas grande para permitir el scroll infinito
        //El return me retornará el numero de paginas que pueden existir, SIEMPRE creará automaticamente las paginas anterior y posterior
        return Integer.MAX_VALUE

        //Si por ejemplo le pongo 2, tendra un limite de 2 paginas, y dependiendo de por que paginas empiece (indicandolo en el aminActivity),
        // me creara la pagina siguiente o la anterior (Si le digo que empiece por la pagina 0, que seria la primera, y le pongo return 3, me va a crear solo la pagina siguiente
        //return 3

    }
}
