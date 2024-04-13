package com.example.factoryevents.domain.usecase

import com.example.factoryevents.domain.entity.WorkerHSE
import com.example.factoryevents.domain.repository.HseRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class RefreshHSEListUseCase @Inject constructor(private val repository: HseRepository) {

    suspend operator fun invoke(week: Int) {
        return repository.refreshHSEList(week)
    }
}