package swim.regulski.BMICalculator.logic;

public interface IBMICalc {

    double calcBMI(double weight, double height);

    boolean isValidWeight(double weight);

    boolean isValidHeight(double height);
}
