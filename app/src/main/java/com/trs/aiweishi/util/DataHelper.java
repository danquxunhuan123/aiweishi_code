package com.trs.aiweishi.util;

import com.trs.aiweishi.R;
import com.lf.http.bean.ListData;
import com.lf.http.bean.Site;
import com.trs.aiweishi.bean.TextDrawableBean;

import java.util.List;

/**
 * Created by Liufan on 2018/6/1.
 */

public class DataHelper {
    public static List<ListData> initHomeList(List<ListData> data, int huDongSize) {
        int[] draws = new int[]{R.mipmap.icon_zx, R.mipmap.icon_jishu, R.mipmap.icon_yiyao,
                R.mipmap.icon_zixun, R.mipmap.icon_zhishi, R.mipmap.icon_school
        };
        for (int x = 0; x < data.size(); x++) {
            if (x == 0) {
                data.get(x).setType(ListData.PAGE_HEAD_TYPE);
            } else if (x == 1) {
                data.get(x).setType(ListData.BANNER_HEAD_TYPE);
            } else if (x >= 2 && x < 2 + draws.length) {
                data.get(x).setType(TextDrawableBean.TEXT_DRAWABLE_TYPE);
                data.get(x).setDrawable(draws[x - 2]);
            } else if (x == draws.length + 2 || x == draws.length + 2 + 1 + huDongSize) {
                data.get(x).setType(ListData.ITEM_TITLE_TYPE);
            } else {
                data.get(x).setType(ListData.ITEM_COMMEN_TYPE);
            }
        }
        return data;
    }

    public static List<ListData> initDocList(List<ListData> data, int huDongSize) {
        int[] draws = new int[]{R.mipmap.icon_bingli, R.mipmap.icon_jsjd, R.mipmap.icon_daka,
                R.mipmap.icon_peixun, R.mipmap.icon_keji, R.mipmap.icon_huiyi,
        };
        for (int x = 0; x < data.size(); x++) {
            if (x == 0) {
                data.get(x).setType(ListData.BANNER_HEAD_TYPE);
            } else if (x >= 1 && x <= draws.length) {
                data.get(x).setType(TextDrawableBean.TEXT_DRAWABLE_TYPE);
                data.get(x).setDrawable(draws[x - 1]);
            } else if (x == draws.length + 1 || x == draws.length + 1 + 1 + huDongSize) {
                data.get(x).setType(ListData.ITEM_TITLE_TYPE);
            } else {
                data.get(x).setType(ListData.ITEM_COMMEN_TYPE);
            }
        }
        return data;
    }

    public static List<ListData> initNgoList(int count_1, int count_2, List<ListData> data) {
        for (int x = 0; x < data.size(); x++) {
            if (x == 0)
                data.get(x).setType(ListData.PAGE_HEAD_TYPE);
            else if (x == 1 || x == (2 + count_1) || x == (3 + count_1 + count_2)) {
                data.get(x).setType(ListData.ITEM_TITLE_TYPE);
            } else if (x == (4 + count_1 + count_2)) {
                data.get(x).setType(ListData.SCROOL_LINEAR_TYPE);
            } else {
                data.get(x).setType(ListData.ITEM_COMMEN_TYPE);
            }
        }
        return data;
    }

    public static List<Site.Monitor> fillCheckData(List<Site.Monitor> data) {
        for (int x = 0; x < data.size(); x++) {
            Site.Monitor bean = data.get(x);
            bean.setOrgName(bean.getOrgName());
            bean.setOrgAddr(bean.getOrgAddr());
        }
        return data;
    }
}
