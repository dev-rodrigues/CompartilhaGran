package br.edu.compartilhagran.domain.entity

import android.graphics.Bitmap
import com.google.firebase.firestore.DocumentId
import java.util.*

class Annotation(
    @DocumentId
    var id: String ?= null,
    var foreignKey: String ?= null,
    var createdAt: Date?= null,
    var photo: Bitmap ?= null,
    var title: String ?= null,
    var description: String ?= null
)