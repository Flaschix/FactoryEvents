package com.example.factoryevents.domain.usecase

import com.example.factoryevents.domain.entity.HSE
import com.example.factoryevents.domain.repository.HseRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetHSEListUseCase @Inject constructor(private val repository: HseRepository){

    operator fun invoke(): StateFlow<List<HSE>>{
        return repository.getHSEList()
    }
}