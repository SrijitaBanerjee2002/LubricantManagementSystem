/**
 * Automobile lubricant sales management.
 *
 * @Srijita Banerjee  (Student, St. Patrick's Higher Secondary School, Asansol, West Bengal, India) 
 *  Guided and supervised by @Aayan Kumar; EECS Department, UC Berkeley; Microsoft Research; Computer Science Engineering, IIT Delhi, India;
 * @29/12/2020
 */
import java.io.*;
class Lubricant
{
    String name;
    int cost_price;
    int maximum_retail_price;
    int quantity;   //different fields of class Lubricant 
}
public class Main
{  
    private DataInputStream in;
    private Lubricant inventory[]; //array of class Lubricant
    private int num_lubricants;
    private int sales[];
    /**
     * Constructor for objects of class Main
     */
    public Main() //default constructor
    {
        in=new DataInputStream(System.in);
        inventory=new Lubricant[100];
        sales=new int[100];
        num_lubricants=0;
        //initializing all the variables
    }

    private boolean perform_sale() throws IOException
    {
        System.out.println("Enter lubricant ID:");
        int lubricant_id = Integer.parseInt(in.readLine());
        if(lubricant_id < 0 || lubricant_id >= num_lubricants) //if lubricant ID entered is invalid 
        {
            System.out.println("Error: Lubricant ID"+lubricant_id+" does not exist. quitting");
            return false;//if lubricant id is invalid, this response goes directly to main() method and the while loop stops working. Hence program stops working 
        }
        System.out.println("Enter quantity:");
        int quantity = Integer.parseInt(in.readLine());
        if(quantity <= 0 || quantity > inventory[lubricant_id].quantity)
        {
            System.out.println("Error: quantity should be positive and not more than available quantity. quitting");//program stops working on invalid input
            return false;
        }
        System.out.println("Cost price is "+inventory[lubricant_id].cost_price+" and MRP is "+inventory[lubricant_id].maximum_retail_price);//cost price and mrp printed for convenience of the staff
        System.out.println("Enter selling price");
        int selling_price = Integer.parseInt(in.readLine());
        if(selling_price < inventory[lubricant_id].cost_price || selling_price > inventory[lubricant_id].maximum_retail_price)//program stops working for invalid input
        {
            System.out.println("Error: Selling price must be between cost price and MRP. quitting");
            return false;
        }
        System.out.println("Amount to be collected = Rs"+selling_price*quantity);//amount to be paid to the customer printed 
        inventory[lubricant_id].quantity -= quantity;//total quantity of lubricant in stock decremented
        sales[lubricant_id] += selling_price*quantity;//sales of the said lubricant aggregated
        return true;
    }

    private boolean add_stock() throws IOException
    {
        System.out.println("Enter lubricant ID:");
        int lubricant_id = Integer.parseInt(in.readLine());
        if(lubricant_id < 0 || lubricant_id >= num_lubricants) //program stops on entering invalid input
        {
            System.out.println("Error: Lubricant ID "+lubricant_id+" does not exist. quitting");
            return false;
        }
        System.out.println("Enter quantity to be added:");
        int quantity_add = Integer.parseInt(in.readLine()); 
        if(quantity_add <= 0) //program stops on entering invalid input
        {
            System.out.println("Error: quantity should be positive. quitting");
            return false;
        }
        inventory[lubricant_id].quantity += quantity_add;//inventory is an array of class Lubricant. quantity_add is incremented with the quantity field under inventory[].
        return true;
    }

    private boolean add_new_lubricant() throws IOException
    {
        System.out.println("Enter lubricant Name:");
        String lubricant_name = in.readLine();
        System.out.println("Enter cost price:");
        int cost_price = Integer.parseInt(in.readLine());
        System.out.println("Enter MRP:");
        int mrp = Integer.parseInt(in.readLine());
        System.out.println("Enter quantity:");
        int quantity = Integer.parseInt(in.readLine());

        if(num_lubricants >= 100)//if the program already has details of 100 lubricants stored 
        {
            System.out.println("Cannot add more lubricants");
            return false;
        }

        inventory[num_lubricants] = new Lubricant();
        inventory[num_lubricants].name = lubricant_name;
        inventory[num_lubricants].cost_price = cost_price;
        inventory[num_lubricants].maximum_retail_price = mrp;
        inventory[num_lubricants].quantity = quantity;//values assigned to the particular fields of inventory[].
        sales[num_lubricants]=0;//selling price of the particular lubricant is initialised to 0 initially
        num_lubricants++;//number of lubricants present incremented by 1
        return true;
    }

    private boolean show_sale_report() throws IOException
    {
        System.out.println("Enter lubricant ID:");
        int lubricant_id = Integer.parseInt(in.readLine());
        if(lubricant_id < 0 || lubricant_id >= num_lubricants) //program stops working on receiving invalid input
        {
            System.out.println("Error: Lubricant ID"+lubricant_id+" does not exist. quitting");
            return false;
        }
        System.out.println("Total sales: Rs"+sales[lubricant_id]);
        System.out.println("Quantity remaining:"+inventory[lubricant_id].quantity); //total sales and remaining quantity of lubricant displayed
        return true;
    }

    private boolean edit_inventory() throws IOException
    {
        print_inventory();//details of lubricants displayed
        System.out.println("1)Add stock \n2)Add new lubricant");//choices shown as to how the staff can edit the inventory
        System.out.println("Enter choice 1-2");
        
        int ch = Integer.parseInt(in.readLine());//choice entered by the user

        switch(ch)
        {
            case 1:
            add_stock();//if choice is 1,the staff can add stock of desired lubricant
            return true;

            case 2:
            return add_new_lubricant();//if choice is 2, details of new lubricant can be added

            default:
            System.out.println("Illegal argument!! Quitting");//program stops on receiving invalid input
            return false;
        }
    }

    private boolean print_inventory() throws IOException
    {
        System.out.println("Lubricant ID\tLubricant Name\tCost Price\tMRP\tQuantity");//headings displayed
        for(int i=0; i<num_lubricants;i++)
        {
            System.out.println(i+"\t"+inventory[i].name+"\t"+inventory[i].cost_price+"\t"+inventory[i].maximum_retail_price+"\t"+inventory[i].quantity);//details of all the lubricants printed
        }
        return true;
    }

    private boolean main_menu() throws IOException
    {       
        System.out.println("1)Edit inventory \n2)Display details of all lubricants \n3)Sales \n4)Generate statistics \n5)Quit");
        System.out.println("Enter choice 1-5");//program asks to enter choice
        
        int ch = Integer.parseInt(in.readLine());//choice entered by the user

        switch(ch)
        {
            case 1:
            return edit_inventory();//if choice is 1, inventory is edited
        
            case 2:
            return print_inventory();//if choce is 2, inventory is printed
            
            case 3:
            return perform_sale();//if choice is 3, sales is calculated
            
            case 4:
            return show_sale_report();//if choice is 4, total sales and remaining quantity of a particular lubricant is displayed
            
            case 5:
            System.out.println("quitting");//option to exit from the program
            return false;
        
            default://program stops working on receiving invalid input
            System.out.println("Illegal argument!! Quitting");
            return false;
        }
    }
    
    public static void main(String Args[]) throws IOException
    {
        Main obj=new Main();//object of the class is created
        boolean ch = true;
        while(ch)
        {   
            ch = obj.main_menu();//calls main_menu()            
        }   
    }   
}