/*
 * Copyright (C) 2018 Marcus Pimenta
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.pimenta.bestv.data

import com.pimenta.bestv.common.presentation.model.WorkViewModel
import com.pimenta.bestv.data.local.MediaLocalRepository
import com.pimenta.bestv.data.local.entity.MovieDbModel
import com.pimenta.bestv.data.local.entity.TvShowDbModel
import com.pimenta.bestv.data.local.provider.RecommendationProvider
import com.pimenta.bestv.data.remote.MediaRemoteRepository
import com.pimenta.bestv.data.remote.entity.*
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by marcus on 05-03-2018.
 */
class MediaDataSourceImpl @Inject constructor(
        private val mediaLocalRepository: MediaLocalRepository,
        private val mediaRemoteRepository: MediaRemoteRepository,
        private val recommendationProvider: RecommendationProvider
) : MediaDataSource {

    override fun hasFavorite(): Single<Boolean> =
            mediaLocalRepository.hasFavorite()

    override fun isFavoriteMovie(movieId: Int): Single<Boolean> =
            mediaLocalRepository.isFavoriteMovie(movieId)

    override fun isFavoriteTvShow(tvShowId: Int): Single<Boolean> =
            mediaLocalRepository.isFavoriteTvShow(tvShowId)

    override fun saveFavoriteMovie(movieDbModel: MovieDbModel): Completable =
            mediaLocalRepository.saveFavoriteMovie(movieDbModel)

    override fun saveFavoriteTvShow(tvShowDbModel: TvShowDbModel): Completable =
            mediaLocalRepository.saveFavoriteTvShow(tvShowDbModel)

    override fun deleteFavoriteMovie(movieDbModel: MovieDbModel): Completable =
            mediaLocalRepository.deleteFavoriteMovie(movieDbModel)

    override fun deleteFavoriteTvShow(tvShowDbModel: TvShowDbModel): Completable =
            mediaLocalRepository.deleteFavoriteTvShow(tvShowDbModel)

    override fun getFavoriteMovies(): Single<List<MovieDbModel>> =
            mediaLocalRepository.getMovies()

    override fun getFavoriteTvShows(): Single<List<TvShowDbModel>> =
            mediaLocalRepository.getTvShows()

    override fun getMovie(movieId: Int): MovieResponse? =
            mediaRemoteRepository.getMovie(movieId)

    override fun getTvShow(tvId: Int): TvShowResponse? =
            mediaRemoteRepository.getTvShow(tvId)

    override fun loadWorkByType(page: Int, movieListType: MediaDataSource.WorkType): Single<out WorkPageResponse<*>> =
            when (movieListType) {
                MediaDataSource.WorkType.NOW_PLAYING_MOVIES -> mediaRemoteRepository.getNowPlayingMovies(page)
                MediaDataSource.WorkType.POPULAR_MOVIES -> mediaRemoteRepository.getPopularMovies(page)
                MediaDataSource.WorkType.TOP_RATED_MOVIES -> mediaRemoteRepository.getTopRatedMovies(page)
                MediaDataSource.WorkType.UP_COMING_MOVIES -> mediaRemoteRepository.getUpComingMovies(page)
                MediaDataSource.WorkType.AIRING_TODAY_TV_SHOWS -> mediaRemoteRepository.getAiringTodayTvShows(page)
                MediaDataSource.WorkType.ON_THE_AIR_TV_SHOWS -> mediaRemoteRepository.getOnTheAirTvShows(page)
                MediaDataSource.WorkType.POPULAR_TV_SHOWS -> mediaRemoteRepository.getPopularTvShows(page)
                MediaDataSource.WorkType.TOP_RATED_TV_SHOWS -> mediaRemoteRepository.getTopRatedTvShows(page)
                else -> Single.error(Throwable())
            }

    override fun getMovieGenres(): Single<MovieGenreListResponse> =
            mediaRemoteRepository.getMovieGenres()

    override fun getMovieByGenre(genreId: Int, page: Int): Single<MoviePageResponse> =
            mediaRemoteRepository.getMoviesByGenre(genreId, page)

    override fun getTvShowByGenre(genreId: Int, page: Int): Single<TvShowPageResponse> =
            mediaRemoteRepository.getTvShowByGenre(genreId, page)

    override fun getCastByMovie(workId: Int): Single<CastListResponse> =
            mediaRemoteRepository.getCastByMovie(workId)

    override fun getCastByTvShow(workId: Int): Single<CastListResponse> =
            mediaRemoteRepository.getCastByTvShow(workId)

    override fun getRecommendationByMovie(workId: Int, page: Int): Single<MoviePageResponse> =
            mediaRemoteRepository.getRecommendationByMovie(workId, page)

    override fun getRecommendationByTvShow(workId: Int, page: Int): Single<TvShowPageResponse> =
            mediaRemoteRepository.getRecommendationByTvShow(workId, page)

    override fun getSimilarByMovie(workId: Int, page: Int): Single<MoviePageResponse> =
            mediaRemoteRepository.getSimilarByMovie(workId, page)

    override fun getSimilarByTvShow(workId: Int, page: Int): Single<TvShowPageResponse> =
            mediaRemoteRepository.getSimilarByTvShow(workId, page)

    override fun getVideosByMovie(workId: Int): Single<VideoListResponse> =
            mediaRemoteRepository.getVideosByMovie(workId)

    override fun getVideosByTvShow(workId: Int): Single<VideoListResponse> =
            mediaRemoteRepository.getVideosByTvShow(workId)

    override fun searchMoviesByQuery(query: String, page: Int): Single<MoviePageResponse> =
            mediaRemoteRepository.searchMoviesByQuery(query, page)

    override fun searchTvShowsByQuery(query: String, page: Int): Single<TvShowPageResponse> =
            mediaRemoteRepository.searchTvShowsByQuery(query, page)

    override fun getCastDetails(castId: Int): Single<CastResponse> =
            mediaRemoteRepository.getCastDetails(castId)

    override fun getMovieCreditsByCast(castId: Int): Single<CastMovieListResponse> =
            mediaRemoteRepository.getMovieCreditsByCast(castId)

    override fun getTvShowCreditsByCast(castId: Int): Single<CastTvShowListResponse> =
            mediaRemoteRepository.getTvShowCreditsByCast(castId)

    override fun getTvShowGenres(): Single<TvShowGenreListResponse> =
            mediaRemoteRepository.getTvShowGenres()

    override fun loadRecommendations(works: List<WorkViewModel>?): Completable =
            recommendationProvider.loadRecommendations(works)
}