package com.example.factoryevents.data.mapper

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.factoryevents.domain.entity.AccessType
import com.example.factoryevents.domain.entity.HSE
import com.example.factoryevents.domain.entity.OJT
import com.example.factoryevents.domain.entity.User
import com.example.factoryevents.domain.entity.WorkerHSE
import kotlinx.coroutines.delay
import okhttp3.internal.wait
import org.json.JSONArray
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class DataMapper @Inject constructor(
    private val context: Context
) {

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

    suspend fun mapResponseToOJT(user: User): List<OJT> = suspendCoroutine { continuation ->
        val action = "getUser"
        val nameTest = "Ашихмин"
        var url = "https://script.google.com/macros/s/AKfycbz1ffBCkW4mtoOzk0SgjYDQsZSWiz2DYdQV1p8gZQB6OQWwGNktfyPA5ec6N43znant/exec?"

        url += "action=$action&user=$nameTest"

        val request: JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

//                val userJson: JSONArray = response.getJSONArray("User")
                val list: List<OJT> = arrayListOf(
                    OJT(
                        1,
                        "Пожарка", "40", "Корпус 2, Здание 1", "Сделать то твцф фзцвпщ укт ктуцзптщ ущ укзп цщутп щцктпщтцтп кщтуцщшпкщт щшцукпщшкуцщз тцукпщшкщтцуштпщ цуктпщтк ущшптцщукшптщцшук", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhC5mIuy2KaMll2PVI4iE_18w4c7zDCcSi0PzWijCWeakJEJRGhAnIaSkYGLK7dEYlTwYnqTzwOJJcRxBrNFrZMsRovChY8CVpVIXH5pNPPj5wo1kPfGeth4z690xixWqd69vceT1yMaxGB4nDNXUnQ-kuJm3yHgqJreLneAV0nWp4lsF-BPFX0CgM8Tw/w1200-h630-p-k-no-nu/card%20view.jpg",
                        "wadaw", "Иванов И И", "dwa", "Симомненко М М",
                        "12.05.23", false
                    ),
                    OJT(
                        2,
                        "Пожарка", "40", "Корпус 2, Здание 1", "Сделать то твцф фзцвпщ укт ктуцзптщ ущ укзп цщутп щцктпщтцтп кщтуцщшпкщт щшцукпщшкуцщз тцукпщшкщтцуштпщ цуктпщтк ущшптцщукшптщцшук", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhC5mIuy2KaMll2PVI4iE_18w4c7zDCcSi0PzWijCWeakJEJRGhAnIaSkYGLK7dEYlTwYnqTzwOJJcRxBrNFrZMsRovChY8CVpVIXH5pNPPj5wo1kPfGeth4z690xixWqd69vceT1yMaxGB4nDNXUnQ-kuJm3yHgqJreLneAV0nWp4lsF-BPFX0CgM8Tw/w1200-h630-p-k-no-nu/card%20view.jpg",
                        "wadaw", "Иванов И И", "dwa", "Симомненко М М",
                        "12.05.23", true
                    ),
                    OJT(
                        3,
                        "Пожарка", "40", "Корпус 2, Здание 1", "Сделать то твцф фзцвпщ укт ктуцзптщ ущ укзп цщутп щцктпщтцтп кщтуцщшпкщт щшцукпщшкуцщз тцукпщшкщтцуштпщ цуктпщтк ущшптцщукшптщцшук", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhC5mIuy2KaMll2PVI4iE_18w4c7zDCcSi0PzWijCWeakJEJRGhAnIaSkYGLK7dEYlTwYnqTzwOJJcRxBrNFrZMsRovChY8CVpVIXH5pNPPj5wo1kPfGeth4z690xixWqd69vceT1yMaxGB4nDNXUnQ-kuJm3yHgqJreLneAV0nWp4lsF-BPFX0CgM8Tw/w1200-h630-p-k-no-nu/card%20view.jpg",
                        "wadaw", "Иванов И И", "dwa", "Симомненко М М",
                        "12.05.23", false
                    ),
                )

                continuation.resume(list)
            },
            { error ->
                // Handle error
                continuation.resumeWithException(error)
            }
        )

        val queue: RequestQueue = Volley.newRequestQueue(context)
        queue.add(request)
    }

    suspend fun mapResponseToUser(name: String): User = suspendCoroutine { continuation ->
        val action = "getUser"
        val nameTest = "Ашихмин"
        var url = "https://script.google.com/macros/s/AKfycbz1ffBCkW4mtoOzk0SgjYDQsZSWiz2DYdQV1p8gZQB6OQWwGNktfyPA5ec6N43znant/exec?"

        url += "action=$action&user=$nameTest"

        val request: JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val userJson: JSONArray = response.getJSONArray("User")
                val user = User("awd@efes", AccessType.L3, userJson[2].toString(), userJson[2].toString())
                continuation.resume(user)
            },
            { error ->
                // Handle error
                continuation.resumeWithException(error)
            }
        )

        val queue: RequestQueue = Volley.newRequestQueue(context)
        queue.add(request)
    }
}

//val list: List<OJT> = arrayListOf(
//    OJT(
//        1,
//        "Пожарка", "40", "Корпус 2, Здание 1", "Сделать то твцф фзцвпщ укт ктуцзптщ ущ укзп цщутп щцктпщтцтп кщтуцщшпкщт щшцукпщшкуцщз тцукпщшкщтцуштпщ цуктпщтк ущшптцщукшптщцшук", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhC5mIuy2KaMll2PVI4iE_18w4c7zDCcSi0PzWijCWeakJEJRGhAnIaSkYGLK7dEYlTwYnqTzwOJJcRxBrNFrZMsRovChY8CVpVIXH5pNPPj5wo1kPfGeth4z690xixWqd69vceT1yMaxGB4nDNXUnQ-kuJm3yHgqJreLneAV0nWp4lsF-BPFX0CgM8Tw/w1200-h630-p-k-no-nu/card%20view.jpg",
//        "wadaw", "Иванов И И", "dwa", "Симомненко М М",
//        "12.05.23", false
//    ),
//    OJT(
//        2,
//        "Пожарка", "40", "Корпус 4, Здание 1", "Сделать то твцф фзцвпщ укт ктуцзптщ ущ укзп цщутп щцктпщтцтп кщтуцщшпкщт щшцукпщшкуцщз тцукпщшкщтцуштпщ цуктпщтк ущшптцщукшптщцшук", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhC5mIuy2KaMll2PVI4iE_18w4c7zDCcSi0PzWijCWeakJEJRGhAnIaSkYGLK7dEYlTwYnqTzwOJJcRxBrNFrZMsRovChY8CVpVIXH5pNPPj5wo1kPfGeth4z690xixWqd69vceT1yMaxGB4nDNXUnQ-kuJm3yHgqJreLneAV0nWp4lsF-BPFX0CgM8Tw/w1200-h630-p-k-no-nu/card%20view.jpg",
//        "wadaw", "Иванов И И", "dwa", "Рубцов К А",
//        "12.05.23", true
//    ),
//    OJT(
//        3,
//        "Пожарка", "40", "Корпус 4, Здание 1", "fsef", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhC5mIuy2KaMll2PVI4iE_18w4c7zDCcSi0PzWijCWeakJEJRGhAnIaSkYGLK7dEYlTwYnqTzwOJJcRxBrNFrZMsRovChY8CVpVIXH5pNPPj5wo1kPfGeth4z690xixWqd69vceT1yMaxGB4nDNXUnQ-kuJm3yHgqJreLneAV0nWp4lsF-BPFX0CgM8Tw/w1200-h630-p-k-no-nu/card%20view.jpg",
//        "wadaw", "Иванов И И", "dwa", "Рубцов К А",
//        "12.05.23", true
//    ),
//    OJT(
//        4,
//        "Пожарка", "40", "Корпус 4, Здание 1", "fsef", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhC5mIuy2KaMll2PVI4iE_18w4c7zDCcSi0PzWijCWeakJEJRGhAnIaSkYGLK7dEYlTwYnqTzwOJJcRxBrNFrZMsRovChY8CVpVIXH5pNPPj5wo1kPfGeth4z690xixWqd69vceT1yMaxGB4nDNXUnQ-kuJm3yHgqJreLneAV0nWp4lsF-BPFX0CgM8Tw/w1200-h630-p-k-no-nu/card%20view.jpg",
//        "wadaw", "Иванов И И", "dwa", "Рубцов К А",
//        "12.05.23", false
//    ),
//    OJT(
//        5,
//        "Пожарка", "40", "Корпус 4, Здание 1", "fsef", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhC5mIuy2KaMll2PVI4iE_18w4c7zDCcSi0PzWijCWeakJEJRGhAnIaSkYGLK7dEYlTwYnqTzwOJJcRxBrNFrZMsRovChY8CVpVIXH5pNPPj5wo1kPfGeth4z690xixWqd69vceT1yMaxGB4nDNXUnQ-kuJm3yHgqJreLneAV0nWp4lsF-BPFX0CgM8Tw/w1200-h630-p-k-no-nu/card%20view.jpg",
//        "wadaw", "Иванов И И", "dwa", "Рубцов К А",
//        "12.05.23", true
//    ),
//    OJT(
//        6,
//        "Пожарка", "40", "Корпус 4, Здание 1", "fsef", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhC5mIuy2KaMll2PVI4iE_18w4c7zDCcSi0PzWijCWeakJEJRGhAnIaSkYGLK7dEYlTwYnqTzwOJJcRxBrNFrZMsRovChY8CVpVIXH5pNPPj5wo1kPfGeth4z690xixWqd69vceT1yMaxGB4nDNXUnQ-kuJm3yHgqJreLneAV0nWp4lsF-BPFX0CgM8Tw/w1200-h630-p-k-no-nu/card%20view.jpg",
//        "wadaw", "Иванов И И", "dwa", "Рубцов К А",
//        "12.05.23", false
//    ),
//    OJT(
//        7,
//        "Пожарка", "40", "Корпус 4, Здание 1", "fsef", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhC5mIuy2KaMll2PVI4iE_18w4c7zDCcSi0PzWijCWeakJEJRGhAnIaSkYGLK7dEYlTwYnqTzwOJJcRxBrNFrZMsRovChY8CVpVIXH5pNPPj5wo1kPfGeth4z690xixWqd69vceT1yMaxGB4nDNXUnQ-kuJm3yHgqJreLneAV0nWp4lsF-BPFX0CgM8Tw/w1200-h630-p-k-no-nu/card%20view.jpg",
//        "wadaw", "Иванов И И", "dwa", "Рубцов К А",
//        "12.05.23", true
//    ),
//    OJT(
//        8,
//        "Пожарка", "40", "Корпус 4, Здание 1", "Сделать то твцф фзцвпщ укт ктуцзптщ ущ укзп цщутп щцктпщтцтп кщтуцщшпкщт щшцукпщшкуцщз тцукпщшкщтцуштпщ цуктпщтк ущшптцщукшптщцшук", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhC5mIuy2KaMll2PVI4iE_18w4c7zDCcSi0PzWijCWeakJEJRGhAnIaSkYGLK7dEYlTwYnqTzwOJJcRxBrNFrZMsRovChY8CVpVIXH5pNPPj5wo1kPfGeth4z690xixWqd69vceT1yMaxGB4nDNXUnQ-kuJm3yHgqJreLneAV0nWp4lsF-BPFX0CgM8Tw/w1200-h630-p-k-no-nu/card%20view.jpg",
//        "wadaw", "Иванов И И", "dwa", "Рубцов К А",
//        "12.05.23", true
//    ),
//    OJT(
//        9,
//        "Пожарка", "40", "Корпус 4, Здание 1", "fsef", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhC5mIuy2KaMll2PVI4iE_18w4c7zDCcSi0PzWijCWeakJEJRGhAnIaSkYGLK7dEYlTwYnqTzwOJJcRxBrNFrZMsRovChY8CVpVIXH5pNPPj5wo1kPfGeth4z690xixWqd69vceT1yMaxGB4nDNXUnQ-kuJm3yHgqJreLneAV0nWp4lsF-BPFX0CgM8Tw/w1200-h630-p-k-no-nu/card%20view.jpg",
//        "wadaw", "Иванов И И", "dwa", "Рубцов К А",
//        "12.05.23", false
//    )
//
//)
//
//return list