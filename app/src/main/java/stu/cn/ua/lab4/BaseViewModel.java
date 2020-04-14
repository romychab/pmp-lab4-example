package stu.cn.ua.lab4;

import androidx.lifecycle.ViewModel;
import stu.cn.ua.lab4.model.GithubService;

public class BaseViewModel extends ViewModel {

    private GithubService githubService;

    public BaseViewModel(GithubService githubService) {
        this.githubService = githubService;
    }

    protected final GithubService getGithubService() {
        return githubService;
    }

}
