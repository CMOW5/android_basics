package com.example.cristian.yarumalturistica;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by cristian on 10/03/17.
 */

public class EstablishmentFragment extends Fragment {

    private static final String ARG_SECTION_IMAGE_NUMBER = "section_image_number";
    private static final String ARG_SECTION_TEXT_NUMBER = "section_text_number";

    public EstablishmentFragment() {
    }

    public static EstablishmentFragment newInstance(int sectionImageNumber,
                                                    int sectionTextNumber){

        EstablishmentFragment fragment = new EstablishmentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_IMAGE_NUMBER, sectionImageNumber);
        args.putInt(ARG_SECTION_TEXT_NUMBER, sectionTextNumber);
        fragment.setArguments(args);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_establishment, container, false);
        //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_IMAGE_NUMBER)));

        TextView textView = (TextView) rootView.findViewById(R.id.textViewEstablishment);
        textView.setText(getArguments().getInt(ARG_SECTION_TEXT_NUMBER));

        ImageView imageView = (ImageView) rootView.findViewById(R.id.imageViewEstablishment);
        imageView.setImageResource(getArguments().getInt(ARG_SECTION_IMAGE_NUMBER));

        return rootView;

    }
}
