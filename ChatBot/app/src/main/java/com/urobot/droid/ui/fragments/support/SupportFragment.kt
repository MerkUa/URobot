package com.urobot.droid.ui.fragments.support

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.urobot.droid.R
import kotlinx.android.synthetic.main.support_fragment.*

class SupportFragment : Fragment() {

    companion object {
        fun newInstance() = SupportFragment()
    }

    private lateinit var viewModel: SupportViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.support_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SupportViewModel::class.java)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView1.setOnClickListener {
            val action = SupportFragmentDirections.navigationToDetails()
                .setText(getString(R.string.faqdescription1))
            view.findNavController().navigate(action)
        }

        textView2.setOnClickListener {
            val action = SupportFragmentDirections.navigationToDetails()
                .setText(getString(R.string.faqdescription2))
            view.findNavController().navigate(action)
        }

        textView3.setOnClickListener {
            val action = SupportFragmentDirections.navigationToDetails()
                .setText(getString(R.string.faqdescription3))
            view.findNavController().navigate(action)
        }

        textView4.setOnClickListener {
            val action = SupportFragmentDirections.navigationToDetails()
                .setText(getString(R.string.faqdescription4))
            view.findNavController().navigate(action)
        }

        textView5.setOnClickListener {
            val action = SupportFragmentDirections.navigationToDetails()
                .setText(getString(R.string.faqdescription5))
            view.findNavController().navigate(action)
        }

        textView6.setOnClickListener {
            val action = SupportFragmentDirections.navigationToDetails()
                .setText(getString(R.string.faqdescription6))
            view.findNavController().navigate(action)
        }

        textView7.setOnClickListener {
            val action = SupportFragmentDirections.navigationToDetails()
                .setText(getString(R.string.faqdescription7))
            view.findNavController().navigate(action)
        }

        textView8.setOnClickListener {
            val action = SupportFragmentDirections.navigationToDetails()
                .setText(getString(R.string.faqdescription8))
            view.findNavController().navigate(action)
        }

        textView9.setOnClickListener {
            val action = SupportFragmentDirections.navigationToDetails()
                .setText(getString(R.string.faqdescription9))
            view.findNavController().navigate(action)
        }

        textView10.setOnClickListener {
            val action = SupportFragmentDirections.navigationToDetails()
                .setText(getString(R.string.faqdescription10))
            view.findNavController().navigate(action)
        }

        textView11.setOnClickListener {
            val action = SupportFragmentDirections.navigationToDetails()
                .setText(getString(R.string.faqdescription11))
            view.findNavController().navigate(action)
        }

        textView12.setOnClickListener {
            val action = SupportFragmentDirections.navigationToDetails()
                .setText(getString(R.string.faqdescription12))
            view.findNavController().navigate(action)
        }

        textView13.setOnClickListener {
            val action = SupportFragmentDirections.navigationToDetails()
                .setText(getString(R.string.faqdescription13))
            view.findNavController().navigate(action)
        }

        textView14.setOnClickListener {
            val action = SupportFragmentDirections.navigationToDetails()
                .setText(getString(R.string.faqdescription14))
            view.findNavController().navigate(action)
        }
    }

}
