package pl.michalregulski.bmicalculator

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import pl.michalregulski.bmicalculator.viewmodel.BMIViewModel

class Application : Application() {

    private val module = module {
        viewModel { BMIViewModel() }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(module)
        }
    }

    companion object {
        const val bmiStatusKey = "BMI"
    }

}
