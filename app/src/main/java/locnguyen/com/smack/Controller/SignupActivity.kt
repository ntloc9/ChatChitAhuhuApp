package locnguyen.com.smack.Controller

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_signup.*
import locnguyen.com.smack.R
import java.util.*

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
    }

    var BackgroundColorUserImage = "[0.5, 0.5, 0.5, 1]"
    var UserImage = ""

    fun signupRandomImageClick(view: View){
        val random = Random()
        val darklight = random.nextInt(2)
        val UserImageNum = random.nextInt(28)
        if(darklight == 0){
            UserImage = "dark$UserImageNum"
        }else{
            UserImage = "light$UserImageNum"
        }

        val resourceId = resources.getIdentifier(UserImage, "drawable", packageName)
        signupUserImage.setImageResource(resourceId)
    }

    fun signupGenerateBackgorundBtnColorClick(view: View){
        val random = Random()
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)
        signupUserImage.setBackgroundColor(Color.rgb(r, g, b))

        val saveR = r.toDouble()/255
        val saveG = r.toDouble()/255
        val saveB = b.toDouble()/255

        BackgroundColorUserImage = "[$saveR, $saveG, $saveG, 1]"
    }

    fun signupCreateUserClick(view: View){

    }
}
