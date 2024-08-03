package dev.project.backendcursojava.models.responses;

import java.util.List;

public class UserDetailResponseModel {
    private String userId; // id public uuid
    private String firstName;
    private String lastName;
    private String email;
    private List<PostResponseModel> posts;

    // generate all getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<PostResponseModel> getPosts() {
        return posts;
    }

    public void setPosts(List<PostResponseModel> posts) {
        this.posts = posts;
    }

}
