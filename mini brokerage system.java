package miniBrokerageSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

class InvalidInputException extends Exception 
{
    public InvalidInputException() 
    {
        super();
    }
    public InvalidInputException(String message) 
    {
        super(message);
    }
}

class stocks
{
    String ticker;
    String sector;
    double openPrice,closePrice,highPrice,lowPrice;
    String stockexchange;
    stocks()
    {
        
    }
    
    stocks (String ticker,String sector,double openPrice,double highPrice,double lowPrice,double closePrice)
    {
        this.ticker=ticker;
        this.closePrice=closePrice;
        this.highPrice=highPrice;
        this.lowPrice=lowPrice;
        this.openPrice=openPrice;
        this.sector=sector;
    }
    
    @Override
    public String toString()
    {
        return "scrip:"+ticker+", sector:"+sector+", O: "+openPrice+", H: "+highPrice+", L: "+lowPrice+", C: "+closePrice;
    }
}

class stockss extends stocks
{
    double prevClose,lastPrice;
    Date date;
    static ArrayList<stockss> everyday_data=new ArrayList<>();
    stockss(Date date,String ticker,double prevClose,double openPrice,double highPrice,double lowPrice,double lastPrice,double closePrice)
    {
        this.date=date;
        this.closePrice=closePrice;
        this.highPrice=highPrice;
        this.lastPrice=lastPrice;
        this.lowPrice=lowPrice;
        this.openPrice=openPrice;
        this.prevClose=prevClose;
        this.ticker=ticker;
        switch ((int)(Math.random()*3)) 
        {
            case 0:
                stockexchange="NSE";
                break;
            case 1:
                stockexchange="BSE";
                break;
            case 2:
                stockexchange="Both";
        }
    }
    
    static void addStockss(stockss s)
    {
        everyday_data.add(s);
    }
    
    static void display()
    {
        for (stockss p:everyday_data)
            System.out.println(p.toString());
    }
    
    static void averagePrice()
    {
        double sum=0.0;
        for (stockss p:everyday_data)
            sum+=p.closePrice;
        System.out.println("Average Price of the stock: "+sum/15);
    }
    
    static void maxDrawdown()
    {
        int i,j;
        double drawdown,max=0;
        for(i=1;i<everyday_data.size()-1;i++)
        {
            if(everyday_data.get(i).closePrice>=everyday_data.get(i+1).closePrice&&everyday_data.get(i).closePrice>=everyday_data.get(i-1).closePrice)
            {
                for(j=i+1;j<everyday_data.size()-1;j++)
                {
                    if(everyday_data.get(j).closePrice<=everyday_data.get(j+1).closePrice&&everyday_data.get(j).closePrice<=everyday_data.get(j-1).closePrice)
                    {
                        drawdown=everyday_data.get(i).closePrice-everyday_data.get(j).closePrice;
                        if(drawdown>max)
                            max=drawdown;
                    }
                }
            }
        }   
        System.out.println("Max Drowndown: "+max);
    }   
    
    static void maxReturnPotential()
    {
        double sum=0;
        for (stockss p:everyday_data)
            sum+=Math.abs(p.openPrice-p.closePrice);
        System.out.println("Max return potential: "+sum+"\nMax percentage return potential: "+100*sum/everyday_data.get(0).openPrice);
    }
}

class stocksMethods extends stocks
{
    static ArrayList<stocks> stocklist=new ArrayList<>();
    
    static void addStock(stocks s)
    {
        stocklist.add(s);
    }
    
    static void deleteStock(String s)
    {
        stocks a=new stocks();
        for(stocks p:stocklist)
        
            if(p.ticker.equals(s))
            {
                a=p;
                break;
            }
        System.out.println("Stock:"+a.ticker+" is Deleted\n");
        stocklist.remove(a);
    }
    
    static void displayStocks()
    {
        System.out.println("The List of Stocks:");
        for (stocks p:stocklist)
            System.out.println(p.toString());
        System.out.println("\n");
    }
    
    static void displaySectorStocks(String s)
    {
        System.out.println("Scrips listed in sector:"+s);
        
        for (stocks p:stocklist)
            if(p.sector.equals(s))
                System.out.println(p.toString());
        System.out.println("\n");    
    }
}  

class user
{
    int userID;
    String name;
    double funds;
    ArrayList<userHoldings> user_holdings=new ArrayList<>();
    user()
    {
        
    }
    
    user(int userID,String name,double funds)
    {
        this.funds=funds;
        this.name=name;
        this.userID=userID;
    }
    
    void addUserHoldings(userHoldings u)
    {
        user_holdings.add(u);
    }
    
    void delUserHoldings(String s,int qty)
    {
        userHoldings a = new userHoldings();
        for(userHoldings p:user_holdings)
        {
            if(s.equals(p.ticker))
                a=p;
        }
        if(a.qty==qty)
            user_holdings.remove(a);
        else
            a.qty-=qty;
    }
    
    @Override
    public String toString()
    {
        return  "user:"+name+", funds:"+funds+", holding: ["+userHoldings.toString(user_holdings)+"]";
    }
}

class userHoldings 
{
    String ticker;
    int qty;
    
    userHoldings()
    {
        
    }
    
    userHoldings(String ticker,int qty)
    {
        this.qty=qty;
        this.ticker=ticker;
    }
    
    public static String toString(ArrayList<userHoldings> user_holdings)
    {
        String s=new String();
        for(userHoldings p:user_holdings)
            s+=p.ticker+":"+p.qty+",";
        return s;
    }

}

class userMethods extends user
{
    static public ArrayList<user> userlist=new ArrayList<>();
    
    static void deleteUser(String u)
    {
        user a=new user();
        for(user p:userlist)
        
            if(p.name.equals(u))
            {
                a=p;
                break;
            }
        System.out.println("User:"+a.name+" is Deleted\n");
        userlist.remove(a);
    }
    
    static void addUser(user u)
    {
        userlist.add(u);
    }
    
    static void displayUsers()
    {
        System.out.println("The List of Users:");
        for(user p:userlist)
            System.out.println(p.toString());
        System.out.println("\n");
    }
}

class order
{
    user u;
    String type;
    stocks s;
    int qty;
    double rate;
    
    order()
    {
        
    }
    
    order(user u,String type,stocks s,int qty,double rate)
    {
        this.u=u;
        this.type=type;
        this.s=s;
        this.qty=qty;
        this.rate=rate;
    }
    
    @Override
    public String toString()
    {
        return "user:"+u.name+", type:"+type+", scrip:"+s.ticker+", qty:"+qty+", rate:"+rate;
    }
    
    public String tostring()
    {
        return type+" order "+s.ticker+":"+qty+" at "+rate;
    }
}

class orderbookMethods extends order
{
    static ArrayList<order> orderbookList= new ArrayList<>();
    static ArrayList<order> shortSellList = new ArrayList<>();
    
    static void addOrder(order o) throws InvalidInputException
    {
        int flag=0;
        if(o.rate<0.9*o.s.closePrice)
            flag=2;
        else if(o.rate>1.1*o.s.closePrice)
            flag=3;
        else if("buy".equals(o.type))
        {
            if(o.u.funds<(o.qty*o.rate))
                flag=1;
            
        }
        else if("sell".equals(o.type))
        {
            
            for(userHoldings p: o.u.user_holdings)
            {
                if( p.ticker.equals(o.s.ticker)&&p.qty>o.qty)
                {
                    flag=-1;
                    break;
                }
            }
            if(flag!=-1)
            {
                addShortSell(o);
            }
        }
        
        if(flag==0||flag==-1)
        {
            System.out.println("Order placed for "+o.toString());
            orderbookList.add(o);
        }   
        else
        {
            System.out.print("Order rejected for "+o.toString());
            switch (flag) {
                case 1:
                    System.out.println("  <Reason: Insufficient Funds.>");
                    break;
                case 2:
                    throw new InvalidInputException("<Reason: Lower circuit violation.>");
                case 3:
                    throw new InvalidInputException("<Reason: Upper circuit violation.>");
                }
        }    
    }
                    
    static void delOrder(order o)
    {
        for(order p:orderbookList)
            if(p==o)
            {
                orderbookList.remove(p);
                break;        
            }
    }
    
    static void displayOrderbook()
    {
        System.out.println("Orderbook:");
        for(order p:orderbookList)
            System.out.println(p.tostring());
        System.out.println("\n");
    }
       
    static void addShortSell(order o)
    {
        shortSellList.add(o);
    }

    static void delShortSell(order o)
    {
        shortSellList.remove(o);
    }
    
    static void execute()
    {
        System.out.println("Executed transactions:");
        int min;
        String t;
        for(int i=0;i<orderbookList.size();i++)
        {
            for(int j=i+1;j<orderbookList.size();j++)
            {
                if(orderbookList.get(i).s.ticker.equals(orderbookList.get(j).s.ticker)&&!orderbookList.get(i).type.equals(orderbookList.get(j).type))
                {
                    min=Math.min(orderbookList.get(i).qty, orderbookList.get(j).qty);
                    t=orderbookList.get(i).s.ticker;
                    if(orderbookList.get(i).u==orderbookList.get(j).u)
                    {
                        if(orderbookList.get(i).type.equals("sell"))
                            orderbookMethods.delShortSell(orderbookList.get(i));
                        else if(orderbookList.get(j).type.equals("sell"))
                            orderbookMethods.delShortSell(orderbookList.get(j));
                        orderbookMethods.delOrder(orderbookList.get(j));
                        orderbookMethods.delOrder(orderbookList.get(i));
                    }
                    else if(orderbookList.get(i).type.equals("buy")&&orderbookList.get(i).rate>=orderbookList.get(j).rate)
                    {
                        System.out.println(min+" qty of scrip:"+t+" sold for INR "+orderbookList.get(j).rate+"; Buyer: "+orderbookList.get(i).u.name+", Seller: "+orderbookList.get(j).u.name);
                        orderbookList.get(i).u.addUserHoldings(new userHoldings(t,min));
                        orderbookList.get(j).u.delUserHoldings(t, min);
                        orderbookMethods.delOrder(orderbookList.get(j));
                        orderbookMethods.delOrder(orderbookList.get(i));
                    }
                    else if(orderbookList.get(i).type.equals("sell")&&orderbookList.get(i).rate<=orderbookList.get(j).rate)
                    {
                        System.out.println(min+" qty of scrip:"+t+" sold for INR "+orderbookList.get(j).rate+"; Buyer: "+orderbookList.get(j).u.name+", Seller: "+orderbookList.get(i).u.name);
                        orderbookList.get(j).u.addUserHoldings(new userHoldings(t,min));
                        orderbookList.get(i).u.delUserHoldings(t, min);
                        orderbookMethods.delOrder(orderbookList.get(j));
                        orderbookMethods.delOrder(orderbookList.get(i));
                    }
                }
            }
        }
    }
}

public class GradedLab2
{
    public static void main(String[] args) throws FileNotFoundException, ParseException
    {
        int id=1;
        String[] a= new String[15];
        String[] b= new String[15];
        
        File file1=new File("C:\\Users\\Snayga C R\\Desktop\\stocks.txt");
        Scanner input1=new Scanner(file1);
        
        File file2=new File("C:\\Users\\Snayga C R\\Desktop\\INFY_15days_data.csv");
        Scanner input2=new Scanner(file2);
        
        // .txt file
        
        while(input1.hasNext())
        {
            a=input1.nextLine().split("[{}:,]");
            
            if(null != a[0])
            switch (a[0].trim()) 
            {
                case "Add scrip":
                    System.out.println("Added stock"+a[1]);
                    stocksMethods.addStock(new stocks(a[1].trim(),a[3].trim(),Double.parseDouble(a[5]),Double.parseDouble(a[7]),Double.parseDouble(a[9]),Double.parseDouble(a[11])));
                    break;
                case "Add user":
                    System.out.println("Added user"+a[1]);
                    userHoldings uh;
                    user u= new user(id++,a[1].trim(),Double.parseDouble(a[3].replaceAll("[^0-9]", "")));
                    if(!"None".equals(a[4].trim()))
                    {
                        for(int i=5;i<a.length;i+=2)
                        {
                            uh=new userHoldings(a[i].trim(),Integer.parseInt(a[i+1]));
                            u.addUserHoldings(uh);
                        }
                    }
                    userMethods.addUser(u);
                    break;
                case "Place order":
                    user u1=new user();
                    for(user p:userMethods.userlist)
                        if(p.name.equals(a[2].trim()))
                        {
                           u1=p;
                           break;
                        }
                    stocks s1=new stocks();
                    for(stocks p:stocksMethods.stocklist)
                        if(p.ticker.equals(a[6].trim()))
                        {
                            s1=p;
                            break;
                        }
            {
                try {
                    orderbookMethods.addOrder(new order(u1,a[4].trim(),s1,Integer.parseInt(a[8]),Double.parseDouble(a[10])));
                } catch (InvalidInputException ex) 
                {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
                    break;
                case "Delete scrip":
                    stocksMethods.deleteStock(a[1].trim());
                    break;
                case "Delete User":
                    userMethods.deleteUser(a[1].trim());
                    break;
                case "Show Scrips":
                    stocksMethods.displayStocks();
                    break;
                case "Show Users":
                    userMethods.displayUsers();
                    break;
                case "Show Orderbook":
                    orderbookMethods.displayOrderbook();
                    break;
                case "Show sector":
                    stocksMethods.displaySectorStocks(a[1].trim());
                    break;
                case "Execute":
                    orderbookMethods.execute();
                    break;
                case "Exit" :
                    System.out.println("MARKET CLOSED");
                    break;
                default:
                    break;
            }
       
        } 
        
        // .csv file
        
        input2.nextLine();
        SimpleDateFormat date=new SimpleDateFormat("dd-MMM-yyyy");
        while(input2.hasNext())
        {
            b=input2.nextLine().split(",");
            stockss.addStockss(new stockss(date.parse(b[1]),b[0],Double.parseDouble(b[2]),Double.parseDouble(b[3]),Double.parseDouble(b[4]),Double.parseDouble(b[5]),Double.parseDouble(b[6]),Double.parseDouble(b[7])));
        }
        
        stockss.averagePrice();
        stockss.maxDrawdown();
        stockss.maxReturnPotential();
        
    }   
}


