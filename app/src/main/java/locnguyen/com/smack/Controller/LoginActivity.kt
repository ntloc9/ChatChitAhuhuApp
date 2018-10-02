package locnguyen.com.smack.Controller

import android.app.Service
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*
import locnguyen.com.smack.R
import locnguyen.com.smack.Services.AuthServices

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun loginSigninBtnClicked(view: View){
        val email = loginEmailText.text.toString()
        val password = loginPasswordText.text.toString()

        AuthServices.loginUser(this, email, password){loginSuccess ->
            if (loginSuccess){
                AuthServices.findUserByEmail(this){findSuccess ->
                    if (findSuccess){
                        finish()
                    }
                }
            }
        }
    }

    fun loginSignupBtnClicked(view: View){
        val signupIntent = Intent(this, SignupActivity::class.java)
        startActivity(signupIntent)
    }
}
