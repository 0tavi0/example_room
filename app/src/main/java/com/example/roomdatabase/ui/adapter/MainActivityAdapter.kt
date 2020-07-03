package com.example.roomdatabase.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.R
import com.example.roomdatabase.model.Product
import com.example.roomdatabase.ui.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.item_product.view.*

class MainActivityAdapter(private val viewModel: MainActivityViewModel) :
    RecyclerView.Adapter<MainActivityAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return viewModel.productSize()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = viewModel.productItem(position)
        holder.bindView(product)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameProduct = view.tv_name_product

        fun bindView(product: Product) {
            nameProduct.text = product.productName
        }
    }
}