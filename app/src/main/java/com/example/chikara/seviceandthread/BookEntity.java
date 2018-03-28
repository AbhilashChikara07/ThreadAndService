package com.example.chikara.seviceandthread;

import java.util.ArrayList;

public class BookEntity {

    private String ID;
    private String TITLE;
    private ArrayList<String> THUMBNAIL;
    private String NEW;
    private String THUMB_EXT;
    private String PDF_EXT;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public ArrayList<String> getTHUMBNAIL() {
        return THUMBNAIL;
    }

    public void setTHUMBNAIL(ArrayList<String> THUMBNAIL) {
        this.THUMBNAIL = THUMBNAIL;
    }

    public String getNEW() {
        return NEW;
    }

    public void setNEW(String NEW) {
        this.NEW = NEW;
    }

    public String getTHUMB_EXT() {
        return THUMB_EXT;
    }

    public void setTHUMB_EXT(String THUMB_EXT) {
        this.THUMB_EXT = THUMB_EXT;
    }

    public String getPDF_EXT() {
        return PDF_EXT;
    }

    public void setPDF_EXT(String PDF_EXT) {
        this.PDF_EXT = PDF_EXT;
    }


}
