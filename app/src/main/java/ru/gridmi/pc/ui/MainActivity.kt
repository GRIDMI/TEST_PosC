package ru.gridmi.pc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.FragmentTransaction
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import ru.gridmi.pc.R
import ru.gridmi.pc.tool.API
import ru.gridmi.pc.ui.fragment.ListOfProductsFragment
import ru.gridmi.pc.ui.fragment.ProductFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.runCatching {

            val render: (() -> Unit) = {
                val isProduct = fragments.lastOrNull() is ProductFragment
                supportActionBar?.setDisplayHomeAsUpEnabled(isProduct)
            }

            addFragmentOnAttachListener { _, _ ->
                render()
            }
            addOnBackStackChangedListener {
                render()
            }

            render()

        }

        if (savedInstanceState == null) {
            onMoveFragment(ListOfProductsFragment())
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            supportFragmentManager.popBackStackImmediate()
        }
        return super.onOptionsItemSelected(item)
    }

    fun onMoveFragment(fm: MainFragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragmentContainerView, fm)
        ft.addToBackStack(null)
        ft.commitAllowingStateLoss()
    }

}