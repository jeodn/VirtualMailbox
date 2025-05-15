package com.example.virtualmailbox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// extra import
import com.example.virtualmailbox.ui.theme.VirtualMailboxTheme

// API System
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



interface MailApiService {
    @GET("api/mail")
    suspend fun getMail(): List<MailEntry>
}


object ApiClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://your-server.com/") // NOTE: Must end in /
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val mailApi: MailApiService = retrofit.create(MailApiService::class.java)
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VirtualMailboxApp()
        }
    }
}

@Composable
fun VirtualMailboxApp() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Inbox") },
                actions = {
                    IconButton(onClick = { /* TODO: Open settings */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Settings")
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 8.dp)) {
            SearchBar()
            MailList(mailItems = sampleMailItems)
        }
    }
}

@Composable
fun SearchBar() {
    var query by remember { mutableStateOf("") }
    OutlinedTextField(
        value = query,
        onValueChange = { query = it },
        label = { Text("Search mail") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Composable
fun MailList(mailItems: List<MailItem>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(mailItems) { mail ->
            MailListItem(mail)
            Divider()
        }
    }
}

@Composable
fun MailListItem(mail: MailItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: Open mail */ }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(mail.sender, fontWeight = FontWeight.Bold)
            Text(mail.subject, fontSize = 14.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(
                mail.preview,
                fontSize = 12.sp,
                maxLines = 1,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        }
        Text(mail.timestamp, fontSize = 12.sp, modifier = Modifier.padding(start = 8.dp))
    }
}

@Composable
fun MailScreen() {
    var mailEntries by remember { mutableStateOf<List<MailEntry>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(true) {
        try {
            mailEntries = ApiClient.mailApi.getMail()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    } else {
        MailList(mailItems = mailEntries)
    }
}

data class MailItem(
    val id: Int,
    val sender: String,
    val subject: String,
    val preview: String,
    val timestamp: String,
    val thumbnail_url: String
)

val sampleMailItems = listOf(
    MailItem(
        1,
        "Parcel Delivered",
        "Your package has arrived at the mailbox.",
        "words",
        "words",
        "https://previews.123rf.com/images/antonbrand/antonbrand1105/antonbrand110500002/9438222-cartoon-of-overweight-man-having-a-bbq-isolated-on-white.jpg"
        )
)

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    VirtualMailboxTheme {
        VirtualMailboxApp()
    }
}
