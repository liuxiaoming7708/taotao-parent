package com.taotao.portal.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SearchItem {

    private String id;
    private String title;
    private String sell_point;
    private long price;
    private String image;
    private String category_name;
    private String item_desc;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSell_point() {
        return sell_point;
    }
    public void setSell_point(String sell_point) {
        this.sell_point = sell_point;
    }
    public long getPrice() {
        return price;
    }
    public void setPrice(long price) {
        this.price = price;
    }
    public String getImage() {
        if(null != image && "" != image){
            String[] strings = image.split(",");
            return strings[0];
        }
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getCategory_name() {
        return category_name;
    }
    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
    public String getItem_desc() {
        return item_desc;
    }
    public void setItem_desc(String item_des) {
        this.item_desc = item_des;
    }

    @JsonIgnore
    public String[] getImages() {
        if (image != null) {
            String[] images = image.split(",");
            return images;
        }
        return null;
    }


}
