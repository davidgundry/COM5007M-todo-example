package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private String filename = "todoFile.txt";
    private boolean useExternalFileStorage = false;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (useExternalFileStorage) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                file = new File(getApplicationContext().getExternalFilesDir(null), filename);
            }
        }
        else
            file = new File(getApplicationContext().getFilesDir(), filename);

        _updateDisplay();
    }

    public void addBtn(View view)
    {
        String item = ((EditText) findViewById(R.id.inputText)).getText().toString();
        _appendToFile(item+"\n", file);
        _updateDisplay();
    }

    public void displayBtn(View view)
    {
        _updateDisplay();
    }

    public void clearBtn(View view)
    {
        _deleteFile(file);
        ((TextView) findViewById(R.id.displayText)).setText("");
    }

    private void _appendToFile(String string, File file)
    {
        try {
            FileWriter stream = new FileWriter(file, true);
            stream.append(string);
            stream.close();
            Toast.makeText(getApplicationContext(), string + " was added successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private String _readFileContents(File file)
    {
        String s = "";
        try{
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine())
                s = s + "\n" + reader.nextLine();
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        return s;
    }

    private void _deleteFile(File file)
    {
        if (file.exists()) {
            file.delete();
            Toast.makeText(getApplicationContext(), "List was deleted", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "List does not exist", Toast.LENGTH_SHORT).show();
        }
    }

    private void _updateDisplay()
    {
        if (file.exists()) {
            String s = _readFileContents(file);
            ((TextView) findViewById(R.id.displayText)).setText(s);
        }
    }

}