package com.infinity_coder.remembermenu.ui.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


@Suppress("UNCHECKED_CAST")
fun <T : Fragment> Fragment.extractParentFragment(clazz: Class<out T>): T {
    var currentFragment: Fragment? = this
    do {
        currentFragment = currentFragment?.parentFragment
    } while (currentFragment != null && (currentFragment as T).javaClass != clazz)
    return currentFragment as T? ?: throw IllegalStateException("No parent fragment: $clazz")
}

fun <T : Fragment> Fragment.parentNavController(clazz: Class<out T>): NavController {
    return extractParentFragment(clazz).findNavController()
}

fun AppCompatActivity.setActionBar(toolbar: Toolbar, hasBackNavigation: Boolean = false, isShowTitle: Boolean = true) {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(hasBackNavigation)
    supportActionBar?.setDisplayShowTitleEnabled(isShowTitle)
}

fun Fragment.setActionBar(toolbar: Toolbar, hasBackNavigation: Boolean = false, isShowTitle: Boolean = true) {
    (activity as? AppCompatActivity)?.setActionBar(toolbar, hasBackNavigation, isShowTitle)
}

fun saveImageToFile(bitmap: Bitmap, filePath: String): Boolean {
    try {
        val file = File(filePath)
        file.createNewFile()
        FileOutputStream(filePath).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        }
        return true
    } catch (e: IOException) {
        e.printStackTrace()
        return false
    }
}

fun getImageFromFile(filePath: String?): Bitmap? {
    if (filePath == null) return null
    val file = File(filePath)

    val bitmap = BitmapFactory.decodeFile(file.absolutePath)
    val os = BufferedOutputStream(FileOutputStream(file))
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
    return bitmap
}