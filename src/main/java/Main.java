import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import com.github.sh0nk.matplotlib4j.builder.ArgsBuilderImpl;
import com.github.sh0nk.matplotlib4j.builder.Builder;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.varia.NullAppender;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static ArrayList<Function> initializeFunctions(){
        ArrayList<Function> functions = new ArrayList<Function>();
        functions.add(new Function() {
            @Override
            public Double compute(Double arg) {
                return -1.38 * Math.pow(arg,3) - 5.42 * Math.pow(arg,2) + 2.57 * arg + 10.95;
            }

            @Override
            public String getString() {
                return "-1.38 * x^3 -5.42 * x^2 + 2.57 * x + 10.95";
            }
        });
        functions.add(new Function() {
            @Override
            public Double compute(Double arg) {
                return -3.64 * Math.pow(arg,3) + 2.12 * Math.pow(arg,2) + 10.73 * arg + 1.49;
            }

            @Override
            public String getString() {
                return "-3.64 * x^3 + 2.12 * x^2 + 10.73 * x + 1.49";
            }
        });
        functions.add(new Function() {
            @Override
            public Double compute(Double arg) {
                return 3 * Math.pow(arg, 3) + 1.7 * Math.pow(arg, 2) -15.42 * arg + 6.89;
            }

            @Override
            public String getString() {
                return "3 * (x ^ 3) + 1.7 * (x ^ 2) - 15.42 * x + 6.89";
            }
        });
        functions.add(new Function() {

            @Override
            public Double compute(Double arg) {
                return Math.exp(arg) * Math.sin(arg);
            }

            @Override
            public String getString() {
                return "e^x * sin(x)";
            }
        });
        return functions;
    }
    private static ArrayList<EquasionSystem> initializeEquasionSystems(){
        ArrayList<EquasionSystem> equasionSystems = new ArrayList<>();
        equasionSystems.add(new EquasionSystem(
                new TwoArgumentFunction() {
                    @Override
                    public Double compute(double arg_1, double arg_2) {
                        return 0.1 * Math.pow(arg_1, 2) + arg_1 + 0.2 * Math.pow(arg_2,2);
                    }

                    @Override
                    public String getString() {
                        return "0.1 * x ^ 2 + x + 0.2 * y ^ 2";
                    }
                    },
                new TwoArgumentFunction() {

                    @Override
                    public Double compute(double arg_1, double arg_2) {
                        return 0.2 * Math.pow(arg_1, 2) + arg_2 - 0.1 * arg_1 * arg_2;
                    }

                    @Override
                    public String getString() {
                        return "0.2 * x^2 + y - 0.1 * x * y";
                    }
                },
                0.3,
                0.7,
                new TwoArgumentFunction() {
                    @Override
                    public Double compute(double arg_1, double arg_2) {
                        return 0.3 - 0.1 * Math.pow(arg_1, 2) - 0.2 * Math.pow(arg_2, 2);
                    }

                    @Override
                    public String getString() {
                        return "";
                    }
                },
                new TwoArgumentFunction() {
                    @Override
                    public Double compute(double arg_1, double arg_2) {
                        return 0.7 - 0.2 * Math.pow(arg_1, 2) + 0.1 * arg_1 * arg_2;
                    }

                    @Override
                    public String getString() {
                        return "";
                    }
                }));
        equasionSystems.add(new EquasionSystem(
                new TwoArgumentFunction() {
                    @Override
                    public Double compute(double arg_1, double arg_2) {
                        return Math.pow(arg_1, 2) + Math.pow(arg_2, 2);
                    }

                    @Override
                    public String getString() {
                        return "x^2 + y^2";
                    }
                },
                new TwoArgumentFunction() {
                    @Override
                    public Double compute(double arg_1, double arg_2) {
                        return -3.0 * Math.pow(arg_1, 2) + arg_2;
                    }

                    @Override
                    public String getString() {
                        return "-3 * x^2 + y";
                    }
                },
                4.0,
                0.0,
                new TwoArgumentFunction() {
                    @Override
                    public Double compute(double arg_1, double arg_2) {
                        return Math.sqrt(4.0 - Math.pow(arg_2, 2));
                    }

                    @Override
                    public String getString() {
                        return "sqrt(4 - y^2)";
                    }
                },
                new TwoArgumentFunction() {
                    @Override
                    public Double compute(double arg_1, double arg_2) {
                        return 3.0 * Math.pow(arg_1,2);
                    }

                    @Override
                    public String getString() {
                        return "3 * x^2";
                    }
                }
        ));
        return equasionSystems;
    }
    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    private static boolean isInteger(String strNum){
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public static void main(String args[]) throws PythonExecutionException, IOException {
        ArrayList<Function> functions = initializeFunctions();
        ArrayList<EquasionSystem> equasionSystems = initializeEquasionSystems();
        Scanner scanner = new Scanner(System.in);
        String cont = "Y";
        boolean corrdata = true;
        while(cont.equals("Y")){
            System.out.println("Что делать?\n1) Решение уравнений;\n2) Решение систем уравнений.");
            cont = scanner.nextLine();
            while(!Objects.equals(cont, "1") && !Objects.equals(cont, "2")){
                System.out.println("Введите корректное значение(1/2)!");
                cont = scanner.nextLine();
            }
            if(cont.equals("1")){
                boolean method;
                Double a = null, b = null, e = null, xi = null;
                System.out.println("Выберите уравнение из списка:");
                for (int i = 0; i < functions.size(); i++) {
                    System.out.println((i+1) + ") " + functions.get(i).getString() + ";");
                }
                cont = scanner.nextLine();
                while(!isInteger(cont) || !(Integer.parseInt(cont) >= 1 && Integer.parseInt(cont) <= functions.size())){
                    System.out.println("Введите корректное значение(Число от 1 до " + (functions.size()) + ")!");
                    cont = scanner.nextLine();
                }
                Function function = functions.get(Integer.parseInt(cont)-1);
                System.out.println("Выберите способ решения:\n1) Метод половинного деления;\n2) Метод простой итерации.");
                cont = scanner.nextLine();
                while(!Objects.equals(cont, "1") && !Objects.equals(cont, "2")){
                    System.out.println("Введите корректное значение(1/2)!");
                    cont = scanner.nextLine();
                }
                method = (Integer.parseInt(cont) == 2);
                System.out.println("Выберите способ ввода параментров:\n1) С клавиатуры;\n2) Из файла.");
                cont = scanner.nextLine();
                while(!Objects.equals(cont, "1") && !Objects.equals(cont, "2")){
                    System.out.println("Введите корректное значение(1/2)!");
                    cont = scanner.nextLine();
                }
                if(cont.equals("1")){
                    do {
                        System.out.println("Введите левую границу интервала(a):");
                        cont = scanner.nextLine();
                        while (!isNumeric(cont)) {
                            System.out.println("Введите корректное значение!");
                            cont = scanner.nextLine();
                        }
                        a = Double.parseDouble(cont);
                        System.out.println("Введите правую границу интервала(b):");
                        cont = scanner.nextLine();
                        while (!isNumeric(cont)) {
                            System.out.println("Введите корректное значение!");
                            cont = scanner.nextLine();
                        }
                        b = Double.parseDouble(cont);
                        if(a >= b){
                            System.out.println("Либо границы интервала перепутаны, либо интервал - точка!");
                        }
                    }while(a>=b);
                    if(method){
                        do{
                            System.out.println("Введите начальное приближение(xi_0):");
                            cont = scanner.nextLine();
                            while (!isNumeric(cont)) {
                                System.out.println("Введите корректное значение!");
                                cont = scanner.nextLine();
                            }
                            xi = Double.parseDouble(cont);
                            if(!(a<=xi && b >= xi)){
                                System.out.println("Приближение не принадлежит интервалу!");
                            }
                        }while(!(a<=xi && b >= xi));
                    }
                    do {
                        System.out.println("Введите погрешность(e):");
                        cont = scanner.nextLine();
                        while (!isNumeric(cont)) {
                            System.out.println("Введите корректное значение!");
                            cont = scanner.nextLine();
                        }
                        e = Double.parseDouble(cont);
                        if(e <= 0){
                            System.out.println("Погрешность должна быть положительным числом!");
                        }
                    }while(e <= 0);
                }else {
                    System.out.println("Введите название файла:");
                    cont = scanner.nextLine();
                    BufferedReader br = null;
                    String line;
                    try {
                        br = new BufferedReader(new FileReader("src/main/resources/" + cont));
                        line = br.readLine();
                        a = Double.parseDouble(line);
                        line = br.readLine();
                        b = Double.parseDouble(line);
                        if(a >= b){
                            throw new NumberFormatException();
                        }
                        if(method){
                            line = br.readLine();
                            xi = Double.parseDouble(line);
                            if(!(a<=xi && b >= xi)){
                                throw new NumberFormatException();
                            }
                        }
                        line = br.readLine();
                        e = Double.parseDouble(line);
                        if(e < 0){
                            throw new NumberFormatException();
                        }
                    } catch (FileNotFoundException exception) {
                        System.out.println("Такого файла нет!");
                    } catch(NumberFormatException exception){
                        System.out.println("В файле содержатся некорректные данные!");
                        corrdata = false;
                    }
                    finally{
                        if(br !=null) {
                            br.close();
                        }
                    }
                }
                if(corrdata) {
                    System.out.println("Куда выводить ответ?\n1) В консоль;\n2) В файл.");
                    cont = scanner.nextLine();
                    while (!Objects.equals(cont, "1") && !Objects.equals(cont, "2")) {
                        System.out.println("Введите корректное значение(1/2)!");
                        cont = scanner.nextLine();
                    }
                    String solution;
                    if (method) {
                        FixedPointIterationMethod fpim = new FixedPointIterationMethod(function, a, b, e, xi);
                        solution = fpim.getSolution();
                        fpim.draw();
                    } else {
                        BisectionMethod bm = new BisectionMethod(function, a, b, e);
                        solution = bm.getSolution();
                        bm.draw();
                    }
                    if (cont.equals("1")) {
                        System.out.println(solution);
                    } else {
                        System.out.println("Введите название файла:");
                        cont = scanner.nextLine();
                        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/" + cont));
                        writer.write(solution);
                        writer.close();
                    }
                }
            }else {
                Double x1_0 = null, x2_0 = null, x1_1 = null, x2_1 = null, e = null;
                System.out.println("Выберите систему уравнений:");
                for (int i = 0; i < equasionSystems.size(); i++) {
                    System.out.println((i + 1) + ")\n" + equasionSystems.get(i).getString());
                }
                cont = scanner.nextLine();
                while (!isInteger(cont) || !(Integer.parseInt(cont) >= 1 && Integer.parseInt(cont) <= equasionSystems.size())) {
                    System.out.println("Введите корректное значение(Число от 1 до " + (functions.size()) + ")!");
                    cont = scanner.nextLine();
                }
                EquasionSystem es = equasionSystems.get(Integer.parseInt(cont) - 1);
                System.out.println("Выберите способ ввода параментров:\n1) С клавиатуры;\n2) Из файла.");
                cont = scanner.nextLine();
                while (!Objects.equals(cont, "1") && !Objects.equals(cont, "2")) {
                    System.out.println("Введите корректное значение(1/2)!");
                    cont = scanner.nextLine();
                }
                if (cont.equals("1")) {
                    System.out.println("Введите первую координату точки A:");
                    cont = scanner.nextLine();
                    while (!isNumeric(cont)) {
                        System.out.println("Введите корректное значение!");
                        cont = scanner.nextLine();
                    }
                    x1_0 = Double.parseDouble(cont);
                    System.out.println("Введите вторую координату точки A:");
                    cont = scanner.nextLine();
                    while (!isNumeric(cont)) {
                        System.out.println("Введите корректное значение!");
                        cont = scanner.nextLine();
                    }
                    x2_0 = Double.parseDouble(cont);
                    System.out.println("Введите первую координату точки B:");
                    cont = scanner.nextLine();
                    while (!isNumeric(cont)) {
                        System.out.println("Введите корректное значение!");
                        cont = scanner.nextLine();
                    }
                    x1_1 = Double.parseDouble(cont);
                    System.out.println("Введите вторую координату точки B:");
                    cont = scanner.nextLine();
                    while (!isNumeric(cont)) {
                        System.out.println("Введите корректное значение!");
                        cont = scanner.nextLine();
                    }
                    x2_1 = Double.parseDouble(cont);
                    do {
                        System.out.println("Введите погрешность(e):");
                        cont = scanner.nextLine();
                        while (!isNumeric(cont)) {
                            System.out.println("Введите корректное значение!");
                            cont = scanner.nextLine();
                        }
                        e = Double.parseDouble(cont);
                        if (e <= 0) {
                            System.out.println("Погрешность должна быть положительным числом!");
                        }
                    } while (e <= 0);
                } else {
                    System.out.println("Введите название файла:");
                    cont = scanner.nextLine();
                    BufferedReader br = null;
                    String line;
                    try {
                        br = new BufferedReader(new FileReader("src/main/resources/" + cont));
                        line = br.readLine();
                        x1_0 = Double.parseDouble(line);
                        line = br.readLine();
                        x2_0 = Double.parseDouble(line);
                        line = br.readLine();
                        x1_1 = Double.parseDouble(line);
                        line = br.readLine();
                        x2_1 = Double.parseDouble(line);
                        line = br.readLine();
                        e = Double.parseDouble(line);
                        if (e < 0) {
                            throw new NumberFormatException();
                        }
                    } catch (FileNotFoundException exception) {
                        System.out.println("Такого файла нет!");
                    } catch (NumberFormatException exception) {
                        System.out.println("В файле содержатся некорректные данные!");
                        corrdata = false;
                    } finally {
                        if (br != null) {
                            br.close();
                        }
                    }
                }
                if (corrdata) {
                    System.out.println("Куда выводить ответ?\n1) В консоль;\n2) В файл.");
                    cont = scanner.nextLine();
                    while (!Objects.equals(cont, "1") && !Objects.equals(cont, "2")) {
                        System.out.println("Введите корректное значение(1/2)!");
                        cont = scanner.nextLine();
                    }
                    String solution;
                    FixedPointIterationMethod_system fpims = new FixedPointIterationMethod_system(es, x1_0, x2_0, x1_1, x2_1, e);
                    solution = fpims.getSolution();
                    fpims.draw();
                    if (cont.equals("1")) {
                        System.out.println(solution);
                    } else {
                        System.out.println("Введите название файла:");
                        cont = scanner.nextLine();
                        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/" + cont));
                        writer.write(solution);
                        writer.close();
                    }
                }
            }
            corrdata = true;
            System.out.println("Продолжить(Y/N)?");
            cont = scanner.nextLine();
            while(!Objects.equals(cont, "Y") && !Objects.equals(cont, "N")){
                System.out.println("Введите корректное значение(Y/N)!");
                cont = scanner.nextLine();
            }
        }
    }
}
