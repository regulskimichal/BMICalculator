package pl.michalregulski.bmicalculator.view


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.michalregulski.bmicalculator.R
import pl.michalregulski.bmicalculator.model.TILErrorMatcher

@LargeTest
@RunWith(AndroidJUnit4::class)
class BMIActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(BMIActivity::class.java)

    @Test
    fun bmiActivityTest() {
        val textInputEditText = onView(
            allOf(
                withId(R.id.weightET),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.weightTIL),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("115"), closeSoftKeyboard())

        val floatingActionButton = onView(
            allOf(
                withId(R.id.fab),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        val linearLayout = onView(
            allOf(
                withId(R.id.heightTIL),
                childAtPosition(
                    allOf(
                        withId(R.id.bmiContentCL),
                        childAtPosition(
                            IsInstanceOf.instanceOf(ViewGroup::class.java),
                            1
                        )
                    ),
                    2
                ),
                object : TypeSafeMatcher<View>() {
                    override fun matchesSafely(item: View?): Boolean =
                        if (item is TextInputLayout) {
                            item.isErrorEnabled
                        } else {
                            false
                        }

                    override fun describeTo(description: Description?) = Unit
                }
            )
        )
        linearLayout.check(matches(isDisplayed()))

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.heightET),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.heightTIL),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("190"), closeSoftKeyboard())

        val floatingActionButton2 = onView(
            allOf(
                withId(R.id.fab),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        floatingActionButton2.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.bmiMessageTV), withText("Your BMI:"),
                childAtPosition(
                    allOf(
                        withId(R.id.bmiContentCL),
                        childAtPosition(
                            IsInstanceOf.instanceOf(ViewGroup::class.java),
                            1
                        )
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        textView.check(matches(isDisplayed()))

        val textView3 = onView(
            allOf(
                withId(R.id.bmiValueTV), withText("31.86"),
                childAtPosition(
                    allOf(
                        withId(R.id.bmiContentCL),
                        childAtPosition(
                            IsInstanceOf.instanceOf(ViewGroup::class.java),
                            1
                        )
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("31.86")))

        val textView5 = onView(
            allOf(
                withId(R.id.bmiInfoTV), withText("Obesity"),
                childAtPosition(
                    allOf(
                        withId(R.id.bmiContentCL),
                        childAtPosition(
                            IsInstanceOf.instanceOf(ViewGroup::class.java),
                            1
                        )
                    ),
                    6
                ),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("Obesity")))

        val overflowMenuButton = onView(
            allOf(
                withContentDescription("More options"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        overflowMenuButton.perform(click())

        val appCompatTextView = onView(
            allOf(
                withId(R.id.title), withText("Imperial units"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatTextView.perform(click())

        val floatingActionButton3 = onView(
            allOf(
                withId(R.id.fab),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        floatingActionButton3.perform(click())

        val linearLayout3 = onView(
            allOf(
                withId(R.id.heightTIL),
                childAtPosition(
                    allOf(
                        withId(R.id.bmiContentCL),
                        childAtPosition(
                            IsInstanceOf.instanceOf(ViewGroup::class.java),
                            1
                        )
                    ),
                    2
                ),
                TILErrorMatcher()
            )
        )
        linearLayout3.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
