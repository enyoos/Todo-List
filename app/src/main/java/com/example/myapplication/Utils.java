package com.example.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.ArrayAdapter;

import android.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;

public class Utils {

    // provide the filename with the ext .
    public static void writeToFile(Serializable data, Context context, String /*path*/ filename) {

//        if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED)
//        {
//        }
//        Log.d("[MESSAGE]", "external : " + external.getAbsolutePath());
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(data);
            objectOutputStream.flush();
            objectOutputStream.close();
           }
        catch (IOException e) {
            String ttl = "Uh...";
            String msg = "Hint : you might give us permission to read/write files";
            showAlertPanel(ttl, msg, context);
        }
    }

    public static boolean isFileExisting ( String filename, Context ctx )
    {
        try {
            ctx.openFileInput(filename);
            return true;
        }
        catch( FileNotFoundException e )
        {
            return false;
        }
    }

    public static LinkedList<String> readFromFile (String filename, Context ctx )
    {
        // provide the filename with the ext .
        LinkedList<String> ret = null;
        try {
            FileInputStream fileInputStream = ctx.openFileInput(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ret = (LinkedList<String>) objectInputStream.readObject();
            objectInputStream.close();
        }
        catch ( IOException | ClassNotFoundException e )
        {
            String ttl = "Uh...";
            String msg = "Failed loading the todo storage file";
            showAlertPanel(ttl, msg, ctx);
        }

        return ret;
    }

    public static AlertDialog showAlertPanel (String ttl , String msg, Context ctx )
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
