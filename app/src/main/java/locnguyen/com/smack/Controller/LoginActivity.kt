package locnguyen.com.smack.Controller

import android.app.Service
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import locnguyen.com.smack.R
import locnguyen.com.smack.Services.AuthServices
import org.jetbrains.anko.activityManager
import org.jetbrains.anko.inputMethodManager

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginSpinner.visibility = View.INVISIBLE
    }

    fun loginSigninBtnClicked(view: View){
        enableSpinner(true)
        val email = loginEmailText.text.toString()
        val password = loginPasswordText.text.toString()
        view.hideKeyboard(this.inputMethodManager)
        if (email.isNotEmpty() && password.isNotEmpty()){
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
        }else {
            Toast.makeText(this, "Please fill up email and password", Toast.LENGTH_SHORT).show()
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
            loginSpinner.visibility = View.VISIBLE
        }else {
            loginSpinner.visibility = View.INVISIBLE
        }
        loginSigninBtn.isEnabled = !enable
        loginSignupBtn.isEnabled = !enable
        loginEmailText.isEnabled = !enable
        loginPasswordText.isEnabled = !enable
    }

    fun View.hideKeyboard(inputMethodManager: InputMethodManager){
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}
