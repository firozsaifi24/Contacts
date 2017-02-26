package com.example.firoz.contacts;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText e1,e2,e3,e4;
    TextView tv;
    Button save,update,delete,show;

    DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper=new DatabaseHelper(this);

        e1= (EditText) findViewById(R.id.editText);
        e2=(EditText)findViewById(R.id.editText2);
        e3=(EditText)findViewById(R.id.editText3);
        e4=(EditText)findViewById(R.id.editText4);
        tv=(TextView)findViewById(R.id.textView);


        save=(Button)findViewById(R.id.button);
        update=(Button)findViewById(R.id.button2);
        delete=(Button)findViewById(R.id.button3);
        show=(Button)findViewById(R.id.button4);

        save.setOnClickListener(this);
        update.setOnClickListener(this);
        delete.setOnClickListener(this);
        show.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        String id=e4.getText().toString();
        String name=e1.getText().toString();
        String phone=e2.getText().toString();
        String email=e3.getText().toString();
        switch(v.getId())
        {

            case R.id.button:
                if(name.trim().equals("")||phone.trim().equals("")||email.trim().equals(""))
                {
                    Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show();
                    break;
                }
                else
                {
                    long resultInsert=dbHelper.insert(name,
                            phone,email);
                    e1.setText("");
                    e2.setText("");
                    e3.setText("");
                    e4.setText("");
                    e1.requestFocus();
                    if(resultInsert==-1)
                    {
                        Toast.makeText(getApplicationContext(),"Some error occurred while saving",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Contact Saved Successfully",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

            case R.id.button2:
                if(id.trim().equals("")||name.trim().equals("")||
                        phone.trim().equals("")||email.trim().equals(""))
                {
                    Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show();
                    break;
                }
                else
                {
                    long resultUpdate=dbHelper.update(Integer.parseInt(id),name,
                            phone,email);
                    e1.setText("");
                    e2.setText("");
                    e3.setText("");
                    e4.setText("");
                    e1.requestFocus();
                    if(resultUpdate==0)
                    {
                        Toast.makeText(getApplicationContext(),"Some error occurred while updating",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Contact Updated Successfuly",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

            case R.id.button3:
                if(id.trim().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please fill ID field",Toast.LENGTH_SHORT).show();
                    break;
                }
                else
                {
                    long resultDelete=dbHelper.delete(Integer.parseInt(id));
                    e1.setText("");
                    e2.setText("");
                    e3.setText("");
                    e4.setText("");
                    e1.requestFocus();
                    if(resultDelete==0)
                    {
                        Toast.makeText(getApplicationContext(),"Some error occurred while deleting",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Contact Deleted Successfully",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

            case R.id.button4:
                Cursor cursor=dbHelper.getAllData();
                if(cursor.getCount()==0)
                {
                    tv.setText("");
                    Toast.makeText(getApplicationContext(),"Data not found",Toast.LENGTH_SHORT).show();

                    return;
                }
                StringBuffer data=new StringBuffer();
                while(cursor.moveToNext())
                {
                    data.append("ID: "+cursor.getInt(0)+"\n");
                    data.append("Name: "+cursor.getString(1)+"\n");
                    data.append("Phone: "+cursor.getString(2)+"\n");
                    data.append("Email: "+cursor.getString(3)+"\n\n");
                }
                tv.setText(data);

                break;
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        dbHelper.openDB();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.closeDB();
    }


}
