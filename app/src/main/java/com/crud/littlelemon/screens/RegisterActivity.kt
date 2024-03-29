package com.crud.littlelemon.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.crud.littlelemon.MyApp
import com.crud.littlelemon.R
import com.crud.littlelemon.models.UserDetailsModel
import com.crud.littlelemon.screens.dashboardScreen.HomeActivity
import com.crud.littlelemon.screens.dashboardScreen.MenuViewModel
import com.crud.littlelemon.ui.theme.LittleLemonTheme
import com.crud.littlelemon.utils.IS_LOGGED_USER
import com.crud.littlelemon.utils.getBooleanPref
import com.crud.littlelemon.utils.saveBooleanPref
import com.crud.littlelemon.utils.saveUserInfoPref
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp


class RegisterActivity : ComponentActivity() {
    private val viewModel: MenuViewModel by viewModels()
    private val viewModelDataBase: RegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (getBooleanPref(IS_LOGGED_USER)) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        setContent {
            LittleLemonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(this)
                }
            }
        }
        setupObserver()

    }

    private fun setupObserver() {
        viewModelDataBase.readAllData.observe(this) {
            if (it != null) {
                if (it.isEmpty()) {
                    viewModel.executeMenuData()
                }
            } else {
                viewModel.executeMenuData()
            }
        }
        // Use Case
        viewModel.getMenuDataResponse.observe(this) {
            it.getContentIfNotHandled()?.let { useCase ->
                when (useCase) {
                    is MenuViewModel.MenuLiveData.Error -> {
                        Log.d("MainActivity", "Error ${useCase.code}")
                        showToast("Error ${useCase.code}")
                    }
                    is MenuViewModel.MenuLiveData.ShowMenuItems -> {
                        Log.d(
                            "MainActivity",
                            "ShowItems ${useCase.items.menu?.get(0)?.description}"
                        )
                        // showToast("ShowItems ${useCase.items.menu?.get(0)?.description}")
                        useCase.items.menu?.forEach { data ->
                            viewModelDataBase.addMenu(data)
                        }

                    }
                    is MenuViewModel.MenuLiveData.Saved -> {
                        Log.d("MainActivity", "Saved ${useCase.isSaved}")
                        showToast("Saved ${useCase.isSaved}")
                    }
                }
            }
        }
    }


    @Composable
    fun Greeting(activity: Context) {
        val firstName = remember {
            mutableStateOf("")
        }
        val lastName = remember {
            mutableStateOf("")
        }
        val userEmail = remember {
            mutableStateOf("")
        }

        Column {
            Image(
                painter = painterResource(id = R.drawable.little_lemon_logo),
                contentDescription = stringResource(R.string.little_lemon_logo),
                modifier = Modifier.padding(80.sdp, 20.sdp, 80.sdp, 20.sdp)
            )
            Text(
                text = stringResource(R.string.let_get_to_know_you),
                color = Color.White,
                modifier = Modifier
                    .background(color = Color(R.color.text_color_dash))
                    .fillMaxWidth()
                    .padding(vertical = 30.sdp),
                textAlign = TextAlign.Center,
                fontSize = 16.ssp
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
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text
                    )
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
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next, keyboardType = KeyboardType.Text
                    )
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
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done, keyboardType = KeyboardType.Email
                    )
                )

                Spacer(modifier = Modifier.height(50.sdp))
                Button(
                    onClick = {
                        if (firstName.value.isBlank()) {
                            showToast("Enter first name")
                            return@Button
                        } else if (lastName.value.isBlank()) {
                            showToast("Enter last name")
                            return@Button
                        } else if (userEmail.value.isBlank()) {
                            showToast("Enter email")
                            return@Button
                        } else {
                            val model = UserDetailsModel(
                                firstName.value,
                                lastName.value,
                                userEmail.value
                            )
                            saveUserInfoPref(model)
                            saveBooleanPref(IS_LOGGED_USER, true)
                            showToast("User registered successfully")
                            val intent = Intent(activity, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    },
                    shape = RoundedCornerShape(10.sdp),
                ) {
                    Text(
                        text = stringResource(R.string.register),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 16.ssp
                    )
                }
            }
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

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        LittleLemonTheme {
            Greeting(MyApp.self)
        }
    }
}

