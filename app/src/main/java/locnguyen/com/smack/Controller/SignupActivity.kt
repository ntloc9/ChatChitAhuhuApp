package locnguyen.com.smack.Controller

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.AppCompatEditText
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signup.*
import locnguyen.com.smack.R
import locnguyen.com.smack.Services.AuthServices
import locnguyen.com.smack.Services.UserDataService
import locnguyen.com.smack.Ultilities.BROADCAST_USER_DATA_CHANGE
import java.util.*

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        signupSpinning.visibility = View.INVISIBLE

    }

    var avatarUser = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"

    fun signupGenColorBgBtnClicked(view: View){
        val random = Random()
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)

        signupAvatarUser.setBackgroundColor(Color.rgb(r, g, b))

        val savedR = r.toDouble() / 255
        val savedG = g.toDouble() / 255
        val savedB = b.toDouble() / 255

        avatarColor = "[$savedR, $savedG, $savedB, 1]"
    }

    fun signupAvatarUserClicked(view: View){
        val random = Random()
        val avatarNunber = random.nextInt(28)
        val avatarDL = random.nextInt(2)

        if (avatarDL == 1){
            avatarUser = "dark$avatarNunber"
        }else{
            avatarUser = "light$avatarNunber"
        }

        val resourceID = resources.getIdentifier(avatarUser, "drawable", packageName)
        signupAvatarUser.setImageResource(resourceID)
    }

    fun signupSignupBtnClicked(view: View){
        enableSpinner(true)
        val name = signupNameText.text.toString()
        val email = signupEmailText.text.toString()
        val password = signupPasswordText.text.toString()
        if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
            AuthServices.registerUser(this, email, password){registerSuccess ->
                if (registerSuccess){
                    Log.d("Locnguyenlog13", "reg success")
//                Toast.makeText(this, "registerSuccess", Toast.LENGTH_SHORT).show()
                    AuthServices.loginUser(this, email, password){loginSuccess ->
                        if (loginSuccess){
                            Log.d("Locnguyenlog13", "login success")
////                        Toast.makeText(this, "loginSuccess", Toast.LENGTH_SHORT).show()
                            AuthServices.addUser(this, name, email, avatarUser, avatarColor){addSuccess ->
                                if (addSuccess){
                                    val userDataChange = Intent(BROADCAST_USER_DATA_CHANGE)
                                    LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)
                                    enableSpinner(false)
//                                    Log.d("Locnguyenlog13", "add user success")
//                                    Toast.makeText(this, "Sign In Successfully", Toast.LENGTH_LONG).show()
                                    finish()
                                } else{
                                    errorToast()
                                }
                            }
                        } else{
                            errorToast()
                        }
                    }
                } else{
                    errorToast()
                }
            }
        }else{
            Toast.makeText(this, "Make sure you are fill in name, email and password", Toast.LENGTH_SHORT).show()
            enableSpinner(false)
        }
    }

    fun errorToast(){
        Toast.makeText(this, "Something went wrong, please later", Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }

    fun enableSpinner(enable: Boolean){
        if (enable){
            signupSpinning.visibility = View.VISIBLE
        }else {
            signupSpinning.visibility = View.INVISIBLE
        }
        signupSignupBtn.isEnabled = !enable
        signupAvatarUser.isEnabled = !enable
        signupGenColorBgBtn.isEnabled = !enable
    }
}
