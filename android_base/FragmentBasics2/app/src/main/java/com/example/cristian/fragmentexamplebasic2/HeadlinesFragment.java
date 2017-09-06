package com.example.cristian.fragmentexamplebasic2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class HeadlinesFragment extends Fragment {

    Button mButton;

    public HeadlinesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_headlines, container, false);
        mButton =  (Button) viewRoot.findViewById(R.id.buttonHeadlinesFragment);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create fragment and give it an argument specifying the article it should show
                ArticleFragment newFragment = new ArticleFragment();

                int position = 5;

                Bundle args = new Bundle();
                args.putInt(ArticleFragment.ARG_POSITION, position);
                newFragment.setArguments(args);

                //FragmentTransaction transaction = getFragmentManager().beginTransaction();
                FragmentTransaction transaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.fragment_container, newFragment);
                transaction.addToBackStack(null);

                /*
                 * The addToBackStack() method takes an optional string
                 * parameter that specifies a unique name for the transaction.
                 * The name isn't needed unless you plan to perform advanced
                 * fragment operations using the FragmentManager.BackStackEntry APIs.
                 */

                // Commit the transaction
                transaction.commit();
            }
        });

        return viewRoot;
    }

}
