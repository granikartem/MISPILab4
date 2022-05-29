import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


public class BisectionMethod{
    private Function function;
    private Double a;
    private Double b;
    private Double e;
    private Double f_a;
    private Double f_b;
    private int iteration_counter;
    private Double a_0;
    private Double b_0;

    public BisectionMethod(Function function, Double a, Double b, Double e){
        this.function = function;
        this.a = a;
        this.a_0 = a;
        this.b = b;
        this.b_0 = b;
        this.e = e;
        this.f_a = function.compute(a);
        this.f_b = function.compute(b);
        this.iteration_counter = 0;
    }
    private boolean correctInterval(){
        return f_a * f_b < 0;
    }
    private Double iterate(){
        iteration_counter++;
        Double x = (a + b)/2;
        Double f_x = function.compute(x);
        if(f_x * f_a <= 0){
            this.b = x;
            this.f_b = f_x;
        }else{
            this.a = x;
            this.f_a = f_x;
        }
        return f_x;
    }
    private void solve(){
        while(Math.abs(function.compute((a+b)/2)) > e){
            iterate();
        }
    }
    public void draw() throws PythonExecutionException, IOException {
        List<Double> x = NumpyUtils.linspace(a_0 - 10, b_0 + 10, 1000);
        List<Double> y = x.stream().map(i -> function.compute(i)).toList();
        List<Double> y_0 = x.stream().map(i-> 0.0).toList();
        List<Double> y_axis = NumpyUtils.linspace(-100, 100, 1000);
        List<Double> x_0 = y_axis.stream().map(i-> 0.0).toList();
        Plot plt = Plot.create();
        plt.xlabel("x");
        plt.ylabel("y");
        plt.xlim(a_0 -10, b_0 + 10);
        plt.ylim(-100, 100);
        plt.plot().add(x, y).label(function.getString());
        plt.plot().add(x_0, y_axis).color("black");
        plt.plot().add(x, y_0).color("black");
        plt.plot().add(Collections.singletonList(a_0), Collections.singletonList(0.0), "o").color("red").label("a");
        plt.plot().add(Collections.singletonList(b_0), Collections.singletonList(0.0), "o").color("green").label("b");
        plt.legend().loc("upper right");
        plt.title(function.getString());
        plt.show();
    }
    public String getSolution() {
        if(this.correctInterval()){
            this.solve();
            return ("Метод половинного деления.\nx = " + ((a + b) / 2) +";\nf(x) = " + function.compute((a + b) / 2) + ";\nТочность: e =" + e + ";\nКоличество итераций n = " + iteration_counter + ".");
        }else{
            return "На данном интервале нет корней!";
        }
    }
}
