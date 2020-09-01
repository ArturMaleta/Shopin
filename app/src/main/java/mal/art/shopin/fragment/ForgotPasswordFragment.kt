package mal.art.shopin.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import mal.art.shopin.R

class ForgotPasswordFragment : Fragment(R.layout.forgot_password_fragment_layout) {

  private lateinit var auth: FirebaseAuth

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val emailEt = view.findViewById<EditText>(R.id.forgot_password_email_et)
    val sendBtn = view.findViewById<Button>(R.id.send_reset_passeword_btn)

    auth = FirebaseAuth.getInstance()

    sendBtn.setOnClickListener {
      if(!TextUtils.isEmpty(emailEt.text.toString())) {
        sendEmail(auth, emailEt)
      } else {
        Toast.makeText(context, R.string.forgot_password_err, Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun sendEmail(auth: FirebaseAuth, email: EditText) {
    val email = email.text.toString()

    auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
      if (task.isSuccessful) {
        Toast.makeText(context, "Email wysłany", Toast.LENGTH_SHORT).show()
      } else {
        Toast.makeText(context, "Zły email", Toast.LENGTH_SHORT).show()
      }
    }
  }

  companion object {
    const val TAG = "FORGOT_PASSWORD_TAG"
  }
}