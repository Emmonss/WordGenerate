package com.example.emmons.Model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.emmons.wordgenerate.Adapter.Person;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emmons on 2018/10/16 0016.
 */

public class YinHuan_Company_List {
    private List<String> CompanyName;
    private final String path;

    private static final String Table_Name  = "ComName";
    private static final int DB_VERSION = 1;
    private static final String ComName= "name";

    private SQLiteDatabase db;

    public YinHuan_Company_List(String dic_path){
        this.CompanyName = new ArrayList<String>();
        this.path = dic_path;
        Open_DB();
    }

    private void Open_DB(){
        try {
            db = SQLiteDatabase.openOrCreateDatabase(path, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS "+Table_Name+"(" + ComName+" text primary key autoincrement" + ")");
            find();
//            InputStream ins= new FileInputStream(new File(path));
//            BufferedReader bufReader = new BufferedReader(new InputStreamReader(ins));
//            String line;
//            while((line=bufReader.readLine())!=null)
//                CompanyName.add(line);
//            ins.close();
//            bufReader.close();
        }
        catch (Exception err){
            Log.e("file",err.toString());
        }
    }

    public void insert(String res){
        ContentValues values = new ContentValues();
        values.put(ComName,res);
        db.insert(Table_Name,null,values);

        values.clear();
        find();
    }

    public void Delete(String res){
        String[] res1 = new String []{res};
        db.delete(Table_Name,"name = ?",res1);
        find();
    }

    public String getTest(){
        if (!CompanyName.isEmpty())
            return CompanyName.get(1);
        else
            return null;
    }

    public void find() {
        String sql = "SELECT * FROM " + Table_Name;
        Cursor result = this.db.rawQuery(sql, null); 	//运行查询语句
        for(result.moveToFirst();!result.isAfterLast();result.moveToNext()) {
            CompanyName.add(result.getString(0).split("\n")[0]);
        }
        //this.db.close();
    }

    public List get_List(){
        List<Person> lp = new ArrayList<Person>();
        for(int i =0;i<CompanyName.size();i++)
            lp.add(new Person(CompanyName.get(i)));

        if (!CompanyName.isEmpty())
            return lp;
        else
            return null;
    }
}
