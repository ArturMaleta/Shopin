package mal.art.shopin.repository;

import android.app.Application;
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
import mal.art.shopin.model.ProductModel;

public class ProductRepository2 {

  private static final DatabaseReference fireDatabase = FirebaseDatabaseHelper.INSTANCE.getReference();

  private ProductsDAO productDao;

  private LiveData<List<ProductModel>> allProducts;

  private final FirebaseQueryLiveData shoppingLists = new FirebaseQueryLiveData(fireDatabase);

  private FirebaseQueryLiveData shoppingList = null;

  public ProductRepository2(Application application) {
    ProductRoomDatabase db = ProductRoomDatabase.getInstance(application);
    productDao = db.productsDAO();
    allProducts = productDao.getAllProducts();
  }

  public LiveData<List<ProductModel>> getAllProducts() {
    return allProducts;
  }

  public void insert(final ProductModel productModel) {
    ProductRoomDatabase.dbExecutor.execute(() ->
      productDao.insertProduct(productModel));
  }

  public static void insertToShoppingList(ProductModel productModel) {
    fireDatabase.child(getDatetime()).child(productModel.getProductName()).setValue(productModel);
  }

  public static void changeShoppingListName(String oldName, String newName) {
    String key = fireDatabase.child(oldName).getKey();
    fireDatabase.child(oldName).setValue(newName);
  }

  public LiveData<DataSnapshot> getListOfShoppingListsLiveData() {
    return shoppingLists;
  }

  public LiveData<DataSnapshot> getShoppingListLiveData(String shoppingListName) {
    shoppingList = new FirebaseQueryLiveData(fireDatabase.child(shoppingListName));
    return shoppingList;
  }

  private static String getDatetime() {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar = Calendar.getInstance();

    return formatter.format(calendar.getTime());
  }

  public void addShoppingListName(String name) {
    fireDatabase.child(name).setValue(true);
  }

  public void addProductToParticularShoppingList(String shoppingListName, ProductModel productModel) {
    fireDatabase.child(shoppingListName).child(productModel.getProductName()).setValue(productModel);
  }
}
