package com.example.full.bean;

/**
 * Created   by   Dewey .
 * 自定义实体类VpBean,保存图片和url
 */

public class VpBean {
    private String img;

    public VpBean() {
        super();
    }

    @Override
    public String toString() {
        return "VpBean{" +
                "img='" + img + '\'' +
                '}';
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public VpBean(String img) {
        this.img = img;
    }
}
