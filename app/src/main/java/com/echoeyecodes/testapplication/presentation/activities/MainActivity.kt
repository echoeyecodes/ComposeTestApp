package com.echoeyecodes.testapplication.presentation.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.echoeyecodes.testapplication.R
import com.echoeyecodes.testapplication.data.models.NetworkState
import com.echoeyecodes.testapplication.data.models.User
import com.echoeyecodes.testapplication.presentation.viewmodels.SampleActivityViewModel
import com.echoeyecodes.testapplication.ui.theme.TestApplicationTheme
import com.echoeyecodes.testapplication.utils.generateId
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<SampleActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestApplicationTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        Navbar("List of Students") {
                            finish()
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(10.dp)
                        ) {
                            val networkState = remember { viewModel.getNetworkState() }
                            if (networkState.value is NetworkState.Loading) {
                                Loading()
                            } else if (networkState.value is NetworkState.Error) {
                                Toast.makeText(
                                    this@MainActivity,
                                    (networkState.value as NetworkState.Error).message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            // only show data when not loading
                            if (networkState.value !is NetworkState.Loading) {
                                val data = viewModel.getUsers()
                                val users = data.value
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                ) {
                                    itemsIndexed(users) { index, user ->
                                        ProfileItem(user, index)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Navbar(name: String, onBackPressed: () -> Unit) {
    Box(
        contentAlignment = Alignment.CenterStart
    ) {
        IconButton(onClick = onBackPressed) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
                contentDescription = "back_btn"
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Text(text = name, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun Loading() {
    return Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ProfileItem(data: User, index: Int) {
    val direction =
        if (index % 2 == 0) LocalLayoutDirection provides LayoutDirection.Ltr else LocalLayoutDirection provides LayoutDirection.Rtl
    Column {
        CompositionLocalProvider(direction) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 10.dp, top = 10.dp)
            ) {
                AsyncImage(
                    model = data.avatar, contentDescription = "avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(120.dp)
                        .height(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Black)
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = data.name, fontWeight = FontWeight.Bold)
                    Row {
                        Text(text = "${data.age / 1000} years")
                        Text(text = " | ")
                        Text(text = data.generateId())
                        Text(text = " | ")
                        Text(text = data.department)
                    }
                }
            }
        }
        Divider()
    }
}