package br.edu.compartilhagran.domain.entity

import com.google.firebase.firestore.DocumentId
import java.util.*

class Annotation(
    @DocumentId
    var id: String ?= null,
    var foreignKey: String ?= null,
    var createdAt: Date?= null,
    var title: String ?= null,
    var description: String ?= null,
    var urlImage: String ?= null,
    var country: String ?= null,
    var region: String ?= null,
    var temperature: Int ?= null,
    var linkImageTemperature: String ?= null,
    var profileImage: String ?= null,
    var userName: String ?= null,
    var nickName: String ?= null
)