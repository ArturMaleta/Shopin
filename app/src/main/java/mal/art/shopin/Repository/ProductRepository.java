package mal.art.shopin.Repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.google.firebase.database.DatabaseReference;
import java.util.List;
import mal.art.shopin.Database.ProductRoomDatabase;
import mal.art.shopin.Database.ProductsDAO;
import mal.art.shopin.Model.Product;

public class ProductRepository {

  private ProductsDAO mProductDao;

  private LiveData<List<Product>> mAllProducts;

  public ProductRepository(Application application) {
    ProductRoomDatabase db = ProductRoomDatabase.getInstance(application);
    mProductDao = db.productsDAO();
    mAllProducts = mProductDao.getAllProducts();
  }

  public LiveData<List<Product>> getAllProducts() {
    return mAllProducts;
  }

  public void insert(final Product product) {
    ProductRoomDatabase.dbExecutor.execute(() ->
      mProductDao.insertProduct(product));
  }
}
