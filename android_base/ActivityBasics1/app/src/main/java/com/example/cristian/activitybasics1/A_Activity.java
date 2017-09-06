package com.example.cristian.activitybasics1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class A_Activity extends AppCompatActivity {

    private final String classname = A_Activity.this.getClass().getSimpleName();

    Intent intent;

    private static final String EXTRA_DATA = "com.example.cristian.activitybasiscs1.data";
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);


        String methodname = " onCreate(..) ";
        Log.d(Utility.LOG_TAG, classname + methodname);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
            data = bundle.getString(EXTRA_DATA);
        else
            data = "no data";

        Log.d(Utility.LOG_TAG, classname + methodname + data);

        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();

    }


    public void onClick(View view) {

        switch (view.getId()){
            case (R.id.go_to_main_button):
                intent = new Intent(A_Activity.this, MainActivity.class);
                setResult(RESULT_OK);
                finish(); //finish in necessary, if not, this activity will continue
                /* if setResult is set OK, the result on onActivityResult
                 * will be OK even if the user press the back button
                 * (comment finish to check this out)
                 */

                break;

            case (R.id.go_to_b_button):
                intent = new Intent(A_Activity.this, B_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //CLEAR_TOP pops the activity stack until the B activity is found
                //B activity is created again
                startActivity(intent);


                break;

            case (R.id.go_to_c_button):
                intent = new Intent(A_Activity.this, C_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                break;

            case (R.id.go_to_d_button):
                intent = new Intent(A_Activity.this, D_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }

    }

    public static Intent newIntent(Context packageContext, String data){
        Intent i = new Intent(packageContext, A_Activity.class);
        i.putExtra(EXTRA_DATA, data);
        return i;
    }
}
