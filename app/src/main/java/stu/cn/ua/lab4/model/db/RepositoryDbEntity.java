package stu.cn.ua.lab4.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import stu.cn.ua.lab4.model.network.RepositoryNetworkEntity;

@Entity(tableName = "repositories")
public class RepositoryDbEntity {

    @PrimaryKey
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "stars")
    private int stars;

    @ColumnInfo(name = "username")
    private String username;

    public RepositoryDbEntity() {
    }

    public RepositoryDbEntity(String username, RepositoryNetworkEntity entity) {
        this.username = username;
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.stars = entity.getStars();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
