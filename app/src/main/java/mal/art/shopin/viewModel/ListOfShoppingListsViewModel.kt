package mal.art.shopin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import mal.art.shopin.repository.ProductRepository

class ListOfShoppingListsViewModel(application: Application) : AndroidViewModel(application) {
  private val repository: ProductRepository = ProductRepository(application)

  fun getAllShoppingLists() : LiveData<DataSnapshot> {
    return repository.listOfShoppingListsLiveData
  }
}