import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class FixedPointIterationMethod{
    private Function function;
    private Double a;
    private Double b;
    private Double e;
    private Double x_i;
    private Function phi;
    private int iteration_counter;

    public FixedPointIterationMethod(Function function, Double a, Double b, Double e, Double x_i){
        this.function = function;
        this.a = a;
        this.b = b;
        this.e = e;
        this.x_i = x_i;
        Double k = ( -1 ) / Math.max(function.derivative(a), function.derivative(b));
        this.phi = new Function() {
            @Override
            public Double compute(Double arg) {
                return arg + k * function.compute(arg);
            }

            @Override
            public String getString() {
                return "x + k * (" + function.getString() + ")";
            }
        };
        this.iteration_counter = 0;
    }
    private boolean correctInterval(){
        return function.compute(a) * function.compute(b) < 0;
    }
    private boolean convergence(){
        Double q = Math.max(Math.abs(phi.derivative(a)), Math.max(Math.abs(phi.derivative(b)), Math.abs(phi.derivative(x_i))));
        System.out.println(Math.abs(phi.derivative(a)));
        System.out.println(Math.abs(phi.derivative(b)));
        if (q >= 1){
            return false;
        }else{
            if (q > 0.5) {
                e = (1 - q)/q * e;
            }
            return true;
        }
    }
    private Double iterate(){
        this.x_i = phi.compute(x_i);
        iteration_counter++;
        return x_i;
    }
    private void solve(){
        Double x = x_i;
        while(Math.abs(x - iterate()) > e){
            x = x_i;
        }
    }
    public void draw() throws PythonExecutionException, IOException {
        List<Double> x = NumpyUtils.linspace(a - 10, b + 10, 1000);
        List<Double> y = x.stream().map(i -> function.compute(i)).toList();
        List<Double> y_0 = x.stream().map(i-> 0.0).toList();
        List<Double> y_axis = NumpyUtils.linspace(-100, 100, 1000);
        List<Double> x_0 = y_axis.stream().map(i-> 0.0).toList();
        Plot plt = Plot.create();
        plt.xlabel("x");
        plt.ylabel("y");
        plt.xlim(a -10, b + 10);
        plt.ylim(-100, 100);
        plt.plot().add(x, y).label(function.getString());
        plt.plot().add(x_0, y_axis).color("black");
        plt.plot().add(x, y_0).color("black");
        plt.plot().add(Collections.singletonList(a), Collections.singletonList(0.0), "o").color("red").label("a");
        plt.plot().add(Collections.singletonList(b), Collections.singletonList(0.0), "o").color("green").label("b");
        plt.legend().loc("upper right");
        plt.title(function.getString());
        plt.show();
    }
    public String getSolution(){
        if(this.correctInterval()){
            if(this.convergence()) {
                this.solve();
                return ("Метод простой итерации.\nx = " + x_i + ";\nf(x) = " + function.compute(x_i) + ";\nТочность: e =" + e + ";\nКоличество итераций n = " + iteration_counter + ".");
            }else{
                return "Условие сходимости не выполняется!";
            }
        }else{
            return "На данном интервале нет корней!";
        }
    }
}
