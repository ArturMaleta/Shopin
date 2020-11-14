package mal.art.shopin.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import mal.art.shopin.R
import mal.art.shopin.adapter.ListOfShoppingListsViewAdapter
import mal.art.shopin.adapter.interfaces.OnItemClickListener
import mal.art.shopin.extensions.lazyActivityViewModels
import mal.art.shopin.fragment.helper.changeFragment
import mal.art.shopin.model.ShoppingListModel
import mal.art.shopin.viewModel.ListOfShoppingListsViewModel

class ListOfShoppingListFragment : Fragment(R.layout.list_of_shopping_list_fragment_layout) {

  private val listOfShoppingListViewModel: ListOfShoppingListsViewModel by lazyActivityViewModels {
    ListOfShoppingListsViewModel(activity!!.application)
  }
  private lateinit var _adapter: ListOfShoppingListsViewAdapter

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initializeViewModel()
    initializeFAB()
  }

  private fun initializeViewModel() {
    val query = listOfShoppingListViewModel.getListOfShoppingListsReference().orderBy("shoppingListName", Query.Direction.ASCENDING)

    val options = FirestoreRecyclerOptions.Builder<ShoppingListModel>()
      .setQuery(query, ShoppingListModel::class.java)
      .build()

    _adapter = ListOfShoppingListsViewAdapter(options)
    val recyclerView = view!!.findViewById<RecyclerView>(R.id.list_of_shopping_list_recycler_view)
    recyclerView.let {
      it.layoutManager = LinearLayoutManager(activity)
      it.adapter = _adapter
    }

    _adapter.setOnItemClickListener(object : OnItemClickListener {
      override fun onItemClick(documentSnapshot: DocumentSnapshot, position: Int) {
        val fragment = ShoppingListFragment(documentSnapshot.id)
        fragment.changeFragment(
          supportFragmentManager = parentFragmentManager,
          container = R.id.shopping_list_fragment_container,
          fragment = fragment,
          tag = ShoppingListFragment.TAG
        )
      }
    })
  }

  override fun onStart() {
    super.onStart()
    _adapter.startListening()
  }

  override fun onStop() {
    super.onStop()
    _adapter.stopListening()
  }

  private fun initializeFAB() {
    val fab: View = activity?.findViewById(R.id.fab) ?: FloatingActionButton(context)
    fab.setOnClickListener {
      showAddNewShoppingListDialog()
    }
  }

  private fun showAddNewShoppingListDialog() {
    val addNewShoppingListFragment = AddNewShoppingListDialogFragment()
    addNewShoppingListFragment.show(activity!!.supportFragmentManager, AddNewShoppingListDialogFragment.TAG)
  }


  companion object {
    const val TAG = "LIST_OF_SHOPPING_LIST_FRAGMENT_TAG"
  }
}