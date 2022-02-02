package ru.iliavolkov.worldcinema.repositiry

import retrofit2.Callback
import ru.iliavolkov.worldcinema.model.CoverDTO
import ru.iliavolkov.worldcinema.model.EpisodesDTO
import ru.iliavolkov.worldcinema.model.FilmInfoDTO
import ru.iliavolkov.worldcinema.model.TokenDTO
import ru.iliavolkov.worldcinema.room.App.Companion.retrofit

class RepositoryRemoteImpl : RepositoryRemote {

    override fun signUp(email: String, pass: String, firstName: String, lastName: String, callback: Callback<String>) {
        retrofit.signUp(email,pass,firstName,lastName).enqueue(callback)
    }

    override fun signIn(email: String, pass: String, callback: Callback<TokenDTO>) {
        retrofit.signIn(email,pass).enqueue(callback)
    }

    override fun getCover(callback: Callback<CoverDTO>) {
        retrofit.getCover().enqueue(callback)
    }

    override fun getMoviesList(filter: String, callback: Callback<List<FilmInfoDTO>>) {
        retrofit.getMoviesList(filter).enqueue(callback)
    }

    override fun getEpisodes(movieId: String, callback: Callback<List<EpisodesDTO>>) {
        retrofit.getEpisodes(movieId).enqueue(callback)
    }
}