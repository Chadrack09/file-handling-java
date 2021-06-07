package za.ac.cput.fileHandling;

import java.io.Serializable;

/**
 *
 * @author: Chadrack B. Boudzoumou
 * @email: 219383847@mycput.ac.za
 * @studentN: 219383847
 * @Univerity: Cape Peninsula University Of Technology
 * @since: 04 Jun 2021 | 21:00:34
 */
public class Stakeholder implements Serializable {
    private String stHolderId;

    //Constructor
    public Stakeholder() {
    }
    
    //Constructor
    public Stakeholder(String stHolderId) {
        this.stHolderId = stHolderId;
    }
    
    public String getStHolderId() {
        return stHolderId;
    }
    public void setStHolderId(String stHolderId) {
        this.stHolderId = stHolderId;
    }

    @Override
    public String toString() {
       return stHolderId;
    }
}