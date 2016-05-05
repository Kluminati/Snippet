package com.herokuapp.nodejsserverproject.snippet.pojo;

/**
 * Created by klu shvartsman on 13/04/2016.
 */
public class SnippetPojoObject {


    private String comments;
    private String ivPost;
    private String etTitle;
    private String etUserName;
    private String id;
    private String likes;
    public SnippetPojoObject( String id,String ivPost, String  etTitle,String comments, String  etUserName, String likes) {

        this.id = id;
        this.ivPost = ivPost;
        this.etTitle = etTitle;
        this.comments = comments;
        this.etUserName = etUserName;
        this.likes = likes;
    }

    public String getIvPost() {
        return ivPost;
    }

    public String getComments() {
        return comments;
    }

    public String  getEtTitle() {
        return etTitle;
    }

    public void setEtTitle(String  etTitle) {
        this.etTitle = etTitle;
    }

    public String  getEtUserName() {
        return etUserName;
    }

    public void setEtUserName(String  etUserName) {
        this.etUserName = etUserName;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLikes() {
        return likes;
    }
}
