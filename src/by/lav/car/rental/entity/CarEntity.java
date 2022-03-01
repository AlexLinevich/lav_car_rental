package by.lav.car.rental.entity;

public class CarEntity {

    private Integer id;
    private String model;
    private CarCategoryEntity carCategoryEntity;
    private String colour;
    private Integer seatsQuantity;
    private String image;

    public CarEntity(Integer id, String model, CarCategoryEntity carCategoryEntity,
                     String colour, Integer seatsQuantity, String image) {
        this.id = id;
        this.model = model;
        this.carCategoryEntity = carCategoryEntity;
        this.colour = colour;
        this.seatsQuantity = seatsQuantity;
        this.image = image;
    }

    public CarEntity(String model, CarCategoryEntity carCategoryEntity,
                     String colour, Integer seatsQuantity, String image) {
        this.model = model;
        this.carCategoryEntity = carCategoryEntity;
        this.colour = colour;
        this.seatsQuantity = seatsQuantity;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "CarEntity{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", carCategoryEntity=" + carCategoryEntity +
                ", colour='" + colour + '\'' +
                ", seatsQuantity=" + seatsQuantity +
                ", image='" + image + '\'' +
                '}';
    }
}
