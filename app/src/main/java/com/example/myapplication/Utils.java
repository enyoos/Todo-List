package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

public class Utils {

    // provide the filename with the ext .
    public static void writeToFile(Serializable data, Context context, String /*path*/ filename) {

        String msg;
        msg = "WRITING TO FILE";
        log(msg);

        try {
            FileOutputStream fileOutputStream = context.openFileOutput(filename, Context.MODE_APPEND);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(data);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            String ttl = "Uh...";
            msg = "Hint : you might give us permission to read/write files";
            showAlertPanel(ttl, msg, context);
        }
    }

    public static void removeFile(String fn, Context c) {
        boolean d = c.deleteFile(fn);
        log("is the file really deleted ? : " + d);
    }

    public static boolean isFileExisting(String filename, Context t) {
        try {
            t.openFileInput(filename);
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public static void log(String msg) {
        String header = "[MESSAGE]";
        Log.d(header, msg);
    }


    public static LinkedList<String> readFromFile(String filename, Context ctx) {
        // provide the filename with the ext .
        LinkedList<String> ret = null;
        try {
            FileInputStream fileInputStream = ctx.openFileInput(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ret = (LinkedList<String>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            String ttl = "Uh...";
            String msg = "Failed loading the todo storage file";
            showAlertPanel(ttl, msg, ctx);
        }

        return ret;
    }

    public static AlertDialog showAlertPanel(String ttl, String msg, Context ctx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(ttl);
        builder.setMessage(msg);
        builder.setCancelable(true);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.exit(0);
            }
        });

        return builder.create();
    }

}
