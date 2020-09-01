package mal.art.shopin.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import mal.art.shopin.R
import mal.art.shopin.activity.MainScreen

class LogInSignInFragment : Fragment(R.layout.log_in_sign_in_fragment_layout) {

  private lateinit var auth: FirebaseAuth

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    auth = FirebaseAuth.getInstance()

    val userNameEt = view.findViewById<EditText>(R.id.username_et)

    val passwordEt = view.findViewById<EditText>(R.id.password_et)
    passwordEt.transformationMethod = PasswordTransformationMethod.getInstance()

    val loginBtn = view.findViewById<Button>(R.id.login_btn)
    loginBtn.setOnClickListener {
      if (TextUtils.isEmpty(userNameEt.text.toString()) || TextUtils.isEmpty(passwordEt.text.toString())) {
        Toast.makeText(context, R.string.empty_email_password, Toast.LENGTH_SHORT).show()
      } else {
        login(auth, userNameEt, passwordEt)
      }
    }

    val registerBtn = view.findViewById<Button>(R.id.go_to_register_btn)
    registerBtn.setOnClickListener { goToRegisterFragment() }

    val forgotPasswordTv = view.findViewById<TextView>(R.id.forgot_password_tv)
    forgotPasswordTv.setOnClickListener { goToForgotPasswordFragment() }
  }

  private fun goToRegisterFragment() {
    val fragment = RegisterFragment()
    val ft = activity!!.supportFragmentManager.beginTransaction()
    ft.replace(R.id.register_fragment_container, fragment, RegisterFragment.TAG).addToBackStack(null).commit()
  }

  private fun goToForgotPasswordFragment() {
    val fragment = ForgotPasswordFragment()
    val ft = activity!!.supportFragmentManager.beginTransaction()
    ft.replace(R.id.forgot_fragment_container, fragment, RegisterFragment.TAG).addToBackStack(null).commit()
  }

  private fun login(auth: FirebaseAuth, username: EditText, password: EditText) {
    val username = username.text.toString()
    val password = password.text.toString()

    auth.signInWithEmailAndPassword(username, password).addOnCompleteListener { task ->
      if (task.isSuccessful) {
        Toast.makeText(context, "Siema, $username", Toast.LENGTH_SHORT).show()
        startActivity(Intent(context, MainScreen::class.java))
        activity!!.finish()
      } else {
        Toast.makeText(context, R.string.login_failed_err, Toast.LENGTH_SHORT).show()
      }
    }
  }

  companion object {
    const val TAG = "LOG_IN_SIGN_IN_FRAGMENT_TAG"
  }
}