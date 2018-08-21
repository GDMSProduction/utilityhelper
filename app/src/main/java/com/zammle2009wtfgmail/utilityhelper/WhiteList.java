package com.zammle2009wtfgmail.utilityhelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    private String Database;

    static String[] TextWithInfo;
    // DatabaseReference ref;


    //final FirebaseDatabase database = FirebaseDatabase.getInstance();


    private FirebaseAuth firebaseauth;

    private RecyclerView mRecycle;
    private templateAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;
    private Button mOkay;
    private Button mCancel;
    private ImageView mAppWindow;
    private TextView mAppName;
    private TextView mBlack;
    private TextView mBlack2;
    private TextView mTextView;
    private EditText mAppTime;
    private ImageView mWHite;
    private Switch mSwitch;

    private boolean OpenAPP = true;
    static int Position;

    private TextView debug;

////////////////////////////////////////////////////////////////////////////////
/////////////////// ON CREATE //////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_white_list);


        Database = "";

        firebaseauth = FirebaseAuth.getInstance();


        save = (Button) findViewById(R.id.Save2);
        load = (Button) findViewById(R.id.cloud);
        //  Listload = (Button) findViewById(R.id.Load);


        mOkay = (Button) findViewById(R.id.appokaybutton2);
        mCancel = (Button) findViewById(R.id.appcancelbutton2);
        mAppName = (TextView) findViewById(R.id.appAppname2);
        mBlack = (TextView) findViewById(R.id.addBlack2);
        mAppWindow = (ImageView) findViewById(R.id.appWindow2);
        mBlack2 = (TextView) findViewById(R.id.addBlack3);
        mAppTime = (EditText) findViewById(R.id.apptimer2);
        mTextView = (TextView) findViewById(R.id.apptextview2);
        mWHite = (ImageView) findViewById(R.id.appwhite2);
        mSwitch = (Switch) findViewById(R.id.appswitch2);

        mAppWindow.setVisibility(View.INVISIBLE);
        mBlack.setVisibility(View.INVISIBLE);
        mAppName.setVisibility(View.INVISIBLE);
        mCancel.setVisibility(View.INVISIBLE);
        mOkay.setVisibility(View.INVISIBLE);
        mBlack.setVisibility(View.INVISIBLE);
        mBlack2.setVisibility(View.INVISIBLE);
        mAppTime.setVisibility(View.INVISIBLE);
        mTextView.setVisibility(View.INVISIBLE);
        mWHite.setVisibility(View.INVISIBLE);
        mSwitch.setVisibility(View.INVISIBLE);


        mSwitch.setEnabled(false);
        mAppTime.setEnabled(false);


        debug = (TextView) findViewById(R.id.debugger);








        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();








        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////// Loading on create. Compares Whitelist with List of apps  ///////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ArrayList<String> list = new ArrayList<>();


        try {
            if (CloseList.CreateOnce == 0) {
                String[] newText = WhiteList.text.split(System.getProperty("line.separator"));
                if (newText.length > 2) {
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


        TextWithInfo = MainActivity.ToReturn.split(System.getProperty("line.separator"));

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
                OpenAPP = true;


                if (mAppTime.getText().toString().equals(""))
                {
                    mAppTime.setText("15");
                }

                if (Integer.valueOf(mAppTime.getText().toString()) > 240)
                {
                    mAppTime.setText("240");
                }
                if (Integer.valueOf(mAppTime.getText().toString()) < 1)
                {
                    mAppTime.setText("1");
                }

                CloseList.Holder.get(Position).SetValue(Integer.valueOf(mAppTime.getText().toString()));

                CloseList.Holder.get(Position).SetBool(mSwitch.isChecked());




                if (mSwitch.isChecked() == true)
                {
                    CloseList.Holder.get(Position).SetVis(true);
                    CloseList.Holder.get(Position).SetBool(true);

                    boolean copy = false;


                    for (int i = 0; i < MainActivity.ListRunnables.size(); ++i)
                    {
                        if (MainActivity.ListRunnables.get(i).GetPackageName().equals(CloseList.Holder.get(Position).GetPackageName()))
                        {
                            MainActivity.ListRunnables.get(i).SetBool(1);
                            MainActivity.ListRunnables.get(i).SetTime(Integer.valueOf(mAppTime.getText().toString()));
                            MainActivity.ListHandlers.get(i).postDelayed(MainActivity.ListRunnables.get(i), MainActivity.ListRunnables.get(i).GetTimer() *60*1000);


                            copy = true;
                            Toast.makeText(WhiteList.this,"ADDED: " + MainActivity.ListRunnables.get(i).GetPackageName(), Toast.LENGTH_SHORT).show();

                            break;
                        }
                    }

                    if (copy == false)
                    {
                        Toast.makeText(WhiteList.this,"FAILED", Toast.LENGTH_SHORT).show();
                    }





                }
                else
                {
                    CloseList.Holder.get(Position).SetVis(false);
                    CloseList.Holder.get(Position).SetBool(false);





                    for (int i = 0; i < MainActivity.ListRunnables.size(); ++i)
                    {
                        if (MainActivity.ListRunnables.get(i).GetPackageName().equals(CloseList.Holder.get(Position).GetPackageName()))
                        {
                            MainActivity.ListRunnables.get(i).SetBool(0);
                            MainActivity.ListRunnables.get(i).SetTime(Integer.valueOf(mAppTime.getText().toString()));
                            MainActivity.ListHandlers.get(i).removeCallbacks(MainActivity.ListRunnables.get(i));




                            break;
                        }
                    }
                }



                mWHite.setVisibility(View.INVISIBLE);
                mAppTime.setVisibility(View.INVISIBLE);
                mTextView.setVisibility(View.INVISIBLE);
                mOkay.setVisibility(View.INVISIBLE);
                mCancel.setVisibility(View.INVISIBLE);
                mSwitch.setVisibility(View.INVISIBLE);
                mAppName.setVisibility(View.INVISIBLE);
                mAppWindow.setVisibility(View.INVISIBLE);
                try {
                    mBlack.setVisibility(View.INVISIBLE);
                }
                catch (Exception e)
                {}
                try {
                    mBlack2.setVisibility(View.INVISIBLE);
                }
                catch (Exception e)
                {}

                mSwitch.setEnabled(false);
                mAppTime.setEnabled(false);

                mAdapter.notifyItemChanged(Position);



                // save //

                String UpdateSave ="";
                String[] TextWithInfo = MainActivity.ToReturn.split(System.getProperty("line.separator"));


                for (int i = 0; i < TextWithInfo.length; ++i)
                {
                    String tempstring = TextWithInfo[i].replace(System.getProperty("line.separator"), "");
                    String tempstring2 = CloseList.Holder.get(Position).getAppName().replace(System.getProperty("line.separator"), "");

                    if(tempstring2.equals(tempstring))
                    {

                        //Toast.makeText(WhiteList.this,"IM IN", Toast.LENGTH_SHORT).show();
                        //  TextWithInfo[i+1] = String.valueOf(CloseList.Holder.get(i).getNumberPicker());

                        if (mSwitch.isChecked())
                        {
                            TextWithInfo[i+2] = String.valueOf(1);
                            TextWithInfo[i+1] = mAppTime.getText().toString();
                            // Toast.makeText(WhiteList.this,"IM IN. ON", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            TextWithInfo[i+2] = String.valueOf(0);
                            TextWithInfo[i+1] = mAppTime.getText().toString();
                            // Toast.makeText(WhiteList.this,"IM IN. OFF", Toast.LENGTH_SHORT).show();
                        }



                    }
                    else{
                        //Toast.makeText(WhiteList.this,"IM NOT IN", Toast.LENGTH_SHORT).show();
                    }



                }





                for (int i = 0; i < TextWithInfo.length; ++i)
                {
                    UpdateSave += TextWithInfo[i] + (System.getProperty("line.separator"));

                }

                MainActivity.ToReturn = UpdateSave;



                saveFile(WhiteList.filename2, MainActivity.ToReturn);

                // end of saving //

                hideKeyboard(v);
            }



        });


        FirebaseUser User = firebaseauth.getCurrentUser();

        ref.child(User.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Database = dataSnapshot.getValue(String.class);
                // Toast.makeText(WhiteList.this,"READING FROM DATABASE", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                // Toast.makeText(WhiteList.this,"READING FROM DATABASE part 2", Toast.LENGTH_SHORT).show();
            }
        });



        mCancel.setOnClickListener(new View.OnClickListener()
        {


            @Override
            public void onClick(View v)
            {
                OpenAPP = true;

                mWHite.setVisibility(View.INVISIBLE);
                mAppTime.setVisibility(View.INVISIBLE);
                mTextView.setVisibility(View.INVISIBLE);
                mOkay.setVisibility(View.INVISIBLE);
                mCancel.setVisibility(View.INVISIBLE);
                mSwitch.setVisibility(View.INVISIBLE);
                mAppName.setVisibility(View.INVISIBLE);
                mAppWindow.setVisibility(View.INVISIBLE);
                try {
                    mBlack.setVisibility(View.INVISIBLE);
                }
                catch (Exception e)
                {}
                try {
                    mBlack2.setVisibility(View.INVISIBLE);
                }
                catch (Exception e)
                {}

                if (CloseList.Holder.get(Position).getSwitch())
                {
                    CloseList.Holder.get(Position).SetVis(true);
                    CloseList.Holder.get(Position).SetBool(true);
                }
                else
                {
                    CloseList.Holder.get(Position).SetVis(false);
                    CloseList.Holder.get(Position).SetBool(false);
                }


                mSwitch.setEnabled(false);
                mAppTime.setEnabled(false);




                //  mAdapter.notifyItemChanged(temp);
                hideKeyboard(v);



            }

        });




        mAdapter.setOnItemClickListener(new templateAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position)
            {





                if (OpenAPP == true)
                {
                    OpenAPP = false;
                    Position = position;

                    try {

                        if (CloseList.Holder.get(Position).getSwitch() == true) {


                            mAppName.setVisibility(View.VISIBLE);
                            mBlack.setVisibility(View.VISIBLE);
                            mAppWindow.setVisibility(View.VISIBLE);
                            mOkay.setVisibility(View.VISIBLE);
                            mCancel.setVisibility(View.VISIBLE);
                            mTextView.setVisibility(View.VISIBLE);
                            mSwitch.setVisibility(View.VISIBLE);
                            mWHite.setVisibility(View.VISIBLE);
                            mAppTime.setVisibility(View.VISIBLE);
                            mSwitch.setChecked(true);
                            mSwitch.setEnabled(true);
                            mAppTime.setEnabled(true);


                            mAppName.setText(CloseList.Holder.get(Position).getAppName());
                            mAppTime.setText(String.valueOf(CloseList.Holder.get(Position).getNumberPicker()));
                        } else {

                            mAppName.setVisibility(View.VISIBLE);
                            mBlack2.setVisibility(View.VISIBLE);
                            mAppWindow.setVisibility(View.VISIBLE);
                            mOkay.setVisibility(View.VISIBLE);
                            mCancel.setVisibility(View.VISIBLE);
                            mTextView.setVisibility(View.VISIBLE);
                            mSwitch.setVisibility(View.VISIBLE);
                            mWHite.setVisibility(View.VISIBLE);
                            mAppTime.setVisibility(View.VISIBLE);
                            mSwitch.setEnabled(true);
                            mSwitch.setChecked(false);
                            mAppTime.setEnabled(true);


                            mAppName.setText(CloseList.Holder.get(Position).getAppName());
                            mAppTime.setText(String.valueOf(CloseList.Holder.get(Position).getNumberPicker()));
                        }

                    }
                    catch (Exception e)
                    {

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



                if (!LoginScreen.emailID.equals(""))
                {
                   /* try
                    {*/
                    if (firebaseauth.getCurrentUser() == null)
                    {
                        finish();
                        Intent history = new Intent (WhiteList.this, LoginScreen.class );
                        startActivity(history);



                    }
                    else
                    {




                        FirebaseUser User = firebaseauth.getCurrentUser();

                        ref.child(User.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Database = dataSnapshot.getValue(String.class);
                                // Toast.makeText(WhiteList.this,"READING FROM DATABASE", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError)
                            {
                                // Toast.makeText(WhiteList.this,"READING FROM DATABASE part 2", Toast.LENGTH_SHORT).show();
                            }
                        });







                        // Toast.makeText(WhiteList.this,"Failed Conversion", Toast.LENGTH_SHORT).show();






                        CloseList.Holder.clear();


                        TextWithInfo = MainActivity.ToReturn.split(System.getProperty("line.separator"));
                        String[] DatabaseWithInfo = Database.split(System.getProperty("line.separator"));
                        String NotFoundString = "";

                        int DatabaseSize = DatabaseWithInfo.length;

                        for (int i = 0; i < DatabaseSize; i = i + 4)
                        {
                            try
                            {
                                if (Integer.valueOf(DatabaseWithInfo[i + 2]) == 1) {
                                    boolean found = false;

                                    for (int x = 0; x < TextWithInfo.length; x = x + 4) {
                                        if (DatabaseWithInfo[i].equals(TextWithInfo[x])) {
                                            found = true;
                                            TextWithInfo[x + 2] = "1";

                                            //   Toast.makeText(WhiteList.this,"I AM TURNing on bool", Toast.LENGTH_SHORT).show();
                                            break;
                                        }
                                    }


                                    if (found == false) {

                                        NotFoundString += DatabaseWithInfo[i] + (System.getProperty("line.separator"));
                                        NotFoundString += DatabaseWithInfo[i + 1] + (System.getProperty("line.separator"));
                                        NotFoundString += DatabaseWithInfo[i + 2] + (System.getProperty("line.separator"));
                                        NotFoundString += DatabaseWithInfo[i + 3] + (System.getProperty("line.separator"));

                                        //TextWithInfo = MainActivity.ToReturn.split(System.getProperty("line.separator"));
                                    }

                                }
                            }
                            catch (Exception e)
                            {

                            }


                        }



                        // new code /  combing not found strings with Current string //////////////////////////////////////////////////////

                        String UpdateSave ="";


                        for (int i = 0; i < TextWithInfo.length; ++i)
                        {
                            UpdateSave += TextWithInfo +  (System.getProperty("line.separator"));
                        }

                        //    UpdateSave += NotFoundString;

                        //  TextWithInfo = UpdateSave.split(System.getProperty("line.separator"));



                        // end //////////////////////////////////////////////////////





                        for (int i = 0; i < TextWithInfo.length; i = i + 4)
                        {
                            try {
                                if (Integer.valueOf(TextWithInfo[i + 2]) == 1) {
                                    String appName = TextWithInfo[i];
                                    int Time = Integer.valueOf(TextWithInfo[i + 1]);
                                    boolean bool = true;
                                    String PackageName = TextWithInfo[i + 3];


                                    try {

                                        Drawable icon = getPackageManager().getApplicationIcon(TextWithInfo[i + 3]);


                                        if (Integer.valueOf(TextWithInfo[i + 2]) == 1) {
                                            CloseList.Holder.add(new templateHolder(icon, appName, bool, Time, true, PackageName));
                                        } else {
                                            CloseList.Holder.add(new templateHolder(icon, appName, bool, Time, false, PackageName));
                                        }

                                    } catch (PackageManager.NameNotFoundException e) {
                                        e.printStackTrace();


                                    }
                                }
                            }
                            catch (Exception e)
                            {

                            }
                        }






                        // end of new stuff //



                        ///////////// update adapters here //////////////////
                        mAdapter.notifyDataSetChanged();
                        ////////////////////////////////////////////////////


                        //// save info / Setting ToReturn to updated information////


                        MainActivity.ToReturn = "";

                        for (int i = 0; i < TextWithInfo.length; ++i)
                        {
                            MainActivity.ToReturn += TextWithInfo[i] +  (System.getProperty("line.separator"));


                        }

                        // save //




                        // String[] TextWithInfo = MainActivity.ToReturn.split(System.getProperty("line.separator"));


                        for (int i = 0; i < CloseList.Holder.size(); ++i)
                        {
                            for (int x = 0; x < TextWithInfo.length; x = x + 4)

                            {

                                String tempstring = TextWithInfo[x].replace(System.getProperty("line.separator"), "");
                                String tempstring2 = CloseList.Holder.get(i).getAppName().replace(System.getProperty("line.separator"), "");

                                if (tempstring2.equals(tempstring))

                                {
                                    TextWithInfo[x+1] = String.valueOf(CloseList.Holder.get(i).getNumberPicker());

                                    if (mSwitch.isChecked())
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

                        // sets runnables //


                        for (int i = 0; i < TextWithInfo.length; i = i + 4)
                        {

                            if (Integer.valueOf(TextWithInfo[i + 2]) == 1)
                            {
                                MainActivity.ListHandlers.get(i).postDelayed(MainActivity.ListRunnables.get(i), MainActivity.ListRunnables.get(i).GetTimer() * 60 * 1000);
                            }

                        }

                        //



                        saveFile(WhiteList.filename2, MainActivity.ToReturn);

                        // end of saving //

                        hideKeyboard(v);




                        debug.setText(Database = ref.child(User.getUid()).toString());




                    }



                   /* } catch
                            (Exception e)
                    {

                          Toast.makeText(WhiteList.this,"Failed Catch", Toast.LENGTH_SHORT).show();
                    }*/

                }
                else
                {
                    // Toast.makeText(WhiteList.this,"Failed If", Toast.LENGTH_SHORT).show();
                }






                //  Toast.makeText(WhiteList.this,"END", Toast.LENGTH_SHORT).show();




            }

        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (!LoginScreen.emailID.equals(""))
                {
                    try
                    {
                        if (firebaseauth.getCurrentUser() == null)
                        {
                            finish();
                            Intent history = new Intent (WhiteList.this, LoginScreen.class );
                            startActivity(history);



                        }
                        else
                        {
                            FirebaseUser User = firebaseauth.getCurrentUser();
                            ref.child(User.getUid()).setValue(MainActivity.ToReturn);

                            Toast.makeText(WhiteList.this,"Saved to the cloud", Toast.LENGTH_SHORT).show();
                        }



                    } catch
                            (Exception e)
                    {

                        //  Toast.makeText(WhiteList.this,"Failed Catch", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    //  Toast.makeText(WhiteList.this,"Failed If", Toast.LENGTH_SHORT).show();
                }





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

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


}