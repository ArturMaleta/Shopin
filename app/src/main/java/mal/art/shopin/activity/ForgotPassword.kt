package mal.art.shopin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import mal.art.shopin.R

class ForgotPassword : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_forgot_password)

    // później graficznie ogarnę tę przezroczystość, żeby tylko zdjęcie ładować, a nie jeszcze ustawiać mu opacity
    val opacityToBackgroundImage: ImageView = findViewById(R.id.opacity_background_image)
    opacityToBackgroundImage.imageAlpha = 80
  }

  override fun onStart() {
    super.onStart()
    val overlay = findViewById<View>(R.id.forgot_password_main_layout)
    overlay.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN
  }
}
