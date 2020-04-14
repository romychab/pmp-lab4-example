package stu.cn.ua.lab4.model.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubApi {

    @GET("users/{username}/repos")
    Call<List<RepositoryNetworkEntity>> getRepositories(@Path("username") String username);

    @GET("repositories/{id}")
    Call<RepositoryNetworkEntity> getRepositoryById(@Path("id") long id);

}
