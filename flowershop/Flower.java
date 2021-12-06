package flowershop;

/**
 * @author Laura Hidalgo Rivera
 */

public class Flower {
  private Long id = null;
  private String species;
  private String color;
  private float price;
  private int stock;

  public Flower(String species, String color, float price, int stock) {
    setSpecies(species);
    setColor(color);
    this.price = price;
    this.stock = stock;
  }

  public Long getId() {
    return id;
  }

  public String getSpecies() {
    return species;
  }

  public String getColor() {
    return color;
  }
  
  public float getPrice() {
    return price;
  }
  
  public int getStock() {
    return stock;
  }
  
  public void setId(Long id) {
    this.id = id;
  }

  public void setSpecies(String species) {
    this.species = species.length() > 30 ? species.substring(0,30) : species;
  }

  public void setColor(String color) {
    this.color = color.length() > 30 ? color.substring(0,30) : color;
  }
  
  public void setPrice(float price) {
    this.price = price;
  }
  
  public void setStock(int stock) {
    this.stock = stock;
  }

  @Override
  public String toString() {
    return "Flower [id=" + id + ", species=" + species + ", color=" + color + ", price=" + price
        + ", stock=" + stock + "]";
  }
  
}
