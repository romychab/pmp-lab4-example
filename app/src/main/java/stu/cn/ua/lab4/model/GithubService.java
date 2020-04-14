package stu.cn.ua.lab4.model;

import com.annimon.stream.Stream;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import retrofit2.Response;
import stu.cn.ua.lab4.logger.Logger;
import stu.cn.ua.lab4.model.db.RepositoryDao;
import stu.cn.ua.lab4.model.db.RepositoryDbEntity;
import stu.cn.ua.lab4.model.network.GithubApi;
import stu.cn.ua.lab4.model.network.RepositoryNetworkEntity;

public class GithubService {

    private ExecutorService executorService;
    private RepositoryDao repositoryDao;
    private GithubApi githubApi;
    private Logger logger;

    public GithubService(GithubApi githubApi, RepositoryDao repositoryDao,
                         ExecutorService executorService, Logger logger) {
        this.githubApi = githubApi;
        this.repositoryDao = repositoryDao;
        this.executorService = executorService;
        this.logger = logger;
    }

    public Cancellable getRepositories(String username, Callback<List<Repository>> callback) {
        Future<?> future = executorService.submit(() -> {
            try {
                List<RepositoryDbEntity> entities = repositoryDao.getRepositories(username);
                List<Repository> repositories = convertToRepositories(entities);
                callback.onResults(repositories);

                Response<List<RepositoryNetworkEntity>> response = githubApi.getRepositories(username).execute();
                if (response.isSuccessful()) {
                    List<RepositoryDbEntity> newDbRepositories = networkToDbEntities(username, response.body());
                    repositoryDao.updateRepositoriesForUsername(username, newDbRepositories);
                    callback.onResults(convertToRepositories(newDbRepositories));
                } else {
                    if (!repositories.isEmpty()) {
                        RuntimeException exception = new RuntimeException("Something happened");
                        logger.e(exception);
                        callback.onError(exception);
                    }
                }
            } catch (Exception e) {
                logger.e(e);
                callback.onError(e);
            }
        });

        return new FutureCancellable(future);
    }

    public Cancellable getRepositoryById(long id, Callback<Repository> callback) {
        Future<?> future = executorService.submit(() -> {
            try {
                RepositoryDbEntity dbEntity = repositoryDao.getById(id);
                Repository repository = new Repository(dbEntity);
                callback.onResults(repository);
            } catch (Exception e) {
                logger.e(e);
                callback.onError(e);
            }
        });
        return new FutureCancellable(future);
    }

    private List<Repository> convertToRepositories(List<RepositoryDbEntity> entities) {
        return Stream.of(entities).map(Repository::new).toList();
    }

    private List<RepositoryDbEntity> networkToDbEntities(String username, List<RepositoryNetworkEntity> entities) {
        return Stream.of(entities)
                .map(networkEntity -> new RepositoryDbEntity(username, networkEntity))
                .toList();
    }

    static class FutureCancellable implements Cancellable {
        private Future<?> future;

        public FutureCancellable(Future<?> future) {
            this.future = future;
        }

        @Override
        public void cancel() {
            future.cancel(true);
        }
    }

}
