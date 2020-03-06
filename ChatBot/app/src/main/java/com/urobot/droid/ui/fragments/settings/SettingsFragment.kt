package com.urobot.droid.ui.fragments.ubot

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.squareup.picasso.Picasso
import com.urobot.droid.Helper.Utils
import com.urobot.droid.R
import com.urobot.droid.db.User
import com.urobot.droid.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_settings.*
import java.io.File


class SettingsFragment : Fragment(), SettingsViewModel.ISettingsContract {

    private lateinit var settingsViewModel: SettingsViewModel

    private lateinit var currentUser: User


    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
        //image pick code
        private val FILE_PICK_CODE = 1002;
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        settingsViewModel.User.observe(viewLifecycleOwner, Observer { users ->
            // Update the cached copy of the words in the adapter.
            users?.let {
                currentUser = it
                textViewName.text =
                    if (currentUser != null) currentUser.fName + " " + currentUser.lName else ""
                textViewPhone.text = if (currentUser != null) currentUser.cellPhone else ""
                Log.d("currentUser", "id " + currentUser.id)
                Picasso.get().load(Uri.parse(currentUser.photoURL)).into(photoView)
                Log.d("currentUser", "id " + currentUser.photoURL)

            }
        })
        settingsViewModel.setListener(this)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        supportTextView.setOnClickListener {

            view.findNavController().navigate(R.id.navigation_settings_support)

        }

        promoTextView.setOnClickListener {

            view.findNavController().navigate(R.id.navigation_settings_promo)

        }

        tariffsTextView.setOnClickListener {

            view.findNavController().navigate(R.id.navigation_settings_tariffs)

        }

        messengerTextView.setOnClickListener {

            view.findNavController().navigate(R.id.navigation_settings_add_messenger)

        }

        phoneNumberView.setOnClickListener {
            changePhone(textViewPhone.text.toString())
        }

        photoView.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(context!!, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PermissionChecker.PERMISSION_DENIED) {
                    //permission denied
                    val permissions = arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                if (checkSelfPermission(context!!, Manifest.permission.CAMERA) ==
                        PermissionChecker.PERMISSION_DENIED) {
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.CAMERA);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    //permission already granted
                    pickImageFromGallery();
                }
            } else {
                //system OS is < Marshmallow
                pickImageFromGallery()
            }
        }

        logOutTextView.setOnClickListener {
            settingsViewModel.logout(currentUser.token!!)
        }


        industry.setOnClickListener {
            view.findNavController().navigate(R.id.action_navigation_settings_to_navigation_industry_fragment)
        }
    }


    fun changePhone(phone: String) {
        val builder = AlertDialog.Builder(this.context!!)
        val inflater = layoutInflater
        builder.setTitle("Телефон")
        val dialogLayout = inflater.inflate(R.layout.alert_dialog_with_edittext, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.editText)
        editText.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        editText.setText(phone)
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") { dialogInterface, i ->
            currentUser.cellPhone = editText.text.toString()
            textViewPhone.text = editText.text
            settingsViewModel.update(currentUser)
            // TODO save text only
//            settingsViewModel.sendUpdate(currentUser, )
                settingsViewModel.sendUpdatePhone(currentUser)
        }
        builder.show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                        PermissionChecker.PERMISSION_GRANTED) {
                    //permission from popup granted
                    pickImageFromGallery()
//                    pickPhoto()
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
                Log.d("photoURL", "photoURL " + data?.data)

                photoView.setImageURI(data?.data)

                currentUser.photoURL = data?.data.toString()
                val file = File(Utils.getRealPath(context!!, data?.data!!))

                settingsViewModel.sendUpdate(currentUser, file)
            }
            if (requestCode == FILE_PICK_CODE) {

            }
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        val intent1 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    private fun pickFileFromDevice() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "file/*"
        startActivityForResult(intent, FILE_PICK_CODE)
    }

    private fun pickPhoto() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        var image_uri = context!!.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_PICK_CODE)

    }

    override fun onLogoutResult() {
        Log.d("onLogoutResult", "onLogoutResult ")

        val sharedPrefL: SharedPreferences = activity!!.getSharedPreferences("isLoged", 0)

        var intent = Intent(activity, LoginActivity::class.java)
        val editor = sharedPrefL.edit()
        editor.putBoolean("isLoged", false)
        editor.apply()

        startActivity(intent)
        activity!!.finish()
    }
}