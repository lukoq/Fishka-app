package com.example.fishka.ui.study

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.fishka.databinding.FragmentStudyBinding
import com.example.fishka.model.Concept
import com.example.fishka.viewmodel.ConceptViewModel
import kotlinx.android.synthetic.main.fragment_study.view.*


class StudyFragment : Fragment() {

    private var _binding: FragmentStudyBinding? = null
    private lateinit var mConceptViewModel: ConceptViewModel
    private val binding get() = _binding!!
    var conceptList = mutableListOf<Concept>()
    var conceptListUnknown = mutableListOf<Concept>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStudyBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val adapter = StudyAdapter()
        val viewpager2 = root.rootView.view_pager2

        mConceptViewModel = ViewModelProvider(this).get(ConceptViewModel::class.java)
        mConceptViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            conceptList = it as MutableList<Concept>

            if(conceptList.isNotEmpty()){
                adapter.setData(conceptList)
            }else{
                for(element in conceptListUnknown){
                    conceptList.add(element.copy())
                }
                adapter.setData(conceptList)
                conceptListUnknown.clear()

            }
        })

        viewpager2.adapter = adapter
        viewpager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewpager2.children.find { it is RecyclerView }?.let {
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, (ItemTouchHelper.UP or ItemTouchHelper.DOWN)) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return false
                }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    root.rootView.down.post {root.rootView.down.alpha = 0.0f}
                    root.rootView.up.post {root.rootView.up.alpha = 0.0f}

                    if(direction == 2){ // 1 == UP, 2 == DOWN
                        conceptListUnknown.add(conceptList[viewpager2.currentItem])
                    }

                    conceptList.removeAt(viewpager2.currentItem)
                    viewpager2.setCurrentItem(viewpager2.currentItem, true)
                    adapter.setData(conceptList)

                    if(conceptList.isEmpty()){
                        val action = StudyFragmentDirections.actionNavStudyToScoreFragment(conceptListUnknown.size)
                        findNavController().navigate(action)
                    }


                }
                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    root.rootView.down.post {root.rootView.down.alpha = dYtoAlpha(-dY)}
                    root.rootView.up.post {root.rootView.up.alpha = dYtoAlpha(dY)}
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }).attachToRecyclerView(it as RecyclerView)
        }

        return root
    }

    fun dYtoAlpha(dY: Float): Float {
        if(dY > 500){
            return 1.0f
        }else{
            return dY / 500.0f
        }
    }
}
