package com.cris.proyfirestorek.data.repository

import com.cris.proyfirestorek.data.common.FirestoreConstants
import com.cris.proyfirestorek.data.common.FirestoreInstance
import com.cris.proyfirestorek.data.model.ProductModel
import kotlinx.coroutines.tasks.await

class ProductRepository {
    /* busquedas */
    //obtener lista de productos mediante parametro dato
    suspend fun listar(dato: String): List<ProductModel>{
        return FirestoreInstance.get().collection(FirestoreConstants.COLECCION_PRODUCTO)
            .orderBy("descripcion").startAt(dato).endAt(dato + "\uf8ff").get()
            .await().toObjects(ProductModel::class.java)
    }

    private suspend fun registrar(model: ProductModel): Boolean{
        val documento = FirestoreInstance.get().collection(FirestoreConstants.COLECCION_PRODUCTO).document()
        model.id = documento.id
        documento.set(model).await()

        return true
    }

    private suspend fun actualizar(model: ProductModel): Boolean{
        val documento = FirestoreInstance.get().collection(FirestoreConstants.COLECCION_PRODUCTO)
            .document(model.id)
        documento.update(
            mapOf(
                "descripcion" to model.descripcion,
                "codigobarra" to model.codigobarra,
                "precio" to model.precio,
            )
        ).await()

        return true
    }

    suspend fun eliminar(model: ProductModel): Boolean{
        val documento = FirestoreInstance.get().collection(FirestoreConstants.COLECCION_PRODUCTO)
            .document(model.id)
        documento.delete().await()

        return true
    }

    suspend fun grabar(model: ProductModel): Boolean{
        return if(model.id.isEmpty()){
            registrar(model)
        }else{
            actualizar(model)
        }
    }

}