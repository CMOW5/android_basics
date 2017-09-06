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
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomPickerFragment extends DialogFragment {

    private final String classname =CustomPickerFragment.this.getClass().getSimpleName();

    private static final String ARG_CUSTOM = "custom";
    public static final String EXTRA_CUSTOM =
            "com.example.cristian.dialogbasics1.custom";
    private static final String DIALOG_CUSTOM = "DialogCustomFragment";

    public CustomPickerFragment() {
        // Required empty public constructor
    }

    public static CustomPickerFragment newInstance(String data) {

        final String methodname = " newInstance ";
        final String classname = "CustomPickerFragment";
        Log.d(Utility.LOG_TAG, classname + methodname);

        Bundle args = new Bundle();
        args.putString(ARG_CUSTOM, data);
        CustomPickerFragment fragment = new CustomPickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_custom_picker, container, false);

        String text = getArguments().getString(ARG_CUSTOM);

        final EditText editText = (EditText) rootView.findViewById(R.id.edit_text_custom);
        editText.setText(text);

        Button button = (Button) rootView.findViewById(R.id.button_ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = editText.getText().toString();
                sendResult(Activity.RESULT_OK, data);
                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentByTag(DIALOG_CUSTOM))
                        .commit();

            }
        });

        return rootView;
    }

    private void sendResult(int resultCode, String data) {

        final String methodname = " senResult ";
        Log.d(Utility.LOG_TAG, classname + methodname + data.toString());


        if (getTargetFragment() == null) {
            Log.d(Utility.LOG_TAG, classname + methodname + "getTargetFragment == null");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CUSTOM, data);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);

    }

}
