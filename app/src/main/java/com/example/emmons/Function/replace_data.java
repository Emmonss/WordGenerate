package com.example.emmons.Function;

import android.graphics.BitmapFactory;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Emmons on 2018/10/24 0024.
 */

public class replace_data {

    public void replaceInPara(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            replaceInPara(para, params);
        }
    }



    private void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
        List<XWPFRun> runs;
        Matcher matcher;
        if (this.matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            for (int i=0; i<runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString();
                matcher = this.matcher(runText);
                if (matcher.find()) {
                    while ((matcher = this.matcher(runText)).find()) {
                        runText = matcher.replaceFirst(String.valueOf(params.get(matcher.group(1))));
                    }
                    //直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
                    //所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
                    para.removeRun(i);
                    para.insertNewRun(i).setText(runText);
                }
            }
        }
    }


    public void Add_Info(XWPFDocument doc, List<String> list) {
        List<XWPFTable> tables = doc.getTables();
        XWPFTableRow cell = tables.get(0).getRow(0);
        XWPFRun paras = cell.getTableCells().get(0).getParagraphs().get(0).createRun();

        for (int i =0;i<list.size();i++){
            paras.addBreak();
            paras.setText(list.get(i));
        }

    }


    public void replaceInTable(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        this.replaceInPara(para, params);
                    }
                }
            }
        }
    }




    public void Add_img(XWPFDocument doc,String img,int i){
        try {
            //图片缩放
            int height,width;
            BitmapFactory.Options op=new BitmapFactory.Options();
            op.inJustDecodeBounds=true;
            BitmapFactory.decodeFile(img, op);


            if(op.outWidth>200){
                width = 200;
                height = op.outHeight*200/op.outWidth;
            }
            else if(op.outHeight>300){
                height = 300;
                width = op.outWidth*300/op.outHeight;
            }
            else {
                height = op.outHeight;
                width = op.outWidth;
            }

            InputStream iimg = new FileInputStream(img);
            XWPFParagraph paragraph = doc.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = paragraph.createRun();
            run.addPicture(iimg, XWPFDocument.PICTURE_TYPE_JPEG, "android.jpeg", Units.toEMU(width), Units.toEMU(height));

            XWPFParagraph singal = doc.createParagraph();
            singal.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run2 = singal.createRun();
            run2.setText("图片("+i+")");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


    private Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

}
