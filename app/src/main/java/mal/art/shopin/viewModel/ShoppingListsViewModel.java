package mal.art.shopin.viewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.google.firebase.database.DataSnapshot;
import mal.art.shopin.repository.ProductRepository;

public class ShoppingListsViewModel extends AndroidViewModel {

  private ProductRepository repository;

  public ShoppingListsViewModel(@NonNull Application application) {
    super(application);
    repository = new ProductRepository(application);
  }

  public LiveData<DataSnapshot> getAllShoppingLists() {
    return repository.getDataSnapshotLiveData();
  }
}

