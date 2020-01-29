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
import com.urobot.droid.data.model.ServiceModel
import com.urobot.droid.data.model.TypeServices
import com.urobot.droid.ui.dialogs.BottomCalendarFragment
import com.urobot.droid.ui.dialogs.BottomFragment
import kotlinx.android.synthetic.main.bottom_sheet_service.*
import kotlinx.android.synthetic.main.ubot_service_fragment.*


class ServicesFragment : Fragment(), BottomFragment.BottomSheetListener{

    private lateinit var servicesViewModel: ServicesViewModel
    private val dialog = BottomFragment()
    private lateinit var sheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val calendarFragment = BottomCalendarFragment()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.ubot_service_fragment, container, false)

        val createServiceButton: Button = root.findViewById(R.id.createService)

        val listService: RecyclerView = root.findViewById(R.id.listSevice)
        val list = arrayListOf<ServiceModel>()
        list.add(ServiceModel("1", "Детский прием", "Режим работы, кол-во дней"))
        list.add(ServiceModel("1", "Прием для взрослых и красивых дам", "Режим работы, кол-во дней"))

            servicesViewModel = ViewModelProvider(this).get(ServicesViewModel::class.java)

        /**Get All Services */
        servicesViewModel.currentUser.observe(viewLifecycleOwner, Observer { users ->
            users?.let {
                servicesViewModel.getAllServices(it.token!!)
            }
        })

        val adapterService = ServiceListAdapter()
        listService.layoutManager = LinearLayoutManager(context)
        listService.adapter = adapterService
        adapterService.setData(list)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheet()
        createService.setOnClickListener {
            //            val view = layoutInflater.inflate(R.layout.fragment_ubot, null)
            dialog.setSelectedListener(this)
            dialog.show(activity!!.supportFragmentManager, "BottomSheet")
        }

        if(arguments != null){

            val calendarArgs = ServicesFragmentArgs.fromBundle(arguments!!).onlineRecord
            val paymentArgs = ServicesFragmentArgs.fromBundle(arguments!!).paymentModel

            /**Create Online record Service */
            servicesViewModel.currentUser.observe(viewLifecycleOwner, Observer { users ->
                users?.let {
                    servicesViewModel.createOnlineRecordService(it.token!!,
                        listOf(calendarArgs), TypeServices.onlineRecord.type_id)
                }})

            /**Create Payment record Service */
            servicesViewModel.currentUser.observe(viewLifecycleOwner, Observer { users ->
                users?.let {
                    servicesViewModel.createPaymentService(it.token!!,
                        listOf(paymentArgs), TypeServices.payment.type_id)
                }})
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


