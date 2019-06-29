package com.example.commonproject1.Tab_3;

public class ImageItem {
    private int image;
    private String filterName;

    public ImageItem(int inputimage, String inputFilterName) {
        this.image = inputimage;
        this.filterName = inputFilterName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }
}