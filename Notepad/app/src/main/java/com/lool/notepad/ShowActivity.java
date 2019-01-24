package com.lool.notepad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.lool.notepad.MainActivity.NoteNum;


public class ShowActivity extends AppCompatActivity{

    private TextView show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        initView();
    }

    private void initView() {

        show = findViewById(R.id.show);
        Button Edit = findViewById(R.id.Edit);
        Button Delete = findViewById(R.id.Delete);
        Button back = findViewById(R.id.back);

        //从引导页跳转过来传输数据
        Intent intent = getIntent();
        final int now = intent.getIntExtra("toshow",0);
        Show(now);

        //编辑跳转
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowActivity.this,EditActivity.class);
                intent.putExtra("toedit" ,now);
                startActivity(intent);
            }
        });

        //删除操作
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowActivity.this,MainActivity.class);
                deleteFile("/data/data/com.lool.notepad/files/"+now);

                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }

    //显示文件内容
    protected void Show(int x) {
        FileInputStream in;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();

        String name = ""+x;

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
        show.setText(content.toString());
    }

    //删除文件
    public boolean deleteFile(String Name) {
        File file = new File(Name);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                Log.e("--Method--", "Copy_Delete.deleteSingleFile: 删除单个文件" + Name + "成功！");
                return true;
            } else {
                Toast.makeText(getApplicationContext(), "删除单个文件" + Name + "失败！", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(getApplicationContext(), "删除单个文件失败：" + Name + "不存在！", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
