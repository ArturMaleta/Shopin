package mal.art.shopin.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class Product implements Parcelable {
  // jest wtyczka do Parcable, żeby automatycznie generował kod

  @PrimaryKey
  @NonNull
  @ColumnInfo(name = "product_name")
  public String productName;

  @ColumnInfo(name = "product_category")
  public String productCategory;

  @Ignore
  @ColumnInfo(name = "quantity")
  public int quantity;

  @Ignore
  public String productUnit;

  @Ignore
  private String shoppingStatus;

  // for Firebase
  public Product() {

  }

  public Product(String productName, String productCategory) {
    this.productName = productName;
    this.productCategory = productCategory;
  }

  public Product(String productName, String productCategory, int quantity) {
    this.productName = productName;
    this.productCategory = productCategory;
    this.quantity = quantity;
  }

  // to Firebase
  public Product(String productCategory, int quantity, String productUnit, String shoppingStatus) {
    this.productCategory = productCategory;
    this.quantity = quantity;
    this.productUnit = productUnit;
    this.shoppingStatus = shoppingStatus;
  }

  public Product(Parcel parcel) {
    this.productName = parcel.readString();
    this.productCategory = parcel.readString();
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getProductCategory() {
    return productCategory;
  }

  public void setProductCategory(String productCategory) {
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

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(productName);
    dest.writeString(productCategory);
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
