package by.lav.car.rental.entity;

public class CarEntity {

    private Integer id;
    private String model;
    private CarCategoryEntity carCategoryEntity;
    private String colour;
    private Integer seatsQuantity;

    public CarEntity(Integer id, String model, CarCategoryEntity carCategoryEntity,
                     String colour, Integer seatsQuantity) {
        this.id = id;
        this.model = model;
        this.carCategoryEntity = carCategoryEntity;
        this.colour = colour;
        this.seatsQuantity = seatsQuantity;
    }

    public CarEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public CarCategoryEntity getCarCategory() {
        return carCategoryEntity;
    }

    public void setCarCategory(CarCategoryEntity carCategoryEntity) {
        this.carCategoryEntity = carCategoryEntity;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Integer getSeatsQuantity() {
        return seatsQuantity;
    }

    public void setSeatsQuantity(Integer seatsQuantity) {
        this.seatsQuantity = seatsQuantity;
    }

    @Override
    public String toString() {
        return "CarEntity{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", carCategory='" + carCategoryEntity + '\'' +
                ", colour='" + colour + '\'' +
                ", seatsQuantity=" + seatsQuantity +
                '}';
    }
}
