package mal.art.shopin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.CollectionReference
import mal.art.shopin.model.ProductModel
import mal.art.shopin.repository.ProductRepository

class ProductViewModel(application: Application) : AndroidViewModel(application) {

  private val repository: ProductRepository = ProductRepository(application)

  val allProducts: LiveData<List<ProductModel?>?>?
    get() = repository.allProducts

  fun insert(productModel: ProductModel?) {
    repository.insert(productModel)
    println("produkt wsadzony: $productModel")
  }

  fun testFirestore(productModel: ProductModel) {
    repository.testFirestore(productModel)
    println("produktFirestore: $productModel")
  }

  fun testFirestore2() {
    repository.testFirestore2()
  }

  fun getShoppingListFirestoreReference(): CollectionReference {
    return repository.getShoppingListFirestoreReference()
  }

}