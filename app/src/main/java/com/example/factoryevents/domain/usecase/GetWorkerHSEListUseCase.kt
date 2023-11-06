package com.example.factoryevents.domain.usecase

import com.example.factoryevents.domain.entity.WorkerHSE
import com.example.factoryevents.domain.repository.HseRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetWorkerHSEListUseCase @Inject constructor(private val repository: HseRepository){

    operator fun invoke(): StateFlow<List<WorkerHSE>>{
        return repository.getWorkerHSEList()
    }
}