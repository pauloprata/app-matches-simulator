package com.example.simulatorapp.data;

import com.example.simulatorapp.domain.Match;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

//Chamando a nossa api
public interface MatchesApi {
    @GET("matches.json")
    Call<List<Match>> getMatches();
}
