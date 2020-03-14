package mal.art.shopin.database

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class FirebaseQueryLiveData : LiveData<DataSnapshot?> {
  private val query: Query
  private val listener = CustomValueEventListener()

  constructor(query: Query) {
    this.query = query
  }

  constructor(ref: DatabaseReference) {
    query = ref
  }

  override fun onActive() {
    Log.d(LOG_TAG, "onActive")
    query.addValueEventListener(listener)
  }

  override fun onInactive() {
    Log.d(LOG_TAG, "onInactive")
    query.removeEventListener(listener)
  }

  private inner class CustomValueEventListener : ValueEventListener {
    override fun onDataChange(dataSnapshot: DataSnapshot) {
      value = dataSnapshot
    }

    override fun onCancelled(databaseError: DatabaseError) {
      Log.e(LOG_TAG, "Can't listen to query $query", databaseError.toException())
    }
  }

  companion object {
    private const val LOG_TAG = "FirebaseQueryLiveData"
  }
}