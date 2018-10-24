package com.example.emmons.wordgenerate;
/**
 * Created by Emmons on 2018/10/15 0015.
 * 隐患整改通知
 */

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emmons.Model.YinHuan_Company_List;
import com.example.emmons.wordgenerate.Adapter.GridViewAddImgesAdpter;
import com.example.emmons.wordgenerate.WheelView.BottomDialog;
import com.example.emmons.wordgenerate.WheelView.WheelView;
import com.example.emmons.wordgenerate.time.CustomDatePicker;

import net.bither.util.NativeUtil;


import java.io.File;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class Info_Edit_Activity  extends AppCompatActivity {
    private List<String> pohto_path;

    //日期选择
    private CustomDatePicker customDatePicker_LimitDate,customDatePicker_SigDate,customDatePicker_ZGDate,customDatePicker_FJDate;
    private TextView tv_LimitDate,tv_SigDate,tv_ZGDate,tv_FJDate;
    //公司选择
    private YinHuan_Company_List comlist;
    private ImageView iv_goto_departname;
    private EditText et_depart ;

    //Yinhuan_tv_yinzi
    private TextView tv_yinzi;
    private BottomDialog bottomDialog;

    //添加隐患
    private ScrollView scrollView;
    private LinearLayout ll_yinhuan_Container;
    private LinearLayout ll_add_yinhuan;
    private Button btnYes;

    String com_path;

    //添加照片
    private GridView gw;
    private List<Map<String, Object>> datas;
    private GridViewAddImgesAdpter gridViewAddImgesAdpter;
    private Dialog dialog;
    private final int GET_COMNAME = 1;// 从相册中选择private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private final int PHOTO_REQUEST_CAREMA = 2;// 拍照
    private final int PHOTO_REQUEST_GALLERY = 3;// 从相册中选择private static final String PHOTO_FILE_NAME = "temp_photo.jpg";

    private File tempFile;
    private final String IMAGE_DIR = Environment.getExternalStorageDirectory() + "/WordGenerate/gridview/";
    private final String PHOTO_FILE_NAME = "temp_photo.jpg";

    //save
    private EditText Yinhuan_et_edit_title,et_edit_haozi;


    //delete
    private ImageView iv_delete_title,iv_delete_departname,iv_delete_companyname,iv_process_back;



    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yinhuan_mode_generation);

        Intent intent=getIntent();
        com_path=intent.getStringExtra("path")+"/隐患整改通知/companyname.db";
        comlist = new YinHuan_Company_List(com_path);


        init();
    }

    private void init(){
        pohto_path = new ArrayList<String>();
        //时间选择
        tv_LimitDate = (TextView)findViewById(R.id.Yinhuan_tv_limitdate);
        tv_SigDate = (TextView)findViewById(R.id.Yinhuan_tv_Singdate);
        tv_ZGDate = (TextView)findViewById(R.id.Yinhuan_tv_ZhengGaidate);
        tv_FJDate = (TextView)findViewById(R.id.Yinhuan_tv_Fujiandate);

        //公司选择
        et_depart = (EditText)findViewById(R.id.Yinhuan_et_edit_departname);
        iv_goto_departname = (ImageView)findViewById(R.id.Yinhuan_iv_goto_departname);

        //隐字号选择
        tv_yinzi = (TextView)findViewById(R.id.Yinhuan_tv_yinzi);

        //添加隐患
        scrollView = (ScrollView)findViewById(R.id.Yinhuan_scroll_view);
        ll_yinhuan_Container = (LinearLayout)findViewById(R.id.Yinhuan_ll_item_container);
        ll_add_yinhuan = (LinearLayout)findViewById(R.id.Yinhuan_ll_add_yinhuan_item);
        btnYes = (Button)findViewById(R.id.Yinhuan_bt_create_new_doc);

        //添加照片
        gw = (GridView) findViewById(R.id.pic_item_gw);

        //create
        Yinhuan_et_edit_title = (EditText)findViewById(R.id.Yinhuan_et_edit_title);
        et_edit_haozi = (EditText)findViewById(R.id.et_edit_haozi);





        get_Yinzi();

        get_ComName();

        init_datepicker();

        get_LimtDate();

        get_add_pic();

        get_add_item();

        create();

        delete();
    }

    public void create(){
        final String save_path = Environment.getExternalStorageDirectory()+"/WordGenerate/file/";
        final String demopath = "隐患整改通知.docx";


        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Map<String, String> map =getMap();
                List<String> yinhuan_list = getDataList();
                //CheckMap(map);
                if(CheckMap(map)) {
                    //Toast.makeText(Info_Edit_Activity.this, "可以了", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Info_Edit_Activity.this,Create_a_new_docx.class);
                    intent.putExtra("map",(Serializable)map);
                    intent.putExtra("yinhuan_list",(Serializable)yinhuan_list);
                    intent.putExtra("pohto_path",(Serializable)pohto_path);
                    startActivity(intent);

                }
            }
        });
    }

    public void delete(){

        //Yinhuan_iv_delete_title,Yinhuan_iv_delete_departname,Yinhuan_iv_delete_companyname;
        iv_delete_title = (ImageView)findViewById(R.id.Yinhuan_iv_delete_title);
        iv_delete_departname = (ImageView)findViewById(R.id.Yinhuan_iv_delete_departname);
        iv_delete_companyname = (ImageView)findViewById(R.id.Yinhuan_iv_delete_companyname);
        iv_process_back  = (ImageView)findViewById(R.id.Yinhuan_iv_process_back);

        //标题删除
        iv_delete_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Yinhuan_et_edit_title.setText("");
            }
        });

        //公司名删除
        iv_delete_departname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_depart.setText("");
            }
        });

        //号删除
        iv_delete_companyname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_edit_haozi.setText("");
            }
        });

        iv_process_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
    //添加图片
    private void get_add_pic(){
        datas = new ArrayList<>();
        gridViewAddImgesAdpter = new GridViewAddImgesAdpter(datas, this);

        File dir = new File(IMAGE_DIR);
        if (!dir.exists())
            dir.mkdir();

        gw.setAdapter(gridViewAddImgesAdpter);
        gw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                showdialog();
            }
        });
    }

    public void showdialog() {
        View localView = LayoutInflater.from(this).inflate(
                R.layout.activity_dialog_add_pic, null);
        TextView tv_camera = (TextView) localView.findViewById(R.id.pic_item_tv_camera);
        TextView tv_gallery = (TextView) localView.findViewById(R.id.pic_item_tv_gallery);
        TextView tv_cancel = (TextView) localView.findViewById(R.id.pic_item_tv_cancel);
        dialog = new Dialog(this, R.style.custom_dialog);
        dialog.setContentView(localView);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        // 设置全屏
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        Point size =new Point();
        display.getSize(size);
        lp.width = size.x; // 设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        tv_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });

        tv_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // 拍照
                camera();
            }
        });

        tv_gallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // 从系统相册选取照片
                gallery();
            }
        });
    }

    public void camera() {
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {

            File dir = new File(IMAGE_DIR);
//            if (!dir.exists()) {
//                dir.mkdir();
//            }
            tempFile = new File(dir,
                    System.currentTimeMillis() + "_" + PHOTO_FILE_NAME);
            //从文件中创建uri
            //Uri uri = Uri.fromFile(tempFile);
            Uri uri  = FileProvider.getUriForFile(this, "com.example.emmons.wordgenerate.fileprovider", tempFile);
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addCategory(intent.CATEGORY_DEFAULT);
            // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
            startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
        } else {
            Toast.makeText(this, "未找到存储卡，无法拍照！", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public void gallery() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }



    //添加隐患
    private void get_add_item(){
        for(int i = 0; i < 1; i++){
            addViewItem();
        }
        ll_add_yinhuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addViewItem();
            }
        });


    }

    private void addViewItem(){
        final View viewItem = LayoutInflater.from(this).inflate(R.layout.activity_add_yinhuan_item, ll_yinhuan_Container,false);
        ll_yinhuan_Container.addView(viewItem);
        sortViewItem();
        //添加并且排序之后将布局滚动到底部，方便用户继续添加
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

        LinearLayout llDelete = (LinearLayout) viewItem.findViewById(R.id.Yinhuan_ll_delete_item);
        llDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_yinhuan_Container.removeView(viewItem);
                sortViewItem();
            }
        });

    }

    private void sortViewItem(){
        for(int i = 0; i < ll_yinhuan_Container.getChildCount(); i++){
            final View viewItem = ll_yinhuan_Container.getChildAt(i);
            TextView tvIndex = (TextView)viewItem.findViewById(R.id.Yinhuan_tv_index);
            tvIndex.setText(String.valueOf(i+1));
        }
    }



    //安隐字
    @SuppressWarnings("unchecked")
    private void get_Yinzi(){

        tv_yinzi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View outerView1 = LayoutInflater.from(Info_Edit_Activity.this).inflate(R.layout.activity_pick_yinzi, null);
                final WheelView wv1 = (WheelView) outerView1.findViewById(R.id.Yinhuan_yinzi_wheel);
                wv1.setItems(getNumbers(2018,2032),0);

                TextView tv_ok = (TextView) outerView1.findViewById(R.id.Yinhuan_yinzi_ok);
                TextView tv_cancel = (TextView) outerView1.findViewById(R.id.Yinhuan_yinzi_cancel);

                tv_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        bottomDialog.dismiss();
                        tv_yinzi.setText(wv1.getSelectedItem());
                    }
                });

                //点击取消
                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        bottomDialog.dismiss();
                    }
                });

                //防止弹出两个窗口
                if (bottomDialog !=null && bottomDialog.isShowing())
                    return;

                bottomDialog = new BottomDialog(Info_Edit_Activity.this, R.style.ActionSheetDialogStyle);
                //将布局设置给Dialog
                bottomDialog.setContentView(outerView1);
                bottomDialog.show();//显示对话框

            }
        });

    }

    //公司名
    private void get_ComName(){
        final String list = comlist.getTest();
        iv_goto_departname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list==null)
                    Toast.makeText(Info_Edit_Activity.this,"不存在~",Toast.LENGTH_LONG).show();
                else{
//                    Toast.makeText(Info_Edit_Activity.this,list,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Info_Edit_Activity.this,ComName_List_Activity.class);
                    intent.putExtra("path",com_path);
                    startActivityForResult(intent,GET_COMNAME);
                }

            }
        });

    }

    //日期
    private void init_datepicker(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        tv_LimitDate.setText(now.split(" ")[0]);
        tv_SigDate.setText(now.split(" ")[0]);
        tv_ZGDate.setText(now.split(" ")[0]);
        tv_FJDate.setText(now.split(" ")[0]);

        init_DatePicker();


    }

    //日期选择器
    private void init_DatePicker(){
        //1
        customDatePicker_LimitDate = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tv_LimitDate.setText(time.split(" ")[0]);
            }
        }, "2016-01-01 00:00", "2020-12-31 23:59"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker_LimitDate.showSpecificTime(false); // 不显示时和分
        customDatePicker_LimitDate.setIsLoop(false); // 不允许循环滚动

        //2
        customDatePicker_SigDate = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tv_SigDate.setText(time.split(" ")[0]);
            }
        }, "2016-01-01 00:00", "2020-12-31 23:59"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker_SigDate.showSpecificTime(false); // 不显示时和分
        customDatePicker_SigDate.setIsLoop(false); // 不允许循环滚动

        //3
        customDatePicker_ZGDate = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tv_ZGDate.setText(time.split(" ")[0]);
            }
        }, "2016-01-01 00:00", "2020-12-31 23:59"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker_ZGDate.showSpecificTime(false); // 不显示时和分
        customDatePicker_ZGDate.setIsLoop(false); // 不允许循环滚动

        //4
        customDatePicker_FJDate = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tv_FJDate.setText(time.split(" ")[0]);
            }
        }, "2016-01-01 00:00", "2020-12-31 23:59"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker_FJDate.showSpecificTime(false); // 不显示时和分
        customDatePicker_FJDate.setIsLoop(false); // 不允许循环滚动
    }

    //添加各类日期监听事件
    private void get_LimtDate(){
        tv_LimitDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDatePicker_LimitDate.show(tv_LimitDate.getText().toString());
            }
        });


        tv_SigDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDatePicker_SigDate.show(tv_SigDate.getText().toString());
            }
        });

        tv_ZGDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDatePicker_ZGDate.show(tv_ZGDate.getText().toString());
            }
        });

        tv_FJDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDatePicker_FJDate.show(tv_FJDate.getText().toString());
            }
        });
    }

    //日期规范
    private String date_normalize(String date){
        String[] da = date.split("-");
        if(da.length>=3)
            return da[0]+"年"+da[1]+"月"+da[2]+"日";
        else
            return date;
    }

    //安隐字号选择
    private ArrayList getNumbers( int start,int end) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            list.add(i + "");
        }
        return  list;
    }

    //各类返回事件
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_COMNAME && data!=null) {
                et_depart.setText(data.getStringExtra("backString"));
            }

        if (resultCode == RESULT_OK) {
            if (requestCode == PHOTO_REQUEST_GALLERY) {
                // 从相册返回的数据
                if (data != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    String[] proj = {MediaStore.Images.Media.DATA};
                    //好像是android多媒体数据库的封装接口，具体的看Android文档
                    Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
                    //按我个人理解 这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    //将光标移至开头 ，这个很重要，不小心很容易引起越界
                    cursor.moveToFirst();
                    //最后根据索引值获取图片路径
                    String path = cursor.getString(column_index);
                    pohto_path.add(path);


                    uploadImage(path);
                }

            }
            else if (requestCode == PHOTO_REQUEST_CAREMA ) {
                    Toast.makeText(this, "data.getData():"+tempFile.getPath(), Toast.LENGTH_SHORT).show();
                    // 从相机返回的数据
                    if (hasSdcard()) {
                        if (tempFile != null) {
                            uploadImage(tempFile.getPath());
                            pohto_path.add(tempFile.getPath());
                        } else {
                            Toast.makeText(this, "相机异常请稍后再试！", Toast.LENGTH_SHORT).show();
                        }

                        Log.i("images", "拿到照片path=" + tempFile.getPath());
                    } else {
                        Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                    }
            }

        }




    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0xAAAAAAAA) {
                photoPath(msg.obj.toString());
            }

        }
    };

    private void uploadImage(final String path) {
        new Thread() {
            @Override
            public void run() {
                if (new File(path).exists()) {
                    Log.e("images", "源文件存在" + path);
                } else {
                    Log.e("images", "源文件不存在" + path);
                }

                File dir = new File(IMAGE_DIR);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                final File file = new File(dir + "/temp_photo" + System.currentTimeMillis() + ".jpg");
                NativeUtil.compressBitmap(path, file.getAbsolutePath(), 50);
                if (file.exists()) {
                    Log.e("images", "压缩后的文件存在" + file.getAbsolutePath());
                } else {
                    Log.e("images", "压缩后的不存在" + file.getAbsolutePath());
                }
                Message message = new Message();
                message.what = 0xAAAAAAAA;
                message.obj = file.getAbsolutePath();
                handler.sendMessage(message);

            }
        }.start();
    }


    public void photoPath(String path) {
        Map<String,Object> map=new HashMap<>();
        map.put("path",path);
        datas.add(map);
        gridViewAddImgesAdpter.notifyDataSetChanged();
    }


    private List<String> getDataList() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < ll_yinhuan_Container.getChildCount(); i++) {
            View itemView = ll_yinhuan_Container.getChildAt(i);
            int j = i+1;
            EditText et = (EditText) itemView.findViewById(R.id.Yinhuan_et_yinhuan_item);
            if (et != null) {
                String vipNum = et.getText().toString().trim();
                if (!TextUtils.isEmpty(vipNum)) {
                    result.add(j+"."+vipNum);
                }
            }
        }
        return result;
    }

    private Map<String, String> getMap() {
        String comname = et_depart.getText().toString();
        String yinzi = tv_yinzi.getText().toString();
        String LimitDate =date_normalize( tv_LimitDate.getText().toString());
        String SigDate =date_normalize(  tv_SigDate.getText().toString());
        String ZGDate = date_normalize( tv_ZGDate.getText().toString());
        String FJDate = date_normalize( tv_FJDate.getText().toString());
        String edit_haozi = date_normalize( et_edit_haozi.getText().toString());
        String docname = Yinhuan_et_edit_title.getText().toString();

        Map<String, String> map = new HashMap<String, String>();
        map.put("文件名称",docname);
        map.put("项目部名称", comname);
        map.put("限定日期", LimitDate);
        map.put("签发日期", SigDate);
        map.put("整改日期", ZGDate);
        map.put("复检日期", FJDate);
        map.put("隐字", yinzi);
        map.put("号", edit_haozi);

        return map;

    }

    private boolean CheckMap(Map<String,String> map){
        if(map.get("文件名称").equalsIgnoreCase("")){
            Toast.makeText(Info_Edit_Activity.this, "请填写文件名", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(map.get("项目部名称").equalsIgnoreCase("")){
            Toast.makeText(Info_Edit_Activity.this, "请填写项目部名称", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(map.get("隐字").equalsIgnoreCase("")){
            Toast.makeText(Info_Edit_Activity.this, "请填写隐字", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(map.get("号").equalsIgnoreCase("")){
            Toast.makeText(Info_Edit_Activity.this, "请填写号", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }

}
