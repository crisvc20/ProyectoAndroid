package com.cris.proyfirestorek.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cris.proyfirestorek.data.model.ProductModel
import com.cris.proyfirestorek.databinding.ItemsProductoBinding
import pe.pcs.libpcs.UtilsCommon

class ProductAdapter(
    private val listener: IOnClickListener
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){

    private var lista = emptyList<ProductModel>()

    interface IOnClickListener {
        fun clickEditar(productModel: ProductModel)
        fun clickEliminar(productModel: ProductModel)
    }

    //recibe el modelo de items products
    inner class ProductViewHolder(private val binding: ItemsProductoBinding): RecyclerView.ViewHolder(binding.root){
        fun enlazar(productModel: ProductModel){
            binding.tvTitulo.text = productModel.descripcion
            binding.tvCodigoBarra.text = productModel.codigobarra
            binding.tvPrecio.text = UtilsCommon.formatFromDoubleToString(productModel.precio)

            binding.ibEditar.setOnClickListener{
                listener.clickEditar(productModel)
            }

            binding.ibEliminar.setOnClickListener{
                listener.clickEliminar(productModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemsProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.enlazar(lista[position])
    }

    fun setLista(_Lista: List<ProductModel>){
        this.lista = _Lista
        notifyDataSetChanged()
    }

}