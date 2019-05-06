package com.rdc.project.traveltrace.entity;

import java.util.List;

import cn.bmob.v3.BmobObject;

public class RecommendCard extends BmobObject {

    private List<String> mImgUrls;
    private String mContent;
    private String mLinkUrl;

    public RecommendCard() {

    }

    public List<String> getImgUrls() {
        return mImgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        mImgUrls = imgUrls;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getLinkUrl() {
        return mLinkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        mLinkUrl = linkUrl;
    }

    @Override
    public String toString() {
        return "RecommendCard{" +
                "mImgUrls=" + mImgUrls +
                ", mContent='" + mContent + '\'' +
                ", mLinkUrl='" + mLinkUrl + '\'' +
                '}';
    }
}
