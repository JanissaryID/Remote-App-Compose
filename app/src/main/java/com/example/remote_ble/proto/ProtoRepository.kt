package com.example.remote_ble.proto

import android.content.Context
import android.util.Log
import androidx.datastore.DataStore
import androidx.datastore.createDataStore
import com.example.remote_ble.DataPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

class ProtoRepository(context: Context) {

//    var dataProto: DataPreferences = MutableStateFlow(listOf())

    private val datastore: DataStore<DataPreferences> = context.createDataStore(
        fileName = "myData",
        serializer = MySerializer()
    )

    val readProto: Flow<DataPreferences> = datastore.data
        .catch { exception ->
            if(exception is IOException){
                Log.d("debug", exception.message.toString())
                emit(DataPreferences.getDefaultInstance())
            }
            else{
                throw exception
            }
        }

    suspend fun updateID(idTV: String){
        datastore.updateData { preference ->
            preference.toBuilder().setIDTV(idTV).build()
        }
    }
}