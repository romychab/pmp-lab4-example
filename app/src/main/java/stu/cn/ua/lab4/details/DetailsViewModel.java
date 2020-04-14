package stu.cn.ua.lab4.details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import stu.cn.ua.lab4.BaseViewModel;
import stu.cn.ua.lab4.model.Callback;
import stu.cn.ua.lab4.model.Cancellable;
import stu.cn.ua.lab4.model.GithubService;
import stu.cn.ua.lab4.model.Repository;
import stu.cn.ua.lab4.model.Result;

public class DetailsViewModel extends BaseViewModel {

    private MutableLiveData<Result<Repository>> repositoryLiveData = new MutableLiveData<>();

    {
        repositoryLiveData.setValue(Result.empty());
    }

    private Cancellable cancellable;

    public DetailsViewModel(GithubService githubService) {
        super(githubService);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (cancellable != null) cancellable.cancel();
    }

    public void loadRepositoryById(long id) {
        repositoryLiveData.setValue(Result.loading());
        cancellable = getGithubService().getRepositoryById(id, new Callback<Repository>() {
            @Override
            public void onError(Throwable error) {
                repositoryLiveData.postValue(Result.error(error));
            }

            @Override
            public void onResults(Repository data) {
                repositoryLiveData.postValue(Result.success(data));
            }
        });
    }

    public LiveData<Result<Repository>> getResults() {
        return repositoryLiveData;
    }

}
