package pl.michalregulski.bmicalculator.model

abstract class BMICalculator(
    private val minWeight: Double,
    private val maxWeight: Double,
    private val minHeight: Double,
    private val maxHeight: Double
) {

    fun calculateBMI(weight: Double, height: Double) = when {
        isValidWeight(weight) && isValidHeight(height) -> calculateBMIInternal(weight, height)
        else -> throw IllegalArgumentException("weight: $weight, height: $height")
    }

    fun isValidWeight(weight: Double) = weight in minWeight..maxWeight

    fun isValidHeight(height: Double) = height in minHeight..maxHeight

    abstract fun calculateBMIInternal(weight: Double, height: Double): Double

    companion object {
        private val metricBMICalculator = MetricBMICalculator()
        private val imperialBMICalculator = ImperialBMICalculator()

        fun get(bmiCalculatorType: BMICalculatorType) = when (bmiCalculatorType) {
            BMICalculatorType.METRIC -> metricBMICalculator
            BMICalculatorType.IMPERIAL -> imperialBMICalculator
        }
    }

}
