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
        Thread.sleep(2000)
        val list: List<OJT> = arrayListOf(
            OJT(
                1,
                "Пожарка", "40", "Корпус 2, Здание 1", "fsef", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhC5mIuy2KaMll2PVI4iE_18w4c7zDCcSi0PzWijCWeakJEJRGhAnIaSkYGLK7dEYlTwYnqTzwOJJcRxBrNFrZMsRovChY8CVpVIXH5pNPPj5wo1kPfGeth4z690xixWqd69vceT1yMaxGB4nDNXUnQ-kuJm3yHgqJreLneAV0nWp4lsF-BPFX0CgM8Tw/w1200-h630-p-k-no-nu/card%20view.jpg",
                "wadaw", "Иванов И И", "dwa", "Симомненко М М",
                "12.05.23", false
            ),
            OJT(
                2,
                "Пожарка", "40", "Корпус 4, Здание 1", "fsef", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhC5mIuy2KaMll2PVI4iE_18w4c7zDCcSi0PzWijCWeakJEJRGhAnIaSkYGLK7dEYlTwYnqTzwOJJcRxBrNFrZMsRovChY8CVpVIXH5pNPPj5wo1kPfGeth4z690xixWqd69vceT1yMaxGB4nDNXUnQ-kuJm3yHgqJreLneAV0nWp4lsF-BPFX0CgM8Tw/w1200-h630-p-k-no-nu/card%20view.jpg",
                "wadaw", "Иванов И И", "dwa", "Рубцов К А",
                "12.05.23", true
            )

        )

        return list
    }

    fun mapResponseToUser(): User{
        Thread.sleep(2000)
        return User("awd@efes", AccessType.L3, "gre","ad")
    }
}