# SuperSimpleMVP
A super simple way to create a mvp project.

## TODO

1. 总结 super mvp lib (ssmvplib) 
2. 总结配合 ssmvplib 使用的 ssmvp module 模板
3. 总结配合 ssmvplib 使用的 ssmvp page 模板

[https://github.com/JessYanCoding/MVPArms/wiki#1.3.1](https://github.com/JessYanCoding/MVPArms/wiki#1.3.1)

## 整体框架

- MVP
- Dagger2
- Application.ActivityLifecycleCallbacks
- FragmentManager.FragmentLifecycleCallbacks
- Retrofit
- RxCache
- 图片加载采用策略模式 - 默认使用 Glide 框架

## 整体实现思路

### MVP

- Contract
- Model
- View
- Presenter

#### Contract

Contract 层负责定义当前页面需要的 View 和 Model 层的通用接口，这里面定义好接口之后，因为实例化 Presenter 时会传入这两个对象（通过 Dagger2 自动注入），
这么做的目的，是抽象出一个通用的接口，这样在 Presenter 的实现中只需要和此 Contract 中的协议进行交互而无需去关注具体的实现，将整个代码做了更细的分层，这样
也利于代码的复用。

#### Model

Model 层的实例化也是通过 Dagger2 的依赖注入完成。具体的 Model 需要实现 Contract.Model 中约定的协议，从而供 Presenter 调用获取数据

#### View

View 层即为 Activity 或者 Fragment，每个 Activity 或者 Fragment 需要实现 Contract.View 中约定的协议，同时需要完成 Dagger2 当前页面 Component 的
初始化，完成所有的依赖注入。

### Application.ActivityLifecycleCallbacks

利用 Application.ActivityLifecycleCallbacks 来完成。。。。

### 图片加载

采用策略模式集成

## 整理出所有用到的第三方库

