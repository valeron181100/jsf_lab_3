import javax.faces.bean.ManagedBean;

@ManagedBean(name = "navController")
public class NavigationController {
    private boolean isOk = true;

    public String goForward(){
        return isOk ? "success" : "failure";
    }
}
