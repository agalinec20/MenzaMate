package hr.foi.rampu.menzamatefrontend

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import hr.foi.rampu.ws.services.AuthManager
import models.UserResponse
import hr.foi.rampu.menzamatefrontend.navigation.AppNavHost
import hr.foi.rampu.menzamatefrontend.navigation.components.Screen
import hr.foi.rampu.menzamatefrontend.navigation.components.BottomNavBar
import hr.foi.rampu.menzamatefrontend.navigation.components.DrawerContent
import hr.foi.rampu.menzamatefrontend.ui.theme.MenzaMateFrontendTheme
import hr.foi.rampu.menzamatefrontend.viewmodels.AppViewModel
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import hr.foi.rampu.menzamatefrontend.navigation.components.FilterDialog


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    private lateinit var authManager: AuthManager
    private var isLoggedIn by mutableStateOf(false)
    private var userId: Int? = null
    private var userRole: String? = null

    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->


        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            val account = task.result

            if (account != null) {
                val idToken = account.idToken


                if (idToken != null) {
                    handleGoogleSignInResult(idToken)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authManager = AuthManager(this, lifecycleScope)

        setContent {
            MenzaMateFrontendTheme {
                if (!isLoggedIn) {
                    MainScreen(onGoogleSignInClick = ::startGoogleSignIn)
                } else {
                    userId?.let { id ->
                        userRole?.let { role ->
                            MainAppContent(id, role)
                        } ?: run {
                            Toast.makeText(this, "User role is missing", Toast.LENGTH_SHORT).show()
                        }
                    } ?: run {
                        Toast.makeText(this, "User ID is missing", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun startGoogleSignIn() {

        try {
            val signInIntent = authManager.getGoogleSignInClientInstance().signInIntent
            googleSignInLauncher.launch(signInIntent)
        } catch (e: Exception) {
            Log.e("GoogleSignIn", "Error launching Google sign-in", e)
        }
    }

    private fun handleGoogleSignInResult(idToken: String?) {
        idToken?.let {
            authManager.handleSignInResult(
                idToken = it,
                onSuccess = { userResponse ->
                    authManager.fetchOrCreateUser(
                        idToken = it,
                        onSuccess = { userId ->
                            onLoginSuccess(userResponse, userId)
                        },
                        onError = { errorMessage ->
                            setContent {
                                MenzaMateFrontendTheme {
                                    MainScreen(onGoogleSignInClick = ::startGoogleSignIn, errorMessage)
                                }
                            }
                        }
                    )
                },
                onError = { errorMessage ->
                    setContent {
                        MenzaMateFrontendTheme {
                            MainScreen(onGoogleSignInClick = ::startGoogleSignIn, errorMessage)
                        }
                    }
                }
            )
        }
    }




    private fun onLoginSuccess(userResponse: UserResponse, userId: Int) {
        Toast.makeText(this, "Dobrodošli!", Toast.LENGTH_LONG).show()
        this.userId = userId
        this.userRole = userResponse.role
        isLoggedIn = true
        setContent {
            MenzaMateFrontendTheme {
                    userRole?.let { role ->
                        MainAppContent(userId, role)
                    }
            }
        }
    }



    @Composable
    private fun MainAppContent(userId: Int, role: String) {
        val appViewModel: AppViewModel = viewModel(factory = AppViewModel.AppViewModelFactory(userId, role))
        val navController = rememberNavController()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route

        val showFilterDialog by appViewModel.showFilterDialog.collectAsState()

        val userRoleState by appViewModel.userRole.collectAsState()

        ModalNavigationDrawer(
            drawerContent = {
                val currentRoute =
                    navController.currentBackStackEntryAsState().value?.destination?.route ?: ""
                DrawerContent(
                    currentScreen = currentRoute,
                    onItemSelected = { screen ->
                        scope.launch { drawerState.close() }
                        navController.navigate(screen.route) {
                            popUpTo(Screen.Home.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    }, onLogout = ::logout, userRole = userRoleState
                )
            },
            drawerState = drawerState
        ) {
            Scaffold(
                topBar = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFFCE93D8),
                                        Color(0xFFBA68C8)
                                    )
                                )
                            )
                    ) {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    text = "MenzaMate",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "Menu",
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            },
                            actions = {
                                if (currentRoute == Screen.Home.route) {
                                    IconButton(onClick = { appViewModel.openFilterDialog() }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_filter),
                                            contentDescription = "Filter",
                                            tint = MaterialTheme.colorScheme.onPrimary
                                        )
                                    }
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Transparent
                            ),
                            modifier = Modifier.background(Color.Transparent)
                        )
                    }
                },
                bottomBar = {
                    BottomNavBar(navController, userRoleState)
                }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    AppNavHost(navController = navController, appViewModel = appViewModel)
                }
            }

            if (showFilterDialog && currentRoute == Screen.Home.route) {
                FilterDialog(
                    onDismiss = { appViewModel.closeFilterDialog() },
                    onFilterSelected = { filterMealType, filterDate, excludeSimilar ->
                        appViewModel.setFilterCriteria(filterMealType, filterDate, excludeSimilar)
                        appViewModel.closeFilterDialog()
                    }
                )
            }
        }
    }

    private fun logout() {
        authManager.logout { success ->
            if (success) {

                isLoggedIn = false
                userId = null
                userRole = null


                setContent {
                    MenzaMateFrontendTheme {
                        MainScreen(onGoogleSignInClick = ::startGoogleSignIn)
                    }
                }
            } else {

                Toast.makeText(this, "Logout failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Composable
    fun MainScreen(onGoogleSignInClick: () -> Unit, errorMessage: String? = null, isLoading: Boolean = false) {
        var logoVisible by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            logoVisible = true
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            errorMessage?.let {
                Text(text = it, color = Color.Red, modifier = Modifier.padding(bottom = 16.dp))
            }

            AnimatedVisibility(visible = logoVisible) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(bottom = 16.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(217, 205, 219, 255),
                        modifier = Modifier.size(200.dp)
                            .shadow(8.dp, CircleShape)
                    ) {}
                    Image(
                        painter = painterResource(id = R.drawable.app_logo),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(190.dp)
                            .clip(CircleShape)
                    )
                }
            }

            Spacer(modifier = Modifier.height(100.dp))

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .graphicsLayer {
                            rotationZ = if (isLoading) 360f else 0f
                        }
                )
            }

            Button(
                onClick = { onGoogleSignInClick() },
                enabled = !isLoading,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .border(2.dp, Color.Gray, RoundedCornerShape(25.dp))
                    .graphicsLayer {
                        scaleX = if (isLoading) 0.95f else 1f
                        scaleY = if (isLoading) 0.95f else 1f
                    },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google_logo),
                        contentDescription = "Google Logo",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Prijava pomoću Google-a", color = Color.Black)
                }
            }
        }
    }
}


