package models;

/**
 * Created by Fugi on 6/22/2017.
 */

public class NewsItem {
    //Added img field
    String title;
    String author;
    String url;
    String desc;
    String date;
    String img;

    public NewsItem(String title, String author, String url, String desc, String date, String img) {
        this.title = title;
        this.author = author;
        this.url = url;
        this.desc = desc;
        this.date = date;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
