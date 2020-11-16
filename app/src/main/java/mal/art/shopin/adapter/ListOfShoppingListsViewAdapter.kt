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
import mal.art.shopin.adapter.interfaces.OnItemClickListener
import mal.art.shopin.model.ShoppingListModel

class ListOfShoppingListsViewAdapter(
  options: FirestoreRecyclerOptions<ShoppingListModel>
): FirestoreRecyclerAdapter<ShoppingListModel, ListOfShoppingListsViewHolder>(options) {

  private lateinit var listener: OnItemClickListener

  inner class ListOfShoppingListsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    init {
      view.setOnClickListener {
        val position = adapterPosition
          listener.onItemClick(snapshots.getSnapshot(position), position)
      }
    }

    var shoppingListNameTv: TextView = view.findViewById(R.id.shopping_list_name_txtView)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListOfShoppingListsViewHolder {
    return ListOfShoppingListsViewHolder(LayoutInflater.from(parent.context)
      .inflate(R.layout.list_of_shopping_lists_recyclerview_item, parent, false))
  }

  override fun onBindViewHolder(holder: ListOfShoppingListsViewHolder, position: Int, shoppingListModel: ShoppingListModel) {
    holder.shoppingListNameTv.text = shoppingListModel.shoppingListName
  }

  fun setOnItemClickListener(listener: OnItemClickListener) {
    this.listener = listener
  }
}