####开发过程中的一些想法

#####1.网络请求和图片加载框架的封装
因为现在的图片加载框架和网络请求框架有好多，各有各的优势。当我们使用
其中一种框架的时候，如果项目出现了不能妥协的问题，需要换框架，这时要
修改的代码就太多了，因此我们可以再封装一层，提供统一的请求接口，但是
具体的实现框架可以容易的更换。

#####2.Okgo暂时直接使用，以后能力够了再封装
#####3.使用mvp+rxandroid+rxbus开发（后续会使用T-MVP简化项目）