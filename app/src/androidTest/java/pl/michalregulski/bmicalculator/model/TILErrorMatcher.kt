package pl.michalregulski.bmicalculator.model

import android.view.View
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class TILErrorMatcher : TypeSafeMatcher<View>() {
    override fun matchesSafely(item: View?): Boolean = if (item is TextInputLayout) {
        item.isErrorEnabled
    } else {
        false
    }

    override fun describeTo(description: Description?) = Unit
}
