package com.nate.ssmvp.dagger.scope

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.RUNTIME
import javax.inject.Scope

/**
 * Fragment Scope for Dagger2
 * Created by Nate on 2020/5/2
 */
@Scope
@Documented
@Retention(RUNTIME)
annotation class FragmentScope