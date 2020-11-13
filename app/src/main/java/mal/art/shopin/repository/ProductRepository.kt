package mal.art.shopin.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.firestore.CollectionReference
import mal.art.shopin.database.FirebaseDatabaseHelper.getReference
import mal.art.shopin.database.FirebaseQueryLiveData
import mal.art.shopin.database.FirestoreHelper.Companion.dbReference
import mal.art.shopin.database.ProductRoomDatabase
import mal.art.shopin.database.ProductsDAO
import mal.art.shopin.model.ProductModel
import mal.art.shopin.model.ProductCategoryEnum
import java.text.SimpleDateFormat
import java.util.Calendar

class ProductRepository(application: Application?) {
  private val productDao: ProductsDAO
  val allProducts: LiveData<List<ProductModel?>?>?
  private val shoppingLists = FirebaseQueryLiveData(fireDatabase)
  private var shoppingList: FirebaseQueryLiveData? = null

  fun insert(productModel: ProductModel?) {
    ProductRoomDatabase.dbExecutor.execute { productDao.insertProduct(productModel) }
  }

  fun testFirestore(productModel: ProductModel) {
    val prod = ProductModel("Szyneczka", ProductCategoryEnum.NABIAŁ, 1)

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

  fun getShoppingListFirestoreReference(): CollectionReference {
    return firestore.collection("users").document("ListaA").collection("shoppingList")
  }

  fun getListOfShoppingListsReference(): CollectionReference {
    return firestore.collection("users")
  }

  val listOfShoppingListsLiveData: LiveData<DataSnapshot?>
    get() = shoppingLists

  fun getShoppingListLiveData(shoppingListName: String?): LiveData<DataSnapshot?> {
    shoppingList = FirebaseQueryLiveData(fireDatabase.child(shoppingListName!!))
    return shoppingList as FirebaseQueryLiveData
  }

  fun addShoppingListName(name: String?) {
    fireDatabase.child(name!!).setValue(true)
  }

  fun addProductToParticularShoppingList(shoppingListName: String?, productModel: ProductModel) {
    fireDatabase.child(shoppingListName!!).child(productModel.productName).setValue(productModel)
  }

  companion object {
    private val fireDatabase = getReference()
    fun insertToShoppingList(productModel: ProductModel) {
      fireDatabase.child(datetime).child(productModel.productName).setValue(productModel)
    }

    fun changeShoppingListName(oldName: String?, newName: String?) {
      val key = fireDatabase.child(oldName!!).key
      fireDatabase.child(oldName).setValue(newName)
    }

    private val datetime: String
      private get() {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()
        return formatter.format(calendar.time)
      }

    //
    val firestore = dbReference

    //
  }

  init {
    val db = ProductRoomDatabase.getInstance(application)
    productDao = db.productsDAO()
    allProducts = productDao.allProducts
  }
}