package com.mgm.fallingwords.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.mgm.fallingwords.R
import com.mgm.fallingwords.databinding.FragmentHomeBinding
import com.mgm.fallingwords.utils.StoreUserData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    //Binding
    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var scoreDataStore: StoreUserData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            lifecycle.coroutineScope.launchWhenCreated {
                scoreDataStore.getUserScore().collect() {
                    txtScore.text = String.format(getString(R.string.score), it)
                }
            }
            //start Click
            btnStart.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_gameFragment)
            }
        }
    }

}