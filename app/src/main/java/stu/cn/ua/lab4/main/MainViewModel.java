package stu.cn.ua.lab4.main;

import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import stu.cn.ua.lab4.BaseViewModel;
import stu.cn.ua.lab4.model.Callback;
import stu.cn.ua.lab4.model.Cancellable;
import stu.cn.ua.lab4.model.GithubService;
import stu.cn.ua.lab4.model.Repository;
import stu.cn.ua.lab4.model.Result;

import static stu.cn.ua.lab4.model.Result.Status.EMPTY;
import static stu.cn.ua.lab4.model.Result.Status.ERROR;
import static stu.cn.ua.lab4.model.Result.Status.LOADING;
import static stu.cn.ua.lab4.model.Result.Status.SUCCESS;

public class MainViewModel extends BaseViewModel {

    private Result<List<Repository>> repositoriesResult = Result.empty();
    private MutableLiveData<ViewState> stateLiveData = new MutableLiveData<>();

    {
        updateViewState(Result.empty());
    }

    private Cancellable cancellable;

    @Override
    protected void onCleared() {
        super.onCleared();
        if (cancellable != null) cancellable.cancel();
    }

    public MainViewModel(GithubService githubService) {
        super(githubService);
    }

    public LiveData<ViewState> getViewState() {
        return stateLiveData;
    }

    public void getRepositories(String username) {
        updateViewState(Result.loading());
        cancellable = getGithubService().getRepositories(username, new Callback<List<Repository>>() {
            @Override
            public void onError(Throwable error) {
                if (repositoriesResult.getStatus() != SUCCESS) {
                    updateViewState(Result.error(error));
                }
            }

            @Override
            public void onResults(List<Repository> data) {
                updateViewState(Result.success(data));
            }
        });
    }

    private void updateViewState(Result<List<Repository>> repositoriesResult) {
        this.repositoriesResult = repositoriesResult;
        ViewState state = new ViewState();
        state.enableSearchButton = repositoriesResult.getStatus() != LOADING;
        state.showList = repositoriesResult.getStatus() == SUCCESS;
        state.showEmptyHint = repositoriesResult.getStatus() == EMPTY;
        state.showError = repositoriesResult.getStatus() == ERROR;
        state.showProgress = repositoriesResult.getStatus() == LOADING;
        state.repositories = repositoriesResult.getData();
        stateLiveData.postValue(state);
    }

    static class ViewState {
        private boolean enableSearchButton;
        private boolean showList;
        private boolean showEmptyHint;
        private boolean showError;
        private boolean showProgress;
        private List<Repository> repositories;

        public boolean isEnableSearchButton() {
            return enableSearchButton;
        }

        public boolean isShowList() {
            return showList;
        }

        public boolean isShowEmptyHint() {
            return showEmptyHint;
        }

        public boolean isShowError() {
            return showError;
        }

        public boolean isShowProgress() {
            return showProgress;
        }

        public List<Repository> getRepositories() {
            return repositories;
        }
    }
}
