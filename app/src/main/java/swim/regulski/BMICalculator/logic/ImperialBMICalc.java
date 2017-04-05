package swim.regulski.BMICalculator.logic;

public class ImperialBMICalc implements IBMICalc {

    private static final double MIN_WEIGHT = 20;
    private static final double MAX_WEIGHT = 440;
    private static final double MIN_HEIGHT = 20;
    private static final double MAX_HEIGHT = 100;

    @Override
    public double calcBMI(double weight, double height) {
        if (isValidWeight(weight) && isValidHeight(height)) {
            return weight * 703 / (height * height);
        } else throw new IllegalArgumentException("weight: " + weight + ", height: " + height);
    }

    @Override
    public boolean isValidWeight(double weight) {
        return MIN_WEIGHT <= weight && weight <= MAX_WEIGHT;
    }

    @Override
    public boolean isValidHeight(double height) {
        return MIN_HEIGHT <= height && height <= MAX_HEIGHT;
    }
}
