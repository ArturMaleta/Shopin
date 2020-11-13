package mal.art.shopin.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.Query
import mal.art.shopin.R
import mal.art.shopin.adapter.ListOfShoppingListsViewAdapter
import mal.art.shopin.model.ShoppingListModel
import mal.art.shopin.viewModel.ListOfShoppingListsViewModel

class ListOfShoppingListFragment : Fragment(R.layout.list_of_shopping_list_fragment_layout) {

  private lateinit var listOfShoppingListViewModel: ListOfShoppingListsViewModel

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initializeViewModel()
    initializeFAB()
  }

  private fun initializeViewModel() {
    listOfShoppingListViewModel = ViewModelProviders.of(this).get(ListOfShoppingListsViewModel::class.java)
    val query = listOfShoppingListViewModel.getListOfShoppingListsReference().orderBy("shoppingListName", Query.Direction.DESCENDING)

    val options = FirestoreRecyclerOptions.Builder<ShoppingListModel>()
      .setQuery(query, ShoppingListModel::class.java)
      .build()

    val adapter = ListOfShoppingListsViewAdapter(options)
    val recyclerView = view!!.findViewById<RecyclerView>(R.id.list_of_shopping_list_recycler_view)
    recyclerView.layoutManager = LinearLayoutManager(activity)
    recyclerView.adapter = adapter
    adapter.startListening()
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
    // TODO teraz ogarnij dodawanie produktu
  }


//  override fun onListClick(position: Int) {
//    val listName = listOfShoppingLists[position]
//    Toast.makeText(activity, listName, Toast.LENGTH_SHORT).show()
//
//    val bundle = Bundle()
//    bundle.putString("listName", listName)
//
//    val fragment = ShoppingListFragment()
//    fragment.arguments = bundle
//
//    fragment.changeFragment(
//      activity!!.supportFragmentManager,
//      R.id.shopping_list_fragment_container,
//      fragment,
//      ShoppingListFragment.TAG
//    )
//  }

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