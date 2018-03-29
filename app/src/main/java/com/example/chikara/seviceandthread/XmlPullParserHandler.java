package com.example.chikara.seviceandthread;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class XmlPullParserHandler {

    private ArrayList<BookEntity> mList = new ArrayList<BookEntity>();
    private BookEntity bookEntity;
    private String text;
    private ArrayList<String> thumbNail;

    public ArrayList<BookEntity> getEmployees() {
        return mList;
    }

    public ArrayList<BookEntity> parse(StringReader is) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(is);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase("BOOK")) {
                            // create a new instance of bookEntity  
                            bookEntity = new BookEntity();
                        }
                        if (tagName.equalsIgnoreCase("THUMBNAIL")) {
                            thumbNail = new ArrayList<>();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagName.equalsIgnoreCase("BOOK")) {
                            // add bookEntity object to list  
                            mList.add(bookEntity);
                        } else if (tagName.equalsIgnoreCase("ID")) {
                            bookEntity.setID(text);
                        } else if (tagName.equalsIgnoreCase("TITLE")) {
                            bookEntity.setTITLE(text);
                        } else if (tagName.equalsIgnoreCase("NEW")) {
                            bookEntity.setNEW(text);
                        } else if (tagName.equalsIgnoreCase("THUMBNAIL")) {
                            thumbNail.add(text);
                            bookEntity.setTHUMBNAIL(thumbNail);
                        } else if (tagName.equalsIgnoreCase("THUMB_EXT")) {
                            bookEntity.setTHUMB_EXT(text);
                        } else if (tagName.equalsIgnoreCase("PDF_EXT")) {
                            bookEntity.setPDF_EXT(text);
                        }
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mList;
    }
}  