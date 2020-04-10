package mal.art.shopin.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import mal.art.shopin.R
import mal.art.shopin.model.Product
import mal.art.shopin.viewModel.ProductViewModel

class MainScreen : AppCompatActivity(R.layout.activity_main_screen) {

  private var productViewModel: ProductViewModel? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val goToShoppingListBtn: Button = findViewById(R.id.goToShoppingList_btn)
    val goToProductsList: Button = findViewById(R.id.createShoppingList_btn)
    val addNewProduct: Button = findViewById(R.id.addProduct_btn)

    goToShoppingListBtn.setOnClickListener { v: View? ->
      val shoppingList = ListOfShoppingLists()
      shoppingList.goToListOfShoppingLists(v!!)
    }

    goToProductsList.setOnClickListener { v: View? ->
      val productsList = ProductsList()
      productsList.goToProductsList(v!!)
    }

    addNewProduct.setOnClickListener {
      val goToAddNewProductIntent = Intent(this@MainScreen, AddNewProduct::class.java)
      startActivityForResult(goToAddNewProductIntent, NEW_PRODUCT_ACTIVITY_REQUEST_CODE)
    }

    productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
  }

  public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == NEW_PRODUCT_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
      val product: Product = data!!.getParcelableExtra(AddNewProduct.SAVE_PRODUCT_REPLY)
      productViewModel!!.insert(product)
      Log.d("PRODUKT ZAPISANY", product.productName + " " + product.productCategory)
    }
  }

  companion object {
    private const val NEW_PRODUCT_ACTIVITY_REQUEST_CODE = 1
  }
}