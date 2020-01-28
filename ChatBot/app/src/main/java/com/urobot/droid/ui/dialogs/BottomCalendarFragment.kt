package com.urobot.droid.ui.dialogs

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.DatePicker
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import com.urobot.droid.R
import com.urobot.droid.data.model.OnlineRecordModel
import com.urobot.droid.ui.fragments.ubotservice.ServicesFragment
import kotlinx.android.synthetic.main.fragment_bottom_calendar.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class BottomCalendarFragment : Fragment() {

    var calendars: List<Calendar> = emptyList()
    var listener = OnSelectDateListener {
        calendars = it
        for (calendar in calendars) {
            Log.d("OnSelectDateListener", "calendar " + calendar.timeInMillis)
            Log.d("OnSelectDateListener", "calendar $calendar")
            Log.d("OnSelectDateListener", "calendar $calendars")
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvAddDay.setOnClickListener {
            showCalendar()
        }

        tvBreak.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                tvBreak.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            CustomTimePickerDialog(
                context,
                timeSetListener,
             0,
             0,
                true
            ).show()
        }

        tvSessionDuration.setOnClickListener {

           val cal = Calendar.getInstance()

            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
               cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                tvSessionDuration.text = SimpleDateFormat("HH:mm").format(0)
            }

            CustomTimePickerDialog(
                context,
                timeSetListener,
             0,
             0,
                true
            ).show()
        }

        timePickerFrom.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                timePickerFrom.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(
                context,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()

        }
        timePickerTo.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                timePickerTo.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(
                context,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }


        createBotButton.setOnClickListener {
                val listDate = ArrayList<String>()

                val name = nameEditText.text.toString()
                val timeFrom = timePickerFrom.text.toString()
                val timeTo = timePickerTo.text.toString()
                val breakTime = tvBreak.text.toString()
                val session = tvSessionDuration.text.toString()

            for (calendar in calendars) {

                val timeInMillis = calendar.timeInMillis
                val resultCalendar = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timeInMillis)
                listDate.add(resultCalendar)
            }

            val data = OnlineRecordModel(
                name, timeFrom, timeTo, breakTime , session, listDate)

            val action = BottomCalendarFragmentDirections.actionNavigationCreateCalendarToNavigationServicesFragment().setOnlineRecord(data)
            Navigation.findNavController(view).navigate(action)

            }
    }

    private fun showCalendar() {

        val builder = DatePickerBuilder(context, listener)
            .setPickerType(CalendarView.MANY_DAYS_PICKER)
            .setDate(Calendar.getInstance()) // Initial date as Calendar object
//            .setMinimumDate(Calendar.getInstance()) // Minimum available date
//            .setMaximumDate(Calendar.getInstance()) // Maximum available date
            .setHeaderColor(R.color.colorAccent) // Color of the dialog header
            .setHeaderLabelColor(R.color.white_text) // Color of the header label
//            .setPreviousButtonSrc(R.drawable.drawable) // Custom drawable of the previous arrow
//            .setForwardButtonSrc(R.drawable.drawable) // Custom drawable of the forward arrow
//            .setPreviousPageChangeListener(new OnCalendarPageChangeListener(){}) // Listener called when scroll to the previous page
//            .setForwardPageChangeListener(new OnCalendarPageChangeListener(){}) // Listener called when scroll to the next page
//            .setAbbreviationsBarColor(R.color.color) // Color of bar with day symbols
//            .setAbbreviationsLabelsColor(R.color.color) // Color of symbol labels
//            .setPagesColor(R.color.sampleLighter) // Color of the calendar background
            .setSelectionColor(R.color.colorAccent) // Color of the selection circle
//            .setSelectionLabelColor(R.color.color) // Color of the label in the circle
//            .setDaysLabelsColor(R.color.color) // Color of days numbers
//            .setAnotherMonthsDaysLabelsColor(R.color.color) // Color of visible days numbers from previous and next month page
//            .setDisabledDaysLabelsColor(R.color.color) // Color of disabled days numbers
//            .setHighlightedDaysLabelsColor(R.color.color) // Color of highlighted days numbers
//            .setTodayColor(R.color.color) // Color of the present day background
//            .setTodayLabelColor(R.color.color) // Color of the today number
//            .setDialogButtonsColor(R.color.color) // Color of "Cancel" and "OK" buttons
//            .setMaximumDaysRange(int) // Maximum number of selectable days in range mode
//            .setNavigationVisibility(int) // Navigation buttons visibility
            .setSelectedDays(calendars) /// List of selected days

        val datePicker: DatePicker = builder.build()
        datePicker.show()
    }
}
