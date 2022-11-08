package com.mgm.fallingwords.ui.game

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mgm.fallingwords.R
import com.mgm.fallingwords.databinding.FragmentGameBinding
import com.mgm.fallingwords.models.QuestionModel
import com.mgm.fallingwords.viewmodels.GameViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GameFragment : Fragment() {
    //Binding
    private lateinit var binding: FragmentGameBinding

    //Other
    private val viewModel: GameViewModel by viewModels()
    private lateinit var animation: Animation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            //observe new question (word and translation)
            viewModel.question.observe(viewLifecycleOwner) {
                startQuestion(it)
            }
            //observe user score updates
            viewModel.scoreLiveData.observe(viewLifecycleOwner) { score ->
                txtScore.text = String.format(getString(R.string.score), score)
            }
            //observe when game is over
            viewModel.gameOverLiveData.observe(viewLifecycleOwner) { score ->
                context?.let {
                    txtTranslate.clearAnimation()
                    val directions = GameFragmentDirections.actionGameFragmentToGameResultSheet(score)
                    findNavController().navigate(directions)
                }
            }

            //Correct click
            btnCorrect.setOnClickListener {
                viewModel.onCorrectTransClicked()
            }
            //Wrong click
            btnWrong.setOnClickListener {
                viewModel.onWrongTransClicked()
            }
        }

    }

    fun startQuestion(questionModel: QuestionModel) {
        animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
            0.0f, Animation.RELATIVE_TO_SELF, 12.0f
        )

        animation.duration = 3000
        binding.apply {
            txtTranslate.startAnimation(animation)
            txtTranslate.text = questionModel.translation
            txtWord.text = questionModel.question
        }

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                viewModel.onNoAnswer()
            }
        })


    }

}