package com.easyandroid;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.easytools.tools.DateUtils;
import com.easytools.tools.LongLogUtils;
import com.easytools.tools.TimeUtils;


/**
 * package: com.easyandroid.TestActivity
 * author: gyc
 * description:
 * time: create at 2017/1/8 21:06
 */

public class TestActivity extends BaseActivity {

    EditText edittext1;
    PopupKeyboardUtil smallKeyboardUtil;
    private View viewContainer;
    String str = "{\"msg\":\"获取离线签批数据成功\",\"code\":0,\"body\":[{\"dFlowId\":\"fbdaddd8-9472-4535-9d2d-9a4bdcb47100\",\"document\":{\"opvId\":\"\",\"draftId\":\"fbdaddd8-9472-4535-9d2d-9a4bdcb47100\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"测试分发方案组1\"},{\"dFlowId\":\"fbc2a098-1a58-4659-b99b-01bf60b206c3\",\"document\":{\"opvId\":\"\",\"draftId\":\"fbc2a098-1a58-4659-b99b-01bf60b206c3\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"20180525-002\"},{\"dFlowId\":\"f73f4d14-bfde-4b01-b8b4-36213a94187c\",\"document\":{\"opvId\":\"\",\"draftId\":\"f73f4d14-bfde-4b01-b8b4-36213a94187c\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"0524-001\"},{\"dFlowId\":\"c87aa09a-09be-48cf-b7c1-cdb384161197\",\"document\":{\"opvId\":\"\",\"draftId\":\"c87aa09a-09be-48cf-b7c1-cdb384161197\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"0524-003\"},{\"dFlowId\":\"b9bfa7ef-d209-41dd-8fc8-e61919ad82ec\",\"document\":{\"opvId\":\"\",\"draftId\":\"b9bfa7ef-d209-41dd-8fc8-e61919ad82ec\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"20180525-001\"},{\"dFlowId\":\"a2ff11aa-c0d2-4660-82cc-46456e3ca779\",\"document\":{\"opvId\":\"\",\"draftId\":\"a2ff11aa-c0d2-4660-82cc-46456e3ca779\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"0524-005-同时发多位领导\"},{\"dFlowId\":\"83628233-72ca-40eb-9ba3-05fa6fc4b83c\",\"document\":{\"opvId\":\"\",\"draftId\":\"83628233-72ca-40eb-9ba3-05fa6fc4b83c\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"送zhjhj的文件\"},{\"dFlowId\":\"7ab3d2ee-9dcc-4e1b-b40e-c451cfc15f95\",\"document\":{\"opvId\":\"\",\"draftId\":\"7ab3d2ee-9dcc-4e1b-b40e-c451cfc15f95\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"20180524_院保密室文件发送到综合秘书\"},{\"dFlowId\":\"71e42e7f-e271-41ac-a7d2-002aaf6c2f15\",\"document\":{\"opvId\":\"\",\"draftId\":\"71e42e7f-e271-41ac-a7d2-002aaf6c2f15\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"0525-b-zhms到sz到zhjhj\"},{\"dFlowId\":\"681cb7a7-496b-4ba1-89c1-6e83c6c4bb6c\",\"document\":{\"opvId\":\"\",\"draftId\":\"681cb7a7-496b-4ba1-89c1-6e83c6c4bb6c\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"分发方案组一\"},{\"dFlowId\":\"64b8c913-957c-4647-ba6c-30c01bbd6cf7\",\"document\":{\"opvId\":\"\",\"draftId\":\"64b8c913-957c-4647-ba6c-30c01bbd6cf7\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"分发综合秘书001\"},{\"dFlowId\":\"5689e275-8b85-4aae-a5c0-c3eeb17a3264\",\"document\":{\"opvId\":\"\",\"draftId\":\"5689e275-8b85-4aae-a5c0-c3eeb17a3264\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"0525-c-zhms到sz到zhjhj\"},{\"dFlowId\":\"0b1fc83e-0efe-47bb-9f78-6a72db60de3e\",\"document\":{\"opvId\":\"\",\"draftId\":\"0b1fc83e-0efe-47bb-9f78-6a72db60de3e\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"20180524_文件直接发送到首秘_001\"},{\"dFlowId\":\"0843f8d6-e864-4535-bd4e-23d0624fdba7\",\"document\":{\"opvId\":\"\",\"draftId\":\"0843f8d6-e864-4535-bd4e-23d0624fdba7\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"0524-006-发送综合秘书\"},{\"dFlowId\":\"073ad234-c27c-416f-9ded-e64e378beb55\",\"document\":{\"opvId\":\"\",\"draftId\":\"073ad234-c27c-416f-9ded-e64e378beb55\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"首长分发001\"},{\"dFlowId\":\"047c3473-06b3-45b0-9ac0-855036a5f868\",\"document\":{\"opvId\":\"\",\"draftId\":\"047c3473-06b3-45b0-9ac0-855036a5f868\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"调试zhjhj分发\"},{\"dFlowId\":\"023edc7a-58db-4e59-bef8-281c8aec41ea\",\"document\":{\"opvId\":\"\",\"draftId\":\"023edc7a-58db-4e59-bef8-281c8aec41ea\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"分发方案测试03\"},{\"dFlowId\":\"e0032413-10cb-4117-b4dd-19fca009fa8c\",\"document\":{\"opvId\":\"\",\"draftId\":\"e0032413-10cb-4117-b4dd-19fca009fa8c\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"20180529_移动端阅件测试数据_006\"},{\"dFlowId\":\"19c7c1ab-25f5-4729-9cf5-cdf05dc53935\",\"document\":{\"opvId\":\"\",\"draftId\":\"19c7c1ab-25f5-4729-9cf5-cdf05dc53935\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"20180529_移动端阅件测试数据_007\"},{\"dFlowId\":\"6ac3dde0-b5a6-4b10-ba19-54b11314f521\",\"document\":{\"opvId\":\"\",\"draftId\":\"6ac3dde0-b5a6-4b10-ba19-54b11314f521\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"20180529_移动端阅件测试数据_008\"},{\"dFlowId\":\"6d04c15d-93a9-42c9-962b-cfde3e2c2e34\",\"document\":{\"opvId\":\"\",\"draftId\":\"6d04c15d-93a9-42c9-962b-cfde3e2c2e34\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"20180529_移动端阅件测试数据_001\"},{\"dFlowId\":\"8a8150cc-97a4-450a-93b5-f171558e1ffc\",\"document\":{\"opvId\":\"\",\"draftId\":\"8a8150cc-97a4-450a-93b5-f171558e1ffc\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"20180529_移动端阅件测试数据_002\"},{\"dFlowId\":\"4c6ee07d-05d9-4206-a7f3-63732243f66a\",\"document\":{\"opvId\":\"\",\"draftId\":\"4c6ee07d-05d9-4206-a7f3-63732243f66a\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"20180529_移动端阅件测试数据_003\"},{\"dFlowId\":\"e2a8e57b-0a6b-4c47-b0dc-65bc30d42a90\",\"document\":{\"opvId\":\"\",\"draftId\":\"e2a8e57b-0a6b-4c47-b0dc-65bc30d42a90\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"20180529_移动端阅件测试数据_004\"},{\"dFlowId\":\"dadda4da-ff58-4cb1-8018-47448c1dc5e7\",\"document\":{\"opvId\":\"\",\"draftId\":\"dadda4da-ff58-4cb1-8018-47448c1dc5e7\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"20180529_移动端阅件测试数据_005\"},{\"dFlowId\":\"8ba65b5d-34c5-457f-b5fe-b31f31e46dcb\",\"document\":{\"opvId\":\"\",\"draftId\":\"8ba65b5d-34c5-457f-b5fe-b31f31e46dcb\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"测试分发444\"},{\"dFlowId\":\"38ede901-c257-4008-b349-56f83f3478d5\",\"document\":{\"opvId\":\"\",\"draftId\":\"38ede901-c257-4008-b349-56f83f3478d5\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"测试分发3333\"},{\"dFlowId\":\"e740b46e-f33e-4b7e-a7ae-6783c7cace5f\",\"document\":{\"opvId\":\"\",\"draftId\":\"e740b46e-f33e-4b7e-a7ae-6783c7cace5f\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"测试分发1111\"},{\"dFlowId\":\"d208f784-bca4-44f8-bf55-5412cde2d3d5\",\"document\":{\"opvId\":\"\",\"draftId\":\"d208f784-bca4-44f8-bf55-5412cde2d3d5\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"csff20180528\"},{\"dFlowId\":\"d5708f28-b1c7-4d4e-8722-b5e452596cf1\",\"document\":{\"opvId\":\"\",\"draftId\":\"d5708f28-b1c7-4d4e-8722-b5e452596cf1\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"验证：0525-a-送综合秘书-001\"},{\"dFlowId\":\"bd6e9e05-deb8-411c-9380-192ae7a72702\",\"document\":{\"opvId\":\"\",\"draftId\":\"bd6e9e05-deb8-411c-9380-192ae7a72702\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"验证：0525-a-送首长-001\"},{\"dFlowId\":\"e581e392-9940-43dd-bd3c-ff0f99077aa0\",\"document\":{\"opvId\":\"\",\"draftId\":\"e581e392-9940-43dd-bd3c-ff0f99077aa0\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"0525-002-送zhms001\"},{\"dFlowId\":\"ba4ee036-d096-4601-b183-269f6d647533\",\"document\":{\"opvId\":\"\",\"draftId\":\"ba4ee036-d096-4601-b183-269f6d647533\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"文件测试分发0001\"},{\"dFlowId\":\"8b8f7f03-799d-4087-855e-a9104b81a5da\",\"document\":{\"opvId\":\"\",\"draftId\":\"8b8f7f03-799d-4087-855e-a9104b81a5da\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"分发测试0521001\"},{\"dFlowId\":\"9774d628-fef3-47e2-a923-818f97e6bf14\",\"document\":{\"opvId\":\"\",\"draftId\":\"9774d628-fef3-47e2-a923-818f97e6bf14\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"0525-d-zhms到sz到zhjhj\"},{\"dFlowId\":\"a5ac6514-70d9-499b-8d79-2d3535be0e6a\",\"document\":{\"opvId\":\"\",\"draftId\":\"a5ac6514-70d9-499b-8d79-2d3535be0e6a\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"综合秘书分发测试001\"},{\"dFlowId\":\"65c33a02-8600-4345-bdce-fc0bb2bb94a8\",\"document\":{\"opvId\":\"\",\"draftId\":\"65c33a02-8600-4345-bdce-fc0bb2bb94a8\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"测试分发给首长秘书\"},{\"dFlowId\":\"50cb8c5e-449d-4f7c-b64a-451da33216b0\",\"document\":{\"opvId\":\"\",\"draftId\":\"50cb8c5e-449d-4f7c-b64a-451da33216b0\",\"attachment\":[],\"fileId\":\"3b09c02e825948978caca109abd2b49f\",\"md5\":\"5598280f04f47e100a243a957cb9d574\"},\"state\":\"1\",\"title\":\"首长批示002测试\"}]}";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);

        edittext1 = findViewById(R.id.edittext1);

        smallKeyboardUtil = new PopupKeyboardUtil(self());
        smallKeyboardUtil.attachTo(edittext1, false);
        LongLogUtils.d(str);

        Log.d("年月日时分秒", DateUtils.getCurDateTime());
        Log.d("年月日", DateUtils.getCurDate());

        TextView tv1 = findViewById(R.id.tv1);
        TextView tv2 = findViewById(R.id.tv2);
        TextView tv3 = findViewById(R.id.tv3);
        TextView tv4 = findViewById(R.id.tv4);
        TextView tv5 = findViewById(R.id.tv5);

        tv1.setText(TimeUtils.getTime(9));
        tv2.setText(TimeUtils.getTime(50));
        tv3.setText(TimeUtils.getTime(1000));
        tv4.setText(TimeUtils.getTime(5000));
        tv5.setText(TimeUtils.getTime(108000));
    }

    public void onClickView(View view) {
        if (view.getId() == R.id.btn1)
            smallKeyboardUtil.showSoftKeyboard();
        if (view.getId() == R.id.btn2)
            smallKeyboardUtil.hideSoftKeyboard();

    }

    private Activity self() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}