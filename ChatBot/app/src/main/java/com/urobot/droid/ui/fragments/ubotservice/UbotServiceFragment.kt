package com.urobot.droid.ui.fragments.ubotservice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.urobot.droid.R
import com.urobot.droid.adapter.ServiceListAdapter
import com.urobot.droid.data.model.Service
import com.urobot.droid.ui.dialogs.ButtomFragment
import kotlinx.android.synthetic.main.bottom_sheet_service.*
import kotlinx.android.synthetic.main.ubot_service_fragment.*


class UbotServiceFragment : Fragment(), ButtomFragment.BottomSheetListener {

    private lateinit var viewModel: UbotViewModel

    private val dialog = ButtomFragment()


    private lateinit var sheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.ubot_service_fragment, container, false)
        val createServiceButton: Button = root.findViewById(R.id.createService)

        val listService: RecyclerView = root.findViewById(R.id.listSevice)
        val list = arrayListOf<Service>()
        list.add(Service("1", "Детский прием", "Режим работы, кол-во дней"))
        list.add(Service("1", "Прием для взрослых и красивых дам", "Режим работы, кол-во дней"))


        val adapterService = ServiceListAdapter()
        listService.layoutManager = LinearLayoutManager(context)
        listService.adapter = adapterService
        adapterService.setData(list)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UbotViewModel::class.java)
        // TODO: Use the ViewModel
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


