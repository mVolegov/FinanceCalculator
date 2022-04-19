package Models;

import java.io.Serial;
import java.io.Serializable;

public class Asset implements Serializable {
    private String title;
    private double sum;

    @Serial
    private static final long serialVersionUID = -6554572556197845668L;

    public Asset(String title, double sum) {
        this.title = title;
        this.sum = sum;
    }

    public String getTitle() {
        return title;
    }

    public double getSum() {
        return sum;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
