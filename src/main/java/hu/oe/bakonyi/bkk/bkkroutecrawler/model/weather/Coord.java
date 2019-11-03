package hu.oe.bakonyi.bkk.bkkroutecrawler.model.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Data
@Validated
@Builder
@AllArgsConstructor
@NoArgsConstructor
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-09-02T19:31:53.527Z[GMT]")
public class Coord   {
  @JsonProperty("lon")
  private Double lon;

  @JsonProperty("lat")
  private Double lat;

}
