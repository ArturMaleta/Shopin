package mal.art.shopin.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import mal.art.shopin.R
import mal.art.shopin.adapter.ListOfShoppingListsViewAdapter
import mal.art.shopin.adapter.ListOfShoppingListsViewAdapter.OnListNameClickListener
import mal.art.shopin.fragment.ShoppingListFragment
import mal.art.shopin.viewModel.ListOfShoppingListsViewModel
import java.util.ArrayList

class ListOfShoppingLists : AppCompatActivity(R.layout.activity_shopping_list), OnListNameClickListener {

  private var listOfShoppingLists: MutableList<String> = ArrayList()

  private val fragmentManager: FragmentManager = supportFragmentManager

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val recyclerView = findViewById<RecyclerView>(R.id.list_of_shopping_list_recycler_view)

    val adapter = ListOfShoppingListsViewAdapter(this, this)
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(this)

    val listOfShoppingListViewModel = ViewModelProviders.of(this).get(ListOfShoppingListsViewModel::class.java)

    listOfShoppingListViewModel.getAllShoppingLists().observe(this, Observer { shoppingLists: DataSnapshot ->
      adapter.setShoppingLists(shoppingLists)
      for (date in shoppingLists.children) {
        date.key?.let { listOfShoppingLists.add(it) }
      }
    })
  }

  fun goToListOfShoppingLists(view: View) {
    val shoppingListIntent = Intent(view.context, ListOfShoppingLists::class.java)
    view.context.startActivity(shoppingListIntent)
  }

  override fun onListClick(position: Int) {
    val listName = listOfShoppingLists[position]
    Toast.makeText(this, listName, Toast.LENGTH_SHORT).show()

    val bundle = Bundle()
    bundle.putString("listName", listName)

    val fragment = ShoppingListFragment()
    fragment.arguments = bundle

    val fragmentTransaction = fragmentManager.beginTransaction()
    fragmentTransaction.replace(R.id.myFragmentLinearLayout, fragment)
    fragmentTransaction.addToBackStack(null)
    fragmentTransaction.commit()
  }
}