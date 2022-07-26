package ru.gridmi.pc.ui.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list_of_products.view.*
import kotlinx.android.synthetic.main.fragment_list_of_products_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.gridmi.pc.MainApp
import ru.gridmi.pc.MainApp.Companion.toStringFromRes
import ru.gridmi.pc.R
import ru.gridmi.pc.model.dto.Product
import ru.gridmi.pc.tool.API
import ru.gridmi.pc.tool.LoadState
import ru.gridmi.pc.tool.TinyHelper.resultToAny
import ru.gridmi.pc.ui.MainFragment
import java.util.*

class ListOfProductsFragment: MainFragment() {

    private val model: Model by viewModels()

    override fun getLayout(): Int = R.layout.fragment_list_of_products

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (savedInstanceState == null) model.loadProducts()

        val adapter = Adapter(f = this)
        view.recycleView.adapter = adapter

        view.srl.setOnRefreshListener {
            model.loadProducts()
        }

        model.data.observe(viewLifecycleOwner) {

            view.srl.isRefreshing = it.state == LoadState.State.LOAD

            if (it.state == LoadState.State.DONE) {

                if (it.data is Throwable) {
                    if (!it.isHandled()) {
                        AlertDialog.Builder(context)
                            .setTitle(R.string.error_loading)
                            .setMessage(it.data.message ?: R.string.error_loading.toStringFromRes())
                            .setPositiveButton(R.string.yes) { _, _ -> model.loadProducts() }
                            .setNegativeButton(R.string.no, null)
                            .show()
                    }
                }

                if (it.data is List<*>) {
                    @Suppress("UNCHECKED_CAST")
                    adapter.invalidate(it.data as List<Product>)
                }

            }

        }

    }

    fun onSelectProduct(product: Product) = singleActivity().onMoveFragment(
        ProductFragment.from(product)
    )

    class Adapter(private val f: ListOfProductsFragment) : RecyclerView.Adapter<Adapter.Item>() {

        class Item(private val a: Adapter, itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun onBind(product: Product) = itemView.runCatching {

                cardView.setOnClickListener {
                    a.f.onSelectProduct(product)
                }

                nameView.text = product.name
                remainderView.text = String.format(Locale.getDefault(), "%.2f", product.remainder)
                codeView.text = MainApp.context.resources.getString(R.string.code_of_product, product.code)
                priceView.text = MainApp.context.resources.getString(R.string.price_of_product, product.price)

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Item {
            return Item(this, LayoutInflater.from(parent.context).inflate(R.layout.fragment_list_of_products_item, parent, false))
        }

        override fun onBindViewHolder(holder: Item, position: Int) {
            holder.onBind(items[position])
        }

        private val items: MutableList<Product> = mutableListOf()

        override fun getItemCount(): Int = items.size

        @SuppressLint("NotifyDataSetChanged")
        fun invalidate(items: List<Product>) {
            this.items.clear()
            this.items.addAll(items)
            this.notifyDataSetChanged()
        }

    }

    class Model: ViewModel() {

        val data = MutableLiveData(LoadState.init())

        fun loadProducts() = viewModelScope.launch(Dispatchers.IO) {

            data.postValue(LoadState(LoadState.State.LOAD, data = null))

            data.postValue(LoadState(LoadState.State.DONE, runCatching {
                API.listOfProducts()
            }.resultToAny()))

        }

    }

}