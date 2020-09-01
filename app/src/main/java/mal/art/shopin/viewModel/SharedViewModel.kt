package mal.art.shopin.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class SharedViewModel(application: Application) : AndroidViewModel(application) {

  val shoppingListName: MutableLiveData<String> by lazy { MutableLiveData<String>().apply {

  } }
}