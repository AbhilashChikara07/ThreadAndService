package com.example.chikara.seviceandthread;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<BookEntity> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button) findViewById(R.id.startService)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ServerCommunication communication = new ServerCommunication(
                                "https://s3.eu-central-1.amazonaws.com/testandroid1/data.xml",
                                "POST", new ServerCommunication.ConnectionListener() {

                            @Override
                            public void onPreExecute() {

                            }

                            @Override
                            public void onError(String error) {

                            }

                            @Override
                            public void onSuccess(String result) {
                                demo(result);
                            }

                            @Override
                            public void onNoNetwork() {

                            }
                        });
                        communication.run();
                    }
                });

    }


    private void demo(String xmlResponce) {
        try {

            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new InputSource(new StringReader(xmlResponce)));

            doc.getDocumentElement().normalize();

            NodeList mBookList = doc.getElementsByTagName("BOOK");

            for (int i = 0; i < mBookList.getLength(); i++) {

                Node node = mBookList.item(i);
                Element element = (Element) node;
                BookEntity entity = new BookEntity();

                entity.setID(element.getAttribute("ID"));
                entity.setTITLE(element.getAttribute("TITLE"));

                NodeList mThumbnailList = element.getElementsByTagName("THUMBNAIL");
                ArrayList<String> mTempThumbnailList = new ArrayList<>();

                for (int j = 0; j < mThumbnailList.getLength(); j++) {
                    Node mTempElement = mThumbnailList.item(j);
                    mTempThumbnailList.add(mTempElement.getFirstChild().getNodeValue());
                }

                entity.setTHUMBNAIL(mTempThumbnailList);
                entity.setNEW(element.getAttribute("NEW"));
                entity.setTHUMB_EXT(element.getAttribute("THUMB_EXT"));
                entity.setPDF_EXT(element.getAttribute("PDF_EXT"));

                mList.add(entity);
            }
            Log.e("mList", "" + mList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
