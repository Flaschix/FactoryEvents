package com.example.factoryevents.domain.usecase

import com.example.factoryevents.domain.entity.Order
import com.example.factoryevents.domain.repository.HseRepository
import javax.inject.Inject

class CreateOrderUseCase @Inject constructor(private val repository: HseRepository){

    suspend operator fun invoke(order: Order) {
        return repository.createOrder(order)
    }
}