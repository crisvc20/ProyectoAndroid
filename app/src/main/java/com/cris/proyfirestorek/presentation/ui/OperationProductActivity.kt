package com.cris.proyfirestorek.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.cris.proyfirestorek.R
import com.cris.proyfirestorek.data.model.ProductModel
import com.cris.proyfirestorek.data.repository.ProductRepository
import com.cris.proyfirestorek.databinding.ActivityOperationProductBinding
import com.cris.proyfirestorek.presentation.common.UiState
import com.cris.proyfirestorek.presentation.common.makeCall
import kotlinx.coroutines.launch
import pe.pcs.libpcs.UtilsCommon
import pe.pcs.libpcs.UtilsMessage

class OperationProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOperationProductBinding
    private var _id=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOperationProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()

        if(intent.extras != null)
            obtenerProducto()
    }

    private fun initListener(){
        binding.includeToolbar.toolbar.apply {
            setNavigationOnClickListener{
                onBackPressedDispatcher.onBackPressed()
            }

            subtitle = "Registrar | Editar producto"
            navigationIcon = AppCompatResources.getDrawable(
                this@OperationProductActivity, R.drawable.baseline_arrow_back_24
            )
        }

        binding.includeToolbar.ibAccion.setImageResource(R.drawable.baseline_done_all_24)

        binding.includeToolbar.ibAccion.setOnClickListener{
            if(binding.etDescripcion.text.toString().trim().isEmpty() ||
                binding.etCodigoBarra.text.toString().trim().isEmpty() ||
                binding.etPrecio.text.toString().trim().isEmpty()){
                UtilsMessage.showAlertOk(
                    "ERROR", "Debe rellenar todos los campos", this@OperationProductActivity
                )

                return@setOnClickListener
            }

            grabar(
                ProductModel(
                    id = _id,
                    descripcion = binding.etDescripcion.text.toString(),
                    codigobarra = binding.etCodigoBarra.text.toString(),
                    precio = binding.etPrecio.text.toString().toDouble()
                )
            )
        }
    }

    private fun obtenerProducto(){
        _id = intent.extras?.getString("id") ?: ""
        binding.etDescripcion.setText(intent.extras?.getString("descripcion"))
        binding.etCodigoBarra.setText(intent.extras?.getString("codigobarra"))
        binding.etPrecio.setText(intent.extras?.getDouble("precio").toString())
    }

    private fun grabar(model: ProductModel) = lifecycleScope.launch {
        binding.progressBar.isVisible = true
        makeCall { ProductRepository().grabar(model) }.let {
            when(it){
                is UiState.Error -> {
                    binding.progressBar.isVisible = false
                    UtilsMessage.showAlertOk(
                        "ERROR", it.message, this@OperationProductActivity
                    )
                }
                is UiState.Success -> {
                    binding.progressBar.isVisible = false
                    UtilsMessage.showToast(this@OperationProductActivity, "Registro grabado")
                    UtilsCommon.cleanEditText(binding.root.rootView)
                    UtilsCommon.hideKeyboard(this@OperationProductActivity, binding.root.rootView)
                    binding.etDescripcion.requestFocus()
                    _id = ""
                    MainActivity.existeCambio = true
                }
            }
        }
    }
}