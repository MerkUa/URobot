package com.urobot.droid.ui.fragments.support

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.urobot.droid.R
import kotlinx.android.synthetic.main.support_details_fragment.*

private const val data = ""

class SupportDetailsFragment : Fragment() {

    private var text: String? = null

    private lateinit var viewModel: SupportDetailsViewModel


    companion object {

        private val ARG_DATA = "supportDetailsFragment_data"


        fun newInstance(data: String): SupportDetailsFragment {
            val args: Bundle = Bundle()
            args.putSerializable(ARG_DATA, data)
            val fragment = SupportDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val eventId = arguments?.let { SupportDetailsFragmentArgs.fromBundle(it).text }
        text = eventId

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.support_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SupportDetailsViewModel::class.java)
        detailsTextView.text = text
    }

}
