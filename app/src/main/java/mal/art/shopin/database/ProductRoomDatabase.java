package mal.art.shopin.database;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import mal.art.shopin.model.Product;
import mal.art.shopin.model.ProductCategoryConverter;
import mal.art.shopin.model.ProductCategoryEnum;

@Database(entities = Product.class, exportSchema = false, version = 2)
@TypeConverters(ProductCategoryConverter.class)
public abstract class ProductRoomDatabase extends RoomDatabase {

  private static final String DB_NAME = "shopinDb";

  private static final int NUMBERS_OF_THREADS = 4;

  private static ProductRoomDatabase INSTANCE;

  public static final ExecutorService dbExecutor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);

  private static final String owoc = "Owoce";

  private static final String warzywo = "Warzywa";

  public static synchronized ProductRoomDatabase getInstance(Context context) {
    if (INSTANCE == null) {
      INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ProductRoomDatabase.class, DB_NAME)
        .fallbackToDestructiveMigration()
        .addCallback(sCallback)
        .build();
    }
    return INSTANCE;
  }

  public abstract ProductsDAO productsDAO();

  private static RoomDatabase.Callback sCallback = new RoomDatabase.Callback() {
    @Override
    public void onCreate(@NonNull SupportSQLiteDatabase db) {
      super.onCreate(db);
      prePopulateProducts();
    }

    @Override
    public void onOpen(@NonNull SupportSQLiteDatabase db) {
      super.onOpen(db);
      ProductsDAO dao = INSTANCE.productsDAO();
      LiveData<List<Product>> productList = dao.getAllProducts();
    }
  };

  private static void prePopulateProducts() {
    dbExecutor.execute(() -> {
      ProductsDAO dao = INSTANCE.productsDAO();

      // dopóki nie ogarnę czemu mi się baza nie prepopuluje, muszę pracować na tym
      Product agrest = new Product("Agrest", ProductCategoryEnum.OWOCE);
      Product arbuz = new Product("Arbuz", ProductCategoryEnum.OWOCE);
      Product ananas = new Product("Ananas", ProductCategoryEnum.OWOCE);
      Product baklazan = new Product("Bakłażan", ProductCategoryEnum.WARZYWA);
      Product bob = new Product("Bób", ProductCategoryEnum.WARZYWA);
      Product brokul = new Product("Brokuł", ProductCategoryEnum.WARZYWA);
      dao.insertProduct(agrest);
      dao.insertProduct(arbuz);
      dao.insertProduct(ananas);
      dao.insertProduct(baklazan);
      dao.insertProduct(bob);
      dao.insertProduct(brokul);

      Log.d("BAZA DANYCH ZAŁADOWANA", "POPRAWNIE");
    });
  }
}