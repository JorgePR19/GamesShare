package com.example.gamesshare.domain.network.api.repository

import com.example.gamesshare.domain.models.GameMovieModel
import com.example.gamesshare.domain.models.GamesModel
import com.example.gamesshare.domain.models.SimpleGatApiGamesModel
import com.example.gamesshare.domain.network.ResponseStatus
import com.example.gamesshare.domain.network.api.core.ApiContract
import com.example.gamesshare.domain.network.api.data.response.SimpleGatApiGamesResponse
import com.example.gamesshare.domain.network.api.mapper.GamesDTOMapper
import com.example.gamesshare.domain.network.api.mapper.MoviesDTOMapper
import com.example.gamesshare.domain.network.api.mapper.SimpleGameMapper
import com.example.gamesshare.domain.network.makeNetWorkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject


interface ApiTask {
    suspend fun getPagingGames(
        page: Int, size: Int
    ): ResponseStatus<List<GamesModel>>

    suspend fun getMovieOfGame(id:String):ResponseStatus<List<GameMovieModel>>

    suspend fun getGameById(id:Int):ResponseStatus<SimpleGatApiGamesModel>

}

class ApiRepository @Inject constructor(private val apiContract: ApiContract) : ApiTask {

    override suspend fun getPagingGames(page: Int, size: Int): ResponseStatus<List<GamesModel>> {
        return withContext(Dispatchers.IO) {
            val getGamesDeferred = async { getGames(page, size) }
            val gamesResponse = getGamesDeferred.await()
            delay(1000)
            gamesResponse
        }
    }

    private suspend fun getGames(
        page: Int,
        size: Int
    ): ResponseStatus<List<GamesModel>> = makeNetWorkCall {
        val response = apiContract.getGamesPaging(page,size)
        val gamesDto = response.results
        val mapper = GamesDTOMapper()
        mapper.fromDtoListToDomainList(gamesDto)
    }

    override suspend fun getMovieOfGame(id: String): ResponseStatus<List<GameMovieModel>> {
        return withContext(Dispatchers.IO){
            val getMovieDeferred = async { getMovies(id) }
            val response = getMovieDeferred.await()
            response
        }
    }


    private suspend fun getMovies(
        id: String
    ): ResponseStatus<List<GameMovieModel>> = makeNetWorkCall {
        val response = apiContract.getMovieOfGame(id)
        val movieDto = response.results
        val mapper = MoviesDTOMapper()
        mapper.fromDtoListToDomainList(movieDto)
    }


    override suspend fun getGameById(id: Int): ResponseStatus<SimpleGatApiGamesModel> {
        return withContext(Dispatchers.IO){
            val getMovieDeferred = async { getGameId(id) }
            val response = getMovieDeferred.await()
            response
        }
    }

    private suspend fun getGameId(
        id: Int
    ): ResponseStatus<SimpleGatApiGamesModel> = makeNetWorkCall {
        val response = apiContract.getGameById(id)
        val movieDto = response.body()
        val mapper = SimpleGameMapper()
        mapper.fromDtoToDomain(movieDto!!)
    }
}