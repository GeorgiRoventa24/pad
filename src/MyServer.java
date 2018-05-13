import java.rmi.Naming;
import java.rmi.Remote;

public class MyServer {
    public static void main(String args[]) throws Exception{
        Remote r = new ShopImpl();
        Naming.rebind("rmi://localhost:6666", r);
    }
}
