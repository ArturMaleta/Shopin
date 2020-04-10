package mal.art.shopin.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mal.art.shopin.R
import mal.art.shopin.adapter.ProductListAdapter
import mal.art.shopin.adapter.ProductListAdapter.OnAddProductListener
import mal.art.shopin.model.Product
import mal.art.shopin.repository.ProductRepository
import mal.art.shopin.viewModel.ProductViewModel
import java.util.ArrayList

class ProductsList : AppCompatActivity(R.layout.activity_products_list), OnAddProductListener {

  private val productsList: MutableList<Product> = ArrayList()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val recyclerView = findViewById<RecyclerView>(R.id.product_list_recycler_view)

    val adapter = ProductListAdapter(this, this)

    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(this)

    val productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
    productViewModel.allProducts.observe(this, Observer { products: List<Product>? ->
      adapter.setProducts(products)
      productsList.addAll(products!!)
    })
  }

  fun goToProductsList(view: View) {
    val productsListIntent = Intent(view.context, ProductsList::class.java)
    view.context.startActivity(productsListIntent)
  }

  override fun onProductClick(position: Int) {
    val productName = productsList[position].productName

    val productCategory = productsList[position].productCategory
    val intent = Intent(this@ProductsList, AddProductToShoppingList::class.java)

    intent.putExtra("productNameToAdd", productName)
    intent.putExtra("productCategoryToAdd", productCategory)

    startActivityForResult(intent, PRODUCT_ADD_REQUEST_CODE)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == PRODUCT_ADD_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
      val product = Product(
        data!!.getStringExtra("productName"),
        data.getStringExtra("productCategory"),
        Integer.valueOf(data.getStringExtra("productQuantity")!!),
        data.getStringExtra("productUnit"),
        getString(R.string.default_shopping_status)
      )
      ProductRepository.insertToShoppingList(product)
      Log.d("PRODUKT ZAPISANY DO FB", "$product.productName $product.productQuantity $product.productUnit")
    }
  }

  companion object {
    const val PRODUCT_ADD_REQUEST_CODE = 1
  }
}