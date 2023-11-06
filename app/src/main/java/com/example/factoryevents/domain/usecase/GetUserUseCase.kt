package com.example.factoryevents.domain.usecase

import com.example.factoryevents.domain.entity.User
import com.example.factoryevents.domain.entity.WorkerHSE
import com.example.factoryevents.domain.repository.HseRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: HseRepository){

    operator fun invoke(): StateFlow<User> {
        return repository.getUser()
    }
}