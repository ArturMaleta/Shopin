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

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView productName_txtView;

        private ProductViewHolder(View view) {
            super(view);
            productName_txtView = view.findViewById(R.id.product_name_txtView);
        }
    }

    private LayoutInflater mInflater;
    private List<Product> mProducts;

    public ProductListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.products_recyclerview_item, parent, false);

        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        if (mProducts != null) {
            Product curProductName = mProducts.get(position);
            holder.productName_txtView.setText(curProductName.getProductName());
        } else {
            holder.productName_txtView.setText("No product");
        }
    }

    public void setProducts(List<Product> products) {
        mProducts = products;
        notifyDataSetChanged();
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
