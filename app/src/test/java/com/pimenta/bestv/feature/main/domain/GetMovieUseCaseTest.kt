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

package com.pimenta.bestv.feature.main.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.pimenta.bestv.common.presentation.model.WorkType
import com.pimenta.bestv.common.presentation.model.WorkViewModel
import com.pimenta.bestv.data.MediaDataSource
import com.pimenta.bestv.data.remote.entity.MovieResponse
import org.junit.Assert
import org.junit.Test

/**
 * Created by marcus on 2019-10-21.
 */
private const val MOVIE_ID = 1
private val MOVIE_RESPONSE = MovieResponse(
        id = MOVIE_ID,
        title = "Batman",
        originalTitle = "Batman"
)
private val MOVIE_VIEW_MODEL = WorkViewModel(
        id = 1,
        title = "Batman",
        originalTitle = "Batman",
        type = WorkType.MOVIE
)

class GetMovieUseCaseTest {

    private val mediaDataSource: MediaDataSource = mock()

    private val useCase = GetMovieUseCase(
            mediaDataSource
    )

    @Test
    fun `should return the right data when loading a movie`() {
        whenever(mediaDataSource.getMovie(MOVIE_ID)).thenReturn(MOVIE_RESPONSE)

        val tvShowResponse = useCase(MOVIE_ID)

        Assert.assertEquals(tvShowResponse, MOVIE_VIEW_MODEL)
    }

    @Test
    fun `should return null when no tv show is found`() {
        whenever(mediaDataSource.getMovie(MOVIE_ID)).thenReturn(null)

        val tvShowResponse = useCase(MOVIE_ID)

        Assert.assertEquals(tvShowResponse, null)
    }
}