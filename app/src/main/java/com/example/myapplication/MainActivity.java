package com.example.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    private static LinkedList<String> todos;
    private static Stack<EntryTodo> todosMirror;
    private static final String FILENAME   = "db.ser";

    // usually this method is happening before
    // the activity is closed
    @Override
    protected void onStop() {
        super.onStop();
        Utils.writeToFile( todos, this, FILENAME);
    }

    @Override
    protected void onDestroy () {
        // call the logic inside the onStop function
        this.onStop();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText inputField = findViewById(R.id.inputNameField);
        ListView lV         = findViewById(R.id.listView);
        Button undoButton   = findViewById(R.id.undo);

        // listView is just a list of text content
        ArrayAdapter<String> arr = null;
        if ( Utils.isFileExisting(FILENAME, this) )
        {
            todos = Utils.readFromFile(FILENAME, this);
            arr = new ArrayAdapter<>(this, R.layout.white_txt_lv, R.id.list_content, todos );
        }
        else
        {
            todos = new LinkedList<String>();
            arr = new ArrayAdapter<>(this, R.layout.white_txt_lv, R.id.list_content, todos);
        }

        // we're not going to serialize the todosMirror too
        todosMirror = new Stack<>();

        lV.setAdapter(arr);

        lV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter arr = ( ArrayAdapter ) adapterView.getAdapter();
                deleteTodo(arr, i);
            }
        });

        undoButton.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                unDo((ArrayAdapter<String>) lV.getAdapter(), MainActivity.this);
            }

        });

        inputField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || i == EditorInfo.IME_ACTION_DONE ) {
                    String name = inputField.getText().toString();
                    addTodo(name, MainActivity.this , lV);
                    inputField.setText("");
                }
                return false;
            }
        });
    }


    private void unDo (ArrayAdapter<String> arr, Context ctx ) {
        boolean isStackEmpty = todosMirror.isEmpty();
        // make with toast
        if ( isStackEmpty ) {
            String msg = "Nothing to undo";
            Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
        }
        else {
            EntryTodo entry = todosMirror.pop();
            todos.add(entry.getIdx(), entry.getString());
            arr.notifyDataSetChanged();
        }
    }

    private void deleteTodo(ArrayAdapter<String> arr , int idx )
    {
        String toRemove = arr.getItem(idx);
        EntryTodo entry = new EntryTodo(toRemove, idx);
        arr.remove(toRemove);
        // push to the Todos mirror
        todosMirror.push(entry);
        arr.notifyDataSetChanged();
    }

    private void addTodo(String content, Context ctx, ListView lV )
    {
        if ( !content.isEmpty() )
        {
            // add the list to the thing
            todos.add(content);
            (( ArrayAdapter<String>) lV.getAdapter()).notifyDataSetChanged();
        }
        else
        {
            // show the alert panel
            String ttl = "Invalid input";
            String msg = "Your todo is empty";
            Utils.showAlertPanel( ttl, msg, ctx ).show();
        }
    }



}