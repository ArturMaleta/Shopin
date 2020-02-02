package mal.art.shopin.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import mal.art.shopin.R

class AddProductToShoppingList : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_product_to_shopping_list)

    val productNameTv = findViewById<TextView>(R.id.to_shopping_list_product_name)

    val bundle: Bundle? = intent.extras
    val productName: String = intent.getStringExtra("productToAdd")

    productNameTv.text = productName

  }

  override fun onStart() {
    super.onStart()
    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
      View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
      View.SYSTEM_UI_FLAG_FULLSCREEN)
  }
}
