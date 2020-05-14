apply from:"../common_module_build.gradle"
<#import "root://activities/common/kotlin_macros.ftl" as kt>
<@kt.addKotlinPlugins />
apply plugin: 'kotlin-kapt'

android {
    //给 Module 内的资源名增加前缀, 避免资源名冲突, 建议使用 Module 名作为前缀
    //resourcePrefix "ModuleName_"
}

dependencies {
    ${getConfigurationName("compile")} fileTree(dir: 'libs', include: ['*.jar'])
    api project(":ssmvplib")
    <@kt.addKotlinDependencies />
}
