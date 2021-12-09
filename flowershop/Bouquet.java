package flowershop;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Laura Hidalgo Rivera
 *
 */

public class Bouquet {

  private Long id = null;
  private Long lineId;
  private Map<Flower, Integer> flowers;
  

  /**
   * 
   */
  public Bouquet() {
    flowers = new HashMap<Flower, Integer>();
  }

  public Long getId() {
    return id;
  }
  
  public Long getLineId() {
    return lineId;
  }

  public Map<Flower, Integer> getFlowers() {
    return flowers;
  }
  
  public void setId(Long id) {
    this.id = id;
  }

  public void setLineId(Long lineId) {
    this.lineId = lineId;
  }

  public void addFlower(Flower flower, int quantity) {
    flowers.put(flower, quantity);
  }
  
  public String name() {
    String name = "";
    for (Flower f : this.getFlowers().keySet()) {
      name += f.getColor() + " " + f.getSpecies() + " ";
    }
    name.trim();
    return name.length() > 50 ? name.substring(0, 50) : name;
  }
  
  public float total() {
    float total = 0;
    for (Flower f : this.getFlowers().keySet()) {
      total += f.getPrice()*this.getFlowers().get(f);
    }
    return total;
  }

  @Override
  public String toString() {
    return "Bouquet [id=" + id + ", lineId=" + lineId + ", flowers=" + flowers + "]";
  }
  
}
