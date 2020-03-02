package pl.michalregulski.bmicalculator.logic

class ImperialBMICalculator : BMICalculator() {

    override fun calculateBMIInternal(weight: Double, height: Double) = weight * 703 / (height * height)

    override fun isValidWeight(weight: Double) = weight in MIN_WEIGHT..MAX_WEIGHT

    override fun isValidHeight(height: Double) = height in MIN_HEIGHT..MAX_HEIGHT

    companion object {
        private const val MIN_WEIGHT = 20.0
        private const val MAX_WEIGHT = 440.0
        private const val MIN_HEIGHT = 20.0
        private const val MAX_HEIGHT = 100.0
    }

}
