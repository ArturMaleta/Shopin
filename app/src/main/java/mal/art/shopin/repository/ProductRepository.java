package mal.art.shopin.repository;

import android.app.Application;
import android.util.Log;
import androidx.lifecycle.LiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import mal.art.shopin.database.FirebaseDatabaseHelper;
import mal.art.shopin.database.FirebaseQueryLiveData;
import mal.art.shopin.database.ProductRoomDatabase;
import mal.art.shopin.database.ProductsDAO;
import mal.art.shopin.model.Product;

public class ProductRepository {

  private static final DatabaseReference fireDatabase = FirebaseDatabaseHelper.INSTANCE.getDbRef();

  private ProductsDAO productDao;

  private LiveData<List<Product>> allProducts;

  private final FirebaseQueryLiveData shoppingLists = new FirebaseQueryLiveData(fireDatabase);

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

  public static void insertToShoppingList(
    String productName,
    String productCategory,
    int productQuantity,
    String productUnit,
    String shoppingStatus
  ) {

    Product product =
      new Product(productCategory, productQuantity, productUnit, shoppingStatus);
    fireDatabase.child(getDatetime()).child(productName).setValue(product);

    Log.d("DUPA", product.toString());
  }

  private static String getDatetime() {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar = Calendar.getInstance();
    String datetime = formatter.format(calendar.getTime());

    return datetime;
  }

  public LiveData<DataSnapshot> getDataSnapshotLiveData() {
    return shoppingLists;
  }
}
