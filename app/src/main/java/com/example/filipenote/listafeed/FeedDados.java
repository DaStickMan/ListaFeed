package com.example.filipenote.listafeed;

/**
 * Created by FilipeNote on 06/08/2016.
 */
public class FeedDados {
    private String title;
    private String descricao;
    private String imgUrl;

    @Override
    public String toString() {
        return "FeedDados{" +
                "title='" + title + '\'' +
                ", descricao='" + descricao + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
