public interface Function {
    Double compute(Double arg);
    String getString();
    default Double derivative(Double arg){
        return (compute(arg + 0.000000001) - compute(arg)) / 0.000000001;
    }
}
