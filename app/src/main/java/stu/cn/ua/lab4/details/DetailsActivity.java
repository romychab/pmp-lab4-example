package stu.cn.ua.lab4.details;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import stu.cn.ua.lab4.App;
import stu.cn.ua.lab4.R;
import stu.cn.ua.lab4.model.Repository;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_REPOSITORY_ID = "REPOSITORY_ID";

    private TextView nameTextView;
    private TextView descriptionTextView;
    private ProgressBar progressBar;

    private DetailsViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        nameTextView = findViewById(R.id.nameTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        progressBar = findViewById(R.id.progress);

        long repositoryId = getIntent().getLongExtra(EXTRA_REPOSITORY_ID, -1);
        if (repositoryId == -1) {
            throw new RuntimeException("There is no repository ID");
        }

        App app = (App) getApplication();
        ViewModelProvider viewModelProvider = new ViewModelProvider(this, app.getViewModelFactory());
        viewModel = viewModelProvider.get(DetailsViewModel.class);

        viewModel.loadRepositoryById(repositoryId);

        viewModel.getResults().observe(this, result -> {
            switch (result.getStatus()) {
                case SUCCESS:
                    Repository repository = result.getData();
                    nameTextView.setText(repository.getName());
                    descriptionTextView.setText(repository.getDescription());
                    progressBar.setVisibility(View.GONE);
                    break;
                case EMPTY:
                    nameTextView.setText("");
                    descriptionTextView.setText("");
                    progressBar.setVisibility(View.GONE);
                    break;
                case LOADING:
                    nameTextView.setText("");
                    descriptionTextView.setText("");
                    progressBar.setVisibility(View.GONE);
            }
        });
    }

}
