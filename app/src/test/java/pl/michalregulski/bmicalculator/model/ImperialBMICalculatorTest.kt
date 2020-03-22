package pl.michalregulski.bmicalculator.model

import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class ImperialBMICalculatorTest(
    private val weight: Double,
    private val height: Double,
    private val bmi: Double
) {

    private val calculator: ImperialBMICalculator = ImperialBMICalculator()

    companion object {
        @JvmStatic
        @Parameters(name = "{index}: {0} * 703 / {1}^2 = {2}")
        fun data() = listOf(arrayOf(253.53, 74.8, 31.85))
    }


    @Test
    fun calculateTest() {
        assertEquals(calculator.calculateBMI(weight, height), (bmi), 0.01)
    }

}
