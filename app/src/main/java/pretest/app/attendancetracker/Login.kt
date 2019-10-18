package pretest.app.attendancetracker

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.auth0.android.Auth0
import com.auth0.android.provider.WebAuthProvider
import pretest.app.attendancetracker.auth.auth0.Auth0LoginResponseHandler
import pretest.app.attendancetracker.auth.auth0.Auth0LogoutCallback
import pretest.app.attendancetracker.contracts.HandleAuth0LogoutRequest
import pretest.app.attendancetracker.viewmodels.Auth0ProviderFactory
import pretest.app.attendancetracker.viewmodels.AuthViewModel


class Login : AppCompatActivity(), HandleAuth0LogoutRequest {

    private val mViewModel: AuthViewModel by viewModels {
        Auth0ProviderFactory(
            activity = this,
            loginResponeHandler = Auth0LoginResponseHandler(this),
            logoutRequestHandler = this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mViewModel.credential.observe(this, Observer {
            it?.let { Log.i(localClassName, it.toString()) }
        })
        mViewModel.profileInfo.observe(this, Observer {
            it?.let { Log.i(localClassName, it.toString()) }
        })
    }

    fun login(view: View) {
        mViewModel.login()
    }

    fun logout(view: View) {
        mViewModel.logout()
    }

    override fun doLogout(auth0: Auth0, onLogoutSuccess: () -> Unit) {
        WebAuthProvider.logout(auth0)
            .withScheme("demo")
            .start(this, Auth0LogoutCallback(this, onLogoutSuccess));
    }
}
