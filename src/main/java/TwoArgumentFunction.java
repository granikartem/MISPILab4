public interface TwoArgumentFunction {
    Double compute(double arg_1, double arg_2);
    String getString();
    default Double partialDerivative(double arg_1, double arg_2, int arg_num){
        if(arg_num == 1) {
            return (compute(arg_1 + 0.0000000001, arg_2) - compute(arg_1, arg_2)) / 0.0000000001;
        }else{
            return (compute(arg_1, arg_2 + 0.0000000001) - compute(arg_1, arg_2)) / 0.0000000001;
        }
    }
}
