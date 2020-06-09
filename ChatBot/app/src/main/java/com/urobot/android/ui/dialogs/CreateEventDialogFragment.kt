package com.urobot.android.ui.dialogs

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PermissionChecker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.urobot.android.Helper.Utils
import com.urobot.android.R
import com.urobot.android.adapter.ButtonsListAdapter
import com.urobot.android.data.model.BotContentItem
import com.urobot.android.data.model.GetAllServicesModel
import com.urobot.android.data.model.ServiceButtons
import kotlinx.android.synthetic.main.dialog_fragment_create_event.*
import java.io.File


class CreateEventDialogFragment : DialogFragment(), View.OnClickListener,
    AddButtonBottomSheetDialog.AddButtonBottomSheetListener, ButtonsListAdapter.ItemClickListener,
    CreateEventDialogViewModel.IEventContract {

    private var addButtonDialog = AddButtonBottomSheetDialog()
    private var changeDataListener: ChangeDataListener? = null
    private var list: ArrayList<ServiceButtons>? = ArrayList()
    private var botContentItem: BotContentItem? = null
    private var listService: ArrayList<GetAllServicesModel> = ArrayList()
    private lateinit var createEventViewModel: CreateEventDialogViewModel
    private lateinit var adapter: ButtonsListAdapter

    companion object {
        private var instanceFragment: CreateEventDialogFragment? = null
        private var robotId: String? = ""

        val IMAGE_PICK_CODE = 1000
        //Permission code
        internal val PERMISSION_CODE = 1001;

        fun newInstance(botItem: BotContentItem, botId: String): CreateEventDialogFragment? {
            instanceFragment = null
            robotId = botId
            instanceFragment =
                CreateEventDialogFragment()
            instanceFragment!!.setContentItem(botItem)
            return instanceFragment
        }
    }

    private fun setContentItem(botItem: BotContentItem) {
        botContentItem = botItem
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.dialog_fragment_create_event, null)

        view.findViewById<View>(R.id.add_buttons_tv).setOnClickListener(this)
        view.findViewById<View>(R.id.deleteTextViewDialogFragment).setOnClickListener(this)
        view.findViewById<TextView>(R.id.save_button).setOnClickListener(this)
        createEventViewModel = ViewModelProvider(this).get(CreateEventDialogViewModel::class.java)
        addButtonDialog.setSelectedListener(this)
        createEventViewModel.setListener(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        descriptionEditText.setText(botContentItem!!.description)
        if (botContentItem!!.image!!.isNotEmpty()) {
            Picasso.get().load(botContentItem!!.image).into(image)
        }
        var listButtons: ArrayList<ServiceButtons> = ArrayList()
        for (button in botContentItem!!.list_buttons!!) {
            if (button.id.toString().length == 13) {
                listButtons.add(button)
            } else {
                val itemView = LinearLayout.inflate(
                    context,
                    R.layout.item_botton,
                    null
                )
                itemView.findViewById<TextView>(R.id.payment_button_dialog_fragment)
                    .setText(button.name)
                linearLayout.addView(itemView)
                list?.add(button)
            }

        }
        adapter.addData(listButtons)
        adapter.addClickListener(this)
        image.setOnClickListener(this)

    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }

    override fun onResume() {
        super.onResume()
        createEventViewModel.currentUser.observe(viewLifecycleOwner, Observer { users ->
            users?.let {
                createEventViewModel.getAllServices(it.token!!, context!!)
            }
        })

        createEventViewModel.getAllServicesLivaData.observe(viewLifecycleOwner, Observer { result ->
            Log.d("Merk", "createEventViewModel" + result.size)

//            adapter.addData()
            listService.clear()
            listService.addAll(result)
            listService.add(GetAllServicesModel(0, null, "кнопка"))
            addButtonDialog.setListSerice(listService)

        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.add_buttons_tv -> {
                val bottomSheetFragment = context?.let { addButtonDialog }


                val manager = (context as AppCompatActivity).supportFragmentManager
                bottomSheetFragment?.show(manager, "dialog")
            }
            R.id.save_button -> {
                changeDataListener.let {
                    val allButtons = list
                    adapter.getItems()?.let { allButtons?.addAll(it) }
                    botContentItem!!.isEmpty = false
                    botContentItem!!.description = descriptionEditText.text.toString()
                    botContentItem!!.list_buttons = allButtons

//                    if (botContentItem!!.parent_id == null) {
//                        botContentItem!!.parent_id = botContentItem!!.id
//                    }
                    it!!.onBotDataChanged(botContentItem!!)
                }
                descriptionEditText.setText("")
//                list?.clear()
                dismiss()
            }
            R.id.deleteTextViewDialogFragment -> {
                changeDataListener!!.onBotDeleted(botContentItem!!)
                dismiss()
            }
            R.id.image -> {
                checkPermission()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        instanceFragment = null
        super.onDismiss(dialog)
    }

    override fun ButtonClick(button: GetAllServicesModel) {
        Log.d("ButtonClick", "list " + list!!.size)

        if (!list!!.contains(ServiceButtons(button.id!!.toLong(), button.name))) {
            if (button.id != 0) {
                val itemView = LinearLayout.inflate(
                    context,
                    R.layout.item_botton,
                    null
                )
                itemView.findViewById<TextView>(R.id.payment_button_dialog_fragment)
                    .setText(button.name)
                linearLayout.addView(itemView)
                list?.add(ServiceButtons(button.id!!.toLong(), button.name))
            } else {
                showDialog()
            }
        }
    }

    fun showDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Новая кнопка")

        val view = layoutInflater.inflate(R.layout.dialog_edit_text, null)

        val categoryEditText = view.findViewById(R.id.categoryEditText) as EditText
        var listButtons: ArrayList<ServiceButtons> = ArrayList()
        builder.setView(view);

        // set up the ok button
        builder.setPositiveButton(android.R.string.ok) { dialog, p1 ->
            if (categoryEditText.text.isNotEmpty()) {
                val itemView = LinearLayout.inflate(
                    context,
                    R.layout.item_botton,
                    null
                )
                val button = ServiceButtons(
                    System.currentTimeMillis(),
                    categoryEditText.text.toString()
                )
                adapter.addButton(button)
                dialog.cancel()
            }
        }

        builder.setNegativeButton(android.R.string.cancel) { dialog, p1 ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun initAdapter() {
        adapter = ButtonsListAdapter()
        listButtonsRV.adapter = adapter
        listButtonsRV.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listButtonsRV.layoutManager = layoutManager
    }

    interface ChangeDataListener {
        fun onBotDataCreated(botContentItem: BotContentItem)
        fun onBotDataChanged(botContentItem: BotContentItem)
        fun onBotDeleted(botContentItem: BotContentItem)
    }

    fun setSelectedListener(listener: ChangeDataListener) {
        this.changeDataListener = listener
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PermissionChecker.PERMISSION_GRANTED
                ) {
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_PICK_CODE) {
                image.setImageURI(data?.data)
                val file = File(Utils.getRealPath(context!!, data?.data!!))
                robotId?.let { createEventViewModel.sendImage(file, botContentItem!!.id!!, it) }
            }
        }
    }

    fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionChecker.checkSelfPermission(
                    context!!,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) ==
                PermissionChecker.PERMISSION_DENIED
            ) {
                //permission denied
                val permissions = arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE);
            }
            if (PermissionChecker.checkSelfPermission(context!!, Manifest.permission.CAMERA) ==
                PermissionChecker.PERMISSION_DENIED
            ) {
                //permission denied
                val permissions = arrayOf(Manifest.permission.CAMERA);
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE);
            } else {
                pickImageFromGallery()
            }
        } else {
            pickImageFromGallery()
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onItemClick(view: View?, position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Изменить кнопку")

        val view = layoutInflater.inflate(R.layout.dialog_edit_text, null)

        val categoryEditText = view.findViewById(R.id.categoryEditText) as EditText
        var listButtons: ArrayList<ServiceButtons> = ArrayList()
        builder.setView(view);
        var item = adapter.getItemByPosirion(position)
        categoryEditText.setText(item.name)
        // set up the ok button
        builder.setPositiveButton(android.R.string.ok) { dialog, p1 ->
            if (categoryEditText.text.isNotEmpty()) {
                item.name = categoryEditText.text.toString()
                adapter.notifyItemChanged(position, item)
                dialog.cancel()
            }
        }

        builder.setNegativeButton(android.R.string.cancel) { dialog, p1 ->
            dialog.cancel()
        }

        builder.show()
    }

    override fun onSendPhotoResult(data: String) {
        botContentItem!!.image = data
    }
}