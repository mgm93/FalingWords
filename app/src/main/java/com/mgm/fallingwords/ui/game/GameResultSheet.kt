package com.mgm.fallingwords.ui.game

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.datastore.core.DataStore
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.whenCreated
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mgm.fallingwords.R
import com.mgm.fallingwords.databinding.LayoutResultSheetBinding
import com.mgm.fallingwords.utils.StoreUserData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GameResultSheet : BottomSheetDialogFragment() {
    //Binding
    private lateinit var binding: LayoutResultSheetBinding

    @Inject
    lateinit var scoreDataStore: StoreUserData

    private val navArgs: GameResultSheetArgs by navArgs()
    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutResultSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        score = navArgs.score
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false

        binding.txtScore.text = score.toString()
        binding.btnPlay.setOnClickListener {
            dismiss()
            findNavController().navigate(R.id.action_gameResultSheet_to_gameFragment)
        }

        dialog?.setOnKeyListener { dialog, keyCode, event ->
            if ((keyCode == KeyEvent.KEYCODE_BACK)) {
                dismiss()
                findNavController().navigate(R.id.action_gameResultSheet_to_homeFragment)
            }
            false
        }
        //store and compare score
        lifecycle.coroutineScope.launchWhenCreated {
            scoreDataStore.getUserScore().collect() {
                if (it < score) {
                    scoreDataStore.saveUserScore(score.toString())
                    binding.animCelebrate.apply {
                        visibility = View.VISIBLE
                    }
                }
            }
        }


    }


}