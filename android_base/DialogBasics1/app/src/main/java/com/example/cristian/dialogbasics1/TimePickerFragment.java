package com.example.cristian.dialogbasics1;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment {

    private final String classname =TimePickerFragment.this.getClass().getSimpleName();
    private TimePicker mTimePicker;

    private static final String ARG_TIME = "time";
    public static final String EXTRA_TIME =
            "com.example.cristian.dialogbasics1.time";

    public TimePickerFragment() {
        // Required empty public constructor
    }

    public static TimePickerFragment newInstance(Time time) {

        final String methodname = " newInstance ";
        final String classname = "TimePickerFragment";
        Log.d(Utility.LOG_TAG, classname + methodname);

        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, time);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        final String methodname = " onCreateDialog(..) ";
        Log.d(Utility.LOG_TAG, classname + methodname);

        Time time = (Time) getArguments().getSerializable(ARG_TIME);

        /*
        This inflates a layout that  consists of a single View object – a DatePicker – that you
        will pass into setView(...).
        */
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_time, null);

        /*
        Remember that View s can save state across configuration changes,
        but only if they have an ID attribute
        */

        mTimePicker = (TimePicker) v.findViewById(R.id.dialog_time_picker);
        //mTimePicker.set

        /*  AlertDialog.Builder is class that provides a fluent interface for
            constructing an AlertDialog instance.
        */

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Pick a time")
                //.setPositiveButton(android.R.string.ok, null)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //int year = mDatePicker.getYear();
                                //int month = mDatePicker.getMonth();
                                //int day = mDatePicker.getDayOfMonth();
                                //Date date = new GregorianCalendar(year, month, day).getTime();
                                int hour = mTimePicker.getCurrentHour();
                                int min = mTimePicker.getCurrentMinute();
                                Time time = new Time(hour,min,0);
                                sendResult(Activity.RESULT_OK, time);
                            }
                        })
                .create();

        /*
            you pass a Context into the AlertDialog.Builder constructor, which returns
            an instance of AlertDialog.Builder .

            The setPositiveButton(...) method accepts a string resource and an object
            that implements DialogInterface.OnClickListener. You pass in an Android
            constant for OK and null for the listener parameter.
        */

    }

    private void sendResult(int resultCode, Time time) {

        final String methodname = " senResult ";
        Log.d(Utility.LOG_TAG, classname + methodname + time.toString());


        if (getTargetFragment() == null) {
            Log.d(Utility.LOG_TAG, classname + methodname + "getTargetFragment == null");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, time);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

}
