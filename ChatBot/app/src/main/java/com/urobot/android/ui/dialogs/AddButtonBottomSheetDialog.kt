package com.urobot.android.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.urobot.android.R
import com.urobot.android.adapter.ServiceButtonsAdapter
import com.urobot.android.data.model.GetAllServicesModel
import kotlinx.android.synthetic.main.bottom_sheet_add_buttons.*


class AddButtonBottomSheetDialog : BottomSheetDialogFragment(),
    ServiceButtonsAdapter.ItemClickListener {

    private var mAddButtonBottomSheetListener: AddButtonBottomSheetListener? = null
    private var list: List<GetAllServicesModel> = emptyList()
    val adapterButtons = ServiceButtonsAdapter()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.bottom_sheet_add_buttons, container, false)


//        val writeButton = view.findViewById<Button>(R.id.add_write_button)
//        val buttonPayment = view.findViewById<Button>(R.id.add_payment_button)
//        val buttonNewLevel = view.findViewById<Button>(R.id.add_new_level)
//
//
//        writeButton.setOnClickListener {
//            mAddButtonBottomSheetListener?.ButtonClick(ServiceButtons(1, null))
//        }
//
//        buttonPayment.setOnClickListener {
//            mAddButtonBottomSheetListener?.ButtonClick(ServiceButtons(2, null))
//        }
//
//        buttonNewLevel.setOnClickListener {
//
//            mAddButtonBottomSheetListener?.ButtonClick(ServiceButtons(3, ""))
//        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttons.layoutManager = LinearLayoutManager(context)
        buttons.adapter = adapterButtons
        adapterButtons.addData(list)
        adapterButtons.addClickListener(this)
    }


    fun setSelectedListener(listener: AddButtonBottomSheetListener) {
        this.mAddButtonBottomSheetListener = listener
    }

    fun setListSerice(listService: List<GetAllServicesModel>) {
        list = listService
    }

    interface AddButtonBottomSheetListener {
        fun ButtonClick(button: GetAllServicesModel)
    }

    override fun onItemClick(view: View?, item: GetAllServicesModel) {
        mAddButtonBottomSheetListener?.ButtonClick(item)
        dismiss()
    }

}