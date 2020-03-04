package mal.art.shopin.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import mal.art.shopin.R
import mal.art.shopin.model.ProductUnitEnum

class AddProductToShoppingList : AppCompatActivity() {



  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_product_to_shopping_list)


    val productNameTv = findViewById<TextView>(R.id.to_shopping_list_product_name)
    val productQuantityEt = findViewById<EditText>(R.id.to_shopping_list_product_quantity)
    val productUnitSp = findViewById<Spinner>(R.id.to_shopping_list_product_unit_spinner)
    val addProductBtn = findViewById<Button>(R.id.add_product_to_shopping_list_btn)

    val productName = intent.getStringExtra("productNameToAdd")
    val productCategory = intent.getStringExtra("productCategoryToAdd")
    productNameTv.text = productName

    showPopupWindow()
    loadSpinnerValues(productUnitSp)

    // sprawdź jakie wyniki będą miały pozostałe funckje zakresowe w tym przypadku
    addProductBtn.setOnClickListener {
      val productQuantity = productQuantityEt.text.toString()
      val productUnit = productUnitSp.selectedItem.toString()
      val productToSaveIntent = Intent(this, ProductsList::class.java).apply {
        putExtra("productName", productName)
        putExtra("productCategory", productCategory)
        putExtra("productQuantity", productQuantity)
        putExtra("productUnit", productUnit)
      }
      if (TextUtils.isEmpty(productQuantityEt.text)) {
        setResult(Activity.RESULT_CANCELED)
      } else {
        setResult(Activity.RESULT_OK, productToSaveIntent)
        super.finish()
      }
    }
  }

  private fun showPopupWindow() {

    val dm: DisplayMetrics = applicationContext.resources.displayMetrics
    windowManager.defaultDisplay.getMetrics(dm)

    val width = dm.widthPixels
    val height = dm.heightPixels

    window.setLayout(
      ((width * .8).toInt()),
      ((height * .35).toInt())
      //      (width * resources.getDimension(R.dimen.popup_window_width_multiplier)).toInt(),
      //      (height * resources.getDimension(R.dimen.popup_window_height_multiplier)).toInt()
    )
  }

  private fun loadProductName(productNameTv: TextView) {
    productNameTv.text = intent.getStringExtra("productNameToAdd")
  }

  private fun loadSpinnerValues(productUnitSp: Spinner) {
    val adapter = ArrayAdapter<ProductUnitEnum>(
      this,
      android.R.layout.simple_list_item_1,
      ProductUnitEnum.values()
    )
    productUnitSp.adapter = adapter
  }

  override fun onStart() {
    super.onStart()
    window.decorView.systemUiVisibility = (
      View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
        View.SYSTEM_UI_FLAG_FULLSCREEN
      )
  }
}
