package by.lav.car.rental.entity;

import java.math.BigDecimal;

public class CarCategoryEntity {

    private Integer id;
    private String category;
    private BigDecimal dayPrice;

    public CarCategoryEntity(Integer id, String category, BigDecimal dayPrice) {
        this.id = id;
        this.category = category;
        this.dayPrice = dayPrice;
    }

    public CarCategoryEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getDayPrice() {
        return dayPrice;
    }

    public void setDayPrice(BigDecimal dayPrice) {
        this.dayPrice = dayPrice;
    }

    @Override
    public String toString() {
        return "CarCategoryEntity{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", dayPrice=" + dayPrice +
                '}';
    }
}
