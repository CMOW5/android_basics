package com.example.cristian.asynctaskexample;

import android.os.AsyncTask;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button mButton, mButton2, mButton3;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);

        mButton2 = (Button) findViewById(R.id.button2);
        mButton2.setOnClickListener(this);

        mButton3 = (Button) findViewById(R.id.button3);
        mButton3.setOnClickListener(this);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void oneSecond(){
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){

        }
    }

    @Override
    public void onClick(View v) {

        //Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();

        switch (v.getId()){
            case (R.id.button):

                for (int i = 0; i < 5; i++){
                  oneSecond();
                }

                break;

            case (R.id.button2):

                HilosJava();

                break;

            case (R.id.button3):

                AsyncTaskEx task = new AsyncTaskEx();
                //no se pasan parametros de entrada
                //porque en la definicion el primer valor
                //que es el parametro de entrada es Void
                task.execute();


                break;

            default:
                break;
        }

    }

    void HilosJava(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++){
                    oneSecond();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(),  "tarea finalizada", Toast.LENGTH_SHORT).show();
                    }
                });
                //Toast.makeText(getBaseContext(), "tarea finalizada", Toast.LENGTH_SHORT).show();
            }
        }).start();

    }
                                                //doInBackground
                                                //in         //out
                                                             //Void
    private class AsyncTaskEx extends AsyncTask<Void,Integer,Boolean>{


        public AsyncTaskEx() {
            super();
        }

        //la que se ejecuta en el hilo principal
        //se ejecuta antes de realizar la tarea en segundo plano
        //por ejemplo inicializar vables, objetos, interfaz
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //INIT
            mProgressBar.setMax(100);
            mProgressBar.setProgress(0);
        }

        //lo que se ejecuta en segundo plano
        //params
        @Override
        protected Boolean doInBackground(Void... params) {

            for(int i = 1; i < 10; i++){
                oneSecond();
                //hace llamar onProgressUpdate
                //para comunicar hilo secundario con ppal
                publishProgress(i*10);
                if(isCancelled()){
                    break;
                }
            }

            return true;
        }

        //se ejecuta en el hilo principal
        //cuando se ejecuta publishUpdate
        @Override
        protected void onProgressUpdate(Integer... values) {
            //hilo ppal
            super.onProgressUpdate(values);
            mProgressBar.setProgress(values[0].intValue());


        }

        //se ejecuta cuando termina el doInbackground
        //cuando se acaba el hilo aVoid = out Void
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            //super.onPostExecute(aBoolean);
            if(aBoolean){
                Toast.makeText(getApplicationContext(), "tarea AsyncTask finalizada",
                        Toast.LENGTH_SHORT)
                        .show();
            }

        }


        //si se corta la ejecucion del hilo ppal
        @Override
        protected void onCancelled() {
            super.onCancelled();

            Toast.makeText(getApplicationContext(), "tarea AsyncTask cancelada",
                    Toast.LENGTH_SHORT)
                    .show();

        }


    }


}
