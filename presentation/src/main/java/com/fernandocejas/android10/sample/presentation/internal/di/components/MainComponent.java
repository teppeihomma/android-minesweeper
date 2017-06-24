package com.fernandocejas.android10.sample.presentation.internal.di.components;

import com.fernandocejas.android10.sample.presentation.internal.di.PerActivity;
import com.fernandocejas.android10.sample.presentation.internal.di.modules.ActivityModule;
import com.fernandocejas.android10.sample.presentation.internal.di.modules.MainModule;
import com.fernandocejas.android10.sample.presentation.view.fragment.GameFragment;
import com.fernandocejas.android10.sample.presentation.view.fragment.StartFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, MainModule.class})
public interface MainComponent extends ActivityComponent {
    void inject(StartFragment startFragment);
    void inject(GameFragment gameFragment);
}
