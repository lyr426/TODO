package com.example.yulmoostodo;

public class MainData {

    private boolean ch_box;
    private String tv_content;
    private int Id;
    private boolean ch_imp;


    public MainData(boolean ch_box, String tv_content, int id, boolean ch_imp) {
        this.ch_box = ch_box;
        this.tv_content = tv_content;
        this.Id = id;
        this.ch_imp = ch_imp;
    }

    public  int getId() {return Id;}

    public void setId(int id ) {this.Id = id; }

    public boolean getCh_box() {
        return ch_box;
    }

    public void setCh_box(boolean ch_box) {
        this.ch_box = ch_box;
    }

    public boolean getCh_imp() {
        return ch_imp;
    }

    public void setCh_imp(boolean ch_imp) {
        this.ch_imp = ch_imp;
    }

    public String getTv_content() {
        return tv_content;
    }

    public void setTv_content(String tv_content) {
        this.tv_content = tv_content;
    }




}
