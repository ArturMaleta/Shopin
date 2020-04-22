package mal.art.shopin.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import mal.art.shopin.R
import mal.art.shopin.activity.MainScreen

class RegisterFragment : Fragment(R.layout.register_fragment_layout) {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val newUserEmail = view.findViewById<EditText>(R.id.new_user_username_et)
    val newUserPassword = view.findViewById<EditText>(R.id.new_user_password_et)
    val newUserPasswordConfirmation = view.findViewById<EditText>(R.id.new_user_password_confirm_et)
    val registerBtn = view.findViewById<Button>(R.id.new_user_confirm_btn)

    newUserPassword.transformationMethod = PasswordTransformationMethod.getInstance()
    newUserPasswordConfirmation.transformationMethod = PasswordTransformationMethod.getInstance()

    val auth = FirebaseAuth.getInstance()

    registerBtn.setOnClickListener {
      if (!TextUtils.isEmpty(newUserEmail.text.toString()) && !TextUtils.isEmpty(newUserPassword.text.toString())) {
        registerUser(auth, newUserEmail, newUserPassword, newUserPasswordConfirmation)
      } else {
        Toast.makeText(context, R.string.empty_email_password, Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun registerUser(auth: FirebaseAuth, newUserEmail: EditText, newUserPassword: EditText, newUserPasswordConfirmation: EditText) {
    val userEmail = newUserEmail.text.toString()
    val userPassword = newUserPassword.text.toString()
    val userPasswordConfirmation = newUserPasswordConfirmation?.text.toString()

    // ZRÓB OBSŁUGĘ BŁĘDÓW ZGODNIE ZE SZTUKĄ
    auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener { task ->
      if (task.isSuccessful) {
        startActivity(Intent(context, MainScreen::class.java))
        activity!!.finish()
      } else {
        when {
          (userPassword == userPasswordConfirmation).not() -> {
            Toast.makeText(context, R.string.wrong_password_confirmation_err, Toast.LENGTH_SHORT).show()
          }
          userPassword.length < 6 -> {
            Toast.makeText(context, R.string.weak_password_err, Toast.LENGTH_SHORT).show()
          }
          else -> {
            Toast.makeText(context, R.string.user_exist_err, Toast.LENGTH_SHORT).show()
          }
        }
      }
    }
  }

  companion object {
    const val TAG = "REGISTER_FRAGMENT_TAG"
  }
}