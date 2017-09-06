package com.example.cristian.activitybasics1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class B_Activity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
    }

    public void onClick(View view) {

        switch (view.getId()){

            case (R.id.go_to_main_button):
                intent = new Intent(B_Activity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case (R.id.go_to_a_button):
                intent = A_Activity.newIntent(getApplicationContext(), "data from B");
                //intent = new Intent(B_Activity.this, A_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent); //sobreescribe la instancia de A por una nueva
                                       //se llama de nuevo el metodo onCreate en A
                break;

            case (R.id.go_to_c_button):
                intent = new Intent(B_Activity.this, C_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case (R.id.go_to_d_button):
                intent = new Intent(B_Activity.this, D_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }

    }
}
