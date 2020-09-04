package mal.art.shopin.database

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FirebaseDatabaseHelper {

  fun getReference(): DatabaseReference{
    val db = FirebaseDatabase.getInstance("https://shopin-f7853.firebaseio.com")
    db.setPersistenceEnabled(true)
    val dbReference = db.getReference("ShoppingList")
    dbReference.keepSynced(true)

    return dbReference
  }

  fun getRef(): FirebaseFirestore {
    return Firebase.firestore
  }

  fun get() {
    getRef().collection("lista")
      .get()
      .addOnCompleteListener { "blablabla" }
  }
}