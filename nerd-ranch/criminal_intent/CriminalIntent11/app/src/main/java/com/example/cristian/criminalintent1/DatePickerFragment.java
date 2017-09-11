package com.example.cristian.criminalintent1;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by root on 1/25/17.
 */

public class DatePickerFragment extends DialogFragment {

    private static final String ARG_DATE = "date";
    public static final String EXTRA_DATE =
            "com.cristian.android.criminalintent.date";

    private DatePicker mDatePicker;

    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /*  The FragmentManager of the hosting activity calls this method as part of putting
        the DialogFragment on screen.
     */

    /*  this implementation of onCreateDialog(...) builds an
        AlertDialog with a title and one OK button
    */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        /*
        To get the year, month and day as an integers you need, you must create
        a Calendar object and use the Date to configure the Calendar. Then you
        can retrieve the required information from the Calendar .
        */

        Date date = (Date) getArguments().getSerializable(ARG_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        /*
        This inflates a layout that  consists of a single View object – a DatePicker – that you
        will pass into setView(...).
        */

        /*Using a layout makes modifications easy if you change your mind about
        what the dialog should present
        you could have done this: (nor recommended this doenst save the state across
                                    rotations)
            DatePicker datePicker = new DatePicker(getActivity());
            //in AlertDialog.Builder(getActivity())
            .setView(datePicker)
            ...
            .create();

        */
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);
        /*
        Remember that View s can save state across configuration changes,
        but only if they have an ID attribute
        */

        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_picker);
        mDatePicker.init(year, month, day, null);

        /*  AlertDialog.Builder is class that provides a fluent interface for
            constructing an AlertDialog instance.
        */

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                //.setPositiveButton(android.R.string.ok, null)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int year = mDatePicker.getYear();
                                int month = mDatePicker.getMonth();
                                int day = mDatePicker.getDayOfMonth();
                                Date date = new GregorianCalendar(year, month, day).getTime();
                                sendResult(Activity.RESULT_OK, date);
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

    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }





}
