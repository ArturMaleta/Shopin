package mal.art.shopin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import mal.art.shopin.R
import mal.art.shopin.adapter.ListOfShoppingListsViewAdapter.ListOfShoppingListsViewHolder
import mal.art.shopin.model.ShoppingListModel

class ListOfShoppingListsViewAdapter(
  options: FirestoreRecyclerOptions<ShoppingListModel>
): FirestoreRecyclerAdapter<ShoppingListModel, ListOfShoppingListsViewHolder>(options) {

  inner class ListOfShoppingListsViewHolder(view: View):RecyclerView.ViewHolder(view) {
    var shoppingListNameTv: TextView = view.findViewById(R.id.shopping_list_name_txtView)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListOfShoppingListsViewHolder {
    return ListOfShoppingListsViewHolder(LayoutInflater.from(parent.context)
      .inflate(R.layout.list_of_shopping_lists_recyclerview_item, parent, false))
  }

  override fun onBindViewHolder(holder: ListOfShoppingListsViewHolder, position: Int, shoppingListModel: ShoppingListModel) {
    holder.shoppingListNameTv.text = shoppingListModel.shoppingListName
  }

}
//class ListOfShoppingListsViewAdapter(
//  context: Context?,
//  private val onListNameClickListener: OnListNameClickListener
//) : RecyclerView.Adapter<ListOfShoppingListsViewHolder>() {
//
//  inner class ListOfShoppingListsViewHolder(
//    view: View,
//    private var onListNameClickListener: OnListNameClickListener
//  ) : ViewHolder(view), View.OnClickListener {
//    var shoppingListName_txtView: TextView = view.findViewById(R.id.shopping_list_name_txtView)
//
//    override fun onClick(v: View) {
//      onListNameClickListener.onListClick(adapterPosition)
//    }
//
//    init {
//      initializeOnClickListener(view, this)
//    }
//  }
//
//  private val inflater: LayoutInflater = LayoutInflater.from(context)
//  private val shoppingLists: MutableList<String?>? = ArrayList()
//
//  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListOfShoppingListsViewHolder {
//    return ListOfShoppingListsViewHolder(inflater.inflate(R.layout.list_of_shopping_lists_recyclerview_item, parent, false), onListNameClickListener)
//  }
//
//  override fun onBindViewHolder(holder: ListOfShoppingListsViewHolder, position: Int) {
//    if (shoppingLists != null) {
//      val currShoppingList = shoppingLists[position]
//      holder.shoppingListName_txtView.text = currShoppingList
//    } else {
//      holder.shoppingListName_txtView.setText(R.string.no_shopping_list_available)
//    }
//  }
//
//  fun setShoppingLists(shoppingLists: DataSnapshot) {
//    this.shoppingLists!!.clear()
//    for (temp in shoppingLists.children) {
//      val date = temp.key
//      this.shoppingLists.add(date)
//    }
//    notifyDataSetChanged()
//  }
//
//  override fun getItemCount(): Int {
//    return shoppingLists?.size ?: 0
//  }
//
//  private fun initializeOnClickListener(v: View, vh: ListOfShoppingListsViewHolder) {
//    v.setOnClickListener(vh)
//  }
//
//  interface OnListNameClickListener {
//    fun onListClick(position: Int)
//  }
//
//}