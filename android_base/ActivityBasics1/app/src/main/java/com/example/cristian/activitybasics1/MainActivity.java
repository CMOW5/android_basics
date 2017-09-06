package com.example.cristian.activitybasics1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String classname = MainActivity.this.getClass().getSimpleName();

    private int REQUEST_CODE_ACTIVY_A = 1;
    private int REQUEST_CODE_ACTIVY_B = 2;
    private int REQUEST_CODE_ACTIVY_C = 3;
    private int REQUEST_CODE_ACTIVY_D = 4;

    private Button mButtonA, mButtonB, mButtonC, mButtonD;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String methodname = " onCreate(..) ";
        Log.d(Utility.LOG_TAG, classname + methodname);

        //not necesary
        mButtonA = (Button) findViewById(R.id.go_to_a_button);
        mButtonB = (Button) findViewById(R.id.go_to_b_button);
        mButtonC = (Button) findViewById(R.id.go_to_c_button);
        mButtonD = (Button) findViewById(R.id.go_to_d_button);

    }

    public void onClick(View view) {

        String methodname = " onClick(..) ";

        switch (view.getId()){
            case (R.id.go_to_a_button):
                Log.d(Utility.LOG_TAG, classname + methodname + "go to A");
                //intent = new Intent(MainActivity.this, A_Activity.class);
                intent = A_Activity.newIntent(getApplicationContext(), "data from Main");
                startActivityForResult(intent,REQUEST_CODE_ACTIVY_A);
                //startActivity(intent);
                //finish();
                break;

            case (R.id.go_to_b_button):
                Log.d(Utility.LOG_TAG, classname + methodname + "go to B");
                intent = new Intent(MainActivity.this, B_Activity.class);
                startActivityForResult(intent,REQUEST_CODE_ACTIVY_B);
                break;
            case (R.id.go_to_c_button):
                Log.d(Utility.LOG_TAG, classname + methodname + "go to C");
                intent = new Intent(MainActivity.this, C_Activity.class);
                startActivityForResult(intent,REQUEST_CODE_ACTIVY_C);

                break;
            case (R.id.go_to_d_button):
                Log.d(Utility.LOG_TAG, classname + methodname + "go to D");
                intent = new Intent(MainActivity.this, D_Activity.class);
                startActivityForResult(intent,REQUEST_CODE_ACTIVY_D);

                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "onActivityResult", Toast.LENGTH_SHORT).show();

        if(requestCode == REQUEST_CODE_ACTIVY_A){
            if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "onActivityResult A CANCELED", Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RESULT_OK){
                Toast.makeText(this, "onActivityResult A OK", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == REQUEST_CODE_ACTIVY_B){
            if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "onActivityResult B CANCELED", Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RESULT_OK){
                Toast.makeText(this, "onActivityResult B OK", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == REQUEST_CODE_ACTIVY_C){
            if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "onActivityResult C CANCELED", Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RESULT_OK){
                Toast.makeText(this, "onActivityResult C OK", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == REQUEST_CODE_ACTIVY_D){
            if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, "onActivityResult D CANCELED", Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RESULT_OK){
                Toast.makeText(this, "onActivityResult D OK", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
