package mal.art.shopin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import mal.art.shopin.model.Product
import mal.art.shopin.repository.ProductRepository

class ProductViewModel(application: Application) : AndroidViewModel(application) {

  private val repository: ProductRepository = ProductRepository(application)

  val allProducts: LiveData<List<Product>>
    get() = repository.allProducts

  fun insert(product: Product?) {
    repository.insert(product)
  }

}