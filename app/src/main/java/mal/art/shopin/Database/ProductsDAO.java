package mal.art.shopin.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import mal.art.shopin.Model.Product;

@Dao
public interface ProductsDAO {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertProduct(Product product);

  @Query("SELECT * FROM products")
  LiveData<List<Product>> getAllProducts();
}
