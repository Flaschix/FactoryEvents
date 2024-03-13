package com.example.factoryevents.data.mapper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.factoryevents.domain.entity.AccessType
import com.example.factoryevents.domain.entity.HSE
import com.example.factoryevents.domain.entity.OJT
import com.example.factoryevents.domain.entity.Order
import com.example.factoryevents.domain.entity.User
import com.example.factoryevents.domain.entity.WorkerHSE
import org.json.JSONArray
import java.io.ByteArrayOutputStream
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


                    var date19 = covid.getString("Date")
                    if(date19 != "N/A" && date19 != "#N/A" && date19.isNotEmpty()) {
                        date19 = date19.substring(0, date19.indexOf("T"));
                        val datespl = date19.split('-')
                        date19 = "${datespl[2]}-${datespl[1]}-${datespl[0].substring(2,4)}"
                    }else date19 = "N/A"

                    var dateSaf = saf.getString("Date")
                    if(dateSaf != "N/A" && dateSaf != "#N/A" && dateSaf.isNotEmpty()) {
                        dateSaf = dateSaf.substring(0, dateSaf.indexOf("T"));
                        val datespl = dateSaf.split('-')
                        dateSaf = "${datespl[2]}-${datespl[1]}-${datespl[0].substring(2,4)}"
                    }else dateSaf = "N/A"

                    var dateSgr = sgr.getString("Date")
                    if(dateSgr != "N/A" && dateSgr != "#N/A" && dateSgr.isNotEmpty()) {
                        dateSgr = dateSgr.substring(0, dateSgr.indexOf("T"));
                        val datespl = dateSgr.split('-')
                        dateSgr = "${datespl[2]}-${datespl[1]}-${datespl[0].substring(2,4)}"
                    }else dateSgr = "N/A"

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
                                    date = date19
                                ),
                                HSE(
                                    title = "SAF",
                                    plannedLine = saf.getString("Planned Line"),
                                    doneOnLine = saf.getString("Done on Line"),
                                    score = saf.getString("Score"),
                                    date = dateSaf
                                ),
                                HSE(
                                    title = "SGR",
                                    plannedLine = sgr.getString("Planned Line"),
                                    doneOnLine = sgr.getString("Done on Line"),
                                    score = sgr.getString("Score"),
                                    date = dateSgr
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

    }

    suspend fun mapResponseToReport(order: Order) {

        val stringBitmap64Base = if (order.imgLink.toString().isEmpty()) ""
        else getStringImage(getBitmapFromUri(order.imgLink))

        Log.d("TEST_ORDER", "image code: $stringBitmap64Base")

        val action = "addReport"
        var url = APP_SCRIPT_URL
//            url += "action=$action&uId=$uId&uName=$uName&uImage=$stringBitmap64Base"

        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener<String?> { response ->
                Toast.makeText(context, response, Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    context,
                    error.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params[ACTION] = action
                params[WHAT_HAPPENED] = order.whatHappened
                params[IMAGE] = stringBitmap64Base
                params[IS_IT_A_RECURRENT_ISSUE] = order.isItARecurrentIssue
                params[WHY_IS_IT_PROBLEM] = order.whyIsItProblem
                params[TIME] = order.time
                params[DATE] = order.date
                params[WHO_DETECTED_IT] = order.whoDetectedIt
                params[HOW_IT_WAS_DETECTED] = order.howItWasDetected
                params[DETECTION_COUNT] = order.detectionCount
                params[INDICATE_YOUR_MANAGER] = order.indicateYourManager

                return params
            }
        }

        val socketTimeout = 30000 // 30 seconds. You can change it

        val policy: RetryPolicy = DefaultRetryPolicy(
            socketTimeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        stringRequest.retryPolicy = policy

        val queue: RequestQueue = Volley.newRequestQueue(context)
        queue.add(stringRequest)


        Log.d("TEST_ORDER", "${order}: Order")
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap {
        val inputStream = context.contentResolver.openInputStream(uri)

        return BitmapFactory.decodeStream(inputStream)
    }

    fun getStringImage(bmp: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageBytes = baos.toByteArray()
        return Base64.encodeToString(imageBytes, Base64.DEFAULT)
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
                    var date = rowArray.getString("dueDate")
//                    Log.d("TEST_DATE_MAPPER", "$date")
                    if(date.isNotEmpty()) {
                        date = date.substring(0, date.indexOf("T"));
                        val datespl = date.split('-')
                        date = "${datespl[2]}-${datespl[1]}-${datespl[0].substring(2,4)}"
                    }else date = "N/N"
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
                            dueDate = date,
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
        private const val APP_SCRIPT_URL = "https://script.google.com/macros/s/AKfycbzqFsojt7uCfBkece-N00xP8XRz2lTyjT2zNpWUW7rAxWZsKv3aJEZtPeCkIoylcz-A/exec?"

        private const val ACTION = "action"
        private const val WHAT_HAPPENED = "whatHappened"
        private const val IMAGE = "uImage"
        private const val IS_IT_A_RECURRENT_ISSUE = "isItARecurrentIssue"
        private const val WHY_IS_IT_PROBLEM = "whyIsItProblem"
        private const val TIME = "time"
        private const val DATE = "date"
        private const val WHO_DETECTED_IT = "whoDetectedIt"
        private const val HOW_IT_WAS_DETECTED = "howItWasDetected"
        private const val DETECTION_COUNT = "detectionCount"
        private const val INDICATE_YOUR_MANAGER = "indicateYourManager"
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