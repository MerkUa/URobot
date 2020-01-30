package com.urobot.droid.ui.fragments.ubotservice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.urobot.droid.R
import com.urobot.droid.adapter.ServiceListAdapter
import com.urobot.droid.data.model.TypeServices
import com.urobot.droid.ui.dialogs.BottomCalendarFragment
import com.urobot.droid.ui.dialogs.BottomFragment
import kotlinx.android.synthetic.main.bottom_sheet_service.*
import kotlinx.android.synthetic.main.ubot_service_fragment.*


class ServicesFragment : Fragment(), BottomFragment.BottomSheetListener{

    private lateinit var servicesViewModel: ServicesViewModel
    private val dialog = BottomFragment()
    private lateinit var sheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val adapterService = ServiceListAdapter()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.ubot_service_fragment, container, false)
        val listService: RecyclerView = root.findViewById(R.id.listSevice)
            servicesViewModel = ViewModelProvider(this).get(ServicesViewModel::class.java)

        listService.adapter = adapterService
        listService.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listService.layoutManager = linearLayoutManager

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheet()
        createService.setOnClickListener {
            dialog.setSelectedListener(this)
            dialog.show(activity!!.supportFragmentManager, "BottomSheet")
        }
    }

    private fun setBottomSheet() {
        sheetBehavior = BottomSheetBehavior.from(bottomSheet)
        sheetBehavior.isFitToContents = true
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN


        sheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // empty
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                Timber.d("onSlide $slideOffset")
//                if (slideOffset <= 0.3 && flag) {
//                    flag = false
//                }
            }
        })
    }


    override fun onResume() {
        super.onResume()

        /**Get All Services Request */
        servicesViewModel.currentUser.observe(viewLifecycleOwner, Observer { users ->
            users?.let {
                servicesViewModel.getAllServices(context, it.token!!)
            }
        })

        /**Get All Services Observe LiveData */
        servicesViewModel.getAllServicesLivaData.observe(viewLifecycleOwner, Observer { result->
            adapterService.addData(result)
        })


        if(arguments != null){

            val calendarArgs = ServicesFragmentArgs.fromBundle(arguments!!).onlineRecord
            val paymentArgs = ServicesFragmentArgs.fromBundle(arguments!!).paymentModel

            val namePayment = ServicesFragmentArgs.fromBundle(arguments!!).namePaymentService
            val nameCalendar = ServicesFragmentArgs.fromBundle(arguments!!).onlineRecord?.name

            if(calendarArgs != null ){
                /**Create Online record Service */
                servicesViewModel.currentUser.observe(viewLifecycleOwner, Observer { users ->
                    users?.let {
                        servicesViewModel.createOnlineRecordService(
                            nameCalendar!!, it.token!!,
                            listOf(calendarArgs), TypeServices.onlineRecord.type_id)
                    }})
            }


            if(paymentArgs != null){

                /**Create Payment record Service */
                servicesViewModel.currentUser.observe(viewLifecycleOwner, Observer { users ->
                    users?.let {
                        servicesViewModel.createPaymentService( namePayment,it.token!!,
                            listOf(paymentArgs), TypeServices.payment.type_id)
                    }})
            }

        }

    }
    override fun onCalendarClick() {
        dialog.dismiss()
        findNavController().navigate(R.id.navigation_create_calendar)
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun onPaymentClick() {
        dialog.dismiss()
        findNavController().navigate(R.id.navigation_create_payment)
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }
}


