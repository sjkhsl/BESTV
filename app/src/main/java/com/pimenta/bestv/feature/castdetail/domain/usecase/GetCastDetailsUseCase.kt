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

package com.pimenta.bestv.feature.castdetail.domain.usecase

import com.pimenta.bestv.common.domain.model.CastDomainModel
import com.pimenta.bestv.common.domain.model.WorkDomainModel
import io.reactivex.Single
import io.reactivex.functions.Function3
import javax.inject.Inject

/**
 * Created by marcus on 15-04-2019.
 */
class GetCastDetailsUseCase @Inject constructor(
        private val getCastPersonalDetails: GetCastPersonalDetails,
        private val getMovieCreditsByCastUseCase: GetMovieCreditsByCastUseCase,
        private val getTvShowCreditsByCastUseCase: GetTvShowCreditsByCastUseCase
) {

    operator fun invoke(castId: Int): Single<Triple<CastDomainModel, List<WorkDomainModel>?, List<WorkDomainModel>?>> =
            Single.zip(
                    getCastPersonalDetails(castId),
                    getMovieCreditsByCastUseCase(castId),
                    getTvShowCreditsByCastUseCase(castId),
                    Function3 { castViewModel, castMovieList, castTvShowList ->
                        Triple(castViewModel, castMovieList, castTvShowList)
                    }
            )
}