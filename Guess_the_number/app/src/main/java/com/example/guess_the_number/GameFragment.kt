package com.example.guess_the_number

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.guess_the_number.databinding.FragmentGameBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private var guess = 0
    private var guessCounter = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(layoutInflater)
        guess = (1..100).random()
        binding.btnSubmit.setOnClickListener() {
            onSubmitGuess()
        }
        return binding.root

    }
    private fun onSubmitGuess(){
        val userInput = binding.etUserguess.text.toString()
        if(userInput.isNotEmpty()){
            val userNum = userInput.toInt()
            if(userNum !=null && userNum in 1..100){
                checkNumbers(userNum)
            } else{
                Toast.makeText(context, "Please enter a number between 1 and 100", Toast.LENGTH_SHORT).show()
            }
        } else{
            Toast.makeText(context, "Please enter your guess", Toast.LENGTH_SHORT).show()
        }
    }
    private fun checkNumbers(userNum:Int){
        guessCounter++
        when{
            userNum == guess -> {
               binding.TVStatus.text = "Congratulations! You guessed the number in $guessCounter trials"
                CoroutineScope(Dispatchers.Main).launch {
                    delay(2000) // 2 seconds delay
                    resetGame()
                }
            }
            userNum > guess -> {
                binding.TVStatus.text = "Try a smaller number"
            }
            userNum < guess -> {
                binding.TVStatus.text =  "Try a larger number"
            }
        }
    }
    private fun resetGame() {
        guess = (1..100).random()
        guessCounter = 0
        binding.etUserguess.text?.clear()
        binding.TVStatus.text = "New game started! Enter a number between 1 and 100."
    }

}


