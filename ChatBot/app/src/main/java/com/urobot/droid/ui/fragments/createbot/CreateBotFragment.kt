package com.urobot.droid.ui.fragments.createbot

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.urobot.droid.R
import com.urobot.droid.data.NetModel.Request.RequestCreateBot
import com.urobot.droid.ui.createbot.CreateBotActivity
import com.urobot.droid.ui.dialogs.ChooseMessengerDialogFragment
import kotlinx.android.synthetic.main.create_bot_fragment.*

class CreateBotFragment : Fragment() {

    companion object {
        fun newInstance() = CreateBotFragment()
    }

    private lateinit var createBotViewModel: CreateBotViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_bot_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createBotViewModel = ViewModelProvider(this).get(CreateBotViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createBtn.setOnClickListener {

            createBotViewModel.currentUser.observe(viewLifecycleOwner, Observer { user ->
                user?.let {
                    if (nameBotEditText.text!!.isNotEmpty()) {
                        createBotViewModel.createRobot(
                            it.token!!,
                            nameBotEditText.text.toString(),
                            descriptionBotEditText.text.toString()
                        )
                    }

                }
            })

            createBotViewModel.createRobotLiveData.observe(viewLifecycleOwner, Observer { robot ->
                robot?.let {
                    val intent = Intent(activity, CreateBotActivity::class.java)
                    intent.putExtra(CreateBotActivity.EXTRA_BOT_ID, it.id)
                    startActivityForResult(intent, 1)
                    startActivity(intent)
                }
            })


        }
//        codeLayout.visibility = View.GONE
//        tokenLayout.visibility = View.GONE
//
//        add_messenger.visibility = View.VISIBLE
//        createBtn.visibility = View.GONE

//        add_messenger.setOnClickListener {
//            ChooseMessengerDialogFragment().show(activity!!.supportFragmentManager, "TAG")
//        }
    }

//    override fun onClickListener(messengerName: String) {
//
//        add_messenger.visibility = View.GONE
//        createBtn.visibility = View.VISIBLE
//
//        if (messengerName == RequestCreateBot.VK) {
//            codeLayout.visibility = View.VISIBLE
//            tokenLayout.visibility = View.VISIBLE
//        } else {
//            tokenLayout.visibility = View.VISIBLE
//        }
//

//    }
}

