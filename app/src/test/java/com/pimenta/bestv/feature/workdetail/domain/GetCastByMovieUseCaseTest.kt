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

package com.pimenta.bestv.feature.workdetail.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.pimenta.bestv.common.presentation.model.CastViewModel
import com.pimenta.bestv.common.data.model.remote.CastListResponse
import com.pimenta.bestv.common.data.model.remote.CastResponse
import io.reactivex.Single
import org.junit.Test

/**
 * Created by marcus on 21-10-2019.
 */
private const val WORK_ID = 1
private val CAST_LIST = CastListResponse(
        id = 1,
        casts = listOf(
                CastResponse(
                        id = 1,
                        name = "Name",
                        character = "Character",
                        birthday = "Birthday",
                        deathDay = null,
                        biography = null
                )
        )
)
private val CAST_VIEW_MODELS = listOf(
        CastViewModel(
                id = 1,
                name = "Name",
                character = "Character",
                birthday = "Birthday",
                deathDay = null,
                biography = null
        )
)

class GetCastByMovieUseCaseTest {

    private val mediaRepository: MediaRepository = mock()

    private val useCase = GetCastByMovieUseCase(
            mediaRepository
    )

    @Test
    fun `should return the right data when loading the casts by movie`() {
        whenever(mediaRepository.getCastByMovie(WORK_ID)).thenReturn(Single.just(CAST_LIST))

        useCase(WORK_ID)
                .test()
                .assertComplete()
                .assertResult(CAST_VIEW_MODELS)
    }

    @Test
    fun `should return an error when some exception happens when loading the casts by movie`() {
        whenever(mediaRepository.getCastByMovie(WORK_ID)).thenReturn(Single.error(Throwable()))

        useCase(WORK_ID)
                .test()
                .assertError(Throwable::class.java)
    }
}