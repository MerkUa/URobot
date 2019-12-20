package com.urobot.droid.ui.fragments.createbot

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup

import com.urobot.droid.R
import kotlinx.android.synthetic.main.create_bot_fragment.*

class CreateBotFragment : Fragment() {

    companion object {
        fun newInstance() = CreateBotFragment()
    }

    private lateinit var viewModel: CreateBotViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_bot_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CreateBotViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCodetButton.setOnClickListener {
            phoneLayout.visibility = View.GONE
            codeLayout.visibility = View.VISIBLE
        }
        deleteTextView.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}
