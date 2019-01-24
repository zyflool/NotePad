package com.lool.notepad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static int NoteNum = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        ListView list = findViewById(R.id.list);
        Button build = findViewById(R.id.build);

        boolean Have = false;
        for ( int i = 1 ; i <= NoteNum ; i++ ) {
            String name = "/data/data/com.lool.notepad/files/"+i;
            //判断文件是否存在
            File f = new File(name);
            if (f.exists()) {
                Have = true;
                break;
            }
        }

        final String[] data;

        if ( NoteNum != 0 && Have)
            data = MakeListData(NoteNum);
        else
            data = new String[]{"无"};

        final boolean have = Have;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, data);
        list.setAdapter(adapter);

        //点击列表
        list.setOnItemClickListener ( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if( NoteNum != 0 && have) {
                    Intent intent = new Intent(MainActivity.this,ShowActivity.class);
                    intent.putExtra("toshow", ToInt(data[position]));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, EditActivity.class);
                    startActivity(intent);
                }
            }
        });

        //新建操作
        build.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                startActivity(intent);
            }
        });

    }

    public String[]  MakeListData (int NoteNum) {
        ArrayList<String> data = new ArrayList<>();
        for (int i = 1; i <= NoteNum; i++) {
            String name = "/data/data/com.lool.notepad/files/"+i;

            //判断文件是否存在
            File f = new File(name);
            if (!f.exists())    //不存在
                continue;
            data.add(""+i);
        }
        String[] res = new String[data.size()];
        int j = 0;
        while ( j != data.size() ) {
            res[j]=data.get(j);
            j++;
        }
        return res;
    }

    int ToInt (String c) {
        int l = c.length();
        int res = 0;
        for (int i = l-1, time = 1 ; i >= 0 ; i--,time *= 10 ) {
            int n = (int)(c.charAt(i)-'0');
            res += n*time;
        }
        return res;
    }
}