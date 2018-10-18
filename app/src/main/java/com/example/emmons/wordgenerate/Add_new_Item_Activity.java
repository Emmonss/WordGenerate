package com.example.emmons.wordgenerate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Emmons on 2018/10/18 0018.
 */

public class Add_new_Item_Activity extends AppCompatActivity {
    EditText et_add_item;
    ImageView iv_delete,iv_back;
    Button bt_confirm;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item);

        bt_confirm = (Button)findViewById(R.id.bt_add_item_confrim);
        iv_delete = (ImageView)findViewById(R.id.iv_delete_additem);
        et_add_item = (EditText)findViewById(R.id.et_add_item) ;
        iv_back = (ImageView)findViewById(R.id.iv_additem_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_add_item.setText(null);
            }
        });


        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String res = et_add_item.getText().toString();
                if (res.equalsIgnoreCase(""))
                    Toast.makeText(Add_new_Item_Activity.this,"输入不能为空！",Toast.LENGTH_LONG).show();
                else {
                    Intent intentTemp = new Intent();
                    intentTemp.putExtra("backString", res);
                    setResult(1, intentTemp);
                    finish();
                }
            }
        });
    }
}
