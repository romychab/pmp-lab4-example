package stu.cn.ua.lab4.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import stu.cn.ua.lab4.R;
import stu.cn.ua.lab4.model.Repository;

public class RepositoriesAdapter
        extends RecyclerView.Adapter<RepositoriesAdapter.RepositoryViewHolder>
        implements View.OnClickListener {

    private List<Repository> repositories = Collections.emptyList();
    private RepositoryListener listener;

    public RepositoriesAdapter(RepositoryListener listener) {
        setHasStableIds(true);
        this.listener = listener;
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        Repository repository = (Repository) v.getTag();
        listener.onRepositoryChosen(repository);
    }

    @Override
    public long getItemId(int position) {
        return repositories.get(position).getId();
    }

    @NonNull
    @Override
    public RepositoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(R.layout.item_repository, parent, false);
        return new RepositoryViewHolder(root, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoryViewHolder holder, int position) {
        Repository repository = repositories.get(position);
        holder.nameTextView.setText(repository.getName());
        holder.starsTextView.setText(String.valueOf(repository.getStars()));
        holder.itemView.setTag(repository);
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    static class RepositoryViewHolder extends RecyclerView.ViewHolder {
        private TextView starsTextView;
        private TextView nameTextView;

        public RepositoryViewHolder(@NonNull View itemView, View.OnClickListener listener) {
            super(itemView);
            starsTextView = itemView.findViewById(R.id.starsTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            itemView.setOnClickListener(listener);
        }
    }

    public interface RepositoryListener {
        void onRepositoryChosen(Repository repository);
    }
}
