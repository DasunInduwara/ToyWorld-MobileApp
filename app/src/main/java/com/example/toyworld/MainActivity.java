package com.example.toyworld;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    EditText id, fname, lname, marks;
    Button addDataBtn, viewDataBtn, updateDataBtn, deleteDataBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        id = findViewById(R.id.indexeditText2);
        fname = (EditText)findViewById(R.id.fnameeditText3);
        lname = findViewById(R.id.lnameeditText4);
        marks = findViewById(R.id.markseditText5);
        addDataBtn = (Button)findViewById(R.id.addbutton);
        viewDataBtn = findViewById(R.id.viewbutton3);
        updateDataBtn = findViewById(R.id.updatebutton4);
        deleteDataBtn = findViewById(R.id.deletebutton5);

        addData();
        viewAllData();
        updateData();
        deleteFromDataBase();
    }

    public void addData() {
        addDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = databaseHelper.insertData(fname.getText().toString(), lname.getText().toString(), marks.getText().toString());

                if(result == true) {
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Data NOT Inserted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void updateData() {
        updateDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdated = databaseHelper.updateData(
                        id.getText().toString(),
                        fname.getText().toString(),
                        lname.getText().toString(),
                        marks.getText().toString());

                if(isUpdated == true) {
                    Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Data NOT Updated", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void viewAllData() {
        viewDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor result = databaseHelper.viewData();
                if(result.getCount() == 0) {
                    displayMessage( "ERROR", "No data found!");
                    return;
                }

                StringBuffer stringBuffer = new StringBuffer();
                while (result.moveToNext()) {
                    stringBuffer.append("id : "+ result.getString(0)+ "\n");
                    stringBuffer.append("FNAME : "+ result.getString(1)+ "\n");
                    stringBuffer.append("LNAME : "+ result.getString(2)+ "\n");
                    stringBuffer.append("MARKS : "+ result.getString(3)+ "\n\n");
                }

                displayMessage("DATA_FROM_DB", stringBuffer.toString());
            }
        });
    }

    public void deleteFromDataBase() {
        deleteDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int deletR = databaseHelper. deleteData(id.getText().toString());

                if(deletR > 0) {
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Data NOT Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void displayMessage(String title, String message) {
        AlertDialog.Builder alb = new AlertDialog.Builder(this);
        alb.setCancelable(true);
        alb.setTitle(title);
        alb.setMessage(message);
        alb.show();
    }
}
