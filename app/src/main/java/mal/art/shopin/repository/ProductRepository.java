package mal.art.shopin.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import mal.art.shopin.database.ProductRoomDatabase;
import mal.art.shopin.database.ProductsDAO;
import mal.art.shopin.model.Product;

public class ProductRepository {

  private ProductsDAO productDao;

  private LiveData<List<Product>> allProducts;

  public ProductRepository(Application application) {
    ProductRoomDatabase db = ProductRoomDatabase.getInstance(application);
    productDao = db.productsDAO();
    allProducts = productDao.getAllProducts();
  }

  public LiveData<List<Product>> getAllProducts() {
    return allProducts;
  }

  public void insert(final Product product) {
    ProductRoomDatabase.dbExecutor.execute(() ->
      productDao.insertProduct(product));
  }
}
