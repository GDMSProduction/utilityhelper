package com.zammle2009wtfgmail.utilityhelper;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class WhiteList extends AppCompatActivity {



    public Button save, load;

    static String filename = "utilityhelperstorage.txt";
    static  String filename2 = "utilityhelpercloselist.txt";
    List<templateHolder> AppInfo;

    static String text = "";



    private RecyclerView mRecycle;
    private TemplateAdapter2 mAdapter;
    private RecyclerView.LayoutManager mLayout;


    static ArrayList<TemplateHolder2> Holder2 = new ArrayList<>();

////////////////////////////////////////////////////////////////////////////////
/////////////////// ON CREATE //////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_white_list);


        save = (Button) findViewById(R.id.Save2);
        load = (Button) findViewById(R.id.Load2);



        try
        {
            FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
            FileOutputStream fos2 = openFileOutput(filename2, Context.MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
            fos2.close();
            //Toast.makeText(WhiteList.this,"Saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(WhiteList.this,"Error saving file!", Toast.LENGTH_SHORT).show();
        }




        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////// Loading on create. Compares Whitelist with List of apps  ///////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<String> list = new ArrayList<>();


        if (CloseList.CreateOnce == 0)
        {
            String[] newText = WhiteList.text.split(System.getProperty("line.separator"));
            String hold = readFile(WhiteList.filename2);
            Boolean copy = false;

            String[] TextWithTime = hold.split(System.getProperty("line.separator"));

            for (int i = 0; i < TextWithTime.length; ++i) {
                list.add(TextWithTime[i]);
            }


            for (int i = 0; i < newText.length; ++i) {

                for (int z = 0; z < list.size(); z += 3) {
                    if (newText[i] == list.get(z)) {

                        copy = true;

                        MainActivity.ToReturn += list.indexOf(z);
                        MainActivity.ToReturn += list.indexOf(z + 1);
                        MainActivity.ToReturn += list.indexOf(z + 2);
                    }

                }


                if (copy == false) {
                    if (newText[i] == "utilityhelper")
                    {
                        list.add(newText[i] + (System.getProperty("line.separator")));
                        list.add("15" + (System.getProperty("line.separator")));
                        list.add("0" + (System.getProperty("line.separator")));

                        MainActivity.ToReturn += newText[i] + (System.getProperty("line.separator"));
                        MainActivity.ToReturn += "15" + (System.getProperty("line.separator"));
                        MainActivity.ToReturn += "1" + (System.getProperty("line.separator"));
                    }
                    else
                    {
                        list.add(newText[i] + (System.getProperty("line.separator")));
                        list.add("15" + (System.getProperty("line.separator")));
                        list.add("0" + (System.getProperty("line.separator")));

                        MainActivity.ToReturn += newText[i] + (System.getProperty("line.separator"));
                        MainActivity.ToReturn += "15" + (System.getProperty("line.separator"));
                        MainActivity.ToReturn += "0" + (System.getProperty("line.separator"));
                    }

                }

                copy = false;


            }

            saveFile(WhiteList.filename2, MainActivity.ToReturn);
            CloseList.CreateOnce += 1;
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////// END OF LOADING //////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////// Spliting information from text file //////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////

        String[] TextWithInfo = MainActivity.ToReturn.split(System.getProperty("line.separator"));

        for (int i = 0; i < TextWithInfo.length; i = i + 3)
        {
            if (Integer.valueOf(TextWithInfo[i+2]) == 1)
            {
                String appName = TextWithInfo[i];
                int Time = Integer.valueOf(TextWithInfo[i + 1]);
                boolean bool = true;


                Holder2.add(new TemplateHolder2(R.drawable.defaulticon, appName, bool, Time));
            }
        }




        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////// List of adapters //////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////


        mRecycle = findViewById(R.id.myrecycle2);
        mRecycle.setHasFixedSize(true);
        mLayout = new LinearLayoutManager(this);
        mAdapter = new TemplateAdapter2(Holder2);

        mRecycle.setLayoutManager(mLayout);
        mRecycle.setAdapter(mAdapter);



        mAdapter.setOnItemClickListener2(new TemplateAdapter2.OnItemClickListener() {
            @Override
            public void OnItemClick2(int position) {


                changeBools2(position, TemplateAdapter2.appBool2);

            }
        });




        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);



        //////////////////////////////////////////////////////////////////
        /////////////////////// Update Saves /////////////////////////////
        //////////////////////////////////////////////////////////////////
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String UpdateSave ="";



                for (int i = 0; i < Holder2.size(); ++i)
                {


                    UpdateSave += Holder2.get(i).getAppName() + (System.getProperty("line.separator"));



                    UpdateSave += Holder2.get(i).getNumberPicker() + (System.getProperty("line.separator"));

                    if (Holder2.get(i).getSwitch() == true)
                    {
                        UpdateSave += '1' + (System.getProperty("line.separator"));
                    }
                    else
                    {
                        UpdateSave += '0' + (System.getProperty("line.separator"));
                    }
                }




                MainActivity.ToReturn = UpdateSave;



                saveFile(WhiteList.filename2, MainActivity.ToReturn);
            }
        });

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////Setting Buttons ///////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent history = new Intent (WhiteList.this, CloseList.class );
                startActivity(history);


            }
        });


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


    }


    public void saveFile(String file, String text)
    {
        try
        {
            FileOutputStream fos = openFileOutput(file, Context.MODE_PRIVATE);;

            fos.write(text.getBytes());
            fos.close();
            Toast.makeText(WhiteList.this,"Saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(WhiteList.this,"Error saving file!", Toast.LENGTH_SHORT).show();
        }



    }


    public String readFile (String file)
    {
        String textread = "";

        try
        {
            FileInputStream fis = openFileInput(file);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            textread = new String(buffer);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //Toast.makeText(WhiteList.this,"Error reading file!", Toast.LENGTH_SHORT).show();
        }

        return textread;
    }


    public void changeBools2(int position, boolean value)
    {
        if (TemplateAdapter2.againBool2 == 0)
        {
            Holder2.get(position).SetBool(value);
            mAdapter.notifyItemChanged(position);
            TemplateAdapter2.againBool2 +=1;
        }

    }


}