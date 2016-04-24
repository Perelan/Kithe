package sharecrew.net.kithee;

/**
 * Created by Deep on 11.03.16.
 */
public class Travel {

    private String destination, departure, returnDate, fare, airline, carPrice;

    public Travel(String destination, String departure, String returnDate, String fare, String airline, String carPrice) {
        this.destination    = destination;
        this.departure      = departure;
        this.returnDate     = returnDate;
        this.fare           = fare;
        this.airline        = airline;
        this.carPrice       = carPrice;
    }

    public String getCarPrice() { 
        return carPrice; 
    }

    public String getDestination() {
        return destination;
    }

    public String getDeparture() {
        return departure;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public String getFare() {
        return fare;
    }

    public String getAirline() {
        return airline;
    }
}
