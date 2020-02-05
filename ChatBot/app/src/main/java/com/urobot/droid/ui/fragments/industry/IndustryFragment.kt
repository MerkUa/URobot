package com.urobot.droid.ui.fragments.industry

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.urobot.droid.R
import com.urobot.droid.adapter.IndustryAdapter
import kotlinx.android.synthetic.main.fragment_industry.*


class IndustryFragment : Fragment() {

    private lateinit var industryViewModel: IndustryViewModel
    private lateinit var adapter: IndustryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_industry, container, false)
        industryViewModel = ViewModelProvider(this).get(IndustryViewModel::class.java)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        /** Get all available industries from server */
        industryViewModel.User.observe(viewLifecycleOwner, Observer { users ->

            users?.let {
                industryViewModel.getAllIndustryFromNet(it.token!!)

                /** Get all industries from Local DB */
                industryViewModel.getAllIndustryFromLocalDB()
            }
        })

        /** Observe and set all industries from server */
        industryViewModel.getAllIndustryFromNetLivaData.observe(
            viewLifecycleOwner,
            Observer { result ->
                adapter.addDataFromServer(result)
            })
        /** Observe and set all industries from Local DB */
        industryViewModel.getUserIndustryFromLocalDbLivaData.observe(
            viewLifecycleOwner,
            Observer { result ->
                adapter.addDataFromLocalDB(result)
            })

        updateIndustryButton.setOnClickListener {
            /** Update industries */
            industryViewModel.User.observe(viewLifecycleOwner, Observer { users ->
                users?.let {
                val list = adapter.getUpdateIndustryData()
                        industryViewModel.updateIndustry(
                            it.token!!,
                            list
                        )
                }
            })
            /** Observe response(isSuccess) update industries */
            industryViewModel.boolLiveData.observe(viewLifecycleOwner, Observer { result ->
                if(result){
                    activity?.onBackPressed()
                }
            })
        }
    }

    private fun initAdapter(){
        adapter = IndustryAdapter()
        industryRv.adapter = adapter
        industryRv.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        industryRv.layoutManager = layoutManager
    }

}