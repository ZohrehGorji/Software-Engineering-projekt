package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities;

import java.sql.Timestamp;

public class Vehicle {


    /**
     * The vehicle's id
     */
    private int id;
    /**
     * The Title of Vehicle
     */
    private String title;
    /**
     * The year of foundation of the Vehicle
     */
    private int year = 0;
    /**
     * The description of the Vehicle
     */
    private String description;
    /**
     * The amount of the Vehicle's seats
     */
    private int seats;
    /**
     * The license Plate of the Vehicle
     */
    private String licensePlate;
    /**
     * The type of drive of the Vehicle
     */
    private String typeOfDrive;
    /**
     * The power of the Vehicle incase it'S motorized.
     */
    private int power;
    /**
     * The price of the Vehicle.
     */
    private int price;
    /**
     * The path of an image
     */
    private String imgPath;
    /**
     * The license Class of the vehicle incase its mandatory.
     */
    private String licenseClass;
    /**
     * false, if the vehicle is marked as deleted from database and true otherwise.
     */
    private boolean isDeleted;
    /**
     * time of the last edit of the Vehicle.
     */
    private Timestamp lastEdit;
    /**
     * Constructor with no arguments for dto
     */
    public Vehicle() {
    }
    /**
     * Constructor with possible arguments
     *
     * @param title        the vehicle's title
     * @param year         the vehicle's year
     * @param description  the vehicle's description
     * @param seats        the number of seats in a vehicle
     * @param licensePlate the vehicle's license Plate
     * @param typeOfDrive  type of drive which is either motorized or muscle.
     * @param power        the vehicle's performance,incase it's motorized.
     * @param price        the vehicle's price
     * @param imgPath      the path to the vehicle's image
     * @param licenseClass the vehicle's class of license
     */
    public Vehicle(String title, int year, String description, int seats, String licensePlate, String typeOfDrive, int power, int price, String imgPath
        , String licenseClass) {

        this.title = title;
        this.year = year;
        this.description = description;
        this.seats = seats;
        this.licensePlate = licensePlate;
        this.typeOfDrive = typeOfDrive;
        this.power = power;
        this.price = price;
        this.imgPath = imgPath;
        this.licenseClass = licenseClass;
    }

    /**
     * A getter for the id
     *
     * @return vehicle's id
     */
    public int getId() {
        return id;
    }

    /**
     * A setter for the id
     *
     * @param id the new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * A getter for the title
     *
     * @return vehicle's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * A setter for the title
     *
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * A getter for the year
     *
     * @return vehicle's year
     */
    public int getYear() {
        return year;
    }

    /**
     * A setter for the year
     *
     * @param year the new year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * A getter for the description
     *
     * @return vehicle's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * A setter for the description
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * A getter for the seats
     *
     * @return vehicle's seats
     */
    public int getSeats() {
        return seats;
    }

    /**
     * A setter for the seats
     *
     * @param seats the new seats
     */
    public void setSeats(int seats) {
        this.seats = seats;
    }

    /**
     * A getter for the licensePlate
     *
     * @return vehicle's licensePlate
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * A setter for the licensePlate
     *
     * @param licensePlate the new licensePlate
     */
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    /**
     * A getter for the typeOfDrive
     *
     * @return vehicle's typeOfDrive
     */
    public String getTypeOfDrive() {
        return typeOfDrive;
    }

    /**
     * A setter for the typeOfDrive
     *
     * @param typeOfDrive the new typeOfDrive
     */
    public void setTypeOfDrive(String typeOfDrive) {
        this.typeOfDrive = typeOfDrive;
    }

    /**
     * A getter for the power
     *
     * @return vehicle's power
     */
    public int getPower() {
        return power;
    }

    /**
     * A setter for the power
     *
     * @param power the new power
     */
    public void setPower(int power) {
        this.power = power;
    }

    /**
     * A getter for the price an hour
     *
     * @return vehicle's price per hour
     */
    public int getPrice() {
        return price;
    }

    /**
     * A setter for the price an hour
     *
     * @param price the new price per hour
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * A getter for the imgPath
     *
     * @return vehicle's imgPath
     */
    public String getImgPath() {
        return imgPath;
    }

    /**
     * A setter for the imgPath
     *
     * @param imgPath the new imgPath
     */
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    /**
     * A getter for the licenseClass
     *
     * @return vehicle's licenseClass
     */
    public String getLicenseClass() {
        return licenseClass;
    }

    /**
     * A setter for the licenseClass
     *
     * @param licenseClass the new licenseClass
     */
    public void setLicenseClass(String licenseClass) {
        this.licenseClass = licenseClass;
    }

    /**
     * check if the vehicle is deleted or not.
     *
     * @return deleted is true if the vehicle is deleted.
     */
    public boolean isDeleted() {
        return isDeleted;
    }

    /**
     * A setter for the isdeleted
     *
     * @param deleted is true if the vehicle should be deleted.
     */
    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    /**
     * A getter for the last time of edit
     *
     * @return vehicle's last time of edit
     */
    public Timestamp getLastEdit() {
        return lastEdit;
    }

    /**
     * A setter for the last time of edit
     *
     * @param lastEdit the last time of edit
     */
    public void setLastEdit(Timestamp lastEdit) {
        this.lastEdit = lastEdit;
    }

    /**
     * A toString method
     *
     * @return vehicle with all of its' attributes.
     */
    @Override
    public String toString() {
        return "Vehicle: " +
            "Title='" + title + '\'' +
            ", Year=" + year +
            ", Description='" + description + '\'' +
            ", Seats=" + seats +
            ", LicensePlate='" + licensePlate + '\'' +
            ", TypeOfDrive='" + typeOfDrive + '\'' +
            ", Power=" + power +
            ", Price=" + price +
            ", LicenseClass='" + licenseClass + '\'' +
            '}' + '\n';
    }

    /**
     * A hash method for comparing vehicles
     *
     * @return hashCode of the vehicle
     */
    @Override
    public int hashCode() {
        return id;
    }

    public static final VehicleBuilder builder() {
        return new VehicleBuilder();
    }


    public static class VehicleBuilder {
        private String title;

        public VehicleBuilder title(String title) {
            this.title = title;
            return this;
        }

        private int year;

        public VehicleBuilder year(int year) {
            this.year = year;
            return this;
        }

        private String description;

        public VehicleBuilder description(String description) {
            this.description = description;
            return this;
        }

        private int seats;

        public VehicleBuilder seats(int seats) {
            this.seats = seats;
            return this;
        }

        private String licensePlate;

        public VehicleBuilder licensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }

        private String typeOfDrive;

        public VehicleBuilder typeOfDrive(String typeOfDrive) {
            this.typeOfDrive = typeOfDrive;
            return this;
        }

        private int power;

        public VehicleBuilder power(int power) {
            this.power = power;
            return this;
        }

        private int price;

        public VehicleBuilder price(int price) {
            this.price = price;
            return this;
        }

        private String imgPath;

        public VehicleBuilder imgPath(String imgPath) {
            this.imgPath = imgPath;
            return this;
        }

        private String licenseClass;

        public VehicleBuilder licenseClass(String licenseClass) {
            this.licenseClass = licenseClass;
            return this;
        }

        public Vehicle build() {
            Vehicle vehicle = new Vehicle();

            vehicle.setTitle(title);
            vehicle.setYear(year);
            vehicle.setDescription(description);
            vehicle.setSeats(seats);
            vehicle.setLicensePlate(licensePlate);
            vehicle.setTypeOfDrive(typeOfDrive);
            vehicle.setPower(power);
            vehicle.setPrice(price);
            vehicle.setImgPath(imgPath);
            vehicle.setLicenseClass(licenseClass);
            return vehicle;

        }

    }
}

