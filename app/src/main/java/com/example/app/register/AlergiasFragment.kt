package com.example.app.register

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

import com.example.app.databinding.FragmentAlergiasBinding

class AlergiasFragment : Fragment() {
    private var _binding: FragmentAlergiasBinding? = null
    private val binding get() = _binding!!
    private var alergias: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAlergiasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val todasAlergias = mutableListOf<ImageView>(
            binding.imageView6, binding.imageView7, binding.imageView8,
            binding.imageView11, binding.imageView12, binding.imageView13, binding.imageView14,
            binding.imageView15, binding.imageView16, binding.imageView17, binding.imageView18,
            binding.imageView19, binding.imageView20, binding.imageView21
        )
        todasAlergias.forEach { alergia ->

            alergia.setOnClickListener {
                if (!alergias.contains(alergia.tag.toString())){
                    añadirAlergia(alergia)
                }else{
                    removeAlergia(alergia)
                }

            }
        }
        binding.nextQuestion2.setOnClickListener{
            addData()
            (activity as? RegisterActivity)?.nextQuestion()
        }
    }

    private fun addData() {
        (activity as? RegisterActivity)?.addDato("allergies", alergias)
    }

    private fun removeAlergia(alergia: ImageView) {
        alergia.setColorFilter(null)
        alergias.remove(alergia.tag.toString())
    }

    private fun añadirAlergia(alergia: ImageView) {
        alergia.setColorFilter(Color.GRAY, android.graphics.PorterDuff.Mode.MULTIPLY)
        alergias.add(alergia.tag.toString())
    }


}