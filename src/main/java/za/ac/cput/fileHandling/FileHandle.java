package za.ac.cput.fileHandling;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Collections;

/**
 *
 * @author: Chadrack B. Boudzoumou
 * @email: 219383847@mycput.ac.za
 * @studentN: 219383847
 * @Univerity: Cape Peninsula University Of Technology
 * @since: 04 Jun 2021 | 22:48:28
 * 
 * @assignment 3 - Term 2
 * @Note: This Class or Object called [ FileHandle ] contains all methods
 * and properties required in the assignment;
 */
public class FileHandle {
  private File file;
  private FileOutputStream fo;
  private ObjectOutputStream output;
  private FileInputStream fi;
  private ObjectInputStream input;
  private PrintWriter pw;
  private ArrayList<Customer> customerList = new ArrayList<>();
  private ArrayList<Supplier> supplierList = new ArrayList<>();
  private String path;
  private Date date;
  private DateFormat dateFormatter;
  
  public String getPath() {
    path = ".\\src\\main\\files\\";
    return path;
  }
  
  /**
   * Step 2.a - Reading The File [ stakeholder.ser ]
   * @method readFile()
   * @Note: This method reads the entire file and store objects into lists
   */
  public void readFile() {
    
    try {
      fi = new FileInputStream(".\\stakeholder.ser");
      input = new ObjectInputStream(fi);
      while(true) {
        Object obj = input.readObject();
        if(obj instanceof Customer){
          customerList.add((Customer) obj);
        }
        else if (obj instanceof Supplier) {
          supplierList.add((Supplier) obj);
        }
      }
    } 
    catch(IOException ex) {
      System.out.println("...");
    } catch(ClassNotFoundException ex) {
      System.out.println("Class Not Found");
    }
    displayAll();
  }// end of method readFile
  /* display all in the console was called inside readFile() */
  public void displayAll() {
    sortCustomer();
    sortSupplier();
    
    System.out.println("+---------------------------------------------------------------------------------------------------+");
    System.out.println("All Customers");
    System.out.println("+---------------------------------------------------------------------------------------------------+");
    for(Customer c: customerList) {
      System.out.println(c);
      System.out.println("+---------------------------------------------------------------------------------------------------+");
    }
    System.out.println("\n+---------------------------------------------------------------------------------------------------+");
    System.out.println("All Suppliers");
    System.out.println("+---------------------------------------------------------------------------------------------------+");
    
    for(Supplier s: supplierList) {
      System.out.println(s);
      System.out.println("+---------------------------------------------------------------------------------------------------+");
    }
    System.out.println("All files created by myself are stored in [ ./src/main/files ] folder");
  }
  
  /** --------------------------------------------------------------------------
   * Step 2.b - Sorting
   * @method sortCustomer()
   * @Note: These methods sort customer by stakeholder id and Supplier by name
   */
  public void sortCustomer() {
    Collections.sort(customerList, (o1, o2) -> {
      return o1.getStHolderId().compareTo(o2.getStHolderId());
    });
  }//end of method
  
  /**
   * Step 2.c - Determine each customer's age
   * @method getAllCustomerAge()
   * @Note: This method determine customer's age according to their dob
   */
  public int[] getAllCustomerAge() {
    
    int[] getAllAge = new int[customerList.size()];
    
    for(int i = 0; i < customerList.size(); i++) {
      String customerName = customerList.get(i).getFirstName();
      
      try {
        String dob = customerList.get(i).getDateOfBirth();
        Calendar customerCalendar = Calendar.getInstance();
        customerCalendar.setTime(parseDate(dob));
        
        Calendar localCal = Calendar.getInstance();

        /* Note YOB Stand for Year Of Birth */
        int customerYOB = customerCalendar.get(Calendar.YEAR);
        int currentYear = localCal.get(Calendar.YEAR);        
        
        getAllAge[i] = (currentYear - customerYOB);
      } 
      catch(ParseException ex) {
        System.out.println("Error parsing date");
      }
    }
    return getAllAge;
  }//end of method
  
  /**
   * Step 2.d, 2.e - Reformat Date and Write Customer object to file
   * @method writeCustomerToFile()
   * @Note: This method determine customer's age according to their dob
   */
  public void writeCustomerToFile() {
    sortCustomer();
    try {

      String fileName = "customerOutFile.txt";
      File file = createFile(fileName);
     
      FileWriter fw = new FileWriter(file);
      pw = new PrintWriter(fw);
      int[] age = getAllCustomerAge();
      
      pw.println(String.format("%s", "+--------------------------[ Customer ]----------------------------+"));
      pw.println(String.format("| %-10s %-10s %-10s %-20s %-10s |", "ID", "Name", "Surname", "Date of birth", "Age"));
      pw.println(String.format("%s", "+------------------------------------------------------------------+"));
      
      for(int i = 0; i < customerList.size(); i++) {
        
        String dob = customerList.get(i).getDateOfBirth();
        Date dates = new Date();
        try{
          dates = parseDate(dob);
        } catch(ParseException ex) {System.out.println("error parsing date");}
        DateFormat dateFormatter = new SimpleDateFormat("dd MMMM yyyy");
        String srtFormat = dateFormatter.format(dates);
        
        pw.println(String.format("| %-10s %-10s %-10s %-20s %-10d |", customerList.get(i).getStHolderId(), customerList.get(i).getFirstName(), customerList.get(i).getSurName(), srtFormat, age[i]));

      }
      pw.println(String.format("%s\n", "+------------------------------------------------------------------+"));
      CanOrNotToRent();

      pw.close();
    }
    catch(IOException ex) {
      System.out.println("Error creating the file");
    }
  }
  
  /**
   * Step 2.f - Determine number of customer who or cannot rent
   * @method CanOrNotRent()
   * @Note: This method prints out total of customer's who can or can't rent
   */
  public void CanOrNotToRent() {
    int canRent = 0;
      int cannotRent = 0;
      for(int i = 0; i < customerList.size(); i++) {
        if (customerList.get(i).getCanRent()  == true) {
          canRent++;
        } else {cannotRent++;}
      }
      pw.println(String.format("Number of customers who can rent  : %d", canRent));
      pw.println(String.format("Number of  customers who can rent : %d", cannotRent));
  }
  
  /**
   * Step 3.a Sorting Supplier
   * @method sortSupplier()
   * @Note: These methods sort Supplier by name
   */
  public void sortSupplier() {
    Collections.sort(supplierList, (o1, o2) -> {
      return o1.getName().compareTo(o2.getName());
    });
  }//end of method
  
  /**
   * Step 3.b Write Supplier object to file
   * @method writeSupplierToFile()
   */
  public void writeSupplierToFile() {
    String fileName = "supplierOutFile.txt";
    File file = createFile(fileName);
    sortSupplier();
    
    try {
      FileWriter fw = new FileWriter(file);
      PrintWriter pw = new PrintWriter(fw);
      
      pw.println(String.format("%s","+-------------------------------[ Supplier ]--------------------------------+"));
      pw.println(String.format("| %-10s %-20s %-15s %-25s |", "ID", "Name", "Prod type", "Description"));
      pw.println(String.format("%s","+---------------------------------------------------------------------------+"));
      for(int i = 0; i < supplierList.size(); i++) {
        pw.println(String.format("| %-10s %-20s %-15s %-25s |", supplierList.get(i).getStHolderId(), supplierList.get(i).getName(), supplierList.get(i).getProductType(), supplierList.get(i).getProductDescription()));
      }
      pw.println(String.format("%s","+---------------------------------------------------------------------------+"));
      pw.close();
    } 
    catch(IOException ex) {
      System.out.println("error creating file");
    }
  }
  
  
  
  
  /**---------------------------------------------------------------------------
   * 
   * @note: these methods are being called by other methods
   * @method parseDate()
   * @param dob [date of birth]
   * @Note: This method get dob String parameter and parse it to date object
   */
  public Date parseDate(String dob) throws ParseException {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date;
    Calendar calendar;

    date = dateFormat.parse(dob);
    return date;
  }//end of method
  /**
   * @method createFile()
   * @Note: This method return a file object that was created if does not exist
   */
  public File createFile(String fileName) {
    
    File file = new File(getPath() + fileName);
    try {
      if(file.exists()){
        file.createNewFile();
        System.out.println("File " +fileName+ " already exist");
      } else { System.out.println("file " +fileName+ " created succesfully"); }
    } catch(IOException ex){
      System.out.println("error creating file");
    }
    return file;
  }//end of method
  public void deleteFile(String fileName) {
    File file = new File(getPath() + fileName);
    if(file.delete()){
      System.out.println("File " +fileName+ " was succesfully deleted");
    } else { System.out.println("Error deleting the file"); }
  }
  /*------------------------------------------------------------------------- */
  
  
}//end of class FileHandler.java