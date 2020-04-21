package mal.art.shopin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import mal.art.shopin.R

class ChangeShoppingListNameFragment : DialogFragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val listName: String = arguments!!.getString("listName")!!

    val view = inflater.inflate(R.layout.fragment_change_shopping_list_name, container, false)
    val editShoppingListNameTextView = view.findViewById<TextView>(R.id.shopping_list_name_edit_text)
    editShoppingListNameTextView.text = listName

    return view
  }

  companion object {
    const val TAG = "CHANGE_LIST_NAME_TAG"
  }
}
