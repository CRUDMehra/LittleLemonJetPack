package com.crud.littlelemon.screens.dashboardScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.crud.littlelemon.MyApp
import com.crud.littlelemon.R
import com.crud.littlelemon.screens.RegisterActivity
import com.crud.littlelemon.screens.RegisterViewModel
import com.crud.littlelemon.ui.theme.LittleLemonTheme
import com.crud.littlelemon.utils.clearPrefData
import com.crud.littlelemon.utils.getUserInfoPref
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

class Profile : ComponentActivity() {
    private val viewModelDatabase: RegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProfileView(this)
                }
            }
        }
    }

    @Composable
    fun ProfileView(activity: Context) {
        val firstName = remember {
            mutableStateOf("")
        }
        val lastName = remember {
            mutableStateOf("")
        }
        val userEmail = remember {
            mutableStateOf("")
        }
        val userInfo = getUserInfoPref()
        firstName.value = userInfo?.firstName.toString()
        lastName.value = userInfo?.lastName.toString()
        userEmail.value = userInfo?.email.toString()
        Column {
            Image(
                painter = painterResource(id = R.drawable.little_lemon_logo),
                contentDescription = stringResource(R.string.little_lemon_logo),
                modifier = Modifier.padding(80.sdp, 20.sdp, 80.sdp, 20.sdp)
            )

            Text(
                text = stringResource(R.string.personal_information),
                modifier = Modifier.padding(vertical = 30.sdp, horizontal = 20.sdp),
                fontSize = 14.ssp
            )
            Column(modifier = Modifier.padding(horizontal = 20.sdp)) {
                TitleText(stringResource(R.string.first_name))
                OutlinedTextField(
                    value = firstName.value,
                    onValueChange = {
                        firstName.value = it
                    },
                    shape = RoundedCornerShape(10.sdp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.sdp),
                    enabled = false,
                )
                TitleText(stringResource(R.string.last_name))
                OutlinedTextField(
                    value = lastName.value,
                    onValueChange = {
                        lastName.value = it
                    },
                    shape = RoundedCornerShape(10.sdp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.sdp),
                    enabled = false
                )
                TitleText(stringResource(R.string.email))
                OutlinedTextField(
                    value = userEmail.value,
                    onValueChange = {
                        userEmail.value = it
                    },
                    shape = RoundedCornerShape(10.sdp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.sdp),
                    enabled = false,
                    placeholder = {
                        Text(
                            text = "",
                            style = TextStyle(
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        )
                    },
                )

                Spacer(modifier = Modifier.height(100.sdp))
                Button(
                    onClick = {
                        clearPrefData()
                        viewModelDatabase.deleteMenu()
                        showToast("User logout successfully")
                        startActivity(Intent(activity, RegisterActivity::class.java))
                        finish()
                    },
                    shape = RoundedCornerShape(10.sdp),
                ) {
                    Text(
                        text = stringResource(R.string.logout),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 16.ssp
                    )
                }
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview2() {
        LittleLemonTheme {
            ProfileView(MyApp.self)
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(MyApp.self, message, Toast.LENGTH_SHORT).show()
    }

    @Composable
    fun TitleText(textValue: String) {
        Text(
            text = textValue, fontSize = 12.ssp,
            modifier = Modifier.padding(top = 10.sdp)
        )
    }

}

