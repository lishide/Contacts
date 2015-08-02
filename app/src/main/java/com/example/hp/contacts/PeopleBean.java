package com.example.hp.contacts;

/**
 * Created by hp on 2015/7/7.
 */
public class PeopleBean {
    private String people_name;
    private String people_tel;
    private String people_email;
    private int imageId;


    public String getPeople_name() {
        return people_name;
    }

    public void setPeople_name(String people_name) {
        this.people_name = people_name;
    }

    public String getPeople_tel() {
        return people_tel;
    }

    public void setPeople_tel(String people_tel) {
        this.people_tel = people_tel;
    }

    public String getPeople_email() {
        return people_email;
    }

    public void setPeople_email(String people_email) {
        this.people_email = people_email;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
