package com.zammle2009wtfgmail.utilityhelper;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private templateAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;
    private Button mOkay;
    private Button mCancel;
    private ImageView mWindow;
    private TextView mAppName;
    private TextView mBlack;
    private TextView mBlack2;

    private boolean OpenAPP = true;
    static int Position;


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
        //  Listload = (Button) findViewById(R.id.Load);


        mOkay = (Button) findViewById(R.id.appokaybutton2);
        mCancel = (Button) findViewById(R.id.appcancelbutton2);
        mAppName = (TextView) findViewById(R.id.appAppname2);
        mBlack = (TextView) findViewById(R.id.addBlack2);
        mWindow = (ImageView) findViewById(R.id.appWindow2);
        mBlack2 = (TextView) findViewById(R.id.addBlack3);

        mWindow.setVisibility(View.INVISIBLE);
        mBlack.setVisibility(View.INVISIBLE);
        mAppName.setVisibility(View.INVISIBLE);
        mCancel.setVisibility(View.INVISIBLE);
        mOkay.setVisibility(View.INVISIBLE);
        mBlack.setVisibility(View.INVISIBLE);
        mBlack2.setVisibility(View.INVISIBLE);












        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////// Loading on create. Compares Whitelist with List of apps  ///////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<String> list = new ArrayList<>();


        try {
            if (CloseList.CreateOnce == 0) {
                String[] newText = WhiteList.text.split(System.getProperty("line.separator"));
                String hold = readFile(WhiteList.filename2);
                Boolean copy = false;

                String[] TextWithTime = hold.split(System.getProperty("line.separator"));

                for (int i = 0; i < TextWithTime.length; ++i) {
                    list.add(TextWithTime[i]);
                }


                for (int i = 0; i < newText.length; i = i + 2) {

                    for (int z = 0; z < list.size(); z += 4) {
                        if (newText[i] == list.get(z)) {

                            copy = true;

                            MainActivity.ToReturn += list.indexOf(z);
                            MainActivity.ToReturn += list.indexOf(z + 1);
                            MainActivity.ToReturn += list.indexOf(z + 2);
                            MainActivity.ToReturn += list.indexOf(z + 3);

                        }

                    }


                    if (copy == false) {

                        list.add(newText[i] + (System.getProperty("line.separator")));
                        list.add("15" + (System.getProperty("line.separator")));
                        list.add("0" + (System.getProperty("line.separator")));
                        try {
                            list.add(newText[i + 1] + (System.getProperty("line.separator")));
                        } catch (Exception e) {
                        }


                        MainActivity.ToReturn += newText[i] + (System.getProperty("line.separator"));
                        MainActivity.ToReturn += "15" + (System.getProperty("line.separator"));
                        MainActivity.ToReturn += "0" + (System.getProperty("line.separator"));
                        // new
                        try {
                            MainActivity.ToReturn += newText[i + 1] + (System.getProperty("line.separator"));
                        } catch (Exception e) {
                        }

                    }

                    copy = false;


                }

                saveFile(WhiteList.filename2, MainActivity.ToReturn);
                CloseList.CreateOnce += 1;
            }
        }
        catch (Exception e)
        {
            CloseList.CreateOnce = 0;
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////// END OF LOADING //////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////// Spliting information from text file //////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////


        CloseList.Holder.clear();

        String[] TextWithInfo = MainActivity.ToReturn.split(System.getProperty("line.separator"));

        for (int i = 0; i < TextWithInfo.length; i = i + 4)
        {
            if (Integer.valueOf(TextWithInfo[i+2]) == 1)
            {
                String appName = TextWithInfo[i];
                int Time = Integer.valueOf(TextWithInfo[i + 1]);
                boolean bool = true;
                String PackageName = TextWithInfo[i+3];


                try {

                    Drawable icon = getPackageManager().getApplicationIcon(TextWithInfo[i+3]);


                    if (Integer.valueOf(TextWithInfo[i+2]) == 1)
                    {
                        CloseList.Holder.add(new templateHolder(icon,appName, bool, Time, true,PackageName));
                    }
                    else
                    {
                        CloseList.Holder.add(new templateHolder(icon,appName, bool, Time, false,PackageName));
                    }

                }
                catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();



                }
            }
        }




        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////// List of adapters //////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        mRecycle = findViewById(R.id.myrecycle2);
        mRecycle.setHasFixedSize(true);
        mLayout = new LinearLayoutManager(this);
        mAdapter = new templateAdapter(CloseList.Holder);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecycle.getContext(), layoutManager.getOrientation());

        mRecycle.addItemDecoration(dividerItemDecoration);



        mRecycle.setLayoutManager(mLayout);
        mRecycle.setAdapter(mAdapter);



        mOkay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (CloseList.Holder.get(Position).getSwitch() == true)
                {
                    OpenAPP = true;
                    mWindow.setVisibility(View.INVISIBLE);
                    mBlack.setVisibility(View.INVISIBLE);
                    mAppName.setVisibility(View.INVISIBLE);
                    mCancel.setVisibility(View.INVISIBLE);
                    mOkay.setVisibility(View.INVISIBLE);
                    mBlack2.setVisibility(View.INVISIBLE);
                    CloseList.Holder.get(Position).SetBool(false);
                    CloseList.Holder.get(Position).SetVis(false);


                    mAdapter.notifyItemChanged(Position);
                }
                else
                {
                    OpenAPP = true;
                    mWindow.setVisibility(View.INVISIBLE);
                    mBlack.setVisibility(View.INVISIBLE);
                    mAppName.setVisibility(View.INVISIBLE);
                    mCancel.setVisibility(View.INVISIBLE);
                    mOkay.setVisibility(View.INVISIBLE);
                    mBlack2.setVisibility(View.INVISIBLE);
                    CloseList.Holder.get(Position).SetBool(true);
                    CloseList.Holder.get(Position).SetVis(true);

                    mAdapter.notifyItemChanged(Position);
                }


                String UpdateSave ="";
                String[] TextWithInfo = MainActivity.ToReturn.split(System.getProperty("line.separator"));


                for (int i = 0; i < CloseList.Holder.size(); ++i)
                {
                    for (int x = 0; x < TextWithInfo.length; x = x + 4)

                    {

                        String tempstring = TextWithInfo[x].replace(System.getProperty("line.separator"), "");
                        String tempstring2 = CloseList.Holder.get(i).getAppName().replace(System.getProperty("line.separator"), "");

                        if (tempstring2.equals(tempstring))

                        {
                            TextWithInfo[x+1] = String.valueOf(CloseList.Holder.get(i).getNumberPicker());

                            if (CloseList.Holder.get(i).getSwitch() == true)
                            {
                                TextWithInfo[x+2] = String.valueOf(1);
                               // Toast.makeText(WhiteList.this,"IM IN. ON", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                TextWithInfo[x+2] = String.valueOf(0);
                              //  Toast.makeText(WhiteList.this,"IM IN. OFF", Toast.LENGTH_SHORT).show();
                            }



                        }
                        else{
                            //Toast.makeText(WhiteList.this,"IM NOT IN", Toast.LENGTH_SHORT).show();
                        }



                    }



                }



                for (int i = 0; i < TextWithInfo.length; ++i)
                {
                    UpdateSave += TextWithInfo[i] + (System.getProperty("line.separator"));

                }

                MainActivity.ToReturn = UpdateSave;



                saveFile(WhiteList.filename2, MainActivity.ToReturn);

            }

        });

        mCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (CloseList.Holder.get(Position).getSwitch() == true)
                {
                    OpenAPP = true;
                    mWindow.setVisibility(View.INVISIBLE);
                    mBlack.setVisibility(View.INVISIBLE);
                    mAppName.setVisibility(View.INVISIBLE);
                    mCancel.setVisibility(View.INVISIBLE);
                    mOkay.setVisibility(View.INVISIBLE);
                    mBlack2.setVisibility(View.INVISIBLE);
                    CloseList.Holder.get(Position).SetBool(true);
                    CloseList.Holder.get(Position).SetVis(true);

                    mAdapter.notifyItemChanged(Position);
                }
                else{
                    OpenAPP = true;
                    mWindow.setVisibility(View.INVISIBLE);
                    mBlack.setVisibility(View.INVISIBLE);
                    mAppName.setVisibility(View.INVISIBLE);
                    mCancel.setVisibility(View.INVISIBLE);
                    mOkay.setVisibility(View.INVISIBLE);
                    mBlack2.setVisibility(View.INVISIBLE);
                    CloseList.Holder.get(Position).SetBool(false);
                    CloseList.Holder.get(Position).SetVis(false);

                    mAdapter.notifyItemChanged(Position);

                }


            }

        });



        mAdapter.setOnItemClickListener(new templateAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position)
            {
                if (OpenAPP == true)
                {

                    if (CloseList.Holder.get(Position).getSwitch() == true)
                    {
                        OpenAPP = false;
                        Position = position;
                        mAppName.setVisibility(View.VISIBLE);
                        mBlack.setVisibility(View.VISIBLE);
                        mWindow.setVisibility(View.VISIBLE);
                        mOkay.setVisibility(View.VISIBLE);
                        mCancel.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        OpenAPP = false;
                        Position = position;
                        mAppName.setVisibility(View.VISIBLE);
                        mBlack2.setVisibility(View.VISIBLE);
                        mWindow.setVisibility(View.VISIBLE);
                        mOkay.setVisibility(View.VISIBLE);
                        mCancel.setVisibility(View.VISIBLE);
                    }


                }



            }
        });




        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);



       /* Listload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/



        //////////////////////////////////////////////////////////////////
        /////////////////////// Update Saves /////////////////////////////
        //////////////////////////////////////////////////////////////////
        load.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent history = new Intent (WhiteList.this, CloseList.class );
                startActivity(history);
            }

        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String UpdateSave ="";
                String[] TextWithInfo = MainActivity.ToReturn.split(System.getProperty("line.separator"));


                for (int i = 0; i < CloseList.Holder.size(); ++i)
                {
                    for (int x = 0; x < TextWithInfo.length; x = x + 5)

                    {

                        String tempstring = TextWithInfo[x].replace(System.getProperty("line.separator"), "");
                        String tempstring2 = CloseList.Holder.get(i).getAppName().replace(System.getProperty("line.separator"), "");

                        if (tempstring2.equals(tempstring))

                        {
                            TextWithInfo[x+1] = String.valueOf(CloseList.Holder.get(i).getNumberPicker());

                            if (CloseList.Holder.get(i).getSwitch() == true)
                            {
                                TextWithInfo[x+2] = String.valueOf(1);
                                //Toast.makeText(WhiteList.this,"IM IN. ON", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                TextWithInfo[x+2] = String.valueOf(0);
                             //   Toast.makeText(WhiteList.this,"IM IN. OFF", Toast.LENGTH_SHORT).show();
                            }



                        }
                        else{
                            //Toast.makeText(WhiteList.this,"IM NOT IN", Toast.LENGTH_SHORT).show();
                        }



                    }



                }



                for (int i = 0; i < TextWithInfo.length; ++i)
                {
                    UpdateSave += TextWithInfo[i] + (System.getProperty("line.separator"));

                }

                MainActivity.ToReturn = UpdateSave;



                saveFile(WhiteList.filename2, MainActivity.ToReturn);
            }
        });

    }




    public void saveFile(String file, String text)
    {
        try
        {
            FileOutputStream fos = openFileOutput(file, Context.MODE_PRIVATE);;

            fos.write(text.getBytes());
            fos.close();
           // Toast.makeText(WhiteList.this,"Saved", Toast.LENGTH_SHORT).show();
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
            //  Toast.makeText(CloseList.this,"Error reading file!", Toast.LENGTH_SHORT).show();
        }

        return textread;
    }

    public void changeItem(int position, int value)
    {
        if (templateAdapter.again == 0)
        {
            CloseList.Holder.get(position).SetValue(value);
            mAdapter.notifyItemChanged(position);
            templateAdapter.again +=1;
        }

    }


    public void changeBools(int position, boolean value)
    {
        if (templateAdapter.againBool == 0)
        {
            CloseList.Holder.get(position).SetBool(value);
            mAdapter.notifyItemChanged(position);
            templateAdapter.againBool +=1;
        }

    }

}