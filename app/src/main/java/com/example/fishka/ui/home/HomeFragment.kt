package com.example.fishka.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fishka.R
import com.example.fishka.databinding.FragmentHomeBinding
import com.example.fishka.viewmodel.ConceptViewModel
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var mConceptViewModel: ConceptViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter = HomeAdapter()
        val recyclerView = root.rootView.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )


        mConceptViewModel = ViewModelProvider(this).get(ConceptViewModel::class.java)
        mConceptViewModel.readAllData.observe(viewLifecycleOwner, Observer { concept ->
            adapter.setData(concept)
        })

        root.rootView.add_btn.setOnClickListener(){
            findNavController().navigate(R.id.action_nav_home_to_addFragment)
        }

        setHasOptionsMenu(true);
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteAllConcepts()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun deleteAllConcepts() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_, _->
            mConceptViewModel.deleteAllConcepts()
            Toast.makeText(requireContext(), "Successfully removed everything!", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_, _-> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you wanna delete everything?")
        builder.create().show()
    }
}