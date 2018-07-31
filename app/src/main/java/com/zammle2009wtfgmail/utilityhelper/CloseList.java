package com.zammle2009wtfgmail.utilityhelper;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class CloseList extends AppCompatActivity {


    public Button Listsave, Listload;

    static int temp = 0;

    public static int CreateOnce = 0;
    private RecyclerView mRecycle;
    private templateAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;

    private ImageView mWHite;
    private EditText mAppTime;
    private TextView mTextView;
    private Button mOkay;
    private Button mCancel;
    private Switch mSwitch;
    private TextView mAppName;
    private ImageView mAppWindow;
    private TextView mAdd;


    private boolean OpenApp = true;

    static ArrayList<templateHolder> Holder = new ArrayList<>();
////////////////////////////////////////////////////////////////////////////////
/////////////////// ON CREATE //////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_list);




        Listsave = (Button) findViewById(R.id.Save);
        //  Listload = (Button) findViewById(R.id.Load);


        mWHite = (ImageView)findViewById(R.id.appwhite);
        mAppTime = (EditText) findViewById(R.id.apptimer);
        mTextView = (TextView) findViewById(R.id.apptextview);
        mOkay = (Button) findViewById(R.id.appokaybutton);
        mCancel = (Button) findViewById(R.id.appcancelbutton);
        mSwitch = (Switch) findViewById(R.id.appswitch);
        mAppName = (TextView) findViewById(R.id.appAppname);
        mAppWindow = (ImageView) findViewById(R.id.appWindow);
        mAdd = (TextView) findViewById(R.id.addBlack);


        mWHite.setVisibility(View.INVISIBLE);
        mAppTime.setVisibility(View.INVISIBLE);
        mTextView.setVisibility(View.INVISIBLE);
        mOkay.setVisibility(View.INVISIBLE);
        mCancel.setVisibility(View.INVISIBLE);
        mSwitch.setVisibility(View.INVISIBLE);
        mAppName.setVisibility(View.INVISIBLE);
        mAppWindow.setVisibility(View.INVISIBLE);
        mAdd.setVisibility(View.INVISIBLE);

        mSwitch.setEnabled(false);
        mAppTime.setEnabled(false);











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


            for (int i = 0; i < newText.length; i = i + 2) {

                for (int z = 0; z < list.size(); z += 4) {
                    if (newText[i] == list.get(z)) {

                        copy = true;

                        MainActivity.ToReturn += list.indexOf(z);
                        MainActivity.ToReturn += list.indexOf(z + 1);
                        MainActivity.ToReturn += list.indexOf(z + 2);
                        MainActivity.ToReturn += list.indexOf(z+3);
                    }

                }


                if (copy == false)
                {

                    list.add(newText[i] + (System.getProperty("line.separator")));
                    list.add("15" + (System.getProperty("line.separator")));
                    list.add("0" + (System.getProperty("line.separator")));
                    list.add(newText[i+1] +  (System.getProperty("line.separator")));
                    try {
                        list.add(newText[i + 1] + (System.getProperty("line.separator")));
                    }
                    catch (Exception e)
                    {}


                    MainActivity.ToReturn += newText[i] + (System.getProperty("line.separator"));
                    MainActivity.ToReturn += "15" + (System.getProperty("line.separator"));
                    MainActivity.ToReturn += "0" + (System.getProperty("line.separator"));
                    // new
                    try {
                        MainActivity.ToReturn += newText[i + 1] + (System.getProperty("line.separator"));
                    }
                    catch (Exception e)
                    {}

                }

                copy = false;


            }

            saveFile(WhiteList.filename2, MainActivity.ToReturn);
            CloseList.CreateOnce += 1;
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////// END OF LOADING //////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////// END OF LOADING //////////////////////////////////////////////////////////////
        ///////////////////////////// Spliting information from text file //////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////

        CloseList.Holder.clear();

        String[] TextWithInfo = MainActivity.ToReturn.split(System.getProperty("line.separator"));

        for (int i = 0; i < TextWithInfo.length; i = i + 4)
        {
            String appName = TextWithInfo[i];
            int Time = Integer.valueOf(TextWithInfo[i+1]);
            boolean bool = false;

            if (Integer.valueOf(TextWithInfo[i+2]) == 1)
            {
                bool = true;
            }
            else
            {
                bool = false;

            }


            String PackageName = "SHOULD NOT BE DISPLAYING";
                try {
                    PackageName = TextWithInfo[i + 3];
                } catch (Exception e)
                {}


            Holder.add(new templateHolder(R.drawable.defaulticon,appName, bool, Time, true,PackageName));
        }




        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////// List of adapters //////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////


        mRecycle = findViewById(R.id.myrecycle);
        mRecycle.setHasFixedSize(true);
        mLayout = new LinearLayoutManager(this);
        mAdapter = new templateAdapter(Holder);

        mRecycle.setLayoutManager(mLayout);
        mRecycle.setAdapter(mAdapter);


        mAppTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        mOkay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OpenApp = true;


                if (Integer.valueOf(mAppTime.getText().toString()) > 120)
                {
                    mAppTime.setText("120");
                }
                if (Integer.valueOf(mAppTime.getText().toString()) < 5)
                {
                    mAppTime.setText("5");
                }
                Holder.get(temp).SetValue(Integer.valueOf(mAppTime.getText().toString()));
                Holder.get(temp).SetBool(mSwitch.isEnabled());


                mWHite.setVisibility(View.INVISIBLE);
                mAppTime.setVisibility(View.INVISIBLE);
                mTextView.setVisibility(View.INVISIBLE);
                mOkay.setVisibility(View.INVISIBLE);
                mCancel.setVisibility(View.INVISIBLE);
                mSwitch.setVisibility(View.INVISIBLE);
                mAppName.setVisibility(View.INVISIBLE);
                mAppWindow.setVisibility(View.INVISIBLE);
                mAdd.setVisibility(View.INVISIBLE);

                mSwitch.setEnabled(false);
                mAppTime.setEnabled(false);

                mAdapter.notifyItemChanged(temp);
                hideKeyboard(v);
            }

        });

        mCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OpenApp = true;

                mWHite.setVisibility(View.INVISIBLE);
                mAppTime.setVisibility(View.INVISIBLE);
                mTextView.setVisibility(View.INVISIBLE);
                mOkay.setVisibility(View.INVISIBLE);
                mCancel.setVisibility(View.INVISIBLE);
                mSwitch.setVisibility(View.INVISIBLE);
                mAppName.setVisibility(View.INVISIBLE);
                mAppWindow.setVisibility(View.INVISIBLE);
                mAdd.setVisibility(View.INVISIBLE);

                mSwitch.setEnabled(false);
                mAppTime.setEnabled(false);



                mAdapter.notifyItemChanged(temp);
                hideKeyboard(v);



            }

        });




        mAdapter.setOnItemClickListener(new templateAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position)
            {

                if (OpenApp == true)
                {
                    OpenApp = false;
                    temp = position;

                    mWHite.setVisibility(View.VISIBLE);
                    mAppTime.setVisibility(View.VISIBLE);
                    mTextView.setVisibility(View.VISIBLE);
                    mOkay.setVisibility(View.VISIBLE);
                    mCancel.setVisibility(View.VISIBLE);
                    mSwitch.setVisibility(View.VISIBLE);
                    mAppName.setVisibility(View.VISIBLE);
                    mAppWindow.setVisibility(View.VISIBLE);
                    mAdd.setVisibility(View.VISIBLE);

                    mSwitch.setEnabled(true);
                    mAppTime.setEnabled(true);


                    mAppName.setText(Holder.get(temp).getAppName());
                    mAppTime.setText(String.valueOf(Holder.get(temp).getNumberPicker()));


                    if (Holder.get(temp).getSwitch() == true) {
                        mSwitch.setChecked(true);
                    } else {
                        mSwitch.setChecked(false);
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
        Listsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String UpdateSave ="";



                for (int i = 0; i < Holder.size(); ++i)
                {


                    UpdateSave += Holder.get(i).getAppName() + (System.getProperty("line.separator"));



                    UpdateSave += Holder.get(i).getNumberPicker() + (System.getProperty("line.separator"));

                    if (Holder.get(i).getSwitch() == true)
                    {
                        UpdateSave += '1' + (System.getProperty("line.separator"));
                    }
                    else
                    {
                        UpdateSave += '0' + (System.getProperty("line.separator"));
                    }

                    UpdateSave += Holder.get(i).GetPackageName() + (System.getProperty("line.separator"));



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
            Toast.makeText(CloseList.this,"Saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(CloseList.this,"Error saving file!", Toast.LENGTH_SHORT).show();
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
            Holder.get(position).SetValue(value);
            mAdapter.notifyItemChanged(position);
            templateAdapter.again +=1;
        }

    }


    public void changeBools(int position, boolean value)
    {
        if (templateAdapter.againBool == 0)
        {
            Holder.get(position).SetBool(value);
            mAdapter.notifyItemChanged(position);
            templateAdapter.againBool +=1;
        }

    }


    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

}