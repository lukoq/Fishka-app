package com.example.fishka.ui.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fishka.R
import com.example.fishka.model.Concept
import com.example.fishka.viewmodel.ConceptViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*


class AddFragment : Fragment() {

    private lateinit var mConceptViewModel: ConceptViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add, container, false)

        mConceptViewModel = ViewModelProvider(this).get(ConceptViewModel::class.java)





        view.add_btn.setOnClickListener(){
            insertDataToDatabase()
        }
        return view
    }

    private fun insertDataToDatabase() {
        val frontSide = addFrontSide_et.text.toString()
        val backSide = addBackSide_et.text.toString()
        if(inputCheck(frontSide, backSide)){
            val concept = Concept(0, frontSide, backSide)
            mConceptViewModel.addConcept(concept)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addFragment_to_nav_home)
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(frontSide: String, backSide: String): Boolean{
        return !(TextUtils.isEmpty(frontSide) && TextUtils.isEmpty(backSide))
    }

}