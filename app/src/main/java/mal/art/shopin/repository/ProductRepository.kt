package mal.art.shopin.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.CollectionReference
import mal.art.shopin.database.FirestoreHelper.Companion.dbReference
import mal.art.shopin.database.ProductRoomDatabase
import mal.art.shopin.database.ProductsDAO
import mal.art.shopin.model.ProductModel

class ProductRepository(application: Application?) {
  private val productDao: ProductsDAO
  val allProducts: LiveData<List<ProductModel?>?>?

  init {
    val db = ProductRoomDatabase.getInstance(application)
    productDao = db.productsDAO()
    allProducts = productDao.allProducts
  }

  fun insert(productModel: ProductModel?) {
    ProductRoomDatabase.dbExecutor.execute { productDao.insertProduct(productModel) }
  }

  fun testFirestore(productModel: ProductModel) {
    val prod = ProductModel("Szyneczka", "NABIAŁ", 1)

    firestore.collection("users").document("Zakupy").collection("ListaA").document()
      .set(productModel)
      .addOnSuccessListener { Log.d("Product added", "$prod") }
      .addOnFailureListener { e -> Log.w("Can't add product", e) }
  }

  fun testFirestore2() {
    val docRef = firestore.collection("users").document("userA").collection("ListA").document()
    docRef.get()
      .addOnCompleteListener { prod ->
        Log.d("DUPA", "=> ${prod.result.toString()}")
      }
      .addOnFailureListener {
        Log.d("DUPA1", "NIE MA!")
      }
  }

  fun getShoppingListFirestoreReference(shoppingListName: String): CollectionReference {
    return firestore.collection("users").document(shoppingListName).collection("zakupy")
  }

  fun getListOfShoppingListsReference(): CollectionReference {
    return firestore.collection("users")
  }

  fun addShoppingList(name: String?) {
    firestore.collection("users").document()
      .set(hashMapOf(
        "shoppingListName" to name))
  }

  companion object {

    val firestore = dbReference
  }
}