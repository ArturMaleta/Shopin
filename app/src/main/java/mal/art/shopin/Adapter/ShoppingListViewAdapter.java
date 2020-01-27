package mal.art.shopin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import mal.art.shopin.Model.Product;
import mal.art.shopin.R;

public class ShoppingListViewAdapter extends RecyclerView.Adapter<ShoppingListViewAdapter.ShoppingListViewHolder> {

  private LayoutInflater mInflater;

  private List<Product> mProducts;

  class ShoppingListViewHolder extends RecyclerView.ViewHolder {

    public TextView productName_txtView, quantity_txtView, productUnit_txtView;

    public ShoppingListViewHolder(View view) {
      super(view);
      productName_txtView = view.findViewById(R.id.product_name_txtView);
      quantity_txtView = view.findViewById(R.id.product_quantity_txtView);
      productUnit_txtView = view.findViewById(R.id.product_unit_txtView);
    }
  }

  public ShoppingListViewAdapter(Context context) {
    mInflater = LayoutInflater.from(context);
  }

  @NonNull
  @Override
  public ShoppingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = mInflater.inflate(R.layout.shopping_list_recyclerview_item, parent, false);

    return new ShoppingListViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull ShoppingListViewHolder holder, int position) {
    if (mProducts != null) {
      Product currProductName = mProducts.get(position);
      holder.productName_txtView.setText(currProductName.getProductName());
      holder.quantity_txtView.setText(currProductName.getQuantity());
      holder.productUnit_txtView.setText(currProductName.getProductUnit());
    } else {
      holder.productName_txtView.setText("No product");
    }
  }

  @Override
  public int getItemCount() {
    if (mProducts != null) {
      return mProducts.size();
    } else {
      return 0;
    }
  }

}
