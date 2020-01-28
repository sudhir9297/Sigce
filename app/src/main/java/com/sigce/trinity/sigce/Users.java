package com.sigce.trinity.sigce;

public class Users {

    public String image;
    public String status;
    public String name;
    public String thumb_image;

    public Users(){

    }

    public Users(String name, String image, String status, String thumb_image, String userid){
        this.name=name;
        this.image=image;
        this.status=status;
        this.thumb_image=thumb_image;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumb_img() {
        return thumb_image;
    }

    public void setThumb_img(String thumb_image) {
        this.thumb_image = thumb_image;
    }
}
