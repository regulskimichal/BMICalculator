package swim.regulski.BMICalculator.logic

import spock.lang.Specification
import spock.lang.Unroll

class ImperialBMICalcTest extends Specification {
    @Unroll
    def "IsValidMassTest"() {
        expect:
        calc.isValidWeight(mass)

        where:
        calc = new ImperialBMICalc()
        mass << [20, 30, 45.313, 200, 440]
    }

    @Unroll
    def "IsNotValidMassTest"() {
        expect:
        !calc.isValidWeight(mass)

        where:
        calc = new ImperialBMICalc()
        mass << [-1, 9, 9.9, 0, 10, 440.1]
    }

    @Unroll
    def "IsValidHeightTest"() {
        expect:
        calc.isValidHeight(height)

        where:
        calc = new ImperialBMICalc()
        height << [0.5, 0.6, 0.678, 1, 1.4, 1.9, 2.0, 2, 2.5]
    }

    @Unroll
    def "IsNotValidHeightTest"() {
        expect:
        !calc.isValidHeight(height)

        where:
        calc = new ImperialBMICalc()
        height << [-1, 0, 0.4, 2.51, 0.49]
    }
}