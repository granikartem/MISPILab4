import com.github.sh0nk.matplotlib4j.PythonExecutionException;

import java.io.IOException;

public class FixedPointIterationMethod_system {
    private EquasionSystem es;
    private TwoArgumentFunction func_1;
    private TwoArgumentFunction func_2;
    private Double x1_0;
    private Double x2_0;
    private Double x1_1;
    private Double x2_1;
    private Double e;
    private Double x1_i;
    private Double x2_i;
    private int iteration_counter;
    private Double x1_diff;
    private Double x2_diff;
    public FixedPointIterationMethod_system(EquasionSystem es, Double x1_0, Double x2_0, Double x1_1, Double x2_1, Double e){
        this.es = es;
        this.func_1 = es.getPhi1();
        this.func_2 = es.getPhi2();
        this.x1_0 = x1_0;
        this.x2_0 = x2_0;
        this.x1_1 = x1_1;
        this.x2_1 = x2_1;
        this.e = e;
        this.iteration_counter = 0;
    }
    private boolean convergence(){
        Double q = Math.abs(func_1.partialDerivative(x1_0,x2_0, 1)) + Math.abs(func_1.partialDerivative(x1_0,x2_0, 2));
        q = Math.max(q,Math.abs(func_2.partialDerivative(x1_0,x2_0, 1)) + Math.abs(func_2.partialDerivative(x1_0,x2_0, 2)));
        q = Math.max(q, Math.abs(func_1.partialDerivative(x1_1,x2_1, 1)) + Math.abs(func_1.partialDerivative(x1_1,x2_1, 2)));
        q = Math.max(q,Math.abs(func_2.partialDerivative(x1_1,x2_1, 1)) + Math.abs(func_2.partialDerivative(x1_1,x2_1, 2)));
        return q < 1;
    }
    private void iterate(){
        iteration_counter++;
        double tmp = func_1.compute(x1_i, x2_i);
        x2_i = func_2.compute(x1_i, x2_i);
        x1_i = tmp;
    }
    private void solve(){
        double x1_prev = x1_i;
        double x2_prev = x2_i;
        iterate();
        while(Math.max(Math.abs(x1_i - x1_prev), Math.abs(x2_i -x2_prev)) > e){
            x1_diff = Math.abs(x1_i - x1_prev);
            x2_diff = Math.abs(x2_i - x2_prev);
            x1_prev = x1_i;
            x2_prev = x2_i;
            iterate();
        }
    }
    public void draw() throws PythonExecutionException, IOException {
        es.draw(x1_0,x2_0,x1_1,x2_1);
    }
    public String getSolution(){
            if(this.convergence()) {
                x1_i = x1_0;
                x2_i = x2_0;
                this.solve();
                return ("Метод простой итерации для системы уравнений.\nx1 = " + x1_i +  ";\nx2 = " + x2_i +  ";\nf1(x) = " + (es.func_1.compute(x1_i, x2_i) - es.val1)+";\nf2(x) = "+ (es.func_2.compute(x1_i,x2_i) - es.val2) + ";\nТочность: e =" + e + ";\nКоличество итераций n = " + iteration_counter + ";\nПогрешности: dx1 = " + x1_diff + ", dx2 = " + x2_diff + ".");
            }else{
                return "Условие сходимости не выполняется!";
            }
        }
}
