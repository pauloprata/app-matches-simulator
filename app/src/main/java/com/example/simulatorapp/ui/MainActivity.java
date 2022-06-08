package com.example.simulatorapp.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simulatorapp.R;
import com.example.simulatorapp.data.MatchesApi;
import com.example.simulatorapp.databinding.ActivityMainBinding;
import com.example.simulatorapp.domain.Match;
import com.example.simulatorapp.ui.adapter.MatchesAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MatchesApi matchesAPi;
    private MatchesAdapter matchesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupHttpClient();
        setupMatchesList();
        setupMatchesRefresh();
        setupFloatingButton();
    }

    private void setupHttpClient() {
        //Configurando nossa Api
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pauloprata.github.io/api-matches-simulator/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        matchesAPi = retrofit.create(MatchesApi.class);
    }

    private void setupFloatingButton() {
       binding.btnSimulator.setOnClickListener(view -> {
           view.animate().rotationBy(360).setDuration(500).setListener(new AnimatorListenerAdapter() {
               @Override
               public void onAnimationEnd(Animator animation) {
                   //Implementar Algoritmo de simulação de partida
                   Random random = new Random();
                   for (int i = 0; i < matchesAdapter.getItemCount(); i++){
                       Match match = matchesAdapter.getMatches().get(i);
                       match.getHomeTeam().setScore(random.nextInt(match.getHomeTeam().getStars() + 1));
                       match.getAwayTeam().setScore(random.nextInt(match.getAwayTeam().getStars() + 1));
                       matchesAdapter.notifyItemChanged(i);
                   }
                   //super.onAnimationEnd(animation);
               }
           });
       });
    }
    private void setupMatchesRefresh() {
        binding.srlMatches.setOnRefreshListener(this::findaMathesFromApi);
    }

    private void setupMatchesList() {
        binding.rvListMatches.setHasFixedSize(true);
        binding.rvListMatches.setLayoutManager(new LinearLayoutManager(this));
        findaMathesFromApi();

    }
    private void findaMathesFromApi() {
        binding.srlMatches.setRefreshing(true);
        matchesAPi.getMatches().enqueue(new Callback<List<Match>>() {
            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                if(response.isSuccessful()){
                    List<Match> matches = response.body();
                    matchesAdapter = new MatchesAdapter(matches);
                    binding.rvListMatches.setAdapter(matchesAdapter);
                }else{
                    showErrorMessage();
                }
                binding.srlMatches.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                 showErrorMessage();
                binding.srlMatches.setRefreshing(false);
            }


        });
    }

    private void showErrorMessage() {
        Snackbar.make(binding.btnSimulator, R.string.error_api, Snackbar.LENGTH_LONG).show();
    }


}
