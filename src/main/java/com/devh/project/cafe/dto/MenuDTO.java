package com.devh.project.cafe.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MenuDTO {
    private Long id;
    private String name;
    private long price;
    private boolean ice;
    private boolean onSale;

    public static MenuDTO create(String name, long price, boolean ice, boolean onSale) {
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setName(name);
        menuDTO.setPrice(price);
        menuDTO.setIce(ice);
        menuDTO.setOnSale(onSale);
        return menuDTO;
    }
    public static MenuDTO create(Long id, String name, long price, boolean ice, boolean onSale) {
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setId(id);
        menuDTO.setName(name);
        menuDTO.setPrice(price);
        menuDTO.setIce(ice);
        menuDTO.setOnSale(onSale);
        return menuDTO;
    }
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuDTO other = (MenuDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
    
    
}
