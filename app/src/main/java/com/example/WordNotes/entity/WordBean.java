package com.example.WordNotes.entity;

public class WordBean {
    private int wordid;
    private String wordname;
    private String paraphrase;
    private String wordgroup;
    private String note;
    private int star;
    private String listgroup;

    public int getWordid() {
        return wordid;
    }

    public void setWordid(int wordid) {
        this.wordid = wordid;
    }

    public String getWordname() {
        return wordname;
    }

    public void setWordname(String wordname) {
        this.wordname = wordname;
    }

    public String getParaphrase() {
        return paraphrase;
    }

    public void setParaphrase(String paraphrase) {
        this.paraphrase = paraphrase;
    }

    public String getWordgroup() {
        return wordgroup;
    }

    public void setWordgroup(String wordgroup) {
        this.wordgroup = wordgroup;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getListgroup() {
        return listgroup;
    }

    public void setListgroup(String listgroup) {
        this.listgroup = listgroup;
    }
}
