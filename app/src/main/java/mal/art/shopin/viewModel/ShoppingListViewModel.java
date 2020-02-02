package mal.art.shopin.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import mal.art.shopin.model.Product;
import mal.art.shopin.repository.ProductRepository;

public class ShoppingListViewModel extends ViewModel {

  private ProductRepository repository;

  private String productName, productUnit;

  private int productQuantity;

  private MutableLiveData<List<Product>> shoppingList;
}
