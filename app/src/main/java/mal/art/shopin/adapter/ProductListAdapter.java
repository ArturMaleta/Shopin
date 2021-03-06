package mal.art.shopin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import mal.art.shopin.R;
import mal.art.shopin.model.ProductModel;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

  class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView productName_txtView;

    OnAddProductListener onAddProductListener;

    private ProductViewHolder(View view, OnAddProductListener onAddProductListener) {
      super(view);
      productName_txtView = view.findViewById(R.id.product_name_txtView);
      this.onAddProductListener = onAddProductListener;

      view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      onAddProductListener.onProductClick(getAdapterPosition());
    }
  }

  private LayoutInflater inflater;

  private List<ProductModel> productModels;

  private OnAddProductListener onAddProductListener;

  // TODO nie przekazuj contextu w konstruktorze. znajdź jak to zrobić
  public ProductListAdapter(Context context, OnAddProductListener onAddProductListener) {
    inflater = LayoutInflater.from(context);
    this.onAddProductListener = onAddProductListener;
  }

  @NonNull
  @Override
  public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = inflater.inflate(R.layout.products_recyclerview_item, parent, false);

    return new ProductViewHolder(v, onAddProductListener);
  }

  @Override
  public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
    if (productModels != null) {
      ProductModel curProductModelName = productModels.get(position);
      holder.productName_txtView.setText(curProductModelName.getProductName());
    } else {
      holder.productName_txtView.setText("No product");
    }
  }

  public void setProductModels(List<ProductModel> productModels) {
    this.productModels = productModels;
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount() {
    if (productModels != null) {
      return productModels.size();
    } else {
      return 0;
    }
  }

  public interface OnAddProductListener {
    void onProductClick(int position);
  }
}
