package ru.gridmi.pc.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_product.view.*
import ru.gridmi.pc.MainApp
import ru.gridmi.pc.R
import ru.gridmi.pc.model.dto.Product
import ru.gridmi.pc.ui.MainFragment

class ProductFragment: MainFragment() {

    override fun getLayout(): Int = R.layout.fragment_product

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val product = requireArguments().getSerializable("product") as Product

        view.codeView.text = MainApp.context.resources.getString(R.string.code_of_product, product.code)

        //        <TableRow>
        //            <androidx.appcompat.widget.AppCompatTextView
        //                android:id="@+id/nameView"
        //                android:layout_weight="1"
        //                android:text="@string/name_of_product"/>
        //        </TableRow>
        //
        //        <TableRow>
        //            <androidx.appcompat.widget.AppCompatTextView
        //                android:layout_weight="4"
        //                android:text="@string/remainder"/>
        //            <androidx.appcompat.widget.AppCompatTextView
        //                android:id="@+id/remainderView"
        //                android:layout_weight="1"
        //                android:text="@string/remainder_of_product"/>
        //        </TableRow>
        //
        //        <TableRow>
        //            <androidx.appcompat.widget.AppCompatTextView
        //                android:layout_weight="4"
        //                android:text="@string/price"/>
        //            <androidx.appcompat.widget.AppCompatTextView
        //                android:id="@+id/priceView"
        //                android:layout_weight="1"
        //                android:text="@string/price_of_product"/>
        //        </TableRow>
        //
        //        <TableRow>
        //            <androidx.appcompat.widget.AppCompatTextView
        //                android:layout_weight="4"
        //                android:text="@string/retail_amount"/>
        //            <androidx.appcompat.widget.AppCompatTextView
        //                android:id="@+id/retailAmountView"
        //                android:layout_weight="1"
        //                android:text="(цена * остаток)\u20BD"/>
        //        </TableRow>
        //
        //        <TableRow>
        //            <androidx.appcompat.widget.AppCompatTextView
        //                android:layout_weight="4"
        //                android:text="@string/alcohol_product"/>
        //            <androidx.appcompat.widget.AppCompatTextView
        //                android:id="@+id/markOfAlcoholView"
        //                android:layout_weight="1"
        //                android:text="@string/yes"/>
        //        </TableRow>
        //
        //        <TableRow>
        //            <androidx.appcompat.widget.AppCompatTextView
        //                android:layout_weight="4"
        //                android:text="@string/alcohol_content"/>
        //            <androidx.appcompat.widget.AppCompatTextView
        //                android:id="@+id/contentAlcoholView"
        //                android:layout_weight="1"
        //                android:text="0.00"/>
        //        </TableRow>

    }

    companion object {
        fun from(product: Product): ProductFragment {
            return ProductFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("product", product)
                }
            }
        }
    }

}