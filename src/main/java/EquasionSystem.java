import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class EquasionSystem {
    public TwoArgumentFunction func_1;
    public TwoArgumentFunction func_2;
    private TwoArgumentFunction phi1;
    private TwoArgumentFunction phi2;
    public Double val1;
    public Double val2;

    public EquasionSystem(TwoArgumentFunction func_1, TwoArgumentFunction func_2, Double val1, Double val2) {
        this.func_1 = func_1;
        this.func_2 = func_2;
        this.val1 = val1;
        this.val2 = val2;
    }
    public EquasionSystem(TwoArgumentFunction func_1, TwoArgumentFunction func_2, Double val1, Double val2, TwoArgumentFunction phi1, TwoArgumentFunction phi2) {
        this.func_1 = func_1;
        this.func_2 = func_2;
        this.val1 = val1;
        this.val2 = val2;
        this.phi1 = phi1;
        this.phi2 = phi2;
    }

    public TwoArgumentFunction getPhi1() {
        return phi1;
    }

    public void setPhi1(TwoArgumentFunction phi1) {
        this.phi1 = phi1;
    }

    public TwoArgumentFunction getPhi2() {
        return phi2;
    }

    public void setPhi2(TwoArgumentFunction phi2) {
        this.phi2 = phi2;
    }

    public void draw(double x1_0, double x2_0, double x1_1, double x2_1) throws PythonExecutionException, IOException {
        List<Double> x = NumpyUtils.linspace(x1_0 - 1, x1_1 + 1, 300);
        List<Double> y = NumpyUtils.linspace(x2_0 - 1, x2_1 + 1, 300);
        List<Double> x_axis = NumpyUtils.linspace(-100, 100, 1000);
        List<Double> y_0 = x_axis.stream().map(i-> 0.0).toList();
        List<Double> y_axis = NumpyUtils.linspace(-100, 100, 1000);
        List<Double> x_0 = y_axis.stream().map(i-> 0.0).toList();
        Plot plt = Plot.create();
        plt.xlabel("x");
        plt.ylabel("y");
        plt.xlim(x1_0 - 1, x1_1 + 1);
        plt.ylim(x2_0 - 1, x2_1 + 1);
        plt.plot().add(x_0, y_axis).color("black");
        plt.plot().add(x_axis, y_0).color("black");
        boolean fl1 = true;
        boolean fl2 = true;
        for (Double xi: x) {
            for (Double yi: y) {
                if(Math.abs(func_1.compute(xi,yi) - val1) < 0.005 && fl1){
                    plt.plot().add(Collections.singletonList(xi), Collections.singletonList(yi), ".").color("red").label(func_1.getString() + " = " + val1);
                    fl1 = false;
                }else if(Math.abs(func_1.compute(xi,yi) - val1) < 0.005){
                    plt.plot().add(Collections.singletonList(xi), Collections.singletonList(yi), ".").color("red");
                }
                if(Math.abs(func_2.compute(xi,yi) - val2) < 0.005 && fl2){
                    plt.plot().add(Collections.singletonList(xi), Collections.singletonList(yi), ".").color("blue").label(func_2.getString() + " = " + val2);
                    fl2 = false;
                }else if(Math.abs(func_2.compute(xi,yi) - val2) < 0.005){
                    plt.plot().add(Collections.singletonList(xi), Collections.singletonList(yi), ".").color("blue");
                }
            }
        }
        plt.plot().add(Collections.singletonList(x1_0), Collections.singletonList(x2_0), "o").color("yellow").label("a");
        plt.plot().add(Collections.singletonList(x1_1), Collections.singletonList(x2_1), "o").color("green").label("b");
        plt.title("Система уравнений");
        plt.legend().loc("upper right");
        plt.show();
    }

    public String getString(){
        return "| " + func_1.getString() + " = " + val1 +"\n| " + func_2.getString() + " = " + val2;
    }
    /*private String getStringNoEOL(){
        return func_1.getString() + " = " + val1 +" " + func_2.getString() + " = " + val2;
    }*/
}
