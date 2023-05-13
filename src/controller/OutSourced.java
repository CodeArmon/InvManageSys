package controller;

/** This class holds the Outsourced Parts with companyName.*/
public class OutSourced extends Part {
    private String companyName;

    /**This is the Part Outsourced constructor method.
     This method creates the Part Outsourced constructor.
     @param id ID for the Part
     @param name Name for the Part
     @param stock Inventory level for the Part
     @param price Price for the Part
     @param min Min Inventory for the Part
     @param max Max Inventory for the Part
     @param companyName Company Name for the Part
     */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**This method returns the Company Name
     @return Gets Company Name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**This method sets the Company Name
     @param companyName Sets Company Name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
