package locnguyen.com.smack.Controller

import android.app.Service
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import locnguyen.com.smack.R
import locnguyen.com.smack.Services.AuthServices

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        enableSpinner(false)
    }

    fun loginSigninBtnClicked(view: View){
        enableSpinner(true)
        val email = loginEmailText.text.toString()
        val password = loginPasswordText.text.toString()

        AuthServices.loginUser(this, email, password){loginSuccess ->
            if (loginSuccess){
                AuthServices.findUserByEmail(this){findSuccess ->
                    if (findSuccess){
                        enableSpinner(false)
                        Log.d("locnguyenlog1", "zooooooooo")
                        finish()
                    }else{
                        errorToast()
                    }
                }
            }else{
                errorToast()
            }
        }
    }

    fun loginSignupBtnClicked(view: View){
        val signupIntent = Intent(this, SignupActivity::class.java)
        startActivity(signupIntent)
    }

    fun errorToast(){
        Toast.makeText(this, "Something went wrong, please later", Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }

    fun enableSpinner(enable: Boolean){
        if (enable){
            signinSpinning.visibility = View.VISIBLE
        }else {
            signinSpinning.visibility = View.INVISIBLE
        }
    }
}
