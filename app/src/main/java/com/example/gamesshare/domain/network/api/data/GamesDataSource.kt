package com.example.gamesshare.domain.network.api.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gamesshare.domain.models.GamesModel
import com.example.gamesshare.domain.network.api.repository.ApiRepository
import javax.inject.Inject

class GamesDataSource(val apiRepository: ApiRepository) :
    PagingSource<Int, GamesModel>() {

    /**
     * METODO QUE SE ENCARGA DEL ESTADO DE LA PAGINACION
     */
    override fun getRefreshKey(state: PagingState<Int, GamesModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)

            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    /**
     * ESTE METODO SE ENCARGA DE TRAER LOS DATOS PAGINADOS DE CADA PAGINA MEDIANTE LLAMADA AL MICROSERVICIO
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GamesModel> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = apiRepository.getPagingGames(nextPageNumber, 18)
            val data = response.data ?: emptyList()
            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = if (data.isNotEmpty()) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}