package mal.art.shopin.ViewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import mal.art.shopin.Model.Product;
import mal.art.shopin.Repository.ProductRepository;

public class ProductViewModel extends AndroidViewModel {

  private ProductRepository repository;

  private LiveData<List<Product>> allProducts;

  public ProductViewModel(@NonNull Application application) {
    super(application);
    repository = new ProductRepository(application);
    allProducts = repository.getAllProducts();
  }

  public LiveData<List<Product>> getAllProducts() {
    return allProducts;
  }

  public void insert(Product product) {
    repository.insert(product);
  }
}
