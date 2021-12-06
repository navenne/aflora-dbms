package flowershop;

/**
 * @author Laura Hidalgo Rivera
 *
 */

public class Customer {
  
  private Long id = null;
  private String name;
  private String phoneNumber;
  
  public Customer(String name, String phoneNumber) {
    setName(name);
    setPhoneNumber(phoneNumber);
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }
  
  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name.length() > 30 ? name.substring(0,30) : name;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber.substring(0, 9);
  }

  @Override
  public String toString() {
    return "Customer [id=" + id + ", name=" + name + ", phoneNumber=" + phoneNumber + "]";
  }
  
}
