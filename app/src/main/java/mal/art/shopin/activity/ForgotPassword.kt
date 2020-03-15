package mal.art.shopin.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import mal.art.shopin.R

class ForgotPassword : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_forgot_password)

    val emailEt = findViewById<EditText>(R.id.forgot_password_email_et)
    val sendBtn = findViewById<Button>(R.id.send_reset_passeword_btn)

    val auth = FirebaseAuth.getInstance()

    sendBtn.setOnClickListener {
      if(!TextUtils.isEmpty(emailEt.text.toString())) {
        sendEmail(auth, emailEt)
      } else {
        Toast.makeText(this, R.string.forgot_password_err, Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun sendEmail(auth: FirebaseAuth, email: EditText) {
    val email = email.text.toString()

    auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
      if (task.isSuccessful) {
        Toast.makeText(this, "Email wysłany", Toast.LENGTH_SHORT).show()
      } else {
        Toast.makeText(this, "Zły email", Toast.LENGTH_SHORT).show()
      }
    }
  }

  override fun onStart() {
    super.onStart()
    val overlay = findViewById<View>(R.id.forgot_password_main_layout)
    overlay.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN
  }
}
