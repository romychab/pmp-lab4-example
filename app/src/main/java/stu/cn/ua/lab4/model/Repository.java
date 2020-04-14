package stu.cn.ua.lab4.model;

import stu.cn.ua.lab4.model.db.RepositoryDbEntity;
import stu.cn.ua.lab4.model.network.RepositoryNetworkEntity;

public class Repository {
    private final long id;
    private final String name;
    private final String description;
    private final int stars;

    public Repository(long id, String name, String description, int stars) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.stars = stars;
    }

    public Repository(RepositoryDbEntity entity) {
        this(
            entity.getId(),
            entity.getName(),
            entity.getDescription(),
            entity.getStars()
        );
    }

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
