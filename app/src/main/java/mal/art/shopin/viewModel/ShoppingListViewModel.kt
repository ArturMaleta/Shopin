package mal.art.shopin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import mal.art.shopin.database.FirebaseQueryLiveData
import mal.art.shopin.repository.ProductRepository

class ShoppingListViewModel(application: Application) : AndroidViewModel(application) {
  private val repository: ProductRepository = ProductRepository(application)
  private val shoppingListName : String? = null

  fun getShoppingList(shoppingListName: String) : LiveData<DataSnapshot>? {
    return repository.getShoppingListLiveData(shoppingListName)
  }
}