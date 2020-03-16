package pl.michalregulski.bmicalculator.domain

class ImperialBMICalculator : BMICalculator(MIN_WEIGHT, MAX_WEIGHT, MIN_HEIGHT, MAX_HEIGHT) {

    override fun calculateBMIInternal(weight: Double, height: Double) = weight * 703 / (height * height)

    companion object {
        private const val MIN_WEIGHT = 20.0
        private const val MAX_WEIGHT = 440.0
        private const val MIN_HEIGHT = 20.0
        private const val MAX_HEIGHT = 100.0
    }

}
