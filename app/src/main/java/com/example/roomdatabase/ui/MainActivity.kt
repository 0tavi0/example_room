package com.example.roomdatabase.ui

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.R
import com.example.roomdatabase.data.db.AppDatabase
import com.example.roomdatabase.model.Product
import com.example.roomdatabase.repository.ProductDbDataSource
import com.example.roomdatabase.ui.adapter.MainActivityAdapter
import com.example.roomdatabase.ui.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val adapter: MainActivityAdapter by lazy {
        MainActivityAdapter(viewModel)
    }
    private val layoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private val viewModel: MainActivityViewModel by viewModels(
        factoryProducer = {
            val database = AppDatabase.getDatabase(this)
            MainActivityViewModel.MainActivityViewModelFactory(
                productRepository = ProductDbDataSource(database.productDao())
            )
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        observer()
        setupRecycler()

        fab.setOnClickListener {
            openDialogAddTodo()
        }
    }

    private fun observer() {
        viewModel.getAll()
        viewModel.listProduct.observe(this, Observer {
            handleProducts(it)
        })
    }

    private fun handleProducts(it: List<Product>?) {
        if (it.isNullOrEmpty()) {
            rv_product.visibility = View.GONE
            tv_no_results.visibility = View.VISIBLE
            return
        }
        rv_product.visibility = View.VISIBLE
        tv_no_results.visibility = View.GONE
        adapter.notifyDataSetChanged()
    }

    private fun setupRecycler() {
        rv_product.layoutManager = layoutManager
        rv_product.adapter = adapter

    }

    private fun openDialogAddTodo() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Novo Produto")

        //config edit
        val edit = EditText(this)
        edit.hint = "Novo Produto"
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        lp.setMargins(30, 0, 30, 0)
        edit.layoutParams = lp

        dialog.setView(edit)
        dialog.setPositiveButton("Salvar") { _, _ ->
            viewModel.saveProduct(edit.text.toString())
        }
        dialog.setNegativeButton("Cancel") { _, _ -> null }

        dialog.show()
    }


}
