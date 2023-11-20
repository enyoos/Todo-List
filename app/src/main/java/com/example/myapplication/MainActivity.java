package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText inputField = findViewById(R.id.inputNameField);
        TextView outputField = findViewById(R.id.outputNameField);
        Button btn = findViewById(R.id.buttonEnter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the name from the input field
                String name = inputField.getText().toString();
                registerName(name, MainActivity.this , outputField);
                inputField.setText("");
            }
        });
        inputField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || i == EditorInfo.IME_ACTION_DONE ) {
                    btn.performClick();
                }
                return false;
            }
        });
    }

    public void registerName (String name, Context ctx, TextView tv )
    {
        String temp = tv.getText().toString();
        String rgxp = ":";
        String out = temp.split(rgxp)[0];

        if ( !name.isEmpty() )
        {
            out += ": " + name;
            tv.setText(out);
        }
        else
        {
            // show the alert panel
            String ttl = "invalid input";
            String msg = "your name is empty";
            showAlertPanel( ttl, msg, ctx ).show();
        }
    }

    private AlertDialog showAlertPanel (String ttl , String msg, Context ctx )
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(ttl);
        builder.setMessage(msg);
        builder.setCancelable(true);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) { dialogInterface.cancel(); }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) { System.exit(0); }
        });

        return builder.create();
    }

}