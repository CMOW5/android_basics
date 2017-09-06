package com.example.cristian.dialogbasics1;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Time;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private final String classname = MainFragment.this.getClass().getSimpleName();

    private static final int REQUEST_DATE = 1; //for fragment connection with DatePckerFragment
    private static final int REQUEST_TIME = 2; //for fragment connection with DatePckerFragment
    private static final int REQUEST_CUSTOM = 3; //for fragment connection with DatePckerFragment


    private static final String DIALOG_DATE = "DialogDateFragment";
    private static final String DIALOG_TIME = "DialogTimeFragment";
    private static final String DIALOG_CUSTOM = "DialogCustomFragment";


    private TextView mTextView;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final String methodname = " onCreateView(..) ";
        Log.d(Utility.LOG_TAG, classname + methodname);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Button dateButton = (Button) rootView.findViewById(R.id.button_date);
        Button timeButton = (Button) rootView.findViewById(R.id.button_time);
        Button customButton = (Button) rootView.findViewById(R.id.button_custom);
        Button customButton2 = (Button) rootView.findViewById(R.id.button_custom2);
        TextView textView = (TextView) rootView.findViewById(R.id.text_view);
        mTextView = textView;

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(Utility.LOG_TAG, classname + methodname + " onClick dateButton ");

                /*  The string parameter uniquely identifies the DialogFragment in
                    the FragmentManager ’s list. Whether you use the FragmentManager
                    or FragmentTransaction version is up to you. If you pass in a
                    FragmentTransaction , you are responsible for creating and committing
                    that transaction. If you pass in a FragmentManager , a transaction will
                    automatically be created and committed for you.
                */
                FragmentManager manager = getActivity().getSupportFragmentManager();

                Date date = new Date();  //sets date to the current date
                DatePickerFragment dialog = DatePickerFragment.newInstance(date);
                //establish a connection between CrimeFragment and DatePickerFragment
                dialog.setTargetFragment(MainFragment.this, REQUEST_DATE);

                //The string parameter uniquely identifies the DialogFragment in the
                // FragmentManager ’s list
                dialog.show(manager, DIALOG_DATE);
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                Log.d(Utility.LOG_TAG, classname + methodname + " onClick timeButton ");
                /*  The string parameter uniquely identifies the DialogFragment in
                    the FragmentManager ’s list. Whether you use the FragmentManager
                    or FragmentTransaction version is up to you. If you pass in a
                    FragmentTransaction , you are responsible for creating and committing
                    that transaction. If you pass in a FragmentManager , a transaction will
                    automatically be created and committed for you.
                */
                FragmentManager manager = getActivity().getSupportFragmentManager();

                Time time = new Time(0,0,0);  //sets time
                TimePickerFragment dialog = TimePickerFragment.newInstance(time);
                //establish a connection between CrimeFragment and DatePickerFragment
                dialog.setTargetFragment(MainFragment.this, REQUEST_TIME);

                //The string parameter uniquely identifies the DialogFragment in the
                // FragmentManager ’s list
                dialog.show(manager, DIALOG_TIME);
            }
        });

        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Utility.LOG_TAG, classname + methodname + " onClick customButton ");
                 /*  The string parameter uniquely identifies the DialogFragment in
                    the FragmentManager ’s list. Whether you use the FragmentManager
                    or FragmentTransaction version is up to you. If you pass in a
                    FragmentTransaction , you are responsible for creating and committing
                    that transaction. If you pass in a FragmentManager , a transaction will
                    automatically be created and committed for you.
                */
                FragmentManager manager = getActivity().getSupportFragmentManager();

                String data = "custom data";  //set data
                CustomPickerFragment dialog =  CustomPickerFragment.newInstance(data);
                //establish a connection between CrimeFragment and DatePickerFragment
                dialog.setTargetFragment(MainFragment.this, REQUEST_CUSTOM);

                //The string parameter uniquely identifies the DialogFragment in the
                // FragmentManager ’s list
                dialog.show(manager, DIALOG_CUSTOM);
            }
        });

        customButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Utility.LOG_TAG, classname + methodname + " onClick customButton2 ");

                // custom dialog
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.fragment_custom2);
                dialog.setTitle("dialog title");
                dialog.setCanceledOnTouchOutside(false);

                // set the custom dialog components - text, image and button
                Button acceptButton = (Button) dialog.findViewById(R.id.button_ok);
                Button cancelButton = (Button) dialog.findViewById(R.id.button_cancel);
                final EditText passwordEditText = (EditText) dialog.findViewById(R.id.edit_text_password);

                acceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(Utility.LOG_TAG, classname + methodname + "accept");

                        dialog.dismiss();
                    }
                });

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        final String methodname = " onActivityResult(..) ";
        Log.d(Utility.LOG_TAG, classname + methodname);


        if (resultCode != Activity.RESULT_OK) {
            Log.d(Utility.LOG_TAG, classname + methodname + "Activity.RESULT != OK");
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            Log.d(Utility.LOG_TAG, classname + methodname + " requestCode = REQUEST_DATE");
            Log.d(Utility.LOG_TAG, classname + methodname + " date = " + date.toString());

            //actualizar la interfaz de usuario
            mTextView.setText(date.toString());
        }

        else if (requestCode == REQUEST_TIME) {
            Time time = (Time) data
                    .getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            Log.d(Utility.LOG_TAG, classname + methodname + " requestCode = REQUEST_TIME");
            Log.d(Utility.LOG_TAG, classname + methodname + " time = " + time.toString());

            //actualizar la interfaz de usuario
            mTextView.setText(time.toString());


        }

        else if (requestCode == REQUEST_CUSTOM) {

            Bundle bundle = data.getExtras();

            String str = bundle.getString(CustomPickerFragment.EXTRA_CUSTOM);
            Log.d(Utility.LOG_TAG, classname + methodname + " requestCode = REQUEST_CUSTOM");
            Log.d(Utility.LOG_TAG, classname + methodname + " data = " + str);

            //actualizar la interfaz de usuario
            mTextView.setText(str.toString());

            FragmentManager fm = getFragmentManager();
            CustomPickerFragment customFragment = (CustomPickerFragment) getFragmentManager().findFragmentByTag(DIALOG_CUSTOM);

            if (customFragment != null && customFragment.isVisible()) {
                // add your code here
                Log.d(Utility.LOG_TAG, classname + " CustomPickerFragment Exists ");
                /* remove the fragment */
                //getFragmentManager().popBackStack();
            }
            else {

                Log.d(Utility.LOG_TAG, classname + " CustomPickerFragment Does Not Exists ");
                /*
                ProductListFragment productListFrag = new ProductListFragment();
                fm.beginTransaction()
                        .replace(R.id.fragment_container, productListFrag, "LIST_FRAGMENT")
                        .addToBackStack(null)
                        //.add(R.id.fragment_container, productFrag)
                        .commit();
                */
            }
        }
    }

}
