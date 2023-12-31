package com.example.factoryevents.domain.usecase

import com.example.factoryevents.domain.entity.OJT
import com.example.factoryevents.domain.repository.HseRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetOJTListUseCase @Inject constructor(private val repository: HseRepository) {

    operator fun invoke(): StateFlow<List<OJT>> {
        return repository.getOJTList()
    }
}