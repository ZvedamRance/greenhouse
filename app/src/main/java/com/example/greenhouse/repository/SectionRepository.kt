package com.example.greenhouse.repository

import com.example.greenhouse.model.Section
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class SectionRepository {
    private val db = FirebaseFirestore.getInstance()

    fun observeSections(greenhouseId: String, onUpdate: (List<Section>) -> Unit) {
        db.collection("greenhouse")
            .document(greenhouseId)
            .collection("sections")
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    onUpdate(emptyList())
                    return@addSnapshotListener
                }

                val sections = snapshot.documents.map { doc ->
                    Section(
                        id = doc.id,
                        name = doc.getString("name") ?: "",
                        plant = doc.getString("plant") ?: "",
                        moisture = doc.getDouble("moisture")?.toFloat() ?: 0f,
                        water = doc.getDouble("water")?.toFloat() ?: 0f
                    )
                }
                onUpdate(sections)
            }
    }
    suspend fun addSection(
        greenhouseId: String,
        name: String,
        plant: String
    ) {
        val sectionData = hashMapOf(
            "name" to name,
            "plant" to plant,
            "moisture" to 0f,
            "water" to 0f
        )

        db.collection("greenhouse")
            .document(greenhouseId)
            .collection("sections")
            .add(sectionData)
            .await()

    }
}
