package pl.michalregulski.bmicalculator.logic

class MetricBMICalculator : BMICalculator() {

    override fun calculateBMIInternal(weight: Double, height: Double) = weight * 10000 / (height * height)

    override fun isValidWeight(weight: Double) = weight in MIN_WEIGHT..MAX_WEIGHT

    override fun isValidHeight(height: Double) = height in MIN_HEIGHT..MAX_HEIGHT

    companion object {
        private const val MIN_WEIGHT = 10.0
        private const val MAX_WEIGHT = 200.0
        private const val MIN_HEIGHT = 50.0
        private const val MAX_HEIGHT = 250.0
    }

}
