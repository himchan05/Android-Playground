package moi.next.usercard

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import moi.next.ViewModel.UserViewModel

import moi.next.usercard.Model.User
import moi.next.usercard.Service.JsonPlaceholderApi
import moi.next.usercard.ui.theme.UserCardTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
//    private val viewModel: CreditCardViewModel by viewModels()
    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainView(viewModel)
        }
    }
}

@Composable
fun MainView(viewModel: UserViewModel) {
    val users by viewModel.users.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchUsers()
    }

    Column(
        Modifier.padding(10.dp)
    ) {
        if (users.isEmpty()) {
            // Show loading indicator or placeholder
            Text("Loading ...")
        } else {
            // Display the list of credit cards
            LazyColumn {
                items(users) { user ->
                    CardView(user)
                }
            }
        }
    }
}
@Composable
fun CardView(user: User) {
    val typography = MaterialTheme.typography

    Column(
        Modifier.padding(10.dp)
    ) {
        Text(user.name, style = typography.bodyLarge)
        Text(user.email, style = typography.bodyMedium)
        Text(user.username, style = typography.bodySmall)
        Divider()
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainView(UserViewModel())
}