package mal.art.shopin.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.google.firebase.database.Exclude;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Entity(tableName = "products")
public class Product implements Parcelable {
  // jest wtyczka do Parcable, żeby automatycznie generował kod

  @PrimaryKey
  @NonNull
  @ColumnInfo(name = "product_name")
  private String productName;

  @ColumnInfo(name = "product_category")
  private ProductCategoryEnum productCategory;

  @Ignore
  @ColumnInfo(name = "quantity") private int quantity;

  @Ignore private String productUnit;

  @Ignore
  private String shoppingStatus;

  // for Firebase
  public Product() {

  }

  public Product(String productName, ProductCategoryEnum productCategory) {
    this.productName = productName;
    this.productCategory = productCategory;
  }

  public Product(String productName, ProductCategoryEnum productCategory, int quantity) {
    this.productName = productName;
    this.productCategory = productCategory;
    this.quantity = quantity;
  }

  // to Firebase
  public Product(String productName, ProductCategoryEnum productCategory, int quantity, String productUnit, String shoppingStatus) {
    this.productName = productName;
    this.productCategory = productCategory;
    this.quantity = quantity;
    this.productUnit = productUnit;
    this.shoppingStatus = shoppingStatus;
  }

  public Product(Parcel parcel) {
    this.productName = parcel.readString();
    String productCategoryToParcel = productCategory.toString();
    productCategoryToParcel = parcel.readString();
  }

  // To Firebase Database
  public Product(
    @Nullable String productName,
    @NotNull String productCategory,
    int productQuantity,
    @NotNull String productUnit,
    @NotNull String shoppingStatus
  ) {
      this.productName = productName;
      String productCategoryToString = this.productCategory.toString();
      productCategoryToString = productCategory;
      this.quantity = productQuantity;
      this.productUnit = productUnit;
      this.shoppingStatus = shoppingStatus;
  }

  @NotNull
  public String getProductName() {
    return productName;
  }

  public void setProductName(@NotNull String productName) {
    this.productName = productName;
  }

  @Exclude
  public ProductCategoryEnum getProductCategoryVal() {
    return productCategory;
  }

  public String getProductCategory() {
    if (productCategory == null) {
      return null;
    } else {
      return productCategory.toString();
    }
  }

  public void setProductCategory(ProductCategoryEnum productCategory) {
    this.productCategory = productCategory;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String getProductUnit() {
    return productUnit;
  }

  public void setProductUnit(String unit) {
    this.productUnit = unit;
  }

  public String getShoppingStatus() {
    return shoppingStatus;
  }

  public void setShoppingStatus(String shoppingStatus) {
    this.shoppingStatus = shoppingStatus;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(productName);
    dest.writeString(productCategory.toString());
  }

  public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {

    @Override
    public Product createFromParcel(Parcel parcel) {
      return new Product(parcel);
    }

    public Product[] newArray(int size) {
      return new Product[0];
    }
  };

  @Override
  public String toString() {
    return "Product name='" + productName + '\'' +
      ", productCategory='" + productCategory + '\'' +
      ", quantity=" + quantity +
      '}';
  }
}
