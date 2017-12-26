package com.example.user.myapplication;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class goods extends AppCompatActivity {

    EditText editgoods,editprice,editdescribe;
    Button add1,edit1,delete1,query1,Button4;
    ListView listView1;
    SQLiteDatabase dbrw1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        editgoods = (EditText) findViewById(R.id.editgoods);
        editprice = (EditText) findViewById(R.id.editprice);
        editdescribe = (EditText)findViewById(R.id.editdescribe);
        add1 = (Button)findViewById(R.id.add1);
        edit1 = (Button)findViewById(R.id.edit1);
        delete1 = (Button) findViewById(R.id.delete1);
        query1 = (Button) findViewById(R.id.query1);
        Button4 = (Button)findViewById(R.id.Button4);
        listView1 = (ListView)findViewById(R.id.listView1);


        goodsDB dbHelper = new goodsDB(this);
        dbrw1 = dbHelper.getWritableDatabase();



        if(Button4!=null){
            Button4.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(goods.this,MainA.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        add1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                newGoods();
            }
        });

        edit1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                renewGoods();
            }
        });

        delete1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                deleteGoods();
            }
        });

        query1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                queryGoods();
            }
        });
    }

    public void newGoods(){
        if(editgoods.getText().toString().equals("")
                || editprice.getText().toString().equals("")
                || editdescribe.getText().toString().equals(""))
            Toast.makeText(this,"輸入資料不完全",Toast.LENGTH_SHORT).show();
        else{


            ContentValues bv = new ContentValues();
            bv.put("goods",editgoods.getText().toString());
            bv.put("price",editprice.getText().toString());
            bv.put("describe",editdescribe.getText().toString());

            dbrw1.insert("myTable1",null,bv);

            Toast.makeText(this,"新增商品:"+editgoods.getText().toString()
                    +"金額:"+editprice.getText().toString()
                    +"描述:"+editdescribe.getText().toString(),Toast.LENGTH_SHORT).show();

            editgoods.setText("");
            editprice.setText("");
            editdescribe.setText("");
        }
    }

    public void renewGoods(){
        if(editgoods.getText().toString().equals("")
                ||editprice.getText().toString().equals("")
                ||editdescribe.getText().toString().equals(""))
            Toast.makeText(this,"沒有輸入更新值",Toast.LENGTH_SHORT).show();
        else {

            String newprice = (editprice.getText().toString());
            String newdescribe = (editgoods.getText().toString());

            ContentValues bv = new ContentValues();
            bv.put("price",newprice);
            bv.put("describe",newdescribe);

            dbrw1.update("myTable1",bv,"goods="+"'"+editgoods.getText().toString()+"'",null);

            Toast.makeText(this,"成功",Toast.LENGTH_SHORT).show();

            editgoods.setText("");
            editprice.setText("");
            editdescribe.setText("");
        }
    }

    public  void deleteGoods(){

        if(editgoods.getText().toString().equals(""))
            Toast.makeText(this,"請輸入要刪除之值",Toast.LENGTH_SHORT).show();
        else {

            dbrw1.delete("myTable1","goods="+"'"+editgoods.getText().toString()+"'",null);

            Toast.makeText(this,"刪除成功",Toast.LENGTH_SHORT).show();

            editgoods.setText("");
        }
    }

    public void queryGoods(){
        String goods ,price ,describe;
        String[] colum1 = {"goods","price","describe"};


        Cursor b;

        if(editgoods.getText().toString().equals(""))
            b = dbrw1.query("myTable1",colum1,null,null,null,null,null);
        else
            b = dbrw1.query("myTable1",colum1,"goods="+"'"+
                    editgoods.getText().toString()+"'",null,null,null,null);

        goods=new String();
        price=new String();
        describe=new String();

        String[] d=new String[b.getCount()];

        if(b.getCount()>0){

            b.moveToFirst();

            for(int i=0;i<b.getCount();i++){
                d[i]="商品名稱:"+b.getString(0)+"\n"+"金額:"+b.getString(1)+"\n"+"描述:"+b.getString(2)+"\n";
                goods += b.getString(0)+"\n";
                price += b.getString(1)+"\n";
                describe += b.getString(2)+"\n";
                b.moveToNext();
            }

            Toast.makeText(this,"共有"+b.getCount()+"筆記錄",Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> messageAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,d);
        listView1.setAdapter(messageAdapter);


    }


}

