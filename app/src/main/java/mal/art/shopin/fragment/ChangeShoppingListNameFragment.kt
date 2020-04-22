package mal.art.shopin.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import mal.art.shopin.R
import mal.art.shopin.repository.ProductRepository

class ChangeShoppingListNameFragment : DialogFragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val oldListName: String = arguments!!.getString("listName")!!
    val view = inflater.inflate(R.layout.fragment_change_shopping_list_name, container, false)

    val editShoppingListNameEditText = view.findViewById<EditText>(R.id.shopping_list_name_edit_text)
    editShoppingListNameEditText.hint = oldListName
    editShoppingListNameEditText.requestFocus()
    showKeyboard(view)

    val acceptBtn = view.findViewById<Button>(R.id.change_shopping_list_name_accept_btn)
    val cancelBtn = view.findViewById<Button>(R.id.change_shopping_list_name_cancel_btn)

    acceptBtn.setOnClickListener { ProductRepository.changeShoppingListName(oldListName, editShoppingListNameEditText.text.toString()) }

    cancelBtn.setOnClickListener {
      dismiss()
      hideKeyboard(view)
    }

    return view
  }

  private fun showKeyboard(view: View) {
    val imm = view.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
  }

  private fun hideKeyboard(view: View) {
    val imm = view.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    imm?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
  }

  companion object {
    const val TAG = "CHANGE_LIST_NAME_TAG"
  }
}
