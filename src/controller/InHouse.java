package controller;

/** This class holds the InHouse Parts with machineId.*/
public class InHouse extends Part {
    private int machineId;

    /**This is the Part InHouse constructor method.
     This method creates the Part InHouse constructor.
     @param id ID for the Part
     @param name Name for the Part
     @param stock Inventory level for the Part
     @param price Price for the Part
     @param min Min Inventory for the Part
     @param max Max Inventory for the Part
     @param machineId Machine ID for the Part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        super(id, name, price, stock, min, max);

        this.machineId = machineId;
    }

    /**This method returns the Machine ID
     @return Gets Machine ID
     */
    public int getMachineId() {
        return machineId;
    }

    /**This method sets the Machine ID
     @param machineId  Sets Machine ID
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
