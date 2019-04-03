package com.github.atsuya046.fragmenttest

import androidx.fragment.app.Fragment
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.android.*
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class App: DaggerApplication(), HasSupportFragmentInjector {

    private val applicationInjector: AndroidInjector<App> by lazy { DaggerAppComponent.builder().create(this) }

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate() {
        super.onCreate()
        applicationInjector.inject(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = applicationInjector

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> = supportFragmentInjector

}

@Component(modules = [AndroidInjectionModule::class, AppModule::class, MessageModule::class])
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<App>()
}

@Module
abstract class AppModule {
    @ContributesAndroidInjector
    abstract fun contributeActivityInjector(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeFragmentInjector(): MainFragment
}

@Module
class MessageModule {
    @Provides
    fun message() = Message("Hello world.")
}

class Message(val value: String)