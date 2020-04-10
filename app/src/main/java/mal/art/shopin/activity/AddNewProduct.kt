package mal.art.shopin.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.DisplayMetrics
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import mal.art.shopin.model.Product
import mal.art.shopin.model.ProductCategoryEnum
import mal.art.shopin.R

class AddNewProduct : AppCompatActivity(R.layout.activity_add_new_product) {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val productName: EditText = findViewById(R.id.product_name_to_save)
    val productCategory: Spinner = findViewById(R.id.add_new_product_spinner)

    val dm = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(dm)
    val width = dm.widthPixels
    val height = dm.heightPixels

    window.setLayout(
      (width * .8).toInt(),
      (height * .35).toInt() //      (int) (width * getBaseContext().getResources().getDimension(R.dimen.popup_window_width_multiplier)),
      //      (int) (height * getBaseContext().getResources().getDimension(R.dimen.popup_window_height_multiplier))
    )
    val saveProductBtn = findViewById<Button>(R.id.save_button)

    productCategory.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ProductCategoryEnum.values())

    saveProductBtn.setOnClickListener {
      val replyIntent = Intent(this@AddNewProduct, MainScreen::class.java)
      if (TextUtils.isEmpty(productName.text) && TextUtils.isEmpty(
          productCategory.selectedItem.toString())) {
        setResult(Activity.RESULT_CANCELED, replyIntent)
      } else {
        val productToPut = Product(productName.text.toString(),
                                   productCategory.selectedItem.toString())
        replyIntent.putExtra(SAVE_PRODUCT_REPLY, productToPut)
        setResult(Activity.RESULT_OK, replyIntent)
      }

      finish()
    }
  }

  companion object {
    const val SAVE_PRODUCT_REPLY = "mal.art.shopin.save_product.REPLY"
  }
}