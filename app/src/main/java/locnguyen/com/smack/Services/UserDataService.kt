package locnguyen.com.smack.Services

import android.graphics.Color
import java.util.*

object UserDataService {
    var id = ""
    var avatarColor = ""
    var avatarName = ""
    var email = ""
    var name = ""

    fun logout(){
        id = ""
        avatarColor = ""
        avatarName = ""
        email = ""
        name = ""
        AuthServices.userEmail = ""
        AuthServices.isLoggedIn = false
        AuthServices.authToken = ""
    }

    fun convertAvatarColor(rawColor : String) : Int{
        val stripColor = rawColor.replace("[", "").replace("]", "").replace(",", "")
        val scanner = Scanner(stripColor)

        var r = 0
        var g = 0
        var b = 0

        if (scanner.hasNext()){
            r = (scanner.nextDouble() * 255).toInt()
            g = (scanner.nextDouble() * 255).toInt()
            b = (scanner.nextDouble() * 255).toInt()
        }
        return Color.rgb(r, g, b)
    }
}