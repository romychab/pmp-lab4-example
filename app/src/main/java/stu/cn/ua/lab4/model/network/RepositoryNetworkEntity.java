package stu.cn.ua.lab4.model.network;

import com.google.gson.annotations.SerializedName;

public class RepositoryNetworkEntity {
    private long id;
    private String name;
    private String description;
    @SerializedName("stargazers_count")
    private int stars;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getStars() {
        return stars;
    }
}
