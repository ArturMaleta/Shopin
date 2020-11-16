package mal.art.shopin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.firestore.CollectionReference
import mal.art.shopin.repository.ProductRepository

class ListOfShoppingListsViewModel(application: Application) : AndroidViewModel(application) {
  private val repository: ProductRepository = ProductRepository(application)

  fun insertShoppingList(name: String) {
    repository.addShoppingList(name)
  }

  fun getListOfShoppingListsReference(): CollectionReference {
    return repository.getListOfShoppingListsReference()
  }
}