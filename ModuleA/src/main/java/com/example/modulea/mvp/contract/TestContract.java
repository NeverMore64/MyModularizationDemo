package com.example.modulea.mvp.contract;

import io.reactivex.Observable;

/**
 * create by zy on 2019/9/26
 * </p>
 */
public class TestContract {

    public interface IView {

        void showData(String text);
    }

    public interface IPresenter {
        void getData();
    }


    public interface IModel {
        Observable<String> getData();
    }

}
