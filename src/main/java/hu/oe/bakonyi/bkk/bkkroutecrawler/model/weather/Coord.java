package hu.oe.bakonyi.bkk.bkkroutecrawler.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Data
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-09-02T19:31:53.527Z[GMT]")
public class Coord   {
  @JsonProperty("lon")
  private BigDecimal lon = null;

  @JsonProperty("lat")
  private BigDecimal lat = null;

  public Coord lon(BigDecimal lon) {
    this.lon = lon;
    return this;
  }
}
