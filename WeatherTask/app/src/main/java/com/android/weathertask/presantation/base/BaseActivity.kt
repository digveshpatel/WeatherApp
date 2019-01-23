package com.android.weathertask.presantation.base

import android.os.Bundle
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.android.weathertask.R

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        viewModelSetUp()
        viewSetUp()
    }

    abstract fun getLayout(): Int

    abstract fun viewModelSetUp()

    abstract fun viewSetUp()

    /**
     * @param fragment       = fragment object
     * @param isAddBackStack = add to back in stack or not, true to add
     * @param tag            = fragment tag id, to identify fragments in fragment manager [FragmentManager.findFragmentByTag]
     */
    fun switchFragment(fragment: Fragment, isAddBackStack: Boolean, @NonNull tag: String) {
        switchFragment(fragment, isAddBackStack, tag, null)
    }


    /**
     * @param fragment       = fragment object
     * @param isAddBackStack = add to back in stack or not, true to add
     * @param tag            = fragment tag id, to identify fragments in fragment manager [FragmentManager.findFragmentByTag]
     * @param backStackTag   to pop multiple fragments [FragmentManager.popBackStack]
     */
    private fun switchFragment(
        fragment: Fragment,
        isAddBackStack: Boolean, @NonNull tag: String,
        backStackTag: String?
    ) {
        //        setCurrentTopFragment(tag);
        val fragmentTransaction = createFragmentTransaction(fragment, isAddBackStack, tag, backStackTag)
        if (!supportFragmentManager.isStateSaved)
            fragmentTransaction.commit()
    }

    private fun createFragmentTransaction(
        fragment: Fragment,
        isAddBackStack: Boolean, @NonNull tag: String,
        backStackTag: String?
    ): FragmentTransaction {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_container, fragment, tag)
        if (isAddBackStack)
            fragmentTransaction.addToBackStack(backStackTag)
        return fragmentTransaction
    }

    /**
     * safely pop back stack
     *
     * @return true if [FragmentManager.popBackStack] called
     */
    fun popBackStack(): Boolean {
        if (supportFragmentManager.backStackEntryCount > 0 && !supportFragmentManager.isStateSaved) {
            supportFragmentManager.popBackStack()
            return true
        }
        return false
    }

    fun popBackStack(@NonNull tag: String): Boolean {
        if (supportFragmentManager.backStackEntryCount > 0 && !supportFragmentManager.isStateSaved) {
            supportFragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            return true
        }
        return false
    }


}