package com.urobot.droid.ui.fragments.createbot

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.urobot.droid.R
import com.urobot.droid.ui.createbot.CreateBotActivity
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
        createBotButton.setOnClickListener {
            val intent = Intent(activity, CreateBotActivity::class.java)
            startActivityForResult(intent, 1)
        }
        deleteTextView.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}
