package stu.cn.ua.lab4;

import android.util.Log;

import java.lang.reflect.Constructor;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import stu.cn.ua.lab4.model.GithubService;

public class ViewModelFactory implements ViewModelProvider.Factory {

    public static final String TAG = ViewModelFactory.class.getSimpleName();

    private GithubService githubService;

    public ViewModelFactory(GithubService githubService) {
        this.githubService = githubService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            Constructor<T> constructor = modelClass.getConstructor(GithubService.class);
            return constructor.newInstance(githubService);
        } catch (ReflectiveOperationException e) {
            Log.e(TAG, "Error", e);
            RuntimeException wrapper = new RuntimeException();
            wrapper.initCause(e);
            throw wrapper;
        }
    }

}
