package mal.art.shopin.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import mal.art.shopin.R

class RegisterUser : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_register_user)

    val newUserEmail = findViewById<EditText>(R.id.new_user_username_et)
    val newUserPassword = findViewById<EditText>(R.id.new_user_password_et)
    val newUserPasswordConfirmation = findViewById<EditText>(R.id.new_user_password_confirm_et)
    val registerBtn = findViewById<Button>(R.id.new_user_confirm_btn)

    newUserPassword.transformationMethod = PasswordTransformationMethod.getInstance()
    newUserPasswordConfirmation.transformationMethod = PasswordTransformationMethod.getInstance()

    val auth = FirebaseAuth.getInstance()

    registerBtn.setOnClickListener {
      if (!TextUtils.isEmpty(newUserEmail.text.toString()) && !TextUtils.isEmpty(newUserPassword.text.toString())) {
        registerUser(auth, newUserEmail, newUserPassword, newUserPasswordConfirmation)
      } else {
        Toast.makeText(this, R.string.empty_email_password, Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun registerUser(auth: FirebaseAuth, newUserEmail: EditText, newUserPassword: EditText, newUserPasswordConfirmation: EditText?) {
    val userEmail = newUserEmail.text.toString()
    val userPassword = newUserPassword.text.toString()
    val userPasswordConfirmation = newUserPasswordConfirmation?.text.toString()

    // ZRÓB OBSŁUGĘ BŁĘDÓW ZGODNIE ZE SZTUKĄ
    auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener { task ->
      if (task.isSuccessful) {
        startActivity(Intent(this, MainScreen::class.java))
        finish()
      } else {
        when {
          (userPassword == userPasswordConfirmation).not() -> {
            Toast.makeText(this, R.string.wrong_password_confirmation_err, Toast.LENGTH_SHORT).show()
          }
          userPassword.length < 6 -> {
            Toast.makeText(this, R.string.weak_password_err, Toast.LENGTH_SHORT).show()
          }
          else -> {
            Toast.makeText(this, R.string.user_exist_err, Toast.LENGTH_SHORT).show()
          }
        }
      }
    }
  }
}
