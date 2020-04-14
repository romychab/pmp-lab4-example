package stu.cn.ua.lab4;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import stu.cn.ua.lab4.logger.AndroidLogger;
import stu.cn.ua.lab4.logger.Logger;
import stu.cn.ua.lab4.model.GithubService;
import stu.cn.ua.lab4.model.db.AppDatabase;
import stu.cn.ua.lab4.model.db.RepositoryDao;
import stu.cn.ua.lab4.model.network.GithubApi;

public class App extends Application {

    private static final String BASE_URL = "https://api.github.com/";

    private ViewModelProvider.Factory viewModelFactory;

    @Override
    public void onCreate() {
        super.onCreate();

        Logger logger = new AndroidLogger();

        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GithubApi githubApi = retrofit.create(GithubApi.class);

        ExecutorService executorService = Executors.newCachedThreadPool();

        AppDatabase appDatabase = Room.databaseBuilder(this, AppDatabase.class, "database.db")
                .build();
        RepositoryDao repositoryDao = appDatabase.getRepositoryDao();

        GithubService githubService = new GithubService(githubApi, repositoryDao, executorService, logger);
        viewModelFactory = new ViewModelFactory(githubService);
    }

    public ViewModelProvider.Factory getViewModelFactory() {
        return viewModelFactory;
    }

}
