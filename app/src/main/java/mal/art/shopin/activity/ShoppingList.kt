package mal.art.shopin.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import mal.art.shopin.R
import mal.art.shopin.adapter.ListOfShoppingListsViewAdapter
import mal.art.shopin.adapter.ListOfShoppingListsViewAdapter.OnListNameClickListener
import mal.art.shopin.viewModel.ShoppingListsViewModel
import java.util.ArrayList

class ShoppingList : AppCompatActivity(), OnListNameClickListener {

  private var listOfShoppingLists: MutableList<String> = ArrayList()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_shopping_list)
    val recyclerView = findViewById<RecyclerView>(R.id.list_of_shopping_list_recycler_view)

    val adapter = ListOfShoppingListsViewAdapter(this, this)
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(this)

    val listOfShoppingListViewModel = ViewModelProviders.of(this).get(ShoppingListsViewModel::class.java)

    listOfShoppingListViewModel.allShoppingLists.observe(this, Observer { shoppingLists: DataSnapshot ->
      adapter.setShoppingLists(shoppingLists)
      for (date in shoppingLists.children) {
        date.key?.let { listOfShoppingLists.add(it) }
      }
    })
  }

  override fun onStart() {
    super.onStart()
    val overlay = findViewById<View>(R.id.shopping_list_layout)
    overlay.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN
  }

  fun goToShoppingList(view: View) {
    val shoppingListIntent = Intent(view.context, ShoppingList::class.java)
    view.context.startActivity(shoppingListIntent)
  }

  override fun onListClick(position: Int) {
    val listName = listOfShoppingLists[position]
    Toast.makeText(this, listName, Toast.LENGTH_SHORT).show()
  }
}