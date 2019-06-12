package com.meetapp.ecoapp

import androidx.test.InstrumentationRegistry
import com.meetapp.ecoapp.dagger.App
import com.meetapp.ecoapp.dagger4test.AppComponentForTest
import com.meetapp.ecoapp.dagger4test.AppModuleForTest
import it.cosenonjaviste.daggermock.DaggerMock

fun daggerMockRule() = DaggerMock.rule<AppComponentForTest>(AppModuleForTest()) {
    set { component -> component.inject(app) }
    customizeBuilder<AppComponentForTest.Builder> { it.create(app) }
}

val app: App
    get() = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as App