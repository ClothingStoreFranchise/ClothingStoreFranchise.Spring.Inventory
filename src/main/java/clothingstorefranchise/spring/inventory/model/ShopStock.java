package clothingstorefranchise.spring.inventory.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShopStock extends BuildingStock {

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Shop.class)
	@MapsId("building_id")
    @JoinColumn(name = "building_id", nullable = false, insertable = false, updatable = false)
	private Shop shop;
}
