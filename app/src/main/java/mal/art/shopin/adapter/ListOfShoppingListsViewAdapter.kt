package mal.art.shopin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.database.DataSnapshot
import mal.art.shopin.R
import mal.art.shopin.adapter.ListOfShoppingListsViewAdapter.ListOfShoppingListsViewHolder
import java.util.ArrayList

// TODO nie przekazuj contextu w konstruktorze. znajdź jak to zrobić
class ListOfShoppingListsViewAdapter(
  context: Context?,
  private val onListNameClickListener: OnListNameClickListener
) : RecyclerView.Adapter<ListOfShoppingListsViewHolder>() {

  inner class ListOfShoppingListsViewHolder(
    view: View,
    private var onListNameClickListener: OnListNameClickListener
  ) : ViewHolder(view), View.OnClickListener {
    var shoppingListName_txtView: TextView = view.findViewById(R.id.shopping_list_name_txtView)

    override fun onClick(v: View) {
      onListNameClickListener.onListClick(adapterPosition)
    }

    init {
      initializeOnClickListener(view, this)
    }
  }

  private val inflater: LayoutInflater = LayoutInflater.from(context)
  private val shoppingLists: MutableList<String?>? = ArrayList()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListOfShoppingListsViewHolder {
    return ListOfShoppingListsViewHolder(inflater.inflate(R.layout.list_of_shopping_lists_recyclerview_item, parent, false), onListNameClickListener)
  }

  override fun onBindViewHolder(holder: ListOfShoppingListsViewHolder, position: Int) {
    if (shoppingLists != null) {
      val currShoppingList = shoppingLists[position]
      holder.shoppingListName_txtView.text = currShoppingList
    } else {
      holder.shoppingListName_txtView.setText(R.string.no_shopping_list_available)
    }
  }

  fun setShoppingLists(shoppingLists: DataSnapshot) {
    this.shoppingLists!!.clear()
    for (temp in shoppingLists.children) {
      val date = temp.key
      this.shoppingLists.add(date)
    }
    notifyDataSetChanged()
  }

  override fun getItemCount(): Int {
    return shoppingLists?.size ?: 0
  }

  private fun initializeOnClickListener(v: View, vh: ListOfShoppingListsViewHolder) {
    v.setOnClickListener(vh)
  }

  interface OnListNameClickListener {
    fun onListClick(position: Int)
  }

}