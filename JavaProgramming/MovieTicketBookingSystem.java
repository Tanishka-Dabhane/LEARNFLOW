import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Online Movie Ticket Booking System
public class MovieTicketBookingSystem {

    // Movie class
    static class Movie {
        private final String movieID;
        private final String title;
        private final List<Showtime> showtimes;

        public Movie(String movieID, String title) {
            this.movieID = movieID;
            this.title = title;
            this.showtimes = new ArrayList<>();
        }

        public String getMovieID() { return movieID; }
        public String getTitle() { return title; }
        public List<Showtime> getShowtimes() { return showtimes; }

        public void addShowtime(Showtime showtime) {
            showtimes.add(showtime);
        }

        @Override
        public String toString() {
            return movieID + ": " + title;
        }
    }

    // Showtime class
    static class Showtime {
        private final String time;
        private final int totalSeats;
        private int availableSeats;

        public Showtime(String time, int totalSeats) {
            this.time = time;
            this.totalSeats = totalSeats;
            this.availableSeats = totalSeats;
        }

        public String getTime() { return time; }
        public int getTotalSeats() { return totalSeats; }
        public int getAvailableSeats() { return availableSeats; }

        public boolean bookSeats(int seats) {
            if (availableSeats >= seats) {
                availableSeats -= seats;
                return true;
            } else {
                return false;
            }
        }

        public void cancelBooking(int seats) {
            availableSeats += seats;
        }

        @Override
        public String toString() {
            return time + " (Seats available: " + availableSeats + ")";
        }
    }

    // Booking class
    static class Booking {
        private final Movie movie;
        private final Showtime showtime;
        private final int seatsBooked;

        public Booking(Movie movie, Showtime showtime, int seatsBooked) {
            this.movie = movie;
            this.showtime = showtime;
            this.seatsBooked = seatsBooked;
        }

        public Movie getMovie() { return movie; }
        public Showtime getShowtime() { return showtime; }
        public int getSeatsBooked() { return seatsBooked; }

        public void cancel() {
            showtime.cancelBooking(seatsBooked);
        }

        @Override
        public String toString() {
            return "Movie: " + movie.getTitle() + ", Showtime: " + showtime.getTime() + ", Seats booked: " + seatsBooked;
        }
    }

    // Online Movie Ticket Booking System class
    static class BookingSystem {
        private final List<Movie> movies;
        private final List<Booking> bookings;

        public BookingSystem() {
            movies = new ArrayList<>();
            bookings = new ArrayList<>();

            // Adding some sample movies and showtimes
            Movie movie1 = new Movie("M01", "Inside Out");
            movie1.addShowtime(new Showtime("10:00 AM", 100));
            movie1.addShowtime(new Showtime("1:00 PM", 100));
            movie1.addShowtime(new Showtime("5:00 PM", 100));

            Movie movie2 = new Movie("M02", "Toy Story");
            movie2.addShowtime(new Showtime("11:00 AM", 100));
            movie2.addShowtime(new Showtime("3:00 PM", 100));
            movie2.addShowtime(new Showtime("7:00 PM", 100));

            movies.add(movie1);
            movies.add(movie2);
        }

        // View available movies
        public void viewMovies() {
            System.out.println("\nAvailable Movies:");
            for (Movie movie : movies) {
                System.out.println(movie);
            }
        }

        // View showtimes for a specific movie
        public void viewShowtimes(String movieID) {
            Movie movie = findMovie(movieID);
            if (movie != null) {
                System.out.println("\nShowtimes for " + movie.getTitle() + ":");
                for (Showtime showtime : movie.getShowtimes()) {
                    System.out.println(showtime);
                }
            } else {
                System.out.println("Movie not found.");
            }
        }

        // Book tickets for a movie
        public void bookTickets(String movieID, String showtimeStr, int seats) {
            Movie movie = findMovie(movieID);
            if (movie != null) {
                Showtime showtime = findShowtime(movie, showtimeStr);
                if (showtime != null) {
                    if (showtime.bookSeats(seats)) {
                        Booking booking = new Booking(movie, showtime, seats);
                        bookings.add(booking);
                        System.out.println("Successfully booked " + seats + " seats for " + movie.getTitle() + " at " + showtime.getTime());
                    } else {
                        System.out.println("Not enough seats available.");
                    }
                } else {
                    System.out.println("Showtime not found.");
                }
            } else {
                System.out.println("Movie not found.");
            }
        }

        // Cancel a booking
        public void cancelBooking(int bookingID) {
            if (bookingID > 0 && bookingID <= bookings.size()) {
                Booking booking = bookings.get(bookingID - 1);
                booking.cancel();
                bookings.remove(bookingID - 1);
                System.out.println("Booking cancelled: " + booking);
            } else {
                System.out.println("Invalid booking ID.");
            }
        }

        // View all bookings
        public void viewBookings() {
            System.out.println("\nYour Bookings:");
            if (bookings.isEmpty()) {
                System.out.println("No bookings found.");
            } else {
                for (int i = 0; i < bookings.size(); i++) {
                    System.out.println((i + 1) + ". " + bookings.get(i));
                }
            }
        }

        // Find a movie by ID
        private Movie findMovie(String movieID) {
            for (Movie movie : movies) {
                if (movie.getMovieID().equals(movieID)) {
                    return movie;
                }
            }
            return null;
        }

        // Find a showtime by time string
        private Showtime findShowtime(Movie movie, String showtimeStr) {
            for (Showtime showtime : movie.getShowtimes()) {
                if (showtime.getTime().equals(showtimeStr)) {
                    return showtime;
                }
            }
            return null;
        }
    }

    // Main method
    public static void main(String[] args) {
        BookingSystem system = new BookingSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nOnline Movie Ticket Booking System:");
            System.out.println("1. View Movies");
            System.out.println("2. View Showtimes");
            System.out.println("3. Book Tickets");
            System.out.println("4. View Bookings");
            System.out.println("5. Cancel Booking");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> system.viewMovies();
                case 2 -> {
                    System.out.print("Enter Movie ID: ");
                    String movieID = scanner.nextLine();
                    system.viewShowtimes(movieID);
                }
                case 3 -> {
                    System.out.print("Enter Movie ID: ");
                    String movieIDForBooking = scanner.nextLine();
                    System.out.print("Enter Showtime (e.g., 10:00 AM): ");
                    String showtime = scanner.nextLine();
                    System.out.print("Enter number of seats: ");
                    int seats = scanner.nextInt();
                    system.bookTickets(movieIDForBooking, showtime, seats);
                }
                case 4 -> system.viewBookings();
                case 5 -> {
                    system.viewBookings();
                    System.out.print("Enter Booking ID to cancel: ");
                    int bookingID = scanner.nextInt();
                    system.cancelBooking(bookingID);
                }
                case 6 -> {
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
