package com.example.basemodule.network.components;

import com.example.basemodule.basemvp.BaseMImpl;
import com.example.basemodule.network.modules.ApiModule;
import com.example.basemodule.network.scopes.ActivityScope;

import dagger.Component;

/**
 * create by zy on 2019/9/26
 * </p>
 */
@ActivityScope
@Component(dependencies = {NetComponent.class}, modules = {ApiModule.class})
public interface ApiComponent {

    public void inject(BaseMImpl model);


}
