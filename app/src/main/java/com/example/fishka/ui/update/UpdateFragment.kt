package com.example.fishka.ui.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fishka.R
import com.example.fishka.model.Concept
import com.example.fishka.viewmodel.ConceptViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mConceptViewModel: ConceptViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment



        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mConceptViewModel = ViewModelProvider(this).get(ConceptViewModel::class.java)

        view.updateFrontSide_et.setText(args.customConcept.frontSide)
        view.updateBackSide_et.setText(args.customConcept.backside)

        view.update_btn.setOnClickListener(){
            updateItem()
        }
        setHasOptionsMenu(true);
        return view
    }


    private fun updateItem(){
        val frontSide = updateFrontSide_et.text.toString()
        val backSide = updateBackSide_et.text.toString()

        if(inputCheck(frontSide, backSide)){
            val updatedConcept = Concept(args.customConcept.id, frontSide, backSide)
            mConceptViewModel.updateConcept(updatedConcept)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_nav_home)
        }else{
            Toast.makeText(requireContext(), "Please field out all fields!", Toast.LENGTH_SHORT).show()
        }

    }
    private fun inputCheck(frontSide: String, backSide: String): Boolean{
        return !(TextUtils.isEmpty(frontSide) && TextUtils.isEmpty(backSide))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteConcept()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteConcept() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_, _->
            mConceptViewModel.deleteConcept(args.customConcept)
            Toast.makeText(requireContext(), "Successfully removed!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_nav_home)
        }
        builder.setNegativeButton("No"){_, _-> }
        builder.setTitle("Delete ${args.customConcept.frontSide}?")
        builder.setMessage("Are you sure you wanna delete ${args.customConcept.frontSide}?")
        builder.create().show()
    }
}