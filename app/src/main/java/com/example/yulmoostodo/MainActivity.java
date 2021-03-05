package com.example.yulmoostodo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.yulmoostodo.date_process.getToday;
import static com.example.yulmoostodo.date_process.getToday_text;
import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    private ArrayList<MainData> arrayList;
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private String tv_con;
    private EditText edt_con;
    private CheckBox chb_important;
    private View typing_view;

    private TextView tv_day;

    private String check;

    //DB구축 부분

    private myDBHelper myHelper;

    //Progress bar 표시하는 부분
    public static Integer total=0;
    public static Integer complete=0;
    public static ProgressBar prog_day;
    public static TextView tv_percent1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //오늘 날짜 보여주기
        tv_day = (TextView)findViewById(R.id.tv_day);
        String today = getToday_text();
        System.out.println(today);
        tv_day.setText(today.toString());

        //리사이클러뷰를 이용하여 리스트 생성하기
        recyclerView = (RecyclerView)findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        prog_day = (ProgressBar)findViewById(R.id.prog_day);
        tv_percent1 = (TextView)findViewById(R.id.tv_percent1);

        setDataSet();
        progress_day();
        recyclerView.setAdapter(mainAdapter);

        //+버튼 눌렀을 때 팝업 띄우고 정보 저장하기
        ImageButton btn_add = (ImageButton) findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //팝업 띄우기
                typing_view = (View) View.inflate(MainActivity.this, R.layout.typing_contents,null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("TODO LIST");
                dlg.setIcon(R.drawable.check_icon);
                dlg.setView(typing_view);
                dlg.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                edt_con =(EditText)typing_view.findViewById(R.id.edt_contents);
                                chb_important = (CheckBox)typing_view.findViewById(R.id.chb_important);

                                tv_con = edt_con.getText().toString();
                                writeList();
                                addList();
                                mainAdapter.notifyDataSetChanged();
                                progress_day();
                            }
                        });
                dlg.setNegativeButton("취소",null);
                dlg.show();

            }
        });
    }
    public void writeList(){
        myHelper = new myDBHelper(this);
        myHelper.open();
        myHelper.create();

        String Today;
        Today = getToday();

        if(chb_important.isChecked() == true){myHelper.insertRecord(Today, tv_con, 0,1);}
        else if(chb_important.isChecked() == false) {myHelper.insertRecord(Today, tv_con, 0,0);}
        myHelper.close();

        System.out.println("save");

       // mainAdapter.notifyDataSetChanged();

    }
    protected void onDestroy() {
        myHelper.close();
        super.onDestroy();
    }

    public void setDataSet(){
        arrayList = new ArrayList<>();
        mainAdapter = new MainAdapter(this,arrayList);
        myHelper = new myDBHelper(this);
        myHelper.open();
        myHelper.create();

        total = 0;
        complete = 0;

        Cursor cursor = myHelper.origin();

        while (cursor.moveToNext()) {

            String id = cursor.getString(cursor.getColumnIndex("_id"));
            tv_con = cursor.getString(cursor.getColumnIndex("contents"));
            check = cursor.getString(cursor.getColumnIndex("chk"));
            String imp = cursor.getString(cursor.getColumnIndex("chkImp"));
            total++;

            int intId;
            boolean boolImp=false;
            intId = Integer.parseInt(id);
            MainData mainData = null;

            if(imp.equals("1")){boolImp=true;}
            else if(imp.equals("0")){boolImp=false;}

            System.out.println(id + tv_con + check + boolImp);

            if (check.equals("1")) {
                mainData = new MainData(true, tv_con,intId,boolImp);
                arrayList.add(mainData);
                System.out.println("true 일 때 ");
                complete++;

            } else if (check.equals("0")){
                mainData = new MainData(false, tv_con,intId,boolImp);
                arrayList.add(mainData);
                System.out.println("false 일 때 ");
            }
            mainAdapter.notifyDataSetChanged();
        }
        myHelper.close();
    }
    public void addList(){

        myHelper = new myDBHelper(this);
        myHelper.open();
        myHelper.create();

        String id = null;

        Cursor cursor = myHelper.origin();
        if( cursor != null && cursor.moveToFirst() ){
            id = cursor.getString(cursor.getColumnIndex("_id"));
        }

        int intId;
        Boolean boolImp=false;
        intId = Integer.parseInt(id);

        if(chb_important.isChecked()==true){boolImp=true;}
        else if(chb_important.isChecked()==false){boolImp=false;}

        MainData mainData = new MainData(false,tv_con,intId,boolImp);
        arrayList.add(mainData);
        mainAdapter.notifyDataSetChanged();

        myHelper.close();
        total++;
    }
    //진행률 표시
    public static void progress_day(){
        try {
            int value = complete * 100 / total;
            prog_day.setProgress(value);
            tv_percent1.setText(value + "%");

        } catch (Exception e) {
            int value = 0;
            prog_day.setProgress(value);
            tv_percent1.setText(value + "%");
        }
    }
    public static void chk_progress(int type){
        if(type == 0){complete--;}
        else if(type == 1) {complete++;}
        else if(type == 2) {total--; complete--;}
        else if(type == 3) {total--;}
        progress_day();
    }
}