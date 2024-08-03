package dev.project.backendcursojava.models.responses;

import java.util.Date;

public class PostResponseModel {
    private String postId;

    private String title;

    private String content;

    private Date expiredAt;

    private Date createdAt;

    private boolean lapse = false;

    private UserDetailResponseModel user;

    private ExposureResponseModel exposure;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean getLapse() {
        return lapse;
    }

    public void setLapse(boolean lapse) {
        this.lapse = lapse;
    }

    public UserDetailResponseModel getUser() {
        return user;
    }

    public void setUser(UserDetailResponseModel user) {
        this.user = user;
    }

    public ExposureResponseModel getExposure() {
        return exposure;
    }

    public void setExposure(ExposureResponseModel exposure) {
        this.exposure = exposure;
    }
    
}
