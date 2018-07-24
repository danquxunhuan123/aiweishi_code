package com.trs.aiweishi.presenter.impl;

import com.trs.aiweishi.base.BaseBean;
import com.trs.aiweishi.base.BasePresenter;
import com.trs.aiweishi.http.IResponseCallBack;
import com.trs.aiweishi.model.IDataModel;
import com.trs.aiweishi.presenter.IBindPhoneView;
import com.trs.aiweishi.presenter.IUserPresenter;
import com.trs.aiweishi.view.IBaseView;
import com.trs.aiweishi.view.IFindPsdView;
import com.trs.aiweishi.view.IRegistView;
import com.trs.aiweishi.view.IUserEditerView;
import com.trs.aiweishi.view.IUserView;
import com.trs.aiweishi.view.IUserCenterView;

import java.util.Map;

import javax.inject.Inject;

import okhttp3.MultipartBody;

/**
 * Created by Liufan on 2018/5/16.
 */

public class UserPresenterImpl extends BasePresenter implements IUserPresenter {

    IUserView userView;
    IRegistView registVeiw;
    IFindPsdView findPsdView;
    IUserEditerView editVeiw;
    IUserCenterView userInfoView;
    IBindPhoneView bindPhoneView;

    public UserPresenterImpl(IBaseView baseView,IDataModel dataModel) {
        this.dataModel = dataModel;

        if (baseView instanceof IRegistView)
            registVeiw = (IRegistView) baseView;
        else if (baseView instanceof IUserEditerView)
            editVeiw = (IUserEditerView) baseView;
        else if (baseView instanceof IFindPsdView)
            findPsdView = (IFindPsdView) baseView;
        else if (baseView instanceof IUserView)
            this.userView = (IUserView) baseView;
        else if (baseView instanceof IBindPhoneView)
            this.bindPhoneView = (IBindPhoneView) baseView;
        else if (baseView instanceof IUserCenterView)
            this.userInfoView = (IUserCenterView) baseView;
        else
            this.baseView = baseView;
    }

    @Override
    public void login(final int count, Map<String, String> params) {
        dataModel.login(params, new IResponseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                if (count == 0)
                    userView.showSuccess((BaseBean) obj);
                else
                    editVeiw.loginSuccess((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                userView.showError(e);
            }
        });
    }

    @Override
    public void changePsd(Map<String, String> params) {
        dataModel.changePsd(params, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                baseView.showSuccess((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                baseView.showError(e);
            }
        });
    }

    @Override
    public void regist(Map<String, String> params) {
        dataModel.regist(params, new IResponseCallBack() {
            @Override
            public void onSuccess(Object obj) {
                baseView.showSuccess((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                baseView.showError(e);
            }
        });
    }

    @Override
    public void notifyUserAttr(final int count,Map<String, String> params) {
        dataModel.notifyUserAttr(params, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                if (count == 0){
                    registVeiw.notifyCodeSuccess((BaseBean) obj);
                }else if (count == 1){
                    findPsdView.notifyCodeSuccess((BaseBean) obj);
                }
            }

            @Override
            public void onError(Throwable e) {
                registVeiw.showError(e);
            }
        });
    }

    @Override
    public void getCode(final int count, Map<String, String> params) {
        dataModel.getCode(params, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                if (count == 0)
                    registVeiw.showSuccess((BaseBean) obj);
                else
                    findPsdView.getCodeSuccess((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                registVeiw.showError(e);
            }
        });
    }

    @Override
    public void logout(Map<String, String> param) {
        dataModel.getLogout(param, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                editVeiw.showSuccess((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                editVeiw.showError(e);
            }
        });
    }

    @Override
    public void saveEdit(Map<String, String> param) {
        dataModel.saveEdit(param, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                editVeiw.saveInfoSuccess((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                editVeiw.showError(e);
            }
        });
    }

    @Override
    public void refreshSession(Map<String, String> params) {
        dataModel.refreshSession(params, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                editVeiw.refSesSuccess((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                editVeiw.showError(e);
            }
        });
    }

    @Override
    public void getUserInfo(final int count,Map<String, String> param) {
        dataModel.getUserInfo(param, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                if (count == 0)
                    userInfoView.getUserInfo((BaseBean) obj);
                if (count == 1)
                    bindPhoneView.getUserInfo((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                baseView.showError(e);
            }
        });
    }

    @Override
    public void findPsd(Map<String, String> params) {
        dataModel.findPsd(params, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                baseView.showSuccess((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                baseView.showError(e);
            }
        });
    }

    @Override
    public void loginByUID(final int count,Map<String, String> params) {
        dataModel.loginByUID(params, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                if (count == 0){
                    userView.thirdLoginSuccess((BaseBean) obj);
                }else{
                    bindPhoneView.loginByUID((BaseBean) obj);
                }
            }

            @Override
            public void onError(Throwable e) {
                baseView.showError(e);
            }
        });
    }

    @Override
    public void checkAccountMapping(Map<String, String> params) {
        dataModel.checkAccountMapping(params, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                userView.checkAccountMapping((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                userView.showError(e);
            }
        });
    }

    @Override
    public void addAccountMapping(Map<String, String> params) {
        dataModel.addAccountMapping(params, new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                bindPhoneView.showSuccess((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                baseView.showError(e);
            }
        });
    }

    @Override
    public void editHeadImg(String url, MultipartBody.Part part) {
        dataModel.editHeadImg(url,part,new IResponseCallBack() {

            @Override
            public void onSuccess(Object obj) {
                editVeiw.editHeadSuccess((BaseBean) obj);
            }

            @Override
            public void onError(Throwable e) {
                editVeiw.showError(e);
            }
        });
    }
}