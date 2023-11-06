package com.example.factoryevents.data.mapper

import com.example.factoryevents.domain.entity.AccessType
import com.example.factoryevents.domain.entity.HSE
import com.example.factoryevents.domain.entity.OJT
import com.example.factoryevents.domain.entity.User
import com.example.factoryevents.domain.entity.WorkerHSE
import kotlinx.coroutines.delay
import javax.inject.Inject

class DataMapper @Inject constructor() {

    fun mapResponseToHSE(): List<WorkerHSE>{
        val list: List<WorkerHSE> = arrayListOf(
            WorkerHSE(
                3,
                "Kol",
                "SDF",
                arrayListOf(
                    HSE("Covid","2","3","5","5.02")
                )
            ),
            WorkerHSE(
                4,
                "Dol",
                "SDF",
                arrayListOf(
                    HSE("Covid","2","3","5","5.02")
                )
            )
            ,WorkerHSE(
                5,
                "Mol",
                "SDF",
                arrayListOf(
                    HSE("Covid","2","3","5","5.02")
                )
            )
        )

        return list
    }

    fun mapResponseToOJT(): List<OJT>{
        Thread.sleep(3000)
        val list: List<OJT> = arrayListOf(
            OJT(
                1,
                "fsef", "sefs", "fsef", "fsef", "gerg",
                "wadaw", "gerg", "dwa", "fefs",
                "gegerg", false
            ),
            OJT(
                2,
                "fsef", "sefs", "fsef", "fsef", "gerg",
                "gergerge", "gerg", "dwa", "fefs",
                "gegerg", false
            )

        )

        return list
    }

    fun mapResponseToUser(): User{
        Thread.sleep(3000)
        return User("awd@efes", AccessType.L3, "gre","ad")
    }
}