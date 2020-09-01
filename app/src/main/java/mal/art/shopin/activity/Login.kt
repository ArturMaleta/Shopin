package mal.art.shopin.activity

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import mal.art.shopin.R
import mal.art.shopin.fragment.ForgotPasswordFragment
import mal.art.shopin.fragment.LogInSignInFragment
import mal.art.shopin.fragment.RegisterFragment

class Login : AppCompatActivity(R.layout.activity_login) {

  private lateinit var auth: FirebaseAuth

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    checkIfUserIsLoggedIn()
  }

  private fun checkIfUserIsLoggedIn() {
    val user = FirebaseAuth.getInstance().currentUser

    if (user != null) {
      Toast.makeText(this, "Zalogowano automatycznie", Toast.LENGTH_SHORT).show()
      startActivity(Intent(this, MainScreen::class.java))
      finish()
    } else {
      initializeLoginScreen()
    }
  }

  private fun initializeLoginScreen() {
    val fragment = LogInSignInFragment()
    val ft = supportFragmentManager.beginTransaction()
    ft.replace(R.id.log_in_sign_in_fragment_container, fragment, LogInSignInFragment.TAG).addToBackStack(null).commit()
  }
}
