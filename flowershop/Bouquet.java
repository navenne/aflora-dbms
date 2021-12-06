package flowershop;

import java.util.Map;

/**
 * @author Laura Hidalgo Rivera
 *
 */

public class Bouquet {

  private Long id = null;
  private Long lineId;
  private Map<Flower, Integer> flowers;

  public Bouquet(Long lineId) {
    this.lineId = lineId;
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

  @Override
  public String toString() {
    return "Bouquet [id=" + id + ", lineId=" + lineId + ", flowers=" + flowers + "]";
  }
  
}
