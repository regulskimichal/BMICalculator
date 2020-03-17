package pl.michalregulski.bmicalculator.viewmodel

import androidx.databinding.Observable
import androidx.databinding.ObservableField

open class NonNullObservableField<T : Any> : ObservableField<T> {

    constructor()

    constructor(value: T) : super(value)

    constructor(vararg dependencies: Observable) : super(*dependencies)

    override fun get(): T = super.get()!!

    override fun set(value: T) = super.set(value)

}

typealias ObservableString = NonNullObservableField<String>
