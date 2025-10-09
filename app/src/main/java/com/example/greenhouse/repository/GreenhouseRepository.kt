package com.example.greenhouse.repository

import com.example.greenhouse.model.Greenhouse
import com.example.greenhouse.model.Section
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class GreenhouseRepository {
    private val db = FirebaseFirestore.getInstance()

    fun observeGreenhouses(onUpdate: (List<Greenhouse>) -> Unit) {
        db.collection("greenhouse")
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    onUpdate(emptyList())
                    return@addSnapshotListener
                }

                val list = mutableListOf<Greenhouse>()

                snapshot.documents.forEach { doc ->
                    val id = doc.id
                    val name = doc.getString("name") ?: ""

                    db.collection("greenhouse")
                        .document(id)
                        .collection("temp_sensor")
                        .orderBy("timestamp", Query.Direction.DESCENDING)
                        .limit(1)
                        .addSnapshotListener { tempSnapshot, _ ->
                            val latestTemp = tempSnapshot?.documents?.getOrNull(0)
                                ?.getDouble("temperature")?.toFloat() ?: 0f

                            list.removeAll { it.id == id }
                            list.add(
                                Greenhouse(
                                    id = id,
                                    name = name,
                                    temperature = latestTemp
                                )
                            )
                            onUpdate(list.sortedBy { it.name })
                        }
                }
            }
    }
    suspend fun addGreenhouse(name: String, id: String?) {
        val greenhouseData = hashMapOf("name" to name)

        if (id.isNullOrBlank()) {
            db.collection("greenhouse").add(greenhouseData).await()
        } else {
            val docRef = db.collection("greenhouse").document(id)
            val snapshot = docRef.get().await()

            if (snapshot.exists()) {
                throw Exception("Skleník s tímto ID již existuje.")
            }

            docRef.set(greenhouseData).await()
        }
    }

    suspend fun deleteGreenhouse(greenhouseId: String) {
        db.collection("greenhouse")
            .document(greenhouseId)
            .delete()
            .await()
    }
}
