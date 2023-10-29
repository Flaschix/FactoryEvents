package com.example.factoryevents.domain.usecase

import com.example.factoryevents.domain.repository.HseRepository
import javax.inject.Inject

class CheckAuthStateUseCase @Inject constructor(private val repository: HseRepository){

    suspend operator fun invoke() {
        return repository.checkAuthState()
    }
}