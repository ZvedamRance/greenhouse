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
                        water = doc.getDouble("water")?.toFloat() ?: 0f
                    )
                }
                onUpdate(sections)
            }
    }
    suspend fun addSection(greenhouseId: String, sectionId: String?, name: String, plant: String, water: Int) {
        val sectionData = hashMapOf(
            "name" to name,
            "plant" to plant,
            "water" to water
        )

        val sectionCollection = db.collection("greenhouse")
            .document(greenhouseId)
            .collection("sections")

        if (sectionId.isNullOrBlank()) {
            sectionCollection.add(sectionData).await()
        } else {
            val docRef = sectionCollection.document(sectionId)
            val snapshot = docRef.get().await()

            if (snapshot.exists()) {
                throw Exception("Sekce s tímto ID už existuje.")
            }
            docRef.set(sectionData).await()
        }
    }

    suspend fun updateSection(
        greenhouseId: String,
        sectionId: String,
        name: String,
        plant: String,
        water: Int
    ) {
        val sectionRef = db.collection("greenhouse")
            .document(greenhouseId)
            .collection("sections")
            .document(sectionId)

        sectionRef.update(
            mapOf(
                "name" to name,
                "plant" to plant,
                "water" to water
            )
        ).await()
    }


     fun getSectionByID(
        greenhouseId: String,
        sectionId: String,
        onUpdate: (Section?) -> Unit
    ) {
        val docRef = db.collection("greenhouse")
            .document(greenhouseId)
            .collection("sections")
            .document(sectionId)

        docRef.addSnapshotListener { snapshot, error ->
            if (error != null || snapshot == null || !snapshot.exists()) {
                onUpdate(null)
                return@addSnapshotListener
            }

            val section = Section(
                id = snapshot.id,
                name = snapshot.getString("name") ?: "",
                plant = snapshot.getString("plant") ?: "",
                water = snapshot.getDouble("water")?.toFloat() ?: 0f
            )

            onUpdate(section)
        }
    }

    suspend fun deleteSection(greenhouseId: String, sectionId: String) {
        db.collection("greenhouse")
            .document(greenhouseId)
            .collection("sections")
            .document(sectionId)
            .delete()
            .await()
    }

    fun observeLatestMoisture(
        greenhouseId: String,
        sectionId: String,
        onUpdate: (Float?) -> Unit
    ) {
        db.collection("greenhouse")
            .document(greenhouseId)
            .collection("sections")
            .document(sectionId)
            .collection("moist_sensor")
            .orderBy("timestamp")
            .limitToLast(1)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null || snapshot.isEmpty) {
                    onUpdate(null)
                    return@addSnapshotListener
                }

                val latest = snapshot.documents.firstOrNull()
                val moisture = latest?.getDouble("moisture")?.toFloat()
                onUpdate(moisture)
            }
    }

}
