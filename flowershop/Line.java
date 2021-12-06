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

  public Line(int quantity, float subtotal, Long billId) {
    this.quantity = quantity;
    this.subtotal = subtotal;
    this.billId = billId;
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
