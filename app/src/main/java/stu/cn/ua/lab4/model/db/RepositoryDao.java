package stu.cn.ua.lab4.model.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public interface RepositoryDao {

    @Query("SELECT * FROM repositories WHERE username = :username ORDER BY name COLLATE NOCASE")
    List<RepositoryDbEntity> getRepositories(String username);

    @Query("SELECT * FROM repositories WHERE id = :id")
    RepositoryDbEntity getById(long id);

    @Insert
    void insertRepositories(List<RepositoryDbEntity> repositories);

    @Query("DELETE FROM repositories WHERE username = :username")
    void deleteRepositories(String username);

    @Transaction
    default void updateRepositoriesForUsername(String username, List<RepositoryDbEntity> entities) {
        deleteRepositories(username);
        insertRepositories(entities);
    }

}
