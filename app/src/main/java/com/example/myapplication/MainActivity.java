package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static ArrayList<String> todos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText inputField = findViewById(R.id.inputNameField);
        ListView lV         = findViewById(R.id.listView);
        Button btn = findViewById(R.id.buttonEnter);

        // listView is just a list of text content
        ArrayAdapter<String> arr = new ArrayAdapter<>(this, R.layout.white_txt_lv, R.id.list_content, todos);
        lV.setAdapter(arr);

        lV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter arr = ( ArrayAdapter ) adapterView.getAdapter();
                deleteTodo(arr, i);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the name from the input field
                String name = inputField.getText().toString();
                addTodo(name, MainActivity.this , lV);
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

    public void deleteTodo(ArrayAdapter<String> arr , int idx )
    {
        String toRemove = arr.getItem(idx);
        arr.remove(toRemove);
        arr.notifyDataSetChanged();

        String out = String.format("removed : %s at the idx %d", toRemove, idx);
        Log.d ( "[MESSAGE]", out);
    }

    public void addTodo(String content, Context ctx, ListView lV )
    {
        if ( !content.isEmpty() )
        {
            // add the list to the thing
            todos.add(content);
            (( ArrayAdapter<String>) lV.getAdapter()).notifyDataSetChanged();

            String out = String.format("added %s to the arr", content);
            Log.d("[MESSAGE]", out);
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