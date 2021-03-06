package locnguyen.com.smack.Controller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import locnguyen.com.smack.R
import locnguyen.com.smack.R.string.*
import locnguyen.com.smack.Services.AuthServices
import locnguyen.com.smack.Services.UserDataService
import io.socket.client.IO
import locnguyen.com.smack.Ultilities.BROADCAST_USER_DATA_CHANGE
import locnguyen.com.smack.Ultilities.SOCKET_URL

class MainActivity : AppCompatActivity() {

    val socket = IO.socket(SOCKET_URL)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        hideKeyboard()
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, navigation_drawer_open, navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        Log.d("locnguyenlog", "${javaClass.simpleName} OnCreate")
//        println("locnguyenlogUserDataSerColor" + UserDataService.avatarColor)
//        println("locnguyenlogUserDataSerColor bat dau broadcast")
//        LocalBroadcastManager.getInstance(this).registerReceiver(userDataChangeReceiver,IntentFilter(BROADCAST_USER_DATA_CHANGE))

    }

//    val userDataChangeReceiver = object : BroadcastReceiver(){
//        override fun onReceive(context: Context?, intent: Intent?) {
//        }
//    }

    override fun onResume() {
        userChangeData()
        if (AuthServices.isLoggedIn == false) {
            userNameNavHeader.text = ""
            userImageNavHeader.setImageResource(R.drawable.profiledefault)
            loginBtnNavHeader.text = getString(sign_in)
            userImageNavHeader.setBackgroundColor(Color.TRANSPARENT)
        }
        socket.connect()
        super.onResume()
    }

    override fun onDestroy() {
        socket.disconnect()
        super.onDestroy()
    }

    private fun userChangeData(){
        userNameNavHeader.text = UserDataService.name
        userEmailNavHeader.text = UserDataService.email
        val resourceId = resources.getIdentifier(UserDataService.avatarName, "drawable",
                packageName)
        userImageNavHeader.setImageResource(resourceId)
        userImageNavHeader.setBackgroundColor(UserDataService.convertAvatarColor(UserDataService.avatarColor))
        loginBtnNavHeader.text = "sign out"
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun loginBtnNavHeaderClick(view: View){

        if (AuthServices.isLoggedIn){
            UserDataService.logout()
            Log.d("locnguyenlog1", "da log out")
            userNameNavHeader.text = ""
            userEmailNavHeader.text = ""
            userImageNavHeader.setImageResource(R.drawable.profiledefault)
            loginBtnNavHeader.text = getString(sign_in)
            userImageNavHeader.setBackgroundColor(Color.TRANSPARENT)
        }else{
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }

    fun addChannelBtnClick(view: View){
        if (AuthServices.isLoggedIn){
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.add_channel_dialog,null)

            builder.setView(dialogView).setPositiveButton("Add") { dialogInterface, i ->
                // perform some logic when clicked
                val nameTextField = dialogView.findViewById<EditText>(R.id.addChannelNameTxt)
                val descTextField = dialogView.findViewById<EditText>(R.id.addChannelDescTxt)
                val channelName = nameTextField.text.toString()
                val channelDesc = descTextField.text.toString()

                // Create channel with the channel name and description
                socket.emit("newChannel", channelName, channelDesc)
            }
        }
    }

    fun sentMessageBtnClick(view: View){

    }

    private fun hideKeyboard(){
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        if (inputManager.isAcceptingText) {
            inputManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }



}
