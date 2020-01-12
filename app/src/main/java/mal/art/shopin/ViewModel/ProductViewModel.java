package mal.art.shopin.ViewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;
import mal.art.shopin.Model.Product;
import mal.art.shopin.Repository.ProductRepository;

public class ProductViewModel extends AndroidViewModel {

  private ProductRepository mRepository;
  private String productName, category;
  private int quantity;
  private LiveData<List<Product>> mAllProducts;

  public ProductViewModel(@NonNull Application application) {
    super(application);
    mRepository = new ProductRepository(application);
    mAllProducts = mRepository.getAllProducts();
  }

  public LiveData<List<Product>> getAllProducts() {
    return mAllProducts;
  }

  public void insert(Product product) { mRepository.insert(product); }

}
