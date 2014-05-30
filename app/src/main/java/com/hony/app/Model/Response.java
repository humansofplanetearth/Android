package com.hony.app.Model;

import java.util.List;

public class Response{
    private Blog blog;
    private List posts;
    private Number total_posts;

    public Response() {

    }

    public Blog getBlog() {
        return this.blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public List getPosts() {
        return this.posts;
    }

    public void setPosts(List posts) {
        this.posts = posts;
    }

    public Number getTotal_posts() {
        return this.total_posts;
    }

    public void setTotal_posts(Number total_posts) {
        this.total_posts = total_posts;
    }
}