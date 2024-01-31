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
import com.google.gson.Gson
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
    suspend fun mapResponseToHSE(): List<WorkerHSE> = suspendCoroutine { continuation ->
        val action = "getHSE"
        var url = APP_SCRIPT_URL
        val rank = "L2"
        val name = "name"
        url += "action=$action&rank=$rank&name=$name"

        val request: JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->

                val list = mutableListOf<WorkerHSE>()
                val userJsonArray: JSONArray = response.getJSONArray("List")

                for (i in 0 until userJsonArray.length()) {
                    val rowArray = userJsonArray.getJSONObject(i)
                    val covid = rowArray.getJSONObject("covid19")
                    val saf = rowArray.getJSONObject("SAF")
                    val sgr = rowArray.getJSONObject("SGR")

                    list.add(
                        WorkerHSE(
                            id = i,
                            manager = rowArray.getString("manager"),
                            supervisor = rowArray.getString("supervisor"),
                            listOf(
                                HSE(
                                    title = "CV19",
                                    plannedLine = covid.getString("Planned Line"),
                                    doneOnLine = covid.getString("Done on Line"),
                                    score = covid.getString("Score"),
                                    date = covid.getString("Date")
                                ),
                                HSE(
                                    title = "SAF",
                                    plannedLine = saf.getString("Planned Line"),
                                    doneOnLine = saf.getString("Done on Line"),
                                    score = saf.getString("Score"),
                                    date = saf.getString("Date")
                                ),
                                HSE(
                                    title = "SGR",
                                    plannedLine = sgr.getString("Planned Line"),
                                    doneOnLine = sgr.getString("Done on Line"),
                                    score = sgr.getString("Score"),
                                    date = sgr.getString("Date")
                                )
                            )
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
//        val list: List<WorkerHSE> = arrayListOf(
//            WorkerHSE(
//                3,
//                "Aleksandr",
//                "LFD",
//                arrayListOf(
//                    HSE("Covid","2","3","5","5.02"),
//                    HSE("SAF","2","3","5","5.02"),
//                    HSE("SGR","2","3","5","5.02")
//                )
//            ),
//            WorkerHSE(
//                4,
//                "Sofia Dolce",
//                "SDF",
//                arrayListOf(
//                    HSE("Covid","2","3","5","5.02"),
//                    HSE("SAF","2","3","5","5.02"),
//                    HSE("SGR","2","3","5","5.02")
//                )
//            )
//            ,WorkerHSE(
//                5,
//                "Smorodinov",
//                "REG",
//                arrayListOf(
//                    HSE("Covid","2","3","5","5.02"),
//                    HSE("SAF","2","3","5","5.02"),
//                    HSE("SGR","2","3","5","7.2")
//                )
//            )
//        )
//
//        return list
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
                    val rowArray = userJsonArray.getJSONObject(i)

                    list.add(
                        OJT(
                            id = i,
                            type = rowArray.getString("type"),
                            week = rowArray.getString("week"),
                            place = rowArray.getString("place"),
                            offence = rowArray.getString("offence"),
                            img = rowArray.getString("img"),
                            byWhomOpened = rowArray.getString("byWhomOpened"),
                            areResponsible = rowArray.getString( "areResponsible"),
                            options = rowArray.getString("options"),
                            pilot = rowArray.getString("pilot"),
                            dueDate = rowArray.getString("dueDate"),
                            status = rowArray.getBoolean("status")
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
        private const val APP_SCRIPT_URL = "https://script.google.com/macros/s/AKfycbwtXSwBcPvzx1P6vfw_ozL82mlCpLMZhHH5Sftb5zPpu2dK9Y2vxGJDq-KfiY5y1Wtm/exec?"
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