package za.ac.cput.fileHandling;

/**
 *
 * @author: Chadrack B. Boudzoumou
 * @email: 219383847@mycput.ac.za
 * @studentN: 219383847
 * @Univerity: Cape Peninsula University Of Technology
 * @since: 04 Jun 2021 | 21:08:03
 */
public class Supplier extends Stakeholder {
    private String name;
    private String productType;
    private String productDescription;

    public Supplier() {
        super();
    }

    public Supplier(String stHolderId, String name, String productType, String productDescription ) {
        super(stHolderId);
        this.name = name;
        this.productType = productType;
        this.productDescription = productDescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    @Override
    public String toString() {
        return String.format("%-5s\t%-20s\t%-10s\t%-15s",super.toString(), getName(),getProductType(), getProductDescription());
    }
}