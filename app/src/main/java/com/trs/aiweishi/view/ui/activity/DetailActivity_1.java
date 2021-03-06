package com.trs.aiweishi.view.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.maning.mndialoglibrary.MProgressDialog;
import com.trs.aiweishi.R;
import com.trs.aiweishi.base.BaseActivity;
import com.lf.http.bean.BaseBean;
import com.lf.http.bean.DetailBean;
import com.lf.http.bean.ListData;
import com.lf.http.presenter.IHomePresenter;
import com.trs.aiweishi.util.PopWindowUtil;
import com.trs.aiweishi.util.ReadFromFile;
import com.trs.aiweishi.util.RegularConversionUtil;
import com.trs.aiweishi.util.UMShareUtil;
import com.trs.aiweishi.util.Utils;
import com.trs.aiweishi.util.WebViewUtils;
import com.trs.aiweishi.view.ui.fragment.ChannelDialogFragment;
import com.trs.aiweishi.view.ui.fragment.ImgsDialogFragment;
import com.trs.aiweishi.view.ui.fragment.PdfDialogFragment;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailActivity_1 extends BaseActivity implements UMShareUtil.OnShareSuccessListener {

    @Inject
    IHomePresenter presenter;
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.tv_toolbar_name)
    TextView toolName;
    @BindView(R.id.ib_share)
    ImageButton share;
    @BindView(R.id.webview)
    WebView webView;

    private UMShareUtil shareUtil;
    private PopWindowUtil sharePopUtil;
    private List<String> imgs = new ArrayList<>();
    private int type;
    public static final String TITLE_NAME = "title_name";
    public static final String URL = "url";
    public static final String TYPE = "type";
    private String toolbarName = "";
    private String imgUrl;
    private String thumbUrl;
    private String shareUrl;
    private String title;

    @Override
    protected boolean isFitsSystemWindows() {
        if (getIntent().getIntExtra(TYPE, 2) == 3)
            return false;
        else
            return super.isFitsSystemWindows();
    }

    @Override
    protected String initToolBarName() {
        toolbarName = getIntent().getStringExtra(TITLE_NAME);
        return toolbarName;
    }

    @Override
    protected void initComponent() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initListener() {
        webView.setWebChromeClient(new WebChromeClient());
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollBarSize(5);
        webView.getSettings().setSupportZoom(true); // 可以缩放
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setNeedInitialFocus(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.addJavascriptInterface(new JavaScriptInterface(), "imagelistner"); //给图片设置点击监听的
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setLoadsImagesAutomatically(true);//自动加载图片
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
    }

    @Override
    protected void initData() {
        shareUtil = UMShareUtil.getInstance().setOnShareSuccessListener(this);
        sharePopUtil = new PopWindowUtil(this);

        String url = getIntent().getStringExtra(URL);
        type = getIntent().getIntExtra(TYPE, 2);

        if (type == 2) {
            if (url.contains(".json")) {
                //获取ip地址
                String[] split = url.split("/");
                imgUrl = url.substring(0, url.length() - split[split.length - 1].length());
                presenter.getDetailData(url);
            } else {
                WebViewUtils.load(webView, new MyWebViewClient(), url, type);
            }
        } else {
            share.setVisibility(View.GONE);

            WebViewUtils.load(webView, new MyWebViewClient(), url, type);
        }
        MProgressDialog.showProgress(this, config);
    }

    @Override
    public int initLayout() {
        return R.layout.activity_detail_1;
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void showSuccess(BaseBean baseBean) {
        loadUrl(((DetailBean) baseBean).getDatas());
    }

    public void share(View view) {
        sharePopUtil.setContentView(R.layout.share_layout)
                .getView(R.id.tv_qq, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareUtil.share(DetailActivity_1.this, SHARE_MEDIA.QQ,
                                title, title, thumbUrl, shareUrl);
                    }
                })
                .getView(R.id.tv_zone, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareUtil.share(DetailActivity_1.this, SHARE_MEDIA.QZONE,
                                title, title, thumbUrl, shareUrl);
                    }
                })
                .getView(R.id.tv_wecheat, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareUtil.share(DetailActivity_1.this, SHARE_MEDIA.WEIXIN,
                                title, title, thumbUrl, shareUrl);
                    }
                })
                .getView(R.id.tv_circle_friend, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareUtil.share(DetailActivity_1.this, SHARE_MEDIA.WEIXIN_CIRCLE,
                                title, title, thumbUrl, shareUrl);
                    }
                })
                .getView(R.id.tv_sina, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareUtil.share(DetailActivity_1.this, SHARE_MEDIA.SINA,
                                title, title, thumbUrl, shareUrl);
                    }
                })
                .showAtLocation(webView);
    }

    @Override
    public void onShareSuccess(SHARE_MEDIA platform) {
        sharePopUtil.disMiss();
        ToastUtils.showShort(getResources().getString(R.string.share_success));
    }

    @Override
    public void onShareError(SHARE_MEDIA platform, Throwable t) {
        sharePopUtil.disMiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public void loadUrl(ListData listData) {
        shareUrl = listData.getShareurl();
        title = listData.getTitle();
        String source = listData.getSource();
        String updatedate = listData.getTime().split(" ")[0];
        String body = listData.getBody();

        List<String> imgSrcList = Utils.getImgSrcList(body);
        for (int a = 0; a < imgSrcList.size(); a++) {
            String img = imgSrcList.get(a);
            String url = imgUrl + img.substring(1);
            imgs.add(url);
            body = body.replace(img, url);

            if (a == 0)
                thumbUrl = url;
        }

        body = RegularConversionUtil.removeHtmlTag(body);
        String str = ReadFromFile.getFromAssets(this, "xhwDetailedView.html");

        if (!TextUtils.isEmpty(body)) {
            str = str.replace("#CONTENT#", body);
        } else {
            str = str.replace("#CONTENT#", "");
        }
        if (!TextUtils.isEmpty(title)) {
            str = str.replaceAll("#TITLE#", title.replace("&amp;nbsp;", " "));
        } else {
            str = str.replaceAll("#TITLE#", "");
        }

        if (!TextUtils.isEmpty(source)) {
            str = str.replaceAll("#SOURCE#", source);
            str = str.replace("#SHUXIAN#", "来源:");
        } else {
            str = str.replace("#SHUXIAN#", "");
            str = str.replaceAll("#SOURCE#", "");
        }
        if (!TextUtils.isEmpty(updatedate))
            str = str.replaceAll("#TIME#", updatedate);
        else
            str = str.replaceAll("#TIME#", "");

        // webview加载html标签
        webView.loadDataWithBaseURL(null, str, "text/html", "utf-8", null);

        MProgressDialog.dismissProgress();
    }


    class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.endsWith("pdf")) {
                loadPdf(url);
            } else {
                Intent intent = new Intent(DetailActivity_1.this, DetailActivity_1.class);
                intent.putExtra(DetailActivity_1.TITLE_NAME, toolbarName);
                intent.putExtra(DetailActivity_1.URL, url);
                startActivity(intent);
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            addImageClickListner(view);
            MProgressDialog.dismissProgress();
        }
    }

    private void loadPdf(String url) {
        PdfDialogFragment.newInstance(url)
                .show(getSupportFragmentManager(), PdfDialogFragment.TAG);
    }

    private void addImageClickListner(WebView webView) {
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i < objs.length; i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }

    private class JavaScriptInterface {
        public JavaScriptInterface() {
        }

        //点击图片回调方法
        //必须添加注解,否则无法响应
        @JavascriptInterface
        public void openImage(String img) {
            if (ObjectUtils.isNotEmpty(imgs)) {
                ImgsDialogFragment dialogFragment = ImgsDialogFragment.newInstance(imgs.indexOf(img), imgs);
                dialogFragment.show(getSupportFragmentManager(), ChannelDialogFragment.TAG);
//                dialogFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//                    }
//                });
            }
        }
    }

    @Override
    public void onResume() {
        webView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        webView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        webView.destroy();
        if (shareUtil != null)
            shareUtil.onDestory();
        super.onDestroy();
    }
}
