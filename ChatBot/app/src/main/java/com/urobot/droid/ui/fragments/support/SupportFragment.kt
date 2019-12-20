package com.urobot.droid.ui.fragments.support

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
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
            val action = SupportFragmentDirections.Navigation_to_details().setText("lorem ipsum dolor sit amet, consectetuer adipiscing elit." +
                    " Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis," +
                    " ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu." +
                    " In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt.")
            view.findNavController().navigate(action)
        }

        textView2.setOnClickListener {
            val action = SupportFragmentDirections.Navigation_to_details().setText("lorem ipsum dolor sit amet, consectetuer adipiscing elit. " +
                    "Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec," +
                    " pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut," +
                    " imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt.")
            view.findNavController().navigate(action)
        }
    }

}
