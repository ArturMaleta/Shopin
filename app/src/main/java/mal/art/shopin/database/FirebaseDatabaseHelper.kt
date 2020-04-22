package mal.art.shopin.database

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object FirebaseDatabaseHelper {

  fun getReference(): DatabaseReference{
    val db = FirebaseDatabase.getInstance("https://shopin-f7853.firebaseio.com")
    db.setPersistenceEnabled(true)
    val dbReference = db.getReference("ShoppingList")
    dbReference.keepSynced(true)

    return dbReference
  }
}