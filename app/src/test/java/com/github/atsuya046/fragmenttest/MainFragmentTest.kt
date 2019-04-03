package com.github.atsuya046.fragmenttest

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.*
import dagger.android.support.HasSupportFragmentInjector
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
class MainFragmentTest {

    @Test
    fun test() {
        launchFragmentInContainer<MainFragment>()
        onView(withId(R.id.text))
            .check(matches(withText("Hello test world.")))
    }
}


class TestApp: DaggerApplication(), HasSupportFragmentInjector {
    private val applicationInjector: AndroidInjector<TestApp> by lazy { DaggerTestAppComponent.builder().create(this) }

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> = supportFragmentInjector

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = applicationInjector

    override fun onCreate() {
        super.onCreate()
        applicationInjector.inject(this)
    }
}

@Component(
    modules = [AndroidInjectionModule::class, TestAppModule::class, TestMessageModule::class]
)
interface TestAppComponent : AndroidInjector<TestApp> {
    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<TestApp>()
}

@Module(includes = [AppModule::class])
abstract class TestAppModule

@Module
class TestMessageModule {
    @Provides
    fun message() = Message("Hello test world.")
}