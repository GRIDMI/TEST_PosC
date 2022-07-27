package ru.gridmi.pc.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_product.view.*
import ru.gridmi.pc.MainApp
import ru.gridmi.pc.R
import ru.gridmi.pc.model.dto.Product
import ru.gridmi.pc.ui.MainFragment

class ProductFragment: MainFragment() {

    override fun getLayout(): Int = R.layout.fragment_product

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val product = requireArguments().getSerializable("product") as Product

        view.nameView.text = product.name
        view.codeView.text = MainApp.context.resources.getString(R.string.code_of_product, product.code)
        view.remainderView.text = MainApp.context.getString(R.string.remainder_of_product, product.remainder)
        view.priceView.text = MainApp.context.getString(R.string.price_of_product, product.price)
        view.retailAmountView.text = "%.2f\u20BD".format(product.price * product.remainder)

        val isAlcohol = product.findAttr(Product.AttrEnum.TYPE_OF_ALCOHOLIC_PRODUCT) != null

        view.markOfAlcoholView.setText(if (isAlcohol) R.string.yes else R.string.no)

        view.contentAlcoholContainerView.isVisible = isAlcohol
        view.contentAlcoholView.text = when(isAlcohol) {
            true -> product.findAttr(Product.AttrEnum.ALCOHOL_CONTENTING).let {
                if (it != null) return@let "%.2f%%".format(it.value?.toFloatOrNull() ?: 0L)
                return@let "-"
            }
            else -> "-"
        }

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