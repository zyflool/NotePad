package com.lool.notepad;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static com.lool.notepad.MainActivity.NoteNum;

public class EditActivity extends AppCompatActivity {

    private EditText Edit;
    private int now = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        initView();

    }

    private void initView() {
        Edit = findViewById(R.id.Edit);
        Button save = findViewById(R.id.save);
        Button Cancel = findViewById(R.id.Cancel);
        final RelativeLayout editlayout = findViewById(R.id.editlayout);

        Intent intent = getIntent();
        now = intent.getIntExtra("toedit",0);
        if ( now != 0 )
            Show(now);

        editlayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                editlayout.setFocusable(true);
                editlayout.setFocusableInTouchMode(true);
                return false;
            }

        });



        //完成保存
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String InputText = Edit.getText().toString();
                //保存数据
                save(InputText);
                Intent intent = new Intent(EditActivity.this, MainActivity.class);

                startActivity(intent);
            }
        });

        //取消操作
        Cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /*
        //重载onDestory函数
        protected void onDestory() {
            super.onDestroy();
            String inputText = EditText.getText().toString();
            save(inputText);
        }
    */
    //显示文本
    public void Show (int n) {
        FileInputStream in;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();

        String name = ""+n;

        try {
            /* 判断是否存在文件
            File f = new File(name);
            if(!f.exists())
                Log.e("zzzzzzzzzs",""+NoteNum);
             */
            in = openFileInput(name);
            reader = new BufferedReader(new InputStreamReader(in));

            String c;
            while( (c = reader.readLine()) != null)
                content.append(c);
        } catch (IOException e ) {
            e.printStackTrace();
        } finally {
            if (reader != null ) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Edit.setText(content.toString());
    }



    //存储数据
    public void save(String inputText) {
        if ( now == 0 )
            NoteNum++;
        String filename = "/data/data/com.lool.notepad/files/"+NoteNum;
        String name = ""+NoteNum;
        FileOutputStream out;
        BufferedWriter writer = null;
        try {
            out = openFileOutput(name, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
            /* 判断是否保存成功
            File f = new File(filename);
            if(!f.exists())
                Log.e("zzzzzzzzzs",""+NoteNum);
            */
        } catch (IOException e ) {
            e.printStackTrace();
        } finally {
            try {
                if( writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}