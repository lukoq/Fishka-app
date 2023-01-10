package com.example.fishka.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "concept_table")
data class Concept(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val frontSide: String,
    val backside: String
): Parcelable