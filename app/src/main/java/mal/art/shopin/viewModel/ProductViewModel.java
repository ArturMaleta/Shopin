package mal.art.shopin.viewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import mal.art.shopin.model.Product;
import mal.art.shopin.repository.ProductRepository;

public class ProductViewModel extends AndroidViewModel {

  private ProductRepository repository;

  public ProductViewModel(@NonNull Application application) {
    super(application);
    repository = new ProductRepository(application);
  }

  public LiveData<List<Product>> getAllProducts() {
    return repository.getAllProducts();
  }

  public void insert(Product product) {
    repository.insert(product);
  }
}
