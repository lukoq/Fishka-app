package com.example.fishka.ui.score

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fishka.Presets
import com.example.fishka.R
import kotlinx.android.synthetic.main.fragment_score.view.*
import nl.dionsegijn.konfetti.xml.KonfettiView

class ScoreFragment : Fragment() {

    private val args by navArgs<ScoreFragmentArgs>()
    private lateinit var viewConfetti: KonfettiView

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_score, container, false)
        viewConfetti = view.confetti

        requireActivity().onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                Navigation.findNavController(view).popBackStack(R.id.nav_home, false)
            }
        })

        if(args.conceptListSize > 1){
            view.info_text.setText("You have ${ args.conceptListSize} concepts left to learn.")
            festive()
        }
        else if(args.conceptListSize == 1){
            view.info_text.setText("You have only 1 concept left to learn.")
            explode()
        }
        else{
            view.continue_btn.visibility = View.INVISIBLE
            view.info_text.setText("You done everything.")
            rain()
        }
        view.startover_btn.setOnClickListener(){
            findNavController().navigate(R.id.action_scoreFragment_to_nav_study)
        }
        view.continue_btn.setOnClickListener(){
            findNavController().popBackStack(R.id.nav_study, false)
        }


        return view
    }
    private fun festive() {
        viewConfetti.start(Presets.festive())
    }
    private fun explode() {
        viewConfetti.start(Presets.explode())
    }
    private fun rain() {
        viewConfetti.start(Presets.rain())
    }







}


