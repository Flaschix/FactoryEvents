package com.example.factoryevents.domain.entity

import android.net.Uri
import androidx.core.net.toUri

data class Order(
    var whatHappened: String = "",
    var whereDetected: String = "",
    var imgLink: Uri = "".toUri(),
    var isItARecurrentIssue: String = "",
    var whyIsItProblem: String = "",
    var time: String = "",
    var date: String = "",
    var whoDetectedIt: String = "",
    var howItWasDetected: String = "",
    var detectionCount: String = "",
    var indicateYourManager: String = ""
)



