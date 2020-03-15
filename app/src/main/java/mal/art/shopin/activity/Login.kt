package mal.art.shopin.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import mal.art.shopin.R

class Login : AppCompatActivity() {

  private lateinit var auth: FirebaseAuth

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    val userNameEt = findViewById<EditText>(R.id.username_et)
    val passwordEt = findViewById<EditText>(R.id.password_et)
    passwordEt.transformationMethod = PasswordTransformationMethod.getInstance()

    val loginBtn = findViewById<Button>(R.id.login_btn)

    val goToRegisterBtn = findViewById<Button>(R.id.go_to_register_btn)
    goToRegisterBtn.setOnClickListener { goToRegisterActivity() }

    val goToForgotPassword = findViewById<TextView>(R.id.forgot_password_tv)
    goToForgotPassword.setOnClickListener { goToForgotPasswordActivity() }

    auth = FirebaseAuth.getInstance()

    loginBtn.setOnClickListener {
      if (TextUtils.isEmpty(userNameEt.text.toString()) || TextUtils.isEmpty(passwordEt.text.toString())) {
        Toast.makeText(this, R.string.empty_email_password, Toast.LENGTH_SHORT).show()
      } else {
        login(auth, userNameEt, passwordEt)
      }
    }
  }

  private fun login(auth: FirebaseAuth, username: EditText, password: EditText) {
    val username = username.text.toString()
    val password = password.text.toString()

    auth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this) { task ->
      if (task.isSuccessful) {
        Toast.makeText(this, "Siema, $username", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainScreen::class.java))
        finish()
      } else {
        Toast.makeText(baseContext, R.string.login_failed_err, Toast.LENGTH_SHORT).show()
      }
    }
  }

  override fun onStart() {
    super.onStart()
    val overlay = findViewById<View>(R.id.login_main_layout)
    overlay.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN
  }

  private fun goToRegisterActivity() {
    val intent = Intent(this, RegisterUser::class.java)
    startActivity(intent)
  }

  private fun goToForgotPasswordActivity() {
    val intent = Intent(this, ForgotPassword::class.java)
    startActivity(intent)
  }
}
