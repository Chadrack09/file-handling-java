package za.ac.cput.fileHandling;

/**
 *
 * @author: Chadrack B. Boudzoumou
 * @email: 219383847@mycput.ac.za
 * @studentN: 219383847
 * @Univerity: Cape Peninsula University Of Technology
 * @since: 04 Jun 2021 | 23:09:22
 */
public class MainMethod {
  
  public static void main(String[] args) {
    
    /**
     * @mainMethod
     * @note: Three(3) methods [ readFile(), writeCustomerToFile(), writeSupplierToFile() ]  
     *        must be called in the main method to well implement the application        
     * 
     */
    
    FileHandle fileHandle = new FileHandle();
    fileHandle.readFile();
    fileHandle.writeCustomerToFile();
    fileHandle.writeSupplierToFile();
    
  }
  
}