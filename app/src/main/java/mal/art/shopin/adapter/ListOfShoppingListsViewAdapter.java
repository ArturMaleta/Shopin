package mal.art.shopin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import java.util.ArrayList;
import java.util.List;
import mal.art.shopin.R;

public class ListOfShoppingListsViewAdapter extends RecyclerView.Adapter<ListOfShoppingListsViewAdapter.ListOfShoppingListsViewHolder> {

  private LayoutInflater inflater;

  private List<String> shoppingLists = new ArrayList<>();

  class ListOfShoppingListsViewHolder extends RecyclerView.ViewHolder {
    TextView shoppingListName_txtView;

    private ListOfShoppingListsViewHolder(View view) {
      super(view);
      shoppingListName_txtView = view.findViewById(R.id.shopping_list_name_txtView);
    }
  }

  public ListOfShoppingListsViewAdapter(Context context) {
    inflater = LayoutInflater.from(context);
  }

  @NonNull
  @Override
  public ListOfShoppingListsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = inflater.inflate(R.layout.list_of_shopping_lists_recyclerview_item, parent, false);

    return new ListOfShoppingListsViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull ListOfShoppingListsViewHolder holder, int position) {
    if (shoppingLists != null) {
      String currShoppingList = shoppingLists.get(position);
      holder.shoppingListName_txtView.setText(currShoppingList);
    } else {
      holder.shoppingListName_txtView.setText("No shopping lists");
    }
  }

  public void setShoppingLists(DataSnapshot shoppingLists) {
    for (DataSnapshot temp : shoppingLists.getChildren()) {
      String date = temp.getKey();
      this.shoppingLists.add(date);
    }
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount() {
    if (shoppingLists != null) {
      return (int) shoppingLists.size();
    } else {
      return 0;
    }
  }

}
