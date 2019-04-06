package com.rdc.project.traveltrace.entity;

public class GuidePageText {

    private String mTitle;
    private String mContent;

    public GuidePageText(String title, String content) {
        mTitle = title;
        mContent = content;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    @Override
    public String toString() {
        return "GuidePageText{" +
                "mTitle='" + mTitle + '\'' +
                ", mContent='" + mContent + '\'' +
                '}';
    }
}
