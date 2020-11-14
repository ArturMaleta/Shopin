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
import mal.art.shopin.model.ProductModel;

@Database(entities = ProductModel.class, exportSchema = false, version = 2)
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
      LiveData<List<ProductModel>> productList = dao.getAllProducts();
    }
  };

  private static void prePopulateProducts() {
    dbExecutor.execute(() -> {
      ProductsDAO dao = INSTANCE.productsDAO();

      // dopóki nie ogarnę czemu mi się baza nie prepopuluje, muszę pracować na tym
      ProductModel agrest = new ProductModel("Agrest", "OWOCE");
      ProductModel arbuz = new ProductModel("Arbuz", "OWOCE");
      ProductModel ananas = new ProductModel("Ananas", "OWOCE");
      ProductModel baklazan = new ProductModel("Bakłażan", "OWOCE");
      ProductModel bob = new ProductModel("Bób", "OWOCE");
      ProductModel brokul = new ProductModel("Brokuł", "OWOCE");
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