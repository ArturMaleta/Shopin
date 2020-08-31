package mal.art.shopin.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import mal.art.shopin.R
import mal.art.shopin.adapter.ListOfShoppingListsViewAdapter
import mal.art.shopin.fragment.helper.changeFragment
import mal.art.shopin.viewModel.ListOfShoppingListsViewModel
import java.util.ArrayList

class ListOfShoppingListFragment : Fragment(R.layout.list_of_shopping_list_fragment_layout), ListOfShoppingListsViewAdapter.OnListNameClickListener {

  private var listOfShoppingLists: MutableList<String> = ArrayList()

  private lateinit var listOfShoppingListViewModel: ListOfShoppingListsViewModel
//  private lateinit var sharedViewModel: SharedViewModel

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initializeViewModel()
    initializeFAB()
  }

  private fun initializeViewModel() {
    val recyclerView = view!!.findViewById<RecyclerView>(R.id.list_of_shopping_list_recycler_view)
    val adapter = ListOfShoppingListsViewAdapter(activity, this)

    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(activity)

    listOfShoppingListViewModel = ViewModelProviders.of(this).get(ListOfShoppingListsViewModel::class.java)
//    sharedViewModel = ViewModelProviders.of(this).get(SharedViewModel::class.java)
    listOfShoppingListViewModel.getAllShoppingLists().observe(this, Observer { shoppingLists: DataSnapshot ->
      adapter.setShoppingLists(shoppingLists)
      for (date in shoppingLists.children) {
        date.key?.let { listOfShoppingLists.add(it) }
      }
    })
  }

  private fun initializeFAB() {
    val fab: View = activity?.findViewById(R.id.fab) ?: FloatingActionButton(context)
    fab.setOnClickListener {
      showAddNewShoppingListDialog()
    }
  }

  private fun showAddNewShoppingListDialog() {
    val addNewShoppingListFragment = AddNewShoppingListFragment()
    addNewShoppingListFragment.show(activity!!.supportFragmentManager, AddNewShoppingListFragment.TAG)
  }

  override fun onListClick(position: Int) {
    val listName = listOfShoppingLists[position]
    Toast.makeText(activity, listName, Toast.LENGTH_SHORT).show()

    val bundle = Bundle()
    bundle.putString("listName", listName)

    val fragment = ShoppingListFragment()
    fragment.arguments = bundle

    fragment.changeFragment(
      activity!!.supportFragmentManager,
      R.id.shopping_list_fragment_container,
      fragment,
      ShoppingListFragment.TAG
    )
  }

//  nie działa, bo w onResume nie wchodzi po zamknięciu Dialogu
//private fun goToAddedShoppingList() {
//    sharedViewModel.shoppingListName.observe(this, Observer {
//
//      val shoppingListName = sharedViewModel.shoppingListName.value
//
//      val bundle = Bundle()
//      bundle.putString("shoppingListName", shoppingListName)
//
//      val fragment = ShoppingListFragment()
//      fragment.arguments = bundle
//
//      fragment.changeFragment(
//        activity!!.supportFragmentManager,
//        R.id.shopping_list_fragment_container,
//        fragment,
//        ShoppingListFragment.TAG
//      )
//    })
//  }

  companion object {
    const val TAG = "LIST_OF_SHOPPING_LIST_FRAGMENT_TAG"
  }
}