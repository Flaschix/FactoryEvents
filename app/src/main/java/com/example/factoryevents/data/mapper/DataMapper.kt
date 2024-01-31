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
                "Aleksandr",
                "LFD",
                arrayListOf(
                    HSE("Covid","2","3","5","5.02"),
                    HSE("SAF","2","3","5","5.02"),
                    HSE("SGR","2","3","5","5.02")
                )
            ),
            WorkerHSE(
                4,
                "Sofia Dolce",
                "SDF",
                arrayListOf(
                    HSE("Covid","2","3","5","5.02"),
                    HSE("SAF","2","3","5","5.02"),
                    HSE("SGR","2","3","5","5.02")
                )
            )
            ,WorkerHSE(
                5,
                "Smorodinov",
                "REG",
                arrayListOf(
                    HSE("Covid","2","3","5","5.02"),
                    HSE("SAF","2","3","5","5.02"),
                    HSE("SGR","2","3","5","7.2")
                )
            )
        )

        return list
    }

    suspend fun mapResponseToOJT(user: User): List<OJT> = suspendCoroutine { continuation ->
        val action = "getOJT"
        var url = APP_SCRIPT_URL
        val rank = "L2"
        val func = "PTS PLANT"
        url += "action=$action&rank=$rank&func=$func"

        val request: JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                val list = mutableListOf<OJT>()
                val userJsonArray: JSONArray = response.getJSONArray("List")

                for (i in 0 until userJsonArray.length()) {
                    val rowArray: JSONArray = userJsonArray.getJSONArray(i)

                    list.add(
                        OJT(
                            id = i,
                            type = rowArray.optString(0, ""),
                            week = rowArray.optString(1, ""),
                            place = rowArray.optString(2, ""),
                            offence = rowArray.optString(3, ""),
                            img = rowArray.optString(4, ""),
                            byWhomOpened = rowArray.optString(5, ""),
                            areResponsible = rowArray.optString(6, ""),
                            options = rowArray.optString(7, ""),
                            pilot = rowArray.optString(8, ""),
                            dueDate = rowArray.optString(9, ""),
                            status = rowArray.optBoolean(10)
                        )
                    )
                }

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
        val nameTest = "anastasia oleynikova"
        var url = APP_SCRIPT_URL

//        url += "action=$action&user=${name.replace('.', ' ')}"
        url += "action=$action&user=$nameTest"

        val request: JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val userJson: JSONArray = response.getJSONArray("User")
                val user = User("awd@efes", AccessType.L3, userJson[2].toString(), userJson[2].toString(), userJson[1].toString())
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


    companion object{
        private const val APP_SCRIPT_URL = "https://script.google.com/macros/s/AKfycbyEEhpl9AlyBI6m37qxY7XQ86e-Auf6b9Cr3SL4QKmtrYQ14A-lRwMe7R6eHDwudDGX/exec?"
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

//                val list: List<OJT> = arrayListOf(
//                    OJT(
//                        1,
//                        "Пожарка", "40", "Корпус 2, Здание 1", "Сделать то твцф фзцвпщ укт ктуцзптщ ущ укзп цщутп щцктпщтцтп кщтуцщшпкщт щшцукпщшкуцщз тцукпщшкщтцуштпщ цуктпщтк ущшптцщукшптщцшук", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhC5mIuy2KaMll2PVI4iE_18w4c7zDCcSi0PzWijCWeakJEJRGhAnIaSkYGLK7dEYlTwYnqTzwOJJcRxBrNFrZMsRovChY8CVpVIXH5pNPPj5wo1kPfGeth4z690xixWqd69vceT1yMaxGB4nDNXUnQ-kuJm3yHgqJreLneAV0nWp4lsF-BPFX0CgM8Tw/w1200-h630-p-k-no-nu/card%20view.jpg",
//                        "wadaw", "Иванов И И", "dwa", "Симомненко М М",
//                        "12.05.23", false
//                    ),
//                    OJT(
//                        2,
//                        "Пожарка", "40", "Корпус 2, Здание 1", "Сделать то твцф фзцвпщ укт ктуцзптщ ущ укзп цщутп щцктпщтцтп кщтуцщшпкщт щшцукпщшкуцщз тцукпщшкщтцуштпщ цуктпщтк ущшптцщукшптщцшук", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhC5mIuy2KaMll2PVI4iE_18w4c7zDCcSi0PzWijCWeakJEJRGhAnIaSkYGLK7dEYlTwYnqTzwOJJcRxBrNFrZMsRovChY8CVpVIXH5pNPPj5wo1kPfGeth4z690xixWqd69vceT1yMaxGB4nDNXUnQ-kuJm3yHgqJreLneAV0nWp4lsF-BPFX0CgM8Tw/w1200-h630-p-k-no-nu/card%20view.jpg",
//                        "wadaw", "Иванов И И", "dwa", "Симомненко М М",
//                        "12.05.23", true
//                    ),
//                    OJT(
//                        3,
//                        "Пожарка", "40", "Корпус 2, Здание 1", "Сделать то твцф фзцвпщ укт ктуцзптщ ущ укзп цщутп щцктпщтцтп кщтуцщшпкщт щшцукпщшкуцщз тцукпщшкщтцуштпщ цуктпщтк ущшптцщукшптщцшук", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhC5mIuy2KaMll2PVI4iE_18w4c7zDCcSi0PzWijCWeakJEJRGhAnIaSkYGLK7dEYlTwYnqTzwOJJcRxBrNFrZMsRovChY8CVpVIXH5pNPPj5wo1kPfGeth4z690xixWqd69vceT1yMaxGB4nDNXUnQ-kuJm3yHgqJreLneAV0nWp4lsF-BPFX0CgM8Tw/w1200-h630-p-k-no-nu/card%20view.jpg",
//                        "wadaw", "Иванов И И", "dwa", "Симомненко М М",
//                        "12.05.23", false
//                    ),
//                )