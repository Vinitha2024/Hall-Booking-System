import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

    
public class Main {
    static int halls[]={2,2,5,5,10,10,15,20,20,25,25,30,30,35,35,40,40,45,50,50};
    //To store booked halls
    static Set<String> bookings =new HashSet<>();
      public static void hall(int level,int persons,String date, String time) {

        boolean booked=false;
        System.out.println("Checking available halls...");
        int countUnrestricted=0;
        for(int i=0;i<halls.length;i++){
            
            int capacity=halls[i];
            int hallnum=i+1;
            //Checking capacity
            if(capacity< persons)
               continue;

            String timeKey=hallnum+"-"+date+"-"+time;

            boolean slotBooked=bookings.contains(timeKey);

            if(slotBooked)
                continue;

            booked=true;

            boolean restricted=(capacity >=20 && level>3);
            if(!restricted)
                countUnrestricted++;

            //Displaying availability..
            System.out.print("Hall "+hallnum+" with capacity "+capacity+" is available.");
            
            if(restricted){
                System.out.print("-----Restricted access.");   
            }

            System.out.println();
        }
        if(!booked){
            System.out.println("No halls available.");
            return;
        }
        Scanner scan = new Scanner(System.in);
        while(true){
            if(countUnrestricted==0){
                System.out.println("You don't have permission to book these halls as the participant count is higher than 20");
                break;
            }
            System.out.print("Enter the hall number to book: ");
            int selectedhall=scan.nextInt();

            if(selectedhall <1 || selectedhall > halls.length){
                System.out.println("Invalid hall number.");
                continue;
            }

            if(halls[selectedhall -1] < persons){
                System.out.println("Selected hall cannot accommodate the number of participants.");
                continue;
            }

            if(halls[selectedhall -1] >=20 && level >3){
                System.out.println("You do not have permission to book this hall.");
                continue;
            }
            String bookingKey=selectedhall+"-"+date+"-"+time;
            if(bookings.contains(bookingKey)){
                System.out.println("Selected hall is already booked for the chosen slot.");
                continue;
            }

            bookings.add(bookingKey);
        

        System.out.println("Booking Details:");
        System.out.println("Hall Number: "+selectedhall);
        System.out.println("Date: "+date);
        System.out.println("Time: "+(time.equals("M")?"Morning":"Evening"));
        System.out.println("Participants: "+persons);
        scan.close();
         break;
         
        }
        
    }
    public static String checkValidDate(Scanner scan){
        while(true){
            DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
            System.out.print("Enter date (DD-MM-YYYY):");
            String date=scan.next();
             try{
                LocalDate enteredDate= LocalDate.parse(date,formatter);
                LocalDate today= LocalDate.now();

                if(enteredDate.isBefore(today)){
                    System.out.println("Date cannot be in the past.");
                    continue;
                }
                return enteredDate.format(formatter);
                 
             }
             catch(DateTimeParseException e){
                System.out.println("Invalid date.Please enter a valid calender Date.");
             }
        }
    }
    public static void main(String args[]){
        Scanner scan=new Scanner(System.in);
        System.out.println("-----------Hall Booking System-----------");
        System.out.println("If Admin enter 1");
        System.out.println("If SeniorManager enter 2");
        System.out.println("If Manager enter 3");
        System.out.println("If SeniorDeveloper enter 4");
        System.out.println("If Developer enter 5");
      
       
        System.out.println("Enter your role in company:");
        int level=scan.nextInt();

        System.out.println("Enter the number of participants in meeting:");
        int persons=scan.nextInt();

        System.out.println("Date of meeting (DD-MM-YYYY):");
        String date= checkValidDate(scan);


        System.out.println("Enter slot (M for Morning,E for evening):");
        String slot=scan.next().toUpperCase();

        //Invalid condition for slot
        while(!slot.equals("M")&& !slot.equals("E")){
            System.out.println("Invalid slot.");
            slot=scan.next().toUpperCase();
        }

        //Function call
        hall(level,persons,date,slot);
       scan.close();
    }
}
