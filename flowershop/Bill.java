package flowershop;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Laura Hidalgo Rivera
 *
 */

public class Bill {
  private Long id = null;
  private Date date;
  private ArrayList<Line> lines;
  private Long customerId;

  public Bill(Date date, Long customerId) {
    this.date = date;
    this.lines = new ArrayList<>();
    this.customerId = customerId;
  }

  public Long getId() {
    return id;
  }

  public Date getDate() {
    return date;
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

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }
  
  public void addLine(Line line) {
    this.lines.add(line);
  }
  

  public ArrayList<Line> getLines() {
    return lines;
  }


  @Override
  public String toString() {
    return "Bill [id=" + id + ", date=" + date + ", customerId=" + customerId
        + "]";
  }
  
  
}
