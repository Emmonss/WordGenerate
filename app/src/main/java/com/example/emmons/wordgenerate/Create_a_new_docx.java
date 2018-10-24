package com.example.emmons.wordgenerate;

/**
 * Created by Emmons on 2018/10/18 0022.
 * 预览
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.emmons.Function.replace_data;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

/**
 * Created by Emmons on 2018/10/24 0024.
 * 文本预览效果
 */

@SuppressWarnings("unchecked")
public class Create_a_new_docx extends AppCompatActivity {
    Map<String, String> map;
    List<String> yinhuan_list;
    List<String> pohto_path;

    TableView<String[]> tableView;
    private static final String[] TABLE_HEADERS = { "预览列表", "" };

    private ProgressDialog pd;
    Button bt_create;
    ImageView iv_process_back;

    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data_table);


        init();

    }

    public void init(){
        try {
            Intent intent = getIntent();
            map = (Map<String, String>) intent.getSerializableExtra("map");
            yinhuan_list = (List<String>) intent.getSerializableExtra("yinhuan_list");
            pohto_path = (List<String>) intent.getSerializableExtra("pohto_path");
        }
        catch (Exception e){
            Log.e("err",e.toString());
        }

        List<String[]>  data = get_data();



        tableView = (TableView<String[]>) findViewById(R.id.Yinhuan_tableView);
        TableColumnWeightModel columnModel = new TableColumnWeightModel(2);
        columnModel.setColumnWeight(0, 1);
        columnModel.setColumnWeight(1, 3);
        tableView.setColumnModel(columnModel);
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, TABLE_HEADERS));
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, data));

        bt_create = (Button)findViewById(R.id.Yinhuan_bt_table_create);
        bt_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd =  ProgressDialog.show(Create_a_new_docx.this, "", "生成中，请稍后……");
                writeDoc(map,yinhuan_list,pohto_path);
            }
        });

        iv_process_back=(ImageView) findViewById(R.id.table_iv_process_back);
        iv_process_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


    public List<String[]>  get_data(){
        List<String[]> list = new ArrayList<String[]>();
        String[] item1 = {"文件名称:",map.get("文件名称")};
        list.add(item1);
        String[] item2 = {"项目部名称:",map.get("项目部名称")};
        list.add(item2);
        String[] item3 = {"限定日期:",map.get("限定日期")};
        list.add(item3);
        String[] item4 = {"签发日期:",map.get("签发日期")};
        list.add(item4);
        String[] item5 = {"整改日期:",map.get("整改日期")};
        list.add(item5);
        String[] item6 = {"复检日期:",map.get("复检日期")};
        list.add(item6);
        String[] item7 = {"隐字:",map.get("隐字")};
        list.add(item7);
        String[] item8 = {"号:",map.get("号")};
        list.add(item8);
        for(int i =0;i<yinhuan_list.size();i++)
        {
            String[] item = {"隐患"+i+":",yinhuan_list.get(i)};
            list.add(item);
        }
        String[] item9 = {"图片数目:",String.valueOf(pohto_path.size())};
        list.add(item9);


        return list;
    }


    public void writeDoc(final Map<String, String> map ,final List<String> yinhuan_list ,final List<String> pohto_path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String out2 = Environment.getExternalStorageDirectory()+"/WordGenerate/file/"+map.get("文件名称")+".docx";
                    InputStream is = getAssets().open("隐患整改通知.docx");
                    XWPFDocument doc = new XWPFDocument(is);
                    replace_data rep = new replace_data();
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("项目部名称", map.get("项目部名称"));
                    params.put("限定日期", map.get("限定日期"));
                    params.put("签发日期", map.get("签发日期"));
                    params.put("整改日期", map.get("整改日期"));
                    params.put("复检日期", map.get("复检日期"));
                    params.put("字", map.get("隐字"));
                    params.put("号", map.get("号"));
                    rep.replaceInPara(doc, params);
                    rep.replaceInTable(doc, params);
                    rep.Add_Info(doc,yinhuan_list);
                    for(int i=0;i<pohto_path.size();i++){
                        rep.Add_img(doc,pohto_path.get(i));
                    }
                    OutputStream os = new FileOutputStream(out2);
                    doc.write(os);
                    is.close();
                    os.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

                Message ms = new Message();
                ms.obj ="ok";
                handler.sendMessage(ms);

            }
        }).start();

    }



    private  Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.obj == "ok") {
                pd.dismiss();
                Toast.makeText(Create_a_new_docx.this,"成功",Toast.LENGTH_LONG).show();
                Intent intent= new Intent(Create_a_new_docx.this, Initialize_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    };


}
