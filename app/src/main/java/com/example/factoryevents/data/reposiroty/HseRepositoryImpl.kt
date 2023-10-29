package com.example.factoryevents.data.reposiroty

import android.app.Application
import com.example.factoryevents.data.mapper.DataMapper
import com.example.factoryevents.domain.entity.AuthState
import com.example.factoryevents.domain.entity.HSE
import com.example.factoryevents.domain.entity.OJT
import com.example.factoryevents.domain.repository.HseRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class HseRepositoryImpl @Inject constructor(
    private val mapper: DataMapper
): HseRepository {
    override fun getAuthStateFlow(): StateFlow<AuthState> {
        TODO("Not yet implemented")
    }

    override fun getHSEList(): StateFlow<List<HSE>> {
        TODO("Not yet implemented")
    }

    override fun getOJTList(): StateFlow<List<OJT>> {
        TODO("Not yet implemented")
    }

    override fun checkAuthState() {
        TODO("Not yet implemented")
    }
}