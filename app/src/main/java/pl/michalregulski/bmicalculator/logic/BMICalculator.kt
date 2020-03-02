package pl.michalregulski.bmicalculator.logic

abstract class BMICalculator {

    fun calculateBMI(weight: Double, height: Double): Double {
        return when {
            isValidWeight(weight) && isValidHeight(height) -> calculateBMIInternal(weight, height)
            else -> throw IllegalArgumentException("weight: $weight, height: $height")
        }
    }

    abstract fun calculateBMIInternal(weight: Double, height: Double): Double

    abstract fun isValidWeight(weight: Double): Boolean

    abstract fun isValidHeight(height: Double): Boolean

}
