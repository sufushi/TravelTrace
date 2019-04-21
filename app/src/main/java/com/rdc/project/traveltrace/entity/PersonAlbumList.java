package com.rdc.project.traveltrace.entity;

import java.util.List;

public class PersonAlbumList {

    private List<String> mAlbumList;

    public PersonAlbumList(List<String> albumList) {
        mAlbumList = albumList;
    }

    public List<String> getAlbumList() {
        return mAlbumList;
    }

    public void setAlbumList(List<String> albumList) {
        mAlbumList = albumList;
    }

    @Override
    public String toString() {
        return "PersonAlbumList{" +
                "mAlbumList=" + mAlbumList +
                '}';
    }
}
