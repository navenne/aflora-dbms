package flowershop;

/**
 * @author Laura Hidalgo Rivera
 *
 */

public class Line {
  private Long id = null;
  private int quantity;
  private float subtotal;
  private Long billId;
  private Bouquet bouquet;
  
  public Line(int quantity, float subtotal) {
    this.quantity = quantity;
    this.subtotal = subtotal;
  }

  public Line(int quantity, float subtotal, Bouquet bouquet) {
    this.quantity = quantity;
    this.subtotal = subtotal;
    this.bouquet = bouquet;
  }

  public Long getId() {
    return id;
  }

  public int getQuantity() {
    return quantity;
  }

  public float getSubtotal() {
    return subtotal;
  }

  public Long getBillId() {
    return billId;
  }


  public Bouquet getBouquet() {
    return bouquet;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public void setSubtotal(float subtotal) {
    this.subtotal = subtotal;
  }

  public void setBillId(Long billId) {
    this.billId = billId;
  }

  @Override
  public String toString() {
    return "Line [id=" + id + ", quantity=" + quantity + ", subtotal=" + subtotal + ", billId="
        + billId + "]";
  }
  
}
