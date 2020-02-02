package mal.art.shopin.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import mal.art.shopin.Model.Product;
import mal.art.shopin.Repository.ProductRepository;

public class ShoppingListViewModel extends ViewModel {

  private ProductRepository repository;

  private String productName, productUnit;

  private int productQuantity;

  private MutableLiveData<List<Product>> shoppingList;
}
