package com.example.dex.videoplayer.model;

/**
 * Created by dex on 11/4/18.
 */

public class ListItemDetail {

    boolean file_selected;
    private String title;
    private String thumbnail;
    private String abs_file_path;

    public ListItemDetail() {

    }

    public ListItemDetail(String title, String thumbnail, String abs_file_path, boolean file_selected) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.file_selected = file_selected;
        this.abs_file_path = abs_file_path;

    }

    public String getAbs_file_path() {
        return abs_file_path;
    }

    public void setAbs_file_path(String abs_file_path) {
        this.abs_file_path = abs_file_path;
    }

    public boolean isFile_selected() {
        return file_selected;

    }

    public void setFile_selected(boolean file_selected) {
        this.file_selected = file_selected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
