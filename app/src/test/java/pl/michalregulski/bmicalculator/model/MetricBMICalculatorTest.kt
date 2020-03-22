package pl.michalregulski.bmicalculator.model

import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class MetricBMICalculatorTest(
    private val weight: Double,
    private val height: Double,
    private val bmi: Double
) {

    private val calculator: MetricBMICalculator = MetricBMICalculator()

    companion object {
        @JvmStatic
        @Parameters(name = "{index}: {0} * 10000 / {1}^2 = {2}")
        fun data() = listOf(arrayOf(115.0, 190.0, 31.85))
    }


    @Test
    fun calculateTest() {
        assertEquals(calculator.calculateBMI(weight, height), (bmi), 0.01)
    }

}
