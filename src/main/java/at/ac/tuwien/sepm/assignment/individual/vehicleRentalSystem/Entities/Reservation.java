package at.ac.tuwien.sepm.assignment.individual.vehicleRentalSystem.Entities;


import java.sql.Timestamp;

public class Reservation {



    /**
     * The reservation's id
     */
    private int reservation_id;
    /**
     * The reservation's second_id incase of multiple vehicle reservation,it will be increased once the reservation is changed.
     */
    private int reservation_secondID;
    /**
     * The name of the client, who makes the reservation.
     */
    private String clientName;
    /**
     * the Payment of the client. could be iban or credit card.
     */
    private String Payment;
    /**
     * The start time of the reservation
     */
    private Timestamp start;
    /**
     * The end time of the reservation
     */
    private Timestamp end;
    /**
     * The selected vehicle's id
     */
    private int vehicleID;
    /**
     * The price pro vehicle.
     */
    private int price;
    /**
     * The reservation's total price.
     */
    private int totalPrice;
    /*
     *true if the reservation is cancelled.
     *false on default.
     * */
    private Timestamp finalizationDate;
    /*
    *true if the reservation is cancelled.
    *false on default.
    * */
    private boolean isDeleted;
    /*
     * open if the reservation is open. Default is open.
     * closed if the reservation is closed and the invoice is created, no edit is possible.
     * cancelled if the reservation is deleted.
     * */
    private String status;
    /* iban or credit card **/
    private String artOFPayment;
    /**
     * Constructor with possible arguments
     * suitable for builder. DTO
     * */
    public Reservation() {
    }
    /**
     * Constructor with possible arguments
     * @param clientName the client's name
     * @param Payment the client's Payment
     * @param start reservation start time.
     * @param end reservation start time.
     * @param vehicleID the vehicle's id
     * @param price price pro vehicle
     * @param status the status of the reservation.
     */
    public Reservation(String clientName, String Payment, Timestamp start, Timestamp end, int vehicleID, int price, String status) {
        this.clientName = clientName;
        this.Payment = Payment;
        this.start = start;
        this.end = end;
        this.vehicleID = vehicleID;
        this.price = price;
        this.status = status;
    }
    /** get the time of finalization
     * @return time of finalization
     * */
    public Timestamp getFinalizationDate() {
        return finalizationDate;
    }
    /**
     * setter for date, in which reservation will be closed.
     * @param finalizationDate is the date of finalization
     * */
    public void setFinalizationDate(Timestamp finalizationDate) {
        this.finalizationDate = finalizationDate;
    }
    /**
     * A getter for the status
     * @return reservation's status
     */
    public String getStatus() {
        return status;
    }
    /**
     * A setter for the status
     * @param status the new status
     */

    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * A getter for the id
     * @return reservation's id
     */
    public int getReservation_id() {
        return reservation_id;
    }
    /**
     * A setter for the reservatio's id
     * @param reservation_id the new reservation_id
     */
    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }
    /**
     * A getter for the name of the client
     * @return client's name
     */
    public String getClientName() {
        return clientName;
    }
    /**
     * A setter for the name of the client
     * @param clientName the client's name will be saved for the first time or edited.
     */

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    /**
     * A getter for the Payment of the client
     * @return client's Payment
     */
    public String getPayment() {
        return Payment;
    }
    /**
     * A setter for the Payment of the client
     * @param payment the client's Payment.
     */

    public void setPayment(String payment) {
        this.Payment = payment;
    }
    /**
     * A getter for the start time of a reservation
     * @return start time
     */
    public Timestamp getStart() {
        return start;
    }
    /**
     * A setter for the start time of a reservation
     * @param start will be set the start time
     */

    public void setStart(Timestamp start) {
        this.start = start;
    }
    /**
     * A getter for the end time of a reservation
     * @return end time
     */
    public Timestamp getEnd() {
        return end;
    }
    /**
     * A setter for the end time of a reservation
     * @param end will be set the end time
     */
    public void setEnd(Timestamp end) {
        this.end = end;
    }

    /**
     * A getter for the id of the vehicle
     * @return vehicle's id
     */

    public int getVehicleID() {
        return vehicleID;
    }
    /**
     * A setter for vehicles id
     * @param vehicleID of a new vehicle
     */
    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }
    /**
     * A getter for the price pro vehicle
     * @return vehicle's price pro vehicle
     */
    public int getPrice() {
        return price;
    }
    /**
     * A setter for the price pro vehicle
     * @param price the new price pro vehicle
     */
    public void setPrice(int price) {
        this.price = price;
    }
    /**
     * A getter for the total price
     * @return reservation's total price
     */
    public int getTotalPrice() {
        return totalPrice;
    }
    /**
     * A setter for the total price
     * @param totalPrice is the reservation's total price
     */
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
    /**
     * check if the reservation is deleted or not.
     * @return  deleted is true if the vehicle is deleted.
     */
    public boolean isDeleted() {
        return isDeleted;
    }
    /**
     * A setter for the isdeleted
     * @param deleted is true if the reservation should be deleted.
     */
    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
    /**
     * A getter for the art of payment
     * @return  artOFPayment is either iban or credit card
     */
    public String getArtOFPayment() {
        return artOFPayment;
    }
    /**
     * A setter for the art of payment
     * @param artOFPayment is either iban or credit card
     */
    public void setArtOFPayment(String artOFPayment) {
        this.artOFPayment = artOFPayment;
    }
    /**
     * A getter for the second id of a reservation, this is the id that will be
     * changed once the reservation ends and the new one begins.
     * @return  bookingid
     */

    public int getReservation_secondID() {
        return reservation_secondID;
    }
    /**
     * A setter for the second id of a reservation, this is the id that will be
     * changed once the reservation ends and the new one begins.
     * @param   reservation_secondID the new id for the reservation.
     */

    public void setReservation_secondID(int reservation_secondID) {
        this.reservation_secondID = reservation_secondID;
    }

    /**
     * A toString method
     * @return Reservation with all of its' attributes.
     */
    @Override
    public String toString() {
        return "Reservation{" +
            "reservation_id=" + reservation_id +
            ", clientName='" + clientName + '\'' +
            ", start=" + start +
            ", end=" + end +
            ", status=" + status +'}';
    }

    //start of DTO (BUILDER).
    public static final ReservationBuilder builder() {return new ReservationBuilder();}

    public static final class ReservationBuilder {
        private int reservation_id;
        public ReservationBuilder reservation_id(int reservation_id)
        {
            this.reservation_id = reservation_id;
            return this;
        }
        private String clientName;
        public ReservationBuilder clientName(String clientName)
        {
            this.clientName = clientName;
            return this;
        }


        private String artOFPayment;
        public ReservationBuilder artOFPayment(String artOFPayment)
        {
            this.artOFPayment = artOFPayment;
            return this;
        }
        private int reservation_secondID;
        public ReservationBuilder reservation_secondID(int reservation_secondID)
        {
            this.reservation_secondID = reservation_secondID;
            return this;
        }

        private String Payment;
        public ReservationBuilder Payment(String Payment)
        {
            this.Payment = Payment;
            return this;
        }

        private Timestamp start;
        public ReservationBuilder start(Timestamp start)
        {
            this.start = start;
            return this;
        }
        private Timestamp end;
        public ReservationBuilder end(Timestamp end)
        {
            this.end = end;
            return this;
        }
        private int vehicleID;
        public ReservationBuilder vehicleID(int vehicleID)
        {
            this.vehicleID = vehicleID;
            return this;
        }
        private int price;
        public ReservationBuilder price(int price)
        {
            this.price = price;
            return this;
        }
        private int totalPrice;
        public ReservationBuilder totalPrice(int totalPrice)
        {
            this.totalPrice = totalPrice;
            return this;
        }
        private Timestamp finalizationDate;
        public ReservationBuilder finalizationDate(Timestamp finalizationDate)
        {
            this.finalizationDate = finalizationDate;
            return this;
        }
        private boolean isDeleted;
        public ReservationBuilder isDeleted(boolean isDeleted)
        {
            this.isDeleted = isDeleted;
            return this;
        }
        private String status;
        public ReservationBuilder status(String status)
        {
            this.status = status;
            return this;
        }

        public Reservation build()
        {
            Reservation reservation = new Reservation();

            reservation.setClientName(clientName);
            reservation.setPayment(Payment);
            reservation.setStart(start);
            reservation.setEnd(end);
            reservation.setVehicleID(vehicleID);
            reservation.setPrice(price);
            reservation.setStatus(status);
            return reservation;
        }

    }


}

