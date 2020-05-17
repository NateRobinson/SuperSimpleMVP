package com.nate.ssmvp.lifecycle.rxlifecycle

import com.nate.ssmvp.base.SSBaseActivity
import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.ActivityEvent

/**
 * 提供给 [SSBaseActivity] 使用的 [RxLifecycle] 配置接口
 * Created by Nate on 2020/5/4
 */
interface SSActivityLifecycleAble : SSLifecycleAble<ActivityEvent>