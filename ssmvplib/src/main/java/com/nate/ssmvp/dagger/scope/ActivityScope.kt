package com.nate.ssmvp.dagger.scope

import javax.inject.Scope
import kotlin.annotation.AnnotationRetention.RUNTIME

/**
 * Activity Scope for Dagger2
 * Created by Nate on 2020/5/2
 */
@Scope
@MustBeDocumented
@Retention(RUNTIME)
annotation class ActivityScope