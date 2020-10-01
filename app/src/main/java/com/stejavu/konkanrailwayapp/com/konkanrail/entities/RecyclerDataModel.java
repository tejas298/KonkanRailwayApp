package com.stejavu.konkanrailwayapp.com.konkanrail.entities;

public class RecyclerDataModel {

    String content;
    int imageSrc;
    String contentDesc;

    public RecyclerDataModel(String content, int imageSrc, String contentDesc) {
        this.content = content;
        this.imageSrc = imageSrc;
        this.contentDesc = contentDesc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(int imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getContentDesc() {
        return contentDesc;
    }

    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc;
    }
}
