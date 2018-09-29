package com.example.emmons.Function;

import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.emmons.utils.FileUtils;
import com.example.emmons.Function.Common_Fuction;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by Emmons on 2018/9/28 0028.
 */

public class Word_to_Doc {
    Common_Fuction cf = new Common_Fuction();
    public void opendoc(Context context,String docPath, String name, String savePath){
        try {
            File f = new File(savePath);
            cf.deleteFile(context,f);
            Convert_doc_2Html(docPath, savePath + name + ".html",name,savePath);
            cf.notifySystemToScan(context,f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //WebView加载显示本地html文件
//        WebView webView = (WebView)this.findViewById(R.id.office);
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setSupportZoom(true);
//        webSettings.setBuiltInZoomControls(true);
//        webView.loadUrl("file:/"+savePath+name+".html");
    }


    //word文档转成html格式
    public void Convert_doc_2Html(String fileName, String outPutFile,String dirname,String savePath)throws IOException, ParserConfigurationException, TransformerException {
        final String pic_dirname = dirname;
        HWPFDocument wordDocument = null;
        try {
            wordDocument = new HWPFDocument(new FileInputStream(fileName));
            WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                    DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
            //设置图片路径
            wordToHtmlConverter.setPicturesManager(new PicturesManager() {
                public String savePicture(byte[] content,
                                          PictureType pictureType, String suggestedName,
                                          float widthInches, float heightInches) {
                    return pic_dirname + "/" + suggestedName;
                }
            });
            //保存图片
            List<Picture> pics=wordDocument.getPicturesTable().getAllPictures();
            if(pics!=null){
                for(int i=0;i<pics.size();i++){
                    Picture pic = (Picture)pics.get(i);
                    try {
                        String file = savePath+ pic_dirname + "/" + pic.suggestFullFileName();
                        FileUtils.makeDirs(file);
                        pic.writeImageContent(new FileOutputStream(file));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            wordToHtmlConverter.processDocument(wordDocument);
            Document htmlDocument = wordToHtmlConverter.getDocument();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DOMSource domSource = new DOMSource(htmlDocument);
            StreamResult streamResult = new StreamResult(out);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer serializer = tf.newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");
            serializer.transform(domSource, streamResult);
            out.close();
            //保存html文件
            cf.writeFile(new String(out.toByteArray()), outPutFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
