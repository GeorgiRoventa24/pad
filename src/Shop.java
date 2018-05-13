import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

interface Shop extends Remote {
    public List<Article> getArticles() throws RemoteException;
}
