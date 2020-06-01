import com.sun.faces.util.Json;
import database.dao.ResultPointDAO;
import database.dao.models.ResultPoint;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

public class AreaChecker {

    private String x = "0";
    private String y;
    private String r = "2.1";

    private String historyJson;

    private List<ResultPoint> points = new ArrayList<>();
    private List<ResultPoint> userPoints = new ArrayList<>();

    @PostConstruct
    public void init() {
        ResultPointDAO pointDAO = new ResultPointDAO();
        points = pointDAO.findAllPoints();
    }

    public void addPoint(){
        FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
        String sessionId = session.getId();
        ResultPoint point = new ResultPoint();
        double _r = Double.parseDouble(r);
        double _x = Double.parseDouble(x);
        double _y = Double.parseDouble(y);
        point.setR(_r);
        point.setX(_x);
        point.setY(_y);
        point.setEntered(isHit(_x, _y, _r));
        point.setUserId(sessionId);

        ResultPointDAO pointDAO = new ResultPointDAO();
        pointDAO.insertPoint(point);
        points = pointDAO.findAllPoints();
    }

    public List<ResultPoint> getCurrentSessionUserPoints(){
        FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
        String sessionId = session.getId();

        return points.stream().filter(p -> p.getUserId().equals(sessionId)).collect(Collectors.toList());
    }

    public List<ResultPoint> getUserPoints() {
        userPoints.clear();
        userPoints.addAll(getCurrentSessionUserPoints());
        return userPoints;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public List<ResultPoint> getPoints() {
        return points;
    }

    public String getHistoryJson() {
        JSONArray array = new JSONArray();
        getCurrentSessionUserPoints().forEach(p -> array.put(p.getJSON()));
        String k = array.toString();
        historyJson = k;
        return historyJson;
    }

    private boolean isHit(Double x, Double y, Double r){
        if(y <= r && x <= r/2 && x >= 0 && y>=0)
            return true;
        if(y*y + x*x <= r*r/4 && y <= 0 && x >= 0)
            return true;
        if(y >= -0.5*x - r/2 && y <= 0 && x <= 0)
            return true;

        return false;
    }

    /*private boolean isHit(Double x, Double y, Double r) {
        double ax = r / 7.0;
        double ay = r / 6.0;

        boolean ellipse = (Math.pow(x, 2)) / (49 * Math.pow(ax, 2)) + (Math.pow(y, 2)) / (9 * Math.pow(ay, 2)) - 1.0 <= 0;
        boolean ellipseBottomX = (Math.abs(x / ax)) >= 4;
        boolean ellipseBottomY = (y / ay >= -3 * Math.sqrt(33) / 7.0) && (y / ay <= 0);
        boolean ellipseTopX = (Math.abs(x / ax)) >= 3;
        boolean ellipseTopY = y >= 0;
        if (ellipse && ellipseBottomX && ellipseBottomY) {
            return true;
        }
        if (ellipse && ellipseTopX && ellipseTopY) {
            return true;
        }

        boolean tail = (((3 * Math.sqrt(33) - 7) * Math.pow(x, 2)) / (-112.0 * Math.pow(ax, 2))
                + Math.abs(x / ax) / 2
                + Math.sqrt(1 - Math.pow(Math.abs((Math.abs(x / ax)) - 2) - 1, 2)) - y / ay - 3) <= 0;
        boolean tailX = (x / ax <= 4) && (x / ax >= -4);
        boolean tailY = (y / ay >= -3) && (y / ay <= 0);
        if (tail && tailX && tailY) {
            return true;
        }

        boolean ear1 = -8 * Math.abs(x / ax) - y / ay + 9 >= 0;
        boolean earsY = y >= 0;
        boolean ear1X = Math.abs(x / ax) <= 1 && Math.abs(x / ax) >= 0.75;
        if (ear1 && earsY && ear1X) {
            return true;
        }

        boolean ear2 = 3 * Math.abs(x / ax) - y / ay + 0.75 >= 0;
        boolean ears2X = Math.abs(x / ax) <= 0.75 && Math.abs(x / ax) >= 0.5;
        if (ear2 && earsY && ears2X) {
            return true;
        }

        boolean head = 9.0 / 4.0 - y / ay >= 0;
        boolean headX = Math.abs(x / ax) <= 0.5;
        boolean headY = y >= 0;
        if (head && headX && headY) {
            return true;
        }

        boolean wings = -Math.abs(x / ax) / 2 - (3.0 / 7.0) * Math.sqrt(10) * Math.sqrt(4 - Math.pow(Math.abs(x / ax) - 1, 2)) - y / ay + (6 * Math.sqrt(10)) / 7.0 + 1.5 >= 0;
        boolean wingsX = Math.abs(x / ax) <= 3 && Math.abs(x / ax) >= 1;
        boolean wingsY = y >= 0;
        if (wings && wingsX && wingsY) {
            return true;
        }

        return false;
    }*/
}
