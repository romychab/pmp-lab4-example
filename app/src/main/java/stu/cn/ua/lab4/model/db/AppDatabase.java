package stu.cn.ua.lab4.model.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {RepositoryDbEntity.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract RepositoryDao getRepositoryDao();

}
