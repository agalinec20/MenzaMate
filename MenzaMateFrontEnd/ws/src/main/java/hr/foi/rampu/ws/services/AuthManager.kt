package hr.foi.rampu.ws.services

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import models.TokenRequest
import models.UserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

class AuthManager(private val context: Context, private val lifecycleScope: CoroutineScope) {

    interface ApiService {
        @POST("api/auth/google-login")
        suspend fun googleLogin(@Body tokenRequest: TokenRequest): UserResponse
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://menzamate-f0hvaddye6b3fwfw.northeurope-01.azurewebsites.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val apiService by lazy { retrofit.create(ApiService::class.java) }

    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()


    private val googleSignInClient: GoogleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("453869849942-36q4hich436t250tgiks39nsmdv9fo97.apps.googleusercontent.com")
            .requestEmail()
            .build()

        return@lazy GoogleSignIn.getClient(context, gso)
    }
    fun getGoogleSignInClientInstance(): GoogleSignInClient = googleSignInClient

    fun handleSignInResult(
        idToken: String,
        onSuccess: (UserResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    authenticateWithBackend(idToken, onSuccess, onError)
                    Log.d("GoogleSignIn", "ID Token: $idToken")
                } else {
                    onError("Firebase Authentication failed!")
                }
            }
    }


    private fun authenticateWithBackend(
        idToken: String,
        onSuccess: (UserResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        lifecycleScope.launch {
            try {
                val response = apiService.googleLogin(TokenRequest(idToken))
                onSuccess(response)
            } catch (e: Exception) {
                onError("Failed to authenticate with backend!")
            }
        }
    }
    fun fetchOrCreateUser(
        idToken: String,
        onSuccess: (Int) -> Unit,
        onError: (String) -> Unit
    ) {
        lifecycleScope.launch {
            try {
                val response = apiService.googleLogin(TokenRequest(idToken))
                val userId = response.userId
                Log.d("AuthManager", "Fetched UserId: $userId")
                onSuccess(userId)
            } catch (e: Exception) {
                Log.e("AuthManager", "Failed to fetch UserId: ${e.message}", e)
                onError("Failed to fetch UserId!")
            }
        }
    }
    fun logout(callback: (Boolean) -> Unit) {
        try {

            googleSignInClient.signOut().addOnCompleteListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }
        } catch (e: Exception) {
            callback(false)
        }
    }

}