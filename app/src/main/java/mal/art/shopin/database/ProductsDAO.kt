package mal.art.shopin.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mal.art.shopin.model.Product

@Dao
interface ProductsDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertProduct(product: Product?)

  @get:Query("SELECT * FROM products")
  val allProducts: LiveData<List<Product?>?>?
}