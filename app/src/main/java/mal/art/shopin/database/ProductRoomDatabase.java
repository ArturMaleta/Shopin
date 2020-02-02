package mal.art.shopin.database;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import mal.art.shopin.model.Product;

@Database(entities = Product.class, exportSchema = false, version = 2)
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
      dbExecutor.execute(() -> {
        ProductsDAO dao = INSTANCE.productsDAO();
        //        productList jest pusty
        //        LiveData<List<Product>> productList = dao.getAllProducts();

        // dopóki nie ogarnę czemu mi się baza nie prepopuluje, muszę pracować na tym
        Product agrest = new Product("Agrest", owoc);
        Product arbuz = new Product("Arbuz", owoc);
        Product ananas = new Product("Ananas", owoc);
        Product baklazan = new Product("Bakłażan", warzywo);
        Product bob = new Product("Bób", warzywo);
        Product brokul = new Product("Brokuł", warzywo);
        dao.insertProduct(agrest);
        dao.insertProduct(arbuz);
        dao.insertProduct(ananas);
        dao.insertProduct(baklazan);
        dao.insertProduct(bob);
        dao.insertProduct(brokul);

        Log.d("BAZA DANYCH ZAŁADOWANA", "POPRAWNIE");
      });
    }

    @Override
    public void onOpen(@NonNull SupportSQLiteDatabase db) {
      super.onOpen(db);
      ProductsDAO dao = INSTANCE.productsDAO();
      LiveData<List<Product>> productList = dao.getAllProducts();
    }
  };
}
