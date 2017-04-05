package swim.regulski.BMICalculator.logic;

public class MetricBMICalc implements IBMICalc {

    private static final double MIN_WEIGHT = 10;
    private static final double MAX_WEIGHT = 200;
    private static final double MIN_HEIGHT = 50;
    private static final double MAX_HEIGHT = 250;

    @Override
    public double calcBMI(double weight, double height) {
        if (isValidWeight(weight) && isValidHeight(height)) {
            return weight * 10000 / (height * height);
        } else throw new IllegalArgumentException("mass: " + weight + ", height: " + height);
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
