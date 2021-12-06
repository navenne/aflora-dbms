package flowershop;

import java.util.Date;

/**
 * @author Laura Hidalgo Rivera
 *
 */

public class Bill {
  private Long id = null;
  private Date date;
  private float total;
  private Long customerId;

  public Bill(Date date, float total, Long customerId) {
    this.date = date;
    this.total = total;
    this.customerId = customerId;
  }

  public Long getId() {
    return id;
  }

  public Date getDate() {
    return date;
  }

  public float getTotal() {
    return total;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public void setTotal(float total) {
    this.total = total;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  @Override
  public String toString() {
    return "Bill [id=" + id + ", date=" + date + ", total=" + total + ", customerId=" + customerId
        + "]";
  }
  
  
}
