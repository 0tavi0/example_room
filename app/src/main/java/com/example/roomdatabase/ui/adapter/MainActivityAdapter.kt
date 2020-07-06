package com.example.roomdatabase.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
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

        holder.itemView.im_complete.setOnClickListener {
            viewModel.markCompleted(product)
        }

        holder.itemView.setOnClickListener {
            dialog(it, product)
        }
    }

    private fun dialog(view: View, product: Product) {
        val dialog = AlertDialog.Builder(view.context)
        dialog.setTitle("Deletar?")
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        lp.setMargins(30, 0, 30, 0)
        dialog.setPositiveButton("Deletar") { _, _ ->
            viewModel.deleteProduct(product)
        }
        dialog.setNegativeButton("Cancel") { _, _ -> null }
        dialog.show()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameProduct = view.tv_name_product
        fun bindView(product: Product) {
            nameProduct.text = product.productName
            configCompleted(product)

        }

        private fun configCompleted(product: Product) {
            if (product.completed == 1) {
                DrawableCompat.setTint(
                    DrawableCompat.wrap(itemView.im_complete.drawable),
                    ContextCompat.getColor(itemView.context, R.color.colorCompleted)
                )
            } else {
                DrawableCompat.setTint(
                    DrawableCompat.wrap(itemView.im_complete.drawable),
                    ContextCompat.getColor(itemView.context, R.color.colorNotCompleted)
                )
            }
        }
    }
}