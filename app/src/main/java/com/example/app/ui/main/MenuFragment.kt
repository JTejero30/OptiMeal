package com.example.app.ui.main

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.app.R
import com.example.app.databinding.FragmentMenuBinding
import com.example.app.ui.main.adapterWeek.WeekAdapter
import com.example.app.ui.main.detailFragment.DialogMenuFragment
import com.example.app.ui.main.model.DayModel
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso


class MenuFragment : Fragment(), DayItemClickI {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var menuViewModel: MenuViewModel

    private val TAG = "ComprobarSemana"

    val storage = Firebase.storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)

        menuViewModel = ViewModelProvider(this).get(MenuViewModel::class.java)

        menuViewModel.getDates()
        menuViewModel.weekModelL.observe(viewLifecycleOwner) { weekModelList ->
            weekModelList?.let {
                Log.d("MenuFragment", "weekMoidel--> ${weekModelList.toString()}")
                val rv = binding.weekRV
                rv.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

                //Indico la posicion inical del recycler al dia actual
                rv.scrollToPosition(weekModelList.indexOfFirst { it.isCurrentDay })

                //aqui creo el weekAdapter con el listener, que será este fragment
                rv.adapter = WeekAdapter(weekModelList, this)
            }
        }

        /*  binding.loadingIndicator.visibility = View.VISIBLE
          binding.menuSV.visibility = View.GONE*/


        lifecycleScope.launch(Dispatchers.Main) {
            menuViewModel.fetchData(LocalDate.now())
            observeViewModel()
        }

        return binding.root
    }

    private fun observeViewModel() {
        menuViewModel.menuModelL.observe(viewLifecycleOwner) { menuModel ->

            menuModel?.let {
                Log.d("MenuFragment", "weekMoidel--> ${menuModel.toString()}")

                //Desayuno
                binding.nombreCardDesayuno.text = it.menu_del_dia.desayuno.plato
                /* Glide.with(binding.ivDesayuno.context).load(it.menu_del_dia.desayuno.imagen)
                     .into(binding.ivDesayuno)*/

                val imageNameDesayuno = it.menu_del_dia.desayuno.imagen

                Log.d("MenuFragment", "IMAGE REGEX--> ${imageNameDesayuno}")


                val imageRefDesayuno =
                    storage.reference.child("platos_nutri/$imageNameDesayuno")
                imageRefDesayuno.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    /*     Glide.with(binding.ivDesayuno.context).load(imageUrl)
                             .into(binding.ivDesayuno)*/
                    Picasso.get()
                        .load(imageUrl)
                        .into(binding.ivDesayuno, object : com.squareup.picasso.Callback {
                            override fun onSuccess() {
                                binding.loadingIndicatorDesayuno.visibility = View.GONE
                                binding.DesayunoCV.visibility = View.VISIBLE


                                /*     binding.loadingIndicator.visibility = View.GONE
                                     binding.menuSV.visibility = View.VISIBLE*/
                            }

                            override fun onError(e: Exception?) {

                                binding.loadingIndicatorDesayuno.visibility = View.VISIBLE
                                binding.DesayunoCV.visibility = View.GONE
                                /* binding.loadingIndicatorDesayuno.visibility=View.VISIBLE

                                 binding.loadingIndicator.visibility = View.VISIBLE
                                 binding.menuSV.visibility = View.GONE
                                 binding.DesayunoCV.visibility=View.VISIBLE*/

                                Log.e("MenuFragment", "Error getting download URL", e)
                            }
                        })

                }.addOnFailureListener { exception ->
                    Log.e("MenuFragment", "Error getting download URL", exception)
                }

                binding.ivDesayuno.setOnClickListener {
                    val dialogFragment = DialogMenuFragment()
                    dialogFragment.setMenuModel(menuModel.menu_del_dia.desayuno)

                    dialogFragment.show(childFragmentManager, DialogMenuFragment.TAG)
                }


                Log.d("MenuFragment", "Menu--> ${it.menu_del_dia.desayuno.toString()}")

                //Comida
                val imageNameComida = it.menu_del_dia.comida.imagen
                // val imageRef = storage.reference.child("comidas_wetaca/$imageName.jpg")

                val imageRef = storage.reference.child("platos_nutri/$imageNameComida")
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    /*     Glide.with(binding.ivDesayuno.context).load(imageUrl)
                             .into(binding.ivDesayuno)*/
                    Picasso.get()
                        .load(imageUrl)
                        .into(binding.ivComida, object : com.squareup.picasso.Callback {
                            override fun onSuccess() {
                                binding.loadingIndicatorComida.visibility = View.GONE
                                binding.ComidaCV.visibility = View.VISIBLE
                            }

                            override fun onError(e: Exception?) {
                                binding.loadingIndicatorComida.visibility = View.VISIBLE
                                binding.ComidaCV.visibility = View.GONE
                                Log.e("MenuFragment", "Error getting download URL", e)
                            }
                        })

                }.addOnFailureListener { exception ->
                    Log.e("MenuFragment", "Error getting download URL", exception)
                }


                binding.ivComida.setOnClickListener {
                    val dialogFragment = DialogMenuFragment()
                    dialogFragment.setMenuModel(menuModel.menu_del_dia.comida)

                    dialogFragment.show(childFragmentManager, DialogMenuFragment.TAG)
                }

                binding.nombreCardComida.text = it.menu_del_dia.comida.plato


                //Cena
                /* Glide.with(binding.ivCena.context).load(it.menu_del_dia.cena.imagen)
                     .into(binding.ivCena)*/

                val imageNameCena = it.menu_del_dia.cena.imagen

                val imageRefCena = storage.reference.child("platos_nutri/$imageNameCena")
                imageRefCena.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    /*     Glide.with(binding.ivDesayuno.context).load(imageUrl)
                             .into(binding.ivDesayuno)*/
                    Picasso.get()
                        .load(imageUrl)
                        .into(binding.ivCena, object : com.squareup.picasso.Callback {
                            override fun onSuccess() {
                                binding.loadingIndicatorCena.visibility = View.GONE
                                binding.CenaCV.visibility = View.VISIBLE
                            }

                            override fun onError(e: Exception?) {
                                binding.loadingIndicatorCena.visibility = View.VISIBLE
                                binding.CenaCV.visibility = View.GONE
                                Log.e("MenuFragment", "Error getting download URL", e)
                            }
                        })

                }.addOnFailureListener { exception ->
                    Log.e("MenuFragment", "Error getting download URL", exception)
                }

                binding.ivCena.setOnClickListener {
                    val dialogFragment = DialogMenuFragment()
                    dialogFragment.setMenuModel(menuModel.menu_del_dia.cena)


                    dialogFragment.show(childFragmentManager, DialogMenuFragment.TAG)
                }

                binding.nombreCardCena.text = it.menu_del_dia.cena.plato


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

    override fun onDayItemClicked(dayModel: DayModel) {
        Log.d("MenuFragment", "dayModel--> ${dayModel}")


        binding.loadingIndicatorDesayuno.visibility = View.VISIBLE
        binding.DesayunoCV.visibility = View.GONE
        binding.loadingIndicatorComida.visibility = View.VISIBLE
        binding.ComidaCV.visibility = View.GONE
        binding.loadingIndicatorCena.visibility = View.VISIBLE
        binding.CenaCV.visibility = View.GONE


        lifecycleScope.launch(Dispatchers.Main) {

            menuViewModel.fetchData(dayModel.day)
            delay(1500)
            observeViewModel()
        }
    }
}

