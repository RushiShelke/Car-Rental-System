import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car{
    private String carId;

    private String Brand;

    private String Model;

    private double basePricePerDay;

    private boolean isAvailable;

    public Car(String carId, String Brand, String Model, double basePricePerDay){
          this.carId = carId;
          this.Brand = Brand;
          this.Model = Model;
          this.basePricePerDay = basePricePerDay;
          this.isAvailable = true;
    }

    public String getCarId(){
        return carId;
    }

    public String getBrand(){
        return Brand;
    }

    public String getModel(){
        return Model;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar(){
        isAvailable = true;
    }

}

class Customer {
    private String CustomerId;

    private String CustomerName;

    private int AdharNo;

    private int Licence;
    public Customer(String CustomerId, String CustomerName,  int AdharNo, int Licence){
        this.CustomerId = CustomerId;
        this.CustomerName = CustomerName;
        this.AdharNo = AdharNo;
        this.Licence = Licence;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public String getCustomerName(){
        return CustomerName;
    }



    public int getAdharNo() {
        return AdharNo;
    }

    public int getLicence() {
        return Licence;
    }
}

// This (Rental) Class Will store combination of car and customer class.
class Rental {
    private Car car;

    private Customer customer;

    private int days;

    public Rental(Car car, Customer customer, int days){
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();

    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Sorry Car is Not Available at the Moment:(, But other cars are also worth Riding");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental rentaltoRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentaltoRemove = rental;
                break;
            }
        }
        if (rentaltoRemove != null) {
            rentals.remove(rentaltoRemove);
            System.out.println("Car Returned Successfully, Thank You For Using Our Car:)");
        } else {
            System.out.println("Sorry, This Car Was Not Rented");
        }
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("====Car Rental System====");
            System.out.println("1.Rent a Car");
            System.out.println("2.Return a Car");
            System.out.println("3.Exit");
            System.out.print("Enter Your Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();


            if (choice == 1) {
                System.out.println("\n===Rent a Car===\n");
                System.out.println("Enter Your Name:  ");
                String customerName = scanner.nextLine();


                System.out.println("\n===Available Cars At The Moment===\n");
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + "-" + car.getBrand() + "-" + car.getModel());
                    }
                }

                System.out.println("\n=Enter The Car ID You Want To Rent: ");
                String carId = scanner.nextLine();

                System.out.println("\n=For How Much Day You Need a Car: ");
                int rentalDays = scanner.nextInt();
                scanner.nextLine();

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName, 123456789, 987654321);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n===Rental Information===\n");
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name:" + newCustomer.getCustomerName());
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $ %.2f%n", totalPrice);
                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\nCar rented successfully.");
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                } else {
                    System.out.println("\nInvalid car selection or car not available for rent.");
                }
            } else if (choice == 2) {
                System.out.println("\n== Return a Car ==\n");
                System.out.print("Thank You For Using our service,Enter the car ID you want to return: ");
                String carId = scanner.nextLine();
                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }
                if (carToReturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                    if (customer != null) {
                        returnCar (carToReturn);
                        System.out.println("Car returned successfully by "+ customer.getCustomerName());
                    } else {
                        System.out.println("Car was not rented or rental information is missing.");
                    }
                } else {
                    System.out.println("Invalid car ID or car is not rented.");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.:(");
            }
            System.out.println("\nThank you for using Our Car Rental System:)\n");
        }

                }
            }



    public class main {

        public static void main(String[] args) {
            CarRentalSystem rentalSystem = new CarRentalSystem();

            Car car1 = new Car("4200", "Mahindra", "Thar", 3000.0);
            Car car2 = new Car("4300", "Maruti Suzuki", "Fronx", 2500.0);
            Car car3 = new Car("4400", "Toyota", "Fortuner", 3200.0);
            Car car4 = new Car("4500", "Hyundai", "Creta", 2500.0);
            rentalSystem.addCar(car1);
            rentalSystem.addCar(car2);
            rentalSystem.addCar(car3);
            rentalSystem.addCar(car4);

            rentalSystem.menu();
        }
    }
