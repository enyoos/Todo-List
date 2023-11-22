package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.ArrayAdapter;

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

public class Utils {

    // provide the filename with the ext .
    public static void writeToFile(ArrayAdapter<String> data, Context context, String filename) {

        if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            // the permission is not granted
            // so ask for the user to do grant it !
            String perm = "Enable it, in order to save your todos locally";

            // TODO : FINISH THIS
            // TODO: 11/22/2023  
//            ActivityCompat.requestPermissions( (Activity) context, new String[]{perm}, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        else {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(filename);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(data);
                objectOutputStream.flush();
                objectOutputStream.close();
           }
            catch (IOException e) {
                Log.e("Exception", "File write failed: " + e.toString());
            }
        }
    }

    public static boolean isFileExisting ( String filename )
    {
        File file =  new File ( filename );
        return file.exists();
    }

    // returns the object
    public static ArrayAdapter<String> readFromFile (String filename)
    {
        // provide the filename with the ext .
        ArrayAdapter<String> ret = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ret = (ArrayAdapter<String>) objectInputStream.readObject();
            objectInputStream.close();
        }
        catch ( IOException | ClassNotFoundException e )
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }

        return ret;
    }

}
