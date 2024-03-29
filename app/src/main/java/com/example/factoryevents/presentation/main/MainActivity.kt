package com.example.factoryevents.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.factoryevents.GoogleApiContract
import com.example.factoryevents.domain.entity.AccessType
import com.example.factoryevents.domain.entity.AuthState
import com.example.factoryevents.domain.entity.User
import com.example.factoryevents.presentation.FactoryEventApplication
import com.example.factoryevents.presentation.ViewModelFactory
import com.example.factoryevents.ui.theme.FactoryEventsTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import org.json.JSONArray
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as FactoryEventApplication).component
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        component.inject(this)

        super.onCreate(savedInstanceState)

        setContent {
            FactoryEventsTheme {

                val viewModel: MainViewModel = viewModel(factory = viewModelFactory)
                val authState = viewModel.authState.collectAsState(AuthState.Initial)

                val launcher = rememberLauncherForActivityResult(
                    contract = GoogleApiContract()
                ){
                    viewModel.performAuthResult()
                }

                when(authState.value){
                    is AuthState.Authorized-> AccessScreen(viewModelFactory)
                    is AuthState.NotAuthorized -> LoginScreen{
                        launcher.launch(1)
                    }

                    else -> {}
                }

            }
        }
    }





    @Composable
    private fun signIn(){

        val launcher = rememberLauncherForActivityResult(contract = GoogleApiContract()){ task ->
            try {
                val gsa = task?.getResult(ApiException::class.java)

                if (gsa != null) {
                    val acct = GoogleSignIn.getLastSignedInAccount(this)

                    acct?.let {
                        val name = acct.displayName
                        val mail = acct.email

                        Toast.makeText(this, "${name} ${mail} ${acct.idToken}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "ErrorSign", Toast.LENGTH_SHORT).show()
                }
            } catch (e: ApiException) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }



        Button(onClick = { launcher.launch(1) }) {
            Text(text = "Sdwa")
        }
    }



    fun insertTestData(){
        val data = "test1"
        val action = "addItem"
        var url = "https://script.google.com/macros/s/AKfycbxxV_wrkhQJeZRn6C0_E1sygQmpitTZW0i3yIMZF72SYcKOiB92SX-PAVS9UFeozaJ6/exec?"

        url += "action=$action&itemName=$data"

        val request: StringRequest = StringRequest(Request.Method.POST, url, Response.Listener {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }){
            Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
        }

        val queue: RequestQueue = Volley.newRequestQueue(this)
        queue.add(request)
    }

    fun getTestData(){
        val action = "getData"
        var url = "https://script.google.com/macros/s/AKfycbxNtEXFsnsN6s3_8wWJPjnygAGiPQK4TwfQ2I4P2Lwrurda9DZnyZ6kMgLIMaTiZdPG/exec?"

        url += "action=$action"

        val request: JsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            {
                var arr: JSONArray = it.getJSONArray("Items")
            },
            {

            }
        )
    }
}
