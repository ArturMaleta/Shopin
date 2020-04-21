package mal.art.shopin.database

import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class FirebaseQueryLiveData : LiveData<DataSnapshot?> {

  private var query: Query? = null

  private val listener = CustomValueEventListener()

  private var listenerRemovePending = false

  private val handler: Handler? = null

  private val removeListener: Runnable? = kotlinx.coroutines.Runnable {
    kotlin.run {
      query?.removeEventListener(listener)
      listenerRemovePending = false
    }
  }

  constructor(query: Query) {
    this.query = query
  }

  constructor(ref: DatabaseReference) {
    query = ref
  }

  override fun onActive() {
    Log.d(LOG_TAG, "onActive")
    if (listenerRemovePending) {
      handler?.removeCallbacks(removeListener)
    } else {
      query?.addValueEventListener(listener)
    }
    listenerRemovePending = false
  }

  override fun onInactive() {
    Log.d(LOG_TAG, "onInactive")
    listenerRemovePending = true
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
