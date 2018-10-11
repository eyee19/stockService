package com.example.eyee3.stockservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.eyee3.stockservice.service.MyLocalBinder;

public class MainActivity extends AppCompatActivity {

    EditText input;
    Button check;
    TextView output;
    Button unbind;
    service stockService;
    boolean isBound = false;

    private ServiceConnection stockConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyLocalBinder binder = (MyLocalBinder) service;
            stockService = binder.getService();
            isBound = true;
            Toast.makeText(MainActivity.this, "Service Started", Toast.LENGTH_SHORT).show();

            String[] stockPrice = stockService.getStock();

            if (input.getText().toString().equals("AMZN")) {
                output.setText(stockPrice[0]);
            }
            else if (input.getText().toString().equals("AAPL")) {
                output.setText(stockPrice[1]);
            }
            else if (input.getText().toString().equals("BA")) {
                output.setText(stockPrice[2]);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
            Toast.makeText(MainActivity.this, "Service Stopped", Toast.LENGTH_SHORT).show();
        }
    };

    void doUnbindService() {
        if (isBound) {
            unbindService(stockConnection);
            Toast.makeText(MainActivity.this, "Service Stopped", Toast.LENGTH_SHORT).show();
            isBound = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = (EditText) findViewById(R.id.input);
        check = (Button) findViewById(R.id.check);
        output = (TextView) findViewById(R.id.output);
        unbind = (Button) findViewById(R.id.unbind);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (input.getText().toString().equals("AMZN") ||
                        input.getText().toString().equals("AAPL") ||
                        input.getText().toString().equals("BA")) {
                    Intent i = new Intent(MainActivity.this, service.class);
                    bindService(i, stockConnection, Context.BIND_AUTO_CREATE);
                }
                else {
                    Toast.makeText(MainActivity.this, "Stock must be AMZN, AAPL, or BA", Toast.LENGTH_SHORT).show();
                }
            }
        });

        unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doUnbindService();
            }
        });
    }
}
