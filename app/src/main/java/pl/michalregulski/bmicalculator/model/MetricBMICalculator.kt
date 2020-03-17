package pl.michalregulski.bmicalculator.model

class MetricBMICalculator : BMICalculator(MIN_WEIGHT, MAX_WEIGHT, MIN_HEIGHT, MAX_HEIGHT) {

    override fun calculateBMIInternal(weight: Double, height: Double) = weight * 10000 / (height * height)

    companion object {
        private const val MIN_WEIGHT = 10.0
        private const val MAX_WEIGHT = 200.0
        private const val MIN_HEIGHT = 50.0
        private const val MAX_HEIGHT = 250.0
    }

}
