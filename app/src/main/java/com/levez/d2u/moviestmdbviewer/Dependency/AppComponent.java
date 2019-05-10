package com.levez.d2u.moviestmdbviewer.Dependency;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        FragmentBuilder.class,
        ActivityBuilder.class,
        ViewModelModule.class,
})
public interface AppComponent extends AndroidInjector<MyApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(MyApp application);
        Builder appModule(AppModule appModule);
        AppComponent build();
    }

    void inject(MyApp application);

}