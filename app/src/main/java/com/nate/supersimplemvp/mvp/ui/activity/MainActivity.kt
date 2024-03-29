package com.nate.supersimplemvp.mvp.ui.activity

import android.os.Bundle
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.ToastUtils
import com.nate.ssmvp.base.SSBaseActivity
import com.nate.ssmvp.dagger.component.SSAppComponent
import com.nate.ssmvp.imageloader.SSImageLoader
import com.nate.ssmvp.imageloader.glide.GlideImageConfig
import com.nate.supersimplemvp.dagger.component.DaggerMainComponent
import com.nate.supersimplemvp.dagger.module.MainModule
import com.nate.supersimplemvp.databinding.ActivityMainBinding
import com.nate.supersimplemvp.entity.User
import com.nate.supersimplemvp.mvp.contract.MainContract
import com.nate.supersimplemvp.mvp.presenter.MainPresenter
import me.jessyan.progressmanager.ProgressListener
import me.jessyan.progressmanager.ProgressManager
import me.jessyan.progressmanager.body.ProgressInfo
import timber.log.Timber
import javax.inject.Inject

class MainActivity : SSBaseActivity<MainPresenter>(), MainContract.View {

  private lateinit var binding: ActivityMainBinding

  @Inject
  lateinit var imageLoader: SSImageLoader

  override fun setupActivityComponent(ssAppComponent: SSAppComponent) {
    DaggerMainComponent.builder().sSAppComponent(ssAppComponent).mainModule(MainModule(this)).build().inject(this)
  }

  override fun initView(savedInstanceState: Bundle?): Int {
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    return 0
  }

  override fun initData(savedInstanceState: Bundle?) {
    mPresenter?.getGitUser("NateRobinson")

    binding.getUserBtn.setOnClickListener {
      binding.userTv.text = "Loading..."
      mPresenter?.getGitUser("NateRobinson") //      startActivity(Intent(this, ContactActivity::class.java))
    }
  }

  override fun showLoading() {
  }

  override fun hideLoading() {
  }

  override fun showMessage(message: String) {
    ToastUtils.showShort(message)
  }

  override fun getUserSuccess(user: User) {
    binding.userTv.text = GsonUtils.toJson(user)

    ProgressManager.getInstance().addResponseListener(user.avatar_url, object : ProgressListener {
      override fun onProgress(progressInfo: ProgressInfo) {
        Timber.d("progressInfo=>${progressInfo.percent}")
        binding.progressBar.progress = progressInfo.percent
      }

      override fun onError(id: Long, e: Exception) {
        Timber.d("progressInfo e=>$e")
      }

    })

    imageLoader.loadImage(
      this, GlideImageConfig.builder().imageView(binding.iv).url(user.avatar_url).isCenterCrop(true).isCircle(true).blurValue(0).build()
    )
  }
}
