package com.aefottt.gankio

import android.content.Context
import android.widget.Toast
import dalvik.system.DexClassLoader
import dalvik.system.PathClassLoader
import java.io.File
import java.lang.reflect.Array

object FixDexUtil {

    // 补丁文件所处目录名
    private const val DEX_DIR = "dex_directory"
    private const val OPTIMIZE_DIR = "optimize_dex"
    // 包含所有补丁文件的集合
    private val dexSet = HashSet<File>()

    /**
     * 判断是否需要热修复（是否存在补丁文件）
     */
    fun isNeedFix(context: Context): Boolean{
        var canFix = false
        // 获取文件目录，这个文件目录可以为任意值
        val fileTree: FileTreeWalk = File(context.filesDir, DEX_DIR).walk()
        fileTree.maxDepth(1)
            .filter {
                it.isFile &&
                it.name.startsWith("classes") &&
                it.extension in setOf("dex", "apk", "zip", "jar")
            }
            .forEach {
                dexSet.add(it)
                canFix = true
            }
        return canFix
    }

    /**
     * 补丁注入
     */
    fun dexInject(context: Context){
        // 这里的文件目录为固定值
        val appDir = File(context.filesDir.absolutePath + File.separator + OPTIMIZE_DIR)
        if (!appDir.exists()) appDir.mkdir()
        try {
            // 1.加载应用程序的ClassLoader
            val pathLoader: PathClassLoader = context.classLoader as PathClassLoader
            for (dex in dexSet){
                // 2.加载补丁dex文件的ClassLoader
                val dexLoader = DexClassLoader(
                    dex.absolutePath, // 补丁所在目录
                    appDir.absolutePath, // 应用程序存放dex文件的目录
                    null, // 加载dex时需要的库
                    pathLoader // 父类加载器
                )
                // 3.通过反射合并两种dex到同一Element数组中去
                // 3.1 获取dex对应的pathList属性
                val dexPathList = getPathList(dexLoader)
                val appPathList = getPathList(pathLoader)
                // 3.2 通过pathList获取相应的Elements
                val dexElements = getDexElements(dexPathList)
                val appElements = getDexElements(appPathList)
                // 3.3 Elements数组合并
                val elements = combineArray(dexElements, appElements)

                // 4. 通过反射重新给pathList中的elements数组赋值
                val pathList = getPathList(elements) // 需要重复获取，不能直接用pathPathList
                val clazz = pathList?.javaClass
                val field = clazz?.getDeclaredField("dexElements")
                field?.isAccessible = true
                field?.set(pathList, elements)
            }
            Toast.makeText(context, "热修复完成", Toast.LENGTH_SHORT).show()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    /**
     * 通过反射获取dex对应的pathList属性（类型为DexPathList）
     */
    private fun getPathList(baseDexClassLoader: Any?): Any? {
        val clazz = Class.forName("dalvik.system.BaseDexClassLoader")
        val field = clazz.getDeclaredField("pathList")
        field.isAccessible = true
        return field.get(baseDexClassLoader)
    }

    /**
     * 通过反射获取pathList对应的Elements数组
     */
    private fun getDexElements(pathList: Any?): Any? {
        val clazz = pathList?.javaClass
        val field = clazz?.getDeclaredField("dexElements")
        field?.isAccessible = true
        return field?.get(pathList)
    }

    /**
     * 合并数组
     */
    private fun combineArray(dexElements: Any?, appElements: Any?): Any?{
        val clazz = dexElements?.javaClass?.componentType
        val dexLen = Array.getLength(dexElements ?: 0)
        val appLen = Array.getLength(appElements ?: 0)
        if (clazz != null){
            val result = Array.newInstance(clazz, dexLen + appLen)
            System.arraycopy(dexElements ?: 0, 0, result, 0, dexLen)
            System.arraycopy(appElements ?: 0, 0, result, dexLen, appLen)
            return result
        }
        return null
    }

}