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



public class MainA extends AppCompatActivity {

    EditText editname,editphone,editplace;
    Button add,edit,delete,query,Button3;
    ListView listView;
    SQLiteDatabase dbrw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maina);
        editname = (EditText) findViewById(R.id.editname);
        editplace = (EditText) findViewById(R.id.editplace);
        editphone = (EditText)findViewById(R.id.editphone);
        add = (Button)findViewById(R.id.add);
        edit = (Button)findViewById(R.id.edit);
        delete = (Button) findViewById(R.id.delete);
        query = (Button) findViewById(R.id.query);
        Button3 = (Button)findViewById(R.id.Button3);
        listView = (ListView)findViewById(R.id.listView);


        MyDBHelper dbHelper = new MyDBHelper(this);
        dbrw = dbHelper.getWritableDatabase();



        if(Button3!=null){
            Button3.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(MainA.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                newStore();
            }
        });

        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                renewStore();
            }
        });

        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                deleteStore();
            }
        });

        query.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                queryStore();
            }
        });
    }

    public void newStore(){
        if(editname.getText().toString().equals("")
                || editplace.getText().toString().equals("")
                || editphone.getText().toString().equals(""))
            Toast.makeText(this,"輸入資料不完全",Toast.LENGTH_SHORT).show();
        else{


            ContentValues cv = new ContentValues();
            cv.put("name",editname.getText().toString());
            cv.put("place",editplace.getText().toString());
            cv.put("phone",editphone.getText().toString());

            dbrw.insert("myTable",null,cv);

            Toast.makeText(this,"新增店名:"+editname.getText().toString()
                    +"地址:"+editplace.getText().toString()
                    +"電話:"+editphone.getText().toString(),Toast.LENGTH_SHORT).show();

            editname.setText("");
            editplace.setText("");
            editphone.setText("");
        }
    }

    public void renewStore(){
        if(editname.getText().toString().equals("")
                ||editplace.getText().toString().equals("")
                ||editphone.getText().toString().equals(""))
            Toast.makeText(this,"沒有輸入更新值",Toast.LENGTH_SHORT).show();
        else {

            String newplace = (editplace.getText().toString());
            String newphone = (editphone.getText().toString());

            ContentValues cv = new ContentValues();
            cv.put("place",newplace);
            cv.put("phone",newphone);

            dbrw.update("myTable",cv,"name="+"'"+editname.getText().toString()+"'",null);

            Toast.makeText(this,"成功",Toast.LENGTH_SHORT).show();

            editname.setText("");
            editplace.setText("");
            editphone.setText("");
        }
    }

    public  void deleteStore(){

        if(editname.getText().toString().equals(""))
            Toast.makeText(this,"請輸入要刪除之值",Toast.LENGTH_SHORT).show();
        else {

            dbrw.delete("myTable","name="+"'"+editname.getText().toString()+"'",null);

            Toast.makeText(this,"刪除成功",Toast.LENGTH_SHORT).show();

            editname.setText("");
        }
    }

    public void queryStore(){
         String name ,place ,phone;
         String[] colum = {"name","place","phone"};


        Cursor c;

        if(editname.getText().toString().equals(""))
            c = dbrw.query("myTable",colum,null,null,null,null,null);
        else
            c = dbrw.query("myTable",colum,"name="+"'"+
                    editname.getText().toString()+"'",null,null,null,null);

        name=new String();
        place=new String();
        phone=new String();

        String[] s=new String[c.getCount()];

        if(c.getCount()>0){

            c.moveToFirst();

            for(int i=0;i<c.getCount();i++){
                s[i]="店名:"+c.getString(0)+"\n"+"地址:"+c.getString(1)+"\n"+"電話:"+c.getString(2)+"\n";
                name += c.getString(0)+"\n";
                place += c.getString(1)+"\n";
                phone += c.getString(2)+"\n";
                c.moveToNext();
            }

            Toast.makeText(this,"共有"+c.getCount()+"筆記錄",Toast.LENGTH_SHORT).show();

            ArrayAdapter<String> messageAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1,s);
            listView.setAdapter(messageAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent,View view, int position,long id){
                    showListDialog();
                }
            });
        }



    }

    private void showListDialog(){
        final String[] list_item = {"在Google Map上標記位置","商品目錄管理","下單管理","歷史銷售紀錄"};

        AlertDialog.Builder dialog_list = new AlertDialog.Builder(MainA.this);
        dialog_list.setTitle("本店資訊");
        dialog_list.setItems(list_item,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                if(list_item[i]!=null){
                    if(list_item[i]=="在Google Map上標記位置"){
                        Intent intent = new Intent(MainA.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if(list_item[i]=="商品目錄管理"){
                        Intent intent = new Intent(MainA.this,goods.class);
                        startActivity(intent);
                        finish();
                    }
                    if(list_item[i]=="下單管理"){
                        Intent intent = new Intent(MainA.this,goods.class);
                        startActivity(intent);
                        finish();
                    }
                    if(list_item[i]=="歷史銷售紀錄"){
                        Intent intent = new Intent(MainA.this,goods.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
        dialog_list.show();
    }

}

