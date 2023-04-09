package com.crud.littlelemon.screens.dashboardScreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crud.littlelemon.R
import com.crud.littlelemon.networkKtorSer.PostResponse
import com.crud.littlelemon.screens.RegisterViewModel
import com.crud.littlelemon.ui.theme.LittleLemonTheme
import com.google.accompanist.glide.rememberGlidePainter
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

class HomeActivity : ComponentActivity() {

    private val viewModel: RegisterViewModel by viewModels()
    private val listOfMenu = ArrayList<PostResponse.DataMenu>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeView()
                }
            }
        }
        getObserver()
    }

    private fun getObserver() {
        viewModel.readAllData.observe(this) {
            listOfMenu.clear()
            listOfMenu.addAll(it)
        }
    }

    @Composable
    fun HomeView() {
        val textState = remember { mutableStateOf(TextFieldValue("")) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.sdp),
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.little_lemon_logo),
                    contentDescription = stringResource(R.string.little_lemon_logo),
                    modifier = Modifier
                        .height(25.sdp)
                        .align(Alignment.Center)
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = stringResource(R.string.little_lemon_logo),
                    modifier = Modifier
                        .padding(end = 20.sdp)
                        .width(35.sdp)
                        .height(35.sdp)
                        .align(Alignment.CenterEnd)
                        .clickable {
                            startActivity(Intent(this@HomeActivity, Profile::class.java))
                        }
                )
            }

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 50.sdp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.sdp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color(R.color.text_color_dash))
                            .padding(top = 20.sdp, bottom = 20.sdp)
                    ) {
                        Box {
                            Column(
                                modifier = Modifier
                                    .padding(start = 20.sdp, end = 110.sdp, top = 10.sdp)
                            ) {
                                androidx.compose.material3.Text(
                                    text = stringResource(R.string.little_lemon),
                                    color = Color.Yellow,
                                    fontSize = 18.ssp,
                                )
                                androidx.compose.material3.Text(
                                    text = stringResource(R.string.chicago),
                                    color = Color.White,
                                    fontSize = 14.ssp,
                                )
                                androidx.compose.material3.Text(
                                    text = stringResource(R.string.description_of_resturant),
                                    color = Color.White,
                                    fontSize = 12.ssp,
                                    modifier = Modifier
                                        .padding(top = 10.sdp)
                                )
                            }

                            Image(
                                painter = painterResource(id = R.drawable.food_veg),
                                contentDescription = stringResource(R.string.little_lemon_logo),
                                modifier = Modifier
                                    .width(100.sdp)
                                    .align(Alignment.CenterEnd)
                                    .height(90.sdp)
                                    .padding(end = 20.sdp)
                                    .clip(RoundedCornerShape(10.sdp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                        SearchView(textState)

                    }
                }
                Text(
                    text = stringResource(R.string.order_for_delivery), fontSize = 12.ssp,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 25.sdp, vertical = 5.sdp),
                    textAlign = TextAlign.Center,
                )
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 20.sdp)
                ) {
                    FilterView(stringResource(R.string.starters))
                    FilterView(stringResource(R.string.mains))
                    FilterView(stringResource(R.string.dessersts))
                    FilterView(stringResource(R.string.drinks))
                }

                Spacer(modifier = Modifier.padding(top = 20.sdp))
                listOfMenu.forEach {
                    MenuListView(itemData = it)
                }
            }
        }
    }


    @Composable
    fun MenuListView(itemData: PostResponse.DataMenu) {
        Box(
            modifier = Modifier.padding(horizontal = 20.sdp, vertical = 5.sdp)
        ) {
            Column(
                modifier = Modifier.padding(end = 70.sdp)
            ) {
                Text(
                    text = itemData.title.toString(),
                    color = Color.Black,
                    fontSize = 14.ssp
                )
                Text(
                    text = itemData.description.toString(),
                    color = Color.DarkGray,
                    fontSize = 12.ssp,
                )
                Text(
                    text = "$${itemData.price.toString()}",
                    color = Color.DarkGray,
                    fontSize = 11.ssp,
                    modifier = Modifier.padding(top = 5.sdp)
                )
            }

            Image(
                painter = rememberGlidePainter(itemData.image),
                contentDescription = stringResource(R.string.little_lemon_logo),
                modifier = Modifier
                    .width(50.sdp)
                    .align(Alignment.CenterEnd)
                    .height(50.sdp)
                    .clip(RoundedCornerShape(10.sdp)),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .height(0.5.dp)
                    .fillMaxWidth()
                    .background(color = Color.LightGray)
                    .padding(bottom = 10.sdp, top = 15.sdp)
                    .align(Alignment.BottomEnd)
            )
        }
    }

    @Composable
    fun FilterView(text: String) {
        Box(
            Modifier
                .padding(horizontal = 5.sdp)
                .background(color = Color(R.color.gray_400), shape = RoundedCornerShape(7.sdp))
        ) {
            Text(
                text = text, fontSize = 12.ssp,
                color = Color.White,
                modifier = Modifier
                    .padding(horizontal = 10.sdp, vertical = 5.sdp),
                textAlign = TextAlign.Center,
            )
        }
    }

    @Composable
    fun SearchView(state: MutableState<TextFieldValue>) {
        TextField(
            value = state.value,
            onValueChange = { value ->
                state.value = value
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent, shape = RoundedCornerShape(10.sdp))
                .padding(start = 20.sdp, end = 20.sdp, top = 15.sdp),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp)
                )
            },
            trailingIcon = {
                if (state.value != TextFieldValue("")) {
                    IconButton(
                        onClick = {
                            state.value =
                                TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(10.dp)
                                .size(20.dp)
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(10.sdp), // The TextFiled has rounded corners top left and right by default
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                leadingIconColor = Color.Black,
                trailingIconColor = Color.Black,
                backgroundColor = colorResource(id = R.color.white),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        LittleLemonTheme {
            HomeView()
        }
    }
}


